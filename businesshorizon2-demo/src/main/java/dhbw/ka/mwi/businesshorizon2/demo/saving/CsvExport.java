package dhbw.ka.mwi.businesshorizon2.demo.saving;

import dhbw.ka.mwi.businesshorizon2.demo.Texts;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

public final class CsvExport {

    private CsvExport() {
    }

    private static String[] getHeaders(final TableModel model){
        final String[] headers = new String[model.getColumnCount()];
        for (int i = 0; i < headers.length; i++) {
            headers[i] = model.getColumnName(i);
        }
        return headers;
    }

    public static void exportGuv(final TableModel model, final File file) throws IOException {
        try (FileOutputStream fw = new FileOutputStream(file); OutputStreamWriter osw = new OutputStreamWriter(fw,Charset.forName("ISO-8859-1")); CSVPrinter printer = CSVFormat.DEFAULT.withDelimiter(';').withHeader(getHeaders(model)).print(osw) ) {

            for (int row = 0; row < model.getRowCount(); row++) {
                final Collection<Object> vals = new ArrayList<>();
                for (int col = 0; col < model.getColumnCount(); col++) {
                    vals.add(model.getValueAt(row, col));
                }
                printer.printRecord(vals);
            }
        }
    }


    public static void exportBilanz(final TableModel model, final File file) throws IOException {
        try (FileOutputStream fw = new FileOutputStream(file); OutputStreamWriter osw = new OutputStreamWriter(fw,Charset.forName("ISO-8859-1")); CSVPrinter printer = CSVFormat.DEFAULT.withDelimiter(';').withHeader(getHeaders(model)).print(osw) ) {

            for (int row = 0; row < model.getRowCount(); row++) {
                final Collection<Object> vals = new ArrayList<>();
                for (int col = 0; col < model.getColumnCount(); col++) {
                    vals.add(model.getValueAt(row, col));
                }
                printer.printRecord(vals);
            }
        }
    }

    public static void exportScenario(final SzenarioPanel szenarioPanel, final File file) throws IOException{
        try (FileOutputStream fw = new FileOutputStream(file); OutputStreamWriter osw = new OutputStreamWriter(fw,Charset.forName("ISO-8859-1")); CSVPrinter printer = CSVFormat.DEFAULT.withDelimiter(';').withHeader(new String[]{Texts.HEADER.toString(),"Wert"}).print(osw) ) {
            printer.printRecord(Texts.EK_KOSTEN, (Double) szenarioPanel.getEkKosten().getValue() * 100);
            printer.printRecord(Texts.FK_KOSTEN, (Double) szenarioPanel.getFkKosten().getValue() * 100);
            printer.printRecord(Texts.STEUSATZ, (Double) szenarioPanel.getuSteusatz().getValue() * 100);
        }
    }
}
