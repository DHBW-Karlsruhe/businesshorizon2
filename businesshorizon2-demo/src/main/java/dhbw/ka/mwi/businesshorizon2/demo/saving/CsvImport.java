package dhbw.ka.mwi.businesshorizon2.demo.saving;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;
import dhbw.ka.mwi.businesshorizon2.demo.models.BilanzModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.models.GuvModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public final class CsvImport {

    private CsvImport() {
    }

    public static void importSzenario(final SzenarioPanel szenarioPanel, final File file) throws IOException {
        final Iterable<CSVRecord> records = CSVParser.parse(file, Charset.forName("ISO-8859-1"), CSVFormat.DEFAULT.withDelimiter(';'));

        for (final CSVRecord record : records) {
            switch (Texts.get(record.get(0))) {
                case EK_KOSTEN:
                    szenarioPanel.getEkKosten().setValue(Double.parseDouble(record.get(1)) / 100);
                    break;
                case PER_STEUER:
                    szenarioPanel.getPerSteuer().setValue(Double.parseDouble(record.get(1)) / 100);
                    break;
                case JAHRES_UEBERSCHUSS:
                    szenarioPanel.getJahresUeberschuss().setValue(Double.parseDouble(record.get(1)) / 100);
                    break;
                case STRUKTURBILANZEN:
                    szenarioPanel.getStrukturbilanzen().setValue(Double.parseDouble(record.get(1)) / 100);
                    break;
                case ZINSAUFWAND:
                    szenarioPanel.getZinsaufwand().setValue(Double.parseDouble(record.get(1)) / 100);
                    break;

            }
        }
    }

    private static Object entryIsDoubleOrString(final String entry){
        try {
            return Double.parseDouble(entry);
        } catch (final NumberFormatException e){
            return entry;
        }
    }

    private static void checkHeader(final CSVRecord record, final int perioden, final int basisjahr, final CFMode mode){
        if (record.size() != perioden + 1){
            throw new IllegalArgumentException("Anzahl Perioden stimmt nicht mit Selektion überein");
        }
        switch (mode){
            case STOCHI:
                if(!record.get(record.size() - 1).equals(String.valueOf(basisjahr))){
                    throw new IllegalArgumentException("Basisjahr stimmt nicht mit Selektion überein");
                }
                break;
            case DETER:
                if(!record.get(1).equals(String.valueOf(basisjahr))){
                    throw new IllegalArgumentException("Basisjahr stimmt nicht mit Selektion überein");
                }
                break;
        }

    }

    public static TableModel importGuv(final File file, final int perioden, final int basisjahr, final CFMode mode) throws IOException {
        final Iterable<CSVRecord> records = CSVParser.parse(file, Charset.forName("ISO-8859-1"), CSVFormat.DEFAULT.withDelimiter(';'));
        TableModel model = null;

        for (final CSVRecord record : records) {
            switch (Texts.get(record.get(0))) {
                case HEADER:
                    checkHeader(record,perioden,basisjahr,mode);
                    model = GuvModelProvider.getModel(basisjahr,perioden,mode);
                    break;
                case GESAMTLEISTUNG:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(entryIsDoubleOrString(record.get(i)), 0, i);
                    }
                    break;
                case OPKOSTEN:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(entryIsDoubleOrString(record.get(i)), 1, i);
                    }
                    break;
                case ABSCHR:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(entryIsDoubleOrString(record.get(i)), 2, i);
                    }
                    break;
            }
        }
        return model;
    }


    public static TableModel importBilanz(final File file, final int perioden, final int basisjahr, final CFMode mode) throws IOException {
        final Iterable<CSVRecord> records = CSVParser.parse(file, Charset.forName("ISO-8859-1"), CSVFormat.DEFAULT.withDelimiter(';'));
        TableModel model = null;

        for (final CSVRecord record : records) {
            switch (Texts.get(record.get(0))) {
                case HEADER:
                    checkHeader(record,perioden,basisjahr,mode);
                    model = BilanzModelProvider.getModel(basisjahr,perioden,mode);
                    break;
                case ANLAGE:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(Double.parseDouble(record.get(i)), 0, i);
                    }
                    break;
                case UMLAUF:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(Double.parseDouble(record.get(i)), 1, i);
                    }
                    break;
                case EK:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(Double.parseDouble(record.get(i)), 2, i);
                    }
                    break;
                case ZINS_PF_PASSIVA:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(Double.parseDouble(record.get(i)), 3, i);
                    }
                    break;
                case SONST_PASSIVA:
                    for (int i = 1; i < record.size(); i++) {
                        model.setValueAt(Double.parseDouble(record.get(i)), 4, i);
                    }
                    break;
            }
        }
        return model;
    }

}
