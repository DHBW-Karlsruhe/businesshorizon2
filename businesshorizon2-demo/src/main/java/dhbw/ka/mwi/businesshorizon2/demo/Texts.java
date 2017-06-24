package dhbw.ka.mwi.businesshorizon2.demo;

public enum Texts {
    HEADER("Feld"),
    FCF("FCF"),
    FK("Fremdkapital"),
    EK_KOSTEN("Eigenkapitalkosten(unverschuldet)"),
    FK_KOSTEN("Fremdkapitalkosten"),
    STEUSATZ("Steuersatz"),
    MODUS("Modus"),
    BASISJAHR("Basisjahr"),
    PERIODEN("Perioden"),
    DETAILS("Cashflows");

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
