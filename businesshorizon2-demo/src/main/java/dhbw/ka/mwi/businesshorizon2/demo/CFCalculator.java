package dhbw.ka.mwi.businesshorizon2.demo;

import dhbw.ka.mwi.businesshorizon2.cf.*;
import dhbw.ka.mwi.businesshorizon2.demo.converter.ModelToArrayConverter;
import dhbw.ka.mwi.businesshorizon2.demo.ui.CompanyPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.HeaderPanel;
import dhbw.ka.mwi.businesshorizon2.demo.ui.SzenarioPanel;

public class CFCalculator {

    private final HeaderPanel headerPanel;
    private final CompanyPanel companyPanel;
    private final SzenarioPanel szenarioPanel;
    private final CFAlgo algo;

    public CFCalculator(final HeaderPanel headerPanel, final CompanyPanel companyPanel, final SzenarioPanel szenarioPanel, final CFAlgo algo) {
        this.headerPanel = headerPanel;
        this.companyPanel = companyPanel;
        this.szenarioPanel = szenarioPanel;
        this.algo = algo;
    }

    public CFResult calculateDeter(){
        return getAlgo().calculateUWert(getParameter());
    }

    public double[] calculateStochi(){
        /*final Map<Texts,ARModel> modelMap = new EnumMap<>(Texts.class);
        modelMap.put(Texts.FCF,AR.getModel(ModelToArrayConverter.getRow(companyPanel.getModel(),0)));
        modelMap.put(Texts.FK,AR.getModel(ModelToArrayConverter.getRow(companyPanel.getModel(),1)));
        modelMap.put(Texts.ABSCHR,AR.getModel(ModelToArrayConverter.getRow(companyPanel.getModel(),2)));


        return Stochi.doStochi((Integer) headerPanel.getIter().getValue(), () -> {
            final double[] gesamtleistung = modelMap.get(Texts.FCF).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] opkosten = modelMap.get(Texts.FK).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] abschr = modelMap.get(Texts.ABSCHR).predict((Integer) headerPanel.getHorizont().getValue());

            final double[] anlage = modelMap.get(Texts.ANLAGE).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] umlauf = modelMap.get(Texts.UMLAUF).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] ek = modelMap.get(Texts.EK).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] zinsPfPassiva = modelMap.get(Texts.ZINS_PF_PASSIVA).predict((Integer) headerPanel.getHorizont().getValue());
            final double[] sonstPassiva = modelMap.get(Texts.SONST_PASSIVA).predict((Integer) headerPanel.getHorizont().getValue());

            final List<CFPeriode> periodes = new ArrayList<>();
            //System.out.println(Arrays.toString(gesamtleistung));

            periodes.add(new CFPeriode(null,new Bilanz((Double) bilanzPanel.getModel().getValueAt(0, companyPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(1, companyPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(2, companyPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(3, companyPanel.getModel().getColumnCount() - 1),
                    (Double) bilanzPanel.getModel().getValueAt(4, companyPanel.getModel().getColumnCount() - 1)
            )));

            for (int i = 0; i < (Integer) headerPanel.getHorizont().getValue(); i++) {
                periodes.add(new CFPeriode(new GUV(gesamtleistung[i],opkosten[i],abschr[i ]),new Bilanz(anlage[i],umlauf[i],ek[i],zinsPfPassiva[i],sonstPassiva[i])));
            }

            final CFParameter parameter = new CFParameter((Double)szenarioPanel.getEkKosten().getValue(), (Double)szenarioPanel.getFkKosten().getValue(), (Double)szenarioPanel.getuSteusatz().getValue(), (Double)szenarioPanel.getStrukturbilanzen().getValue(), (Double)szenarioPanel.getZinsaufwand().getValue(),periodes);

            return getAlgo().calculate(parameter).getUnternehmenswertNow();
        });*/
        return new double[]{};
    }

    public static double avg(final double[] values){
        double sum = 0;
        for (final double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private CFAlgorithm getAlgo(){
        switch (algo){
            case APV:
                return new APV();
            case FCF:
                return new FCF();
            case FTE:
                return new FTE();
        }
        throw new IllegalArgumentException("This shouldn't happen");
    }

    public CFParameter getParameter(){
        return new CFParameter(ModelToArrayConverter.getRow(companyPanel.getModel(),0),ModelToArrayConverter.getRow(companyPanel.getModel(),1), (Double) szenarioPanel.getEkKosten().getValue(), (Double) szenarioPanel.getuSteusatz().getValue(), (Double) szenarioPanel.getFkKosten().getValue());
    }
}
