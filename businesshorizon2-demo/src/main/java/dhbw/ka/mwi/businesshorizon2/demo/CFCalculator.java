package dhbw.ka.mwi.businesshorizon2.demo;

import dhbw.ka.mwi.businesshorizon2.ar.AR;
import dhbw.ka.mwi.businesshorizon2.ar.model.ARModel;
import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithm;
import dhbw.ka.mwi.businesshorizon2.cf.CFAlgorithms;
import dhbw.ka.mwi.businesshorizon2.cf.CFResult;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.Bilanz;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFParameter;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.CFPeriode;
import dhbw.ka.mwi.businesshorizon2.cf.parameter.GUV;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToArrayConverter;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToBilanzConverter;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToGuvConverter;
import dhbw.ka.mwi.businesshorizon2.demo.ui.BilanzPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.GuvPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.HeaderPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CFCalculator {

    private final HeaderPanel headerPanel;
    private final BilanzPanel bilanzPanel;
    private final GuvPanel guvPanel;
    private final SzenarioPanel szenarioPanel;

    public CFCalculator(final HeaderPanel headerPanel, final BilanzPanel bilanzPanel, final GuvPanel guvPanel, final SzenarioPanel szenarioPanel) {
        this.headerPanel = headerPanel;
        this.bilanzPanel = bilanzPanel;
        this.guvPanel = guvPanel;
        this.szenarioPanel = szenarioPanel;
    }

    public CFResult calculateDeter(){
        return getAlgo().calculate(getParameter());
    }

    public double[] calculateStochi(){
        final Map<Texts,ARModel> modelMap = new EnumMap<>(Texts.class);
        modelMap.put(Texts.GESAMTLEISTUNG,AR.getModel(ModelToArrayConverter.getRow(guvPanel.getModel(),0)));
        modelMap.put(Texts.OPKOSTEN,AR.getModel(ModelToArrayConverter.getRow(guvPanel.getModel(),1)));
        modelMap.put(Texts.ABSCHR,AR.getModel(ModelToArrayConverter.getRow(guvPanel.getModel(),2)));

        modelMap.put(Texts.ANLAGE,AR.getModel(ModelToArrayConverter.getRow(bilanzPanel.getModel(),0)));
        modelMap.put(Texts.UMLAUF,AR.getModel(ModelToArrayConverter.getRow(bilanzPanel.getModel(),1)));
        modelMap.put(Texts.EK,AR.getModel(ModelToArrayConverter.getRow(bilanzPanel.getModel(),2)));
        modelMap.put(Texts.ZINS_PF_PASSIVA,AR.getModel(ModelToArrayConverter.getRow(bilanzPanel.getModel(),3)));
        modelMap.put(Texts.SONST_PASSIVA,AR.getModel(ModelToArrayConverter.getRow(bilanzPanel.getModel(),4)));

        return Stochi.doStochi((Integer) headerPanel.getIter().getValue(), () -> {
            final double[] gesamtleistung = modelMap.get(Texts.GESAMTLEISTUNG).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] opkosten = modelMap.get(Texts.OPKOSTEN).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] abschr = modelMap.get(Texts.ABSCHR).predict((Integer) headerPanel.getHorizont().getValue());

            final double[] anlage = modelMap.get(Texts.ANLAGE).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] umlauf = modelMap.get(Texts.UMLAUF).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] ek = modelMap.get(Texts.EK).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] zinsPfPassiva = modelMap.get(Texts.ZINS_PF_PASSIVA).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] sonstPassiva = modelMap.get(Texts.SONST_PASSIVA).predict((Integer) headerPanel.getHorizont().getValue());

            final List<CFPeriode> periodes = new ArrayList<>();
            //System.out.println(Arrays.toString(gesamtleistung));

            periodes.add(new CFPeriode(null,new Bilanz((Double) bilanzPanel.getModel().getValueAt(0,guvPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(1,guvPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(2,guvPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(3,guvPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(4,guvPanel.getModel().getColumnCount() - 1)
            )));

            for (int i = 0; i < (Integer) headerPanel.getHorizont().getValue(); i++) {
                periodes.add(new CFPeriode(new GUV(gesamtleistung[i],opkosten[i],abschr[i ]),new Bilanz(anlage[i],umlauf[i],ek[i],zinsPfPassiva[i],sonstPassiva[i])));
            }

            final CFParameter parameter = new CFParameter((Double)szenarioPanel.getEkKosten().getValue(), (Double)szenarioPanel.getPerSteuer().getValue(), (Double)szenarioPanel.getJahresUeberschuss().getValue(), (Double)szenarioPanel.getStrukturbilanzen().getValue(), (Double)szenarioPanel.getZinsaufwand().getValue(),periodes);

            return getAlgo().calculate(parameter).getUnternehmenswertNow();
        });
    }

    public static double avg(final double[] values){
        double sum = 0;
        for (final double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private CFAlgorithm getAlgo(){
        switch ((CFAlgo)szenarioPanel.getAlgo().getSelectedItem()){
            case APV:
                return CFAlgorithms.getApvAlgorithm();
            case Entity:
                return CFAlgorithms.getEntityAlgorithm();
            case Equity:
                return CFAlgorithms.getEquityAlgorithm();
        }
        throw new IllegalArgumentException("This shouldn't happen");
    }

    private CFParameter getParameter(){
        final List<Bilanz> bilanzs = ModelToBilanzConverter.convert(bilanzPanel.getModel());
        final List<GUV> guvs = ModelToGuvConverter.convert(guvPanel.getModel());
        final List<CFPeriode> periodes = IntStream.range(0, guvs.size()).mapToObj(i -> new CFPeriode(guvs.get(i), bilanzs.get(i))).collect(Collectors.toList());
        return new CFParameter((Double)szenarioPanel.getEkKosten().getValue(), (Double)szenarioPanel.getPerSteuer().getValue(), (Double)szenarioPanel.getJahresUeberschuss().getValue(), (Double)szenarioPanel.getStrukturbilanzen().getValue(), (Double)szenarioPanel.getZinsaufwand().getValue(),periodes);
    }
}
