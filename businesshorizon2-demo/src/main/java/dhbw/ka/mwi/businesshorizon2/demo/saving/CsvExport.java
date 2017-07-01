package dhbw.ka.mwi.businesshorizon2.demo.saving;

import dhbw.ka.mwi.businesshorizon2.demo.Texts;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.HeaderPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

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

    public static void export(final HeaderPanel headerPanel, final SzenarioPanel szenarioPanel, final CompanyPanel companyPanel, final File file) throws IOException {
        try (FileOutputStream fw = new FileOutputStream(file); OutputStreamWriter osw = new OutputStreamWriter(fw, Charset.forName("ISO-8859-1")); CSVPrinter printer = CSVFormat.DEFAULT.withDelimiter(';').print(osw)) {

            printer.printRecord(Texts.MODUS, headerPanel.getCurrentMode());
            printer.printRecord(Texts.BASISJAHR, headerPanel.getBasisjahr().getValue());
            printer.printRecord(Texts.PERIODEN, headerPanel.getPerioden().getValue());

            for (int row = 0; row < companyPanel.getModel().getRowCount(); row++) {
                final Collection<Object> vals = new ArrayList<>();
                for (int col = 0; col < companyPanel.getModel().getColumnCount(); col++) {
                    vals.add(companyPanel.getModel().getValueAt(row, col));
                }
                printer.printRecord(vals);
            }

            if (companyPanel.getDetailMode().get()) {
                printer.printRecord(Texts.DETAILS, companyPanel.getDetailModel().getRowCount());
                for (int row = 0; row < companyPanel.getDetailModel().getRowCount(); row++) {
                    final Collection<Object> vals = new ArrayList<>();
                    for (int col = 0; col < companyPanel.getDetailModel().getColumnCount(); col++) {
                        vals.add(companyPanel.getDetailModel().getValueAt(row, col));
                    }
                    printer.printRecord(vals);
                }
            } else {
                printer.printRecord(Texts.DETAILS, 0);
            }

            printer.printRecord(Texts.EK_KOSTEN, (Double) szenarioPanel.getEkKosten().getValue() * 100);
            printer.printRecord(Texts.FK_KOSTEN, (Double) szenarioPanel.getFkKosten().getValue() * 100);
            printer.printRecord(Texts.STEUSATZ, (Double) szenarioPanel.getuSteusatz().getValue() * 100);
        }
    }

    public static void exportResults(final File file, final double[] uWerts) throws IOException {
        try (FileOutputStream fw = new FileOutputStream(file); OutputStreamWriter osw = new OutputStreamWriter(fw, Charset.forName("ISO-8859-1")); CSVPrinter printer = CSVFormat.DEFAULT.withDelimiter(';').print(osw)) {
            for (final double uWert : uWerts) {
                printer.printRecord(uWert);
            }
        }
    }

}
