/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.scenarioscreen;

import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Szenario;

import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;

import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ValidationEvent;

import dhbw.ka.mwi.businesshorizon2.ui.process.IllegalValueException;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe des
 * Berechnungsszenarios.
 * 
 * @author Julius Hacker, Tobias Lindner
 * 
 */

public class ScenarioScreenPresenter extends ScreenPresenter<ScenarioScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ScenarioScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;
	
	private boolean showErrors = false;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}
	
//	public void getBerechnungMethod () {
//		project = projectProxy.getSelectedProject();
//	}

	/**
	 * Diese Methode ueberprueft, ob die im Screen getaetigten Eingaben valide
	 * und somit korrekt sind. Er nutzt hierbei die Validierungsmethoden zur
	 * Ueberpruefung der einzelnen Dateneingabefelder.
	 * 
	 * @author Julius Hacker, Tobias Lindner
	 * @return true: Die Eingabewerte der Maske sind insgesamt korrekt false:
	 *         Die Eingabewerte der Maske sind an mindestens einer Stelle nicht
	 *         korrekt.
	 */
	@Override
	public boolean isValid() {
		boolean isValid = true;

		List<Szenario> scenarios = this.projectProxy.getSelectedProject()
				.getScenarios();

		int scenarioNumber = 1;
		for (Szenario scenario : scenarios) {
			if (scenario.isIncludeInCalculation()) {
				if (!isValidCorporateAndSolitaryTax(scenarioNumber)
						|| !isValidBusinessTax(scenarioNumber)
						|| !isValidRateReturnCapitalStock(scenarioNumber)
						|| !isValidRateReturnEquity(scenarioNumber)) {
					isValid = false;
				}
			}

			scenarioNumber++;
		}

		//ValidationEvent mit entsprechendem Übergabewert werfen
		
		if (isValid) {
			eventBus.fireEvent(new ValidationEvent(true));
			logger.debug("Validationevent(true) geworfen.");
			return isValid;
		}
		else {
			eventBus.fireEvent(new ValidationEvent(false));
			logger.debug("Validationevent(false) geworfen.");
			return isValid;
		}
	}

	/**
	 * Diese Methode fuegt dem Projekt ein neues Szenario hinzu und zeigt die
	 * dazugehoerigen Eingabefelder auf dem Screen an.
	 * 
	 * @author Julius Hacker, Tobias Lindner
	 */
	public void addScenario() {
		Szenario scenario = new Szenario(0.0, 0.0, 0.0, 0.0, 0.0, true);
		this.projectProxy.getSelectedProject().addScenario(scenario);
		getView().addScenario(Double.toString(scenario.getRateReturnEquity()),
				Double.toString(scenario.getRateReturnCapitalStock()),
				Double.toString(scenario.getCorporateAndSolitaryTax()),
				Double.toString(scenario.getBusinessTax()),
				scenario.isIncludeInCalculation(),
				this.projectProxy.getSelectedProject().getScenarios().size());
		
		//Szenarioseite aktualisieren
		eventBus.fireEvent(new ShowScenarioViewEvent());
		
		//Event, dass den "Weiter"-Button ausgraut, sodass der Nutzer zuerst valide Eingaben für das neue Szenario machen muss
		eventBus.fireEvent(new ValidationEvent(false));
	}

	/**
	 * Diese Methode entfernt ein Szenario und aktualisiert den Screen.
	 * 
	 * @author Tobias Lindner
	 * @param number Nummer des zu löschenden Szenarios
	 */
	public void removeScenario(int number) {
		logger.debug("Es gibt " + this.projectProxy.getSelectedProject().getScenarios().size() + " Szenarios");
		logger.debug("Lösche Szenario Nummer: " + number);
		this.projectProxy.getSelectedProject().getScenarios()
				.remove(number - 1);
		getView().removeScenario(number - 1);
		getView().updateLabels();
		
		//Szenarioseite aktualisieren
		eventBus.fireEvent(new ShowProcessStepEvent(screen.SCENARIOS));
	}

	/**
	 * Dieser EventHandler reagiert auf die Nachricht, dass der Prozesschritt
	 * zur Szenarienanzeige angezeigt werden soll. Er legt hierbei, falls noch
	 * kein Szenario existiert, zunaechst das im Fachkonzept angegebene
	 * Standardszenario an.
	 * 
	 * @author Tobias Lindner
	 */
	@EventHandler
	public void handleShowView(ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.SCENARIOS)) {
			List<Szenario> scenarios = this.projectProxy.getSelectedProject()
					.getScenarios();
			if (scenarios.size() < 1) {
				scenarios.add(new Szenario(0.09969137, 0.08, 0.14, 0.15825, 0.26375, true));
			}
	
			getView().clear();
	
			int numberOfScenario = 1;
			for (Szenario scenario : scenarios) {
				getView().addScenario(
						Double.toString(scenario.getRateReturnEquity()),
						Double.toString(scenario.getRateReturnCapitalStock()),
						Double.toString(scenario.getCorporateAndSolitaryTax()),
						Double.toString(scenario.getBusinessTax()),
						scenario.isIncludeInCalculation(), numberOfScenario);
				numberOfScenario++;
			}	
		}
	}
	
