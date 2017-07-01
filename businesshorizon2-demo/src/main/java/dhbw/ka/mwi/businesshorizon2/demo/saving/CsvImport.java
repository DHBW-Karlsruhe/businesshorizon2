package dhbw.ka.mwi.businesshorizon2.demo.saving;

import dhbw.ka.mwi.businesshorizon2.demo.CFMode;
import dhbw.ka.mwi.businesshorizon2.demo.FCFMode;
import dhbw.ka.mwi.businesshorizon2.demo.Texts;
import dhbw.ka.mwi.businesshorizon2.demo.models.CompanyModelProvider;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.HeaderPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public final class CsvImport {

    private CsvImport() {
    }

    public static void importCSV(final File file, final HeaderPanel headerPanel, final CompanyPanel companyPanel, final SzenarioPanel szenarioPanel) throws IOException {
        try (CSVParser parser = CSVParser.parse(file, Charset.forName("ISO-8859-1"), CSVFormat.DEFAULT.withDelimiter(';'))) {
            final List<CSVRecord> records = parser.getRecords();
            final CSVRecord modus = records.get(0);
            assertHeader(modus, Texts.MODUS);
            final CFMode mod = CFMode.valueOf(modus.get(1));
            switch (mod) {
                case STOCHI:
                    headerPanel.getStochi().setSelected(true);
                    break;
                case DETER:
                    headerPanel.getDeter().setSelected(true);
                    break;
            }

            final CSVRecord jahr = records.get(1);
            assertHeader(jahr, Texts.BASISJAHR);
            headerPanel.getBasisjahr().setValue(Integer.parseInt(jahr.get(1)));

            final CSVRecord perioden = records.get(2);
            assertHeader(perioden, Texts.PERIODEN);
            headerPanel.getPerioden().setValue(Integer.parseInt(perioden.get(1)));

            final DefaultTableModel model = CompanyModelProvider.getModel((Integer) headerPanel.getBasisjahr().getValue(), (Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode(), companyPanel.getDetailMode());

            final CSVRecord fcf = records.get(3);
            assertHeader(fcf, Texts.FCF);
            if (fcf.size() - 1 != (Integer) headerPanel.getPerioden().getValue()) {
                throw new IllegalArgumentException("Perioden sollte " + headerPanel.getPerioden().getValue() + " sein, es wurden aber " + (fcf.size() - 1) + " FCFs angegeben");
            }

            for (int i = 1; i < fcf.size(); i++) {
                model.setValueAt(Double.parseDouble(fcf.get(i)), 0, i);
            }

            final CSVRecord fk = records.get(4);
            assertHeader(fk, Texts.FK);
            if (fk.size() - 1 != (Integer) headerPanel.getPerioden().getValue()) {
                throw new IllegalArgumentException("Perioden sollte " + headerPanel.getPerioden().getValue() + " sein, es wurden aber " + (fk.size() - 1) + " FKs angegeben");
            }
            for (int i = 1; i < fk.size(); i++) {
                model.setValueAt(Double.parseDouble(fk.get(i)), 1, i);
            }

            companyPanel.setModel(model);

            final CSVRecord details = records.get(5);
            assertHeader(details, Texts.DETAILS);
            final int numDetails = Integer.parseInt(details.get(1));
            companyPanel.getDetailButton().setSelected(numDetails > 0);
            final DefaultTableModel detailModel = CompanyModelProvider.getDetailModel((Integer) headerPanel.getBasisjahr().getValue(), (Integer) headerPanel.getPerioden().getValue(), headerPanel.getCurrentMode());

            for (int i = 0; i < numDetails; i++) {
                final CSVRecord detailRow = records.get(6 + i);

                if (detailRow.size() - 2 != (Integer) headerPanel.getPerioden().getValue() - (headerPanel.getCurrentMode() == CFMode.DETER ? 1 : 0)) {
                    throw new IllegalArgumentException("Perioden sollte " + ((Integer) headerPanel.getPerioden().getValue() - (headerPanel.getCurrentMode() == CFMode.DETER ? 1 : 0)) + " sein, es wurden aber " + (detailRow.size() - 2) + " FCFs angegeben");
                }

                detailModel.setValueAt(detailRow.get(0), i, 0);
                detailModel.setValueAt(FCFMode.valueOf(detailRow.get(1)), i, 1);

                for (int j = 2; j < detailRow.size(); j++) {
                    detailModel.setValueAt(Double.parseDouble(detailRow.get(j)), i, j);
                }
            }

            companyPanel.setDetailModel(detailModel);

            final CSVRecord ekKost = records.get(6 + numDetails);
            assertHeader(ekKost, Texts.EK_KOSTEN);
            szenarioPanel.getEkKosten().setValue(Double.parseDouble(ekKost.get(1)) / 100);

            final CSVRecord fkKost = records.get(7 + numDetails);
            assertHeader(fkKost, Texts.FK_KOSTEN);
            szenarioPanel.getFkKosten().setValue(Double.parseDouble(fkKost.get(1)) / 100);

            final CSVRecord steuSatz = records.get(8 + numDetails);
            assertHeader(steuSatz, Texts.STEUSATZ);
            szenarioPanel.getuSteusatz().setValue(Double.parseDouble(steuSatz.get(1)) / 100);
        }
    }

    private static void assertHeader(final CSVRecord record, final Texts header) {
        if (!record.get(0).equals(header.toString())) {
            throw new IllegalArgumentException("Zeile sollte " + header + " sein, war aber " + record.get(0));
        }
    }

}
