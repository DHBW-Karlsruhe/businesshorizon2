package dhbw.ka.mwi.businesshorizon2.demo;

public enum Texts {
    HEADER("Feld"),
    GESAMTLEISTUNG("Gesamtleistung"),
    OPKOSTEN("operative Kosten"),
    ABSCHR("Abschreibungen"),
    ANLAGE("Anlagevermögen"),
    UMLAUF("Umlaufvermögen"),
    EK("Eigenkapital"),
    ZINS_PF_PASSIVA("Zinspflichtiges Passiva"),
    SONST_PASSIVA("Sonstige Passiva"),
    EK_KOSTEN("Eigenkapitalkosten(unverschuldet)"),
    PER_STEUER("persönliche Steuern"),
    JAHRES_UEBERSCHUSS("Jahresüberschuss"),
    STRUKTURBILANZEN("Struktur-Bilanzen"),
    ZINSAUFWAND("Zinsaufwand");

    private final String text;

    Texts(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Texts get(final String text){
        for (final Texts txt : values()) {
            if (txt.toString().equals(text)){
                return txt;
            }
        }
        throw new IllegalArgumentException("Text " + text + " does not exist");
    }

}