//	/**
//	 * Dieser Handler reagiert auf die Nachricht, dass der Prozesschritt seinen
//	 * Inhalt validieren soll. Er bedient sich dabei der Methode isValid und
//	 * setzt den Status des Screens entsprechend des Ergebnisses auf valide oder
//	 * invalide.
//	 * 
//	 * @author Julius Hacker
//	 */
//	@Override
//	@EventHandler
//	public void validate(ValidateContentStateEvent event) {
//		logger.debug(this.isValid());
//		if (!this.isValid()) {
//			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.SCENARIO,
//					this.showErrors));
//			logger.debug("Scenario not valid, InvalidStateEvent fired");
//		} else {
//			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.SCENARIO));
//			logger.debug("Scenario valid, ValidStateEvent fired");
//		}
//	}

	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur
	 * Renditeforderung Eigenkapital korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig. false: Eingabe ist nicht
	 *         korrekt.
	 */
	public boolean isValidRateReturnEquity(int scenarioNumber) {
		boolean isValid = true;

		try {
			Double rateReturnEquity = Double.parseDouble(getView().getValue(
					scenarioNumber, "rateReturnEquity"));

			if (rateReturnEquity < 0 || rateReturnEquity > 100) {
				throw new IllegalValueException(
						"corporateAndSolitaryTax nicht zwischen 0 und 100");
			}

			getView().setValid(scenarioNumber, "rateReturnEquity");
		} catch (Exception exception) {
			if (showErrors) {
				getView().setInvalid(scenarioNumber, "rateReturnEquity");
			}
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur
	 * Renditeforderung Fremdkapital korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig. false: Eingabe ist nicht
	 *         korrekt.
	 */
	public boolean isValidRateReturnCapitalStock(int scenarioNumber) {
		boolean isValid = true;

		try {
			Double rateReturnCapitalStock = Double.parseDouble(getView()
					.getValue(scenarioNumber, "rateReturnCapitalStock"));

			if (rateReturnCapitalStock < 0 || rateReturnCapitalStock > 100) {
				throw new IllegalValueException(
						"corporateAndSolitaryTax nicht zwischen 0 und 100");
			}

			getView().setValid(scenarioNumber, "rateReturnCapitalStock");
		} catch (Exception exception) {
			if (showErrors) {
				getView().setInvalid(scenarioNumber, "rateReturnCapitalStock");
			}
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur
	 * Gewerbesteuer korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig. false: Eingabe ist nicht
	 *         korrekt.
	 */
	public boolean isValidBusinessTax(int scenarioNumber) {
		boolean isValid = true;

		try {
			Double businessTax = Double.parseDouble(getView().getValue(
					scenarioNumber, "businessTax"));

			if (businessTax < 0 || businessTax > 100) {
				throw new IllegalValueException(
						"corporateAndSolitaryTax nicht zwischen 0 und 100");
			}

			getView().setValid(scenarioNumber, "businessTax");

		} catch (Exception exception) {
			if (showErrors) {
				getView().setInvalid(scenarioNumber, "businessTax");
			}
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur
	 * Koerperschaftssteuer mit Solidaritaetszuschlag korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig. false: Eingabe ist nicht
	 *         korrekt.
	 */
	public boolean isValidCorporateAndSolitaryTax(int scenarioNumber) {
		boolean isValid = true;

		try {
			Double corporateAndSolitaryTax = Double.parseDouble(getView()
					.getValue(scenarioNumber, "corporateAndSolitaryTax"));

			if (corporateAndSolitaryTax < 0 || corporateAndSolitaryTax > 100) {
				throw new IllegalValueException(
						"corporateAndSolitaryTax nicht zwischen 0 und 100");
			}

			getView().setValid(scenarioNumber, "corporateAndSolitaryTax");
		} catch (Exception exception) {
			if (showErrors) {
				getView().setInvalid(scenarioNumber, "corporateAndSolitaryTax");
			}
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Diese Methode holt sich die in den Eingabefeldern der View eingetragenen
	 * Werte und speichert sie nach einer Validierung im Szenarien-Model. Die
	 * Methode wird insbesondere durch die EventHandler der View genutzt, die
	 * auf entsprechende Textaenderungen reagieren.
	 * 
	 * @author Julius Hacker
	 * @param scenarioNumber
	 *            Nummer des Szenarios, dessen Werte geaendert wurden.
	 */
	public void updateScenario(int scenarioNumber) {
		Szenario scenario = this.projectProxy.getSelectedProject()
				.getScenarios().get(scenarioNumber - 1);

		if (isValidRateReturnEquity(scenarioNumber)) {
			scenario.setRateReturnEquity(Double.parseDouble(getView().getValue(
					scenarioNumber, "rateReturnEquity")));
			logger.debug("Renditeforderung Eigenkapital Szenario "
					+ scenarioNumber + " auf " + scenario.getRateReturnEquity()
					+ " ("
					+ getView().getValue(scenarioNumber, "rateReturnEquity")
					+ ")");
		}

		if (isValidRateReturnCapitalStock(scenarioNumber)) {
			scenario.setRateReturnCapitalStock(Double.parseDouble(getView()
					.getValue(scenarioNumber, "rateReturnCapitalStock")));
			logger.debug("Renditeforderung Fremdkapital Szenario "
					+ scenarioNumber
					+ " auf "
					+ scenario.getRateReturnCapitalStock()
					+ " ("
					+ getView().getValue(scenarioNumber,
							"rateReturnCapitalStock") + ")");
		}

		if (isValidBusinessTax(scenarioNumber)) {
			scenario.setBusinessTax(Double.parseDouble(getView().getValue(
					scenarioNumber, "businessTax")));
			logger.debug("Gewerbesteuer Szenario " + scenarioNumber + " auf "
					+ scenario.getBusinessTax() + " ("
					+ getView().getValue(scenarioNumber, "businessTax") + ")");
		}

		if (isValidCorporateAndSolitaryTax(scenarioNumber)) {
			scenario.setCorporateAndSolitaryTax(Double.parseDouble(getView()
					.getValue(scenarioNumber, "corporateAndSolitaryTax")));
			logger.debug("Koerperschaftssteuer und Solidaritaetszuschlag Szenario "
					+ scenarioNumber
					+ " auf "
					+ scenario.getCorporateAndSolitaryTax()
					+ " ("
					+ getView().getValue(scenarioNumber,
							"corporateAndSolitaryTax") + ")");
		}

		scenario.setIncludeInCalculation(getView().getIncludeInCalculation(
				scenarioNumber));
	}

	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub
		
	}
	
//	public boolean isShowErrors() {
//		return showErrors;
//	}

}
