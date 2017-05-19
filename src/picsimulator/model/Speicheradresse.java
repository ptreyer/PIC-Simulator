package picsimulator.model;

/**
 * Stellt das Datenobjekt der Speicheradresse dar. Dies beinhält jeweils 8 Register.
 */
public class Speicheradresse {

    private int adresse;
    private Register[] register;

    /**
     * Konstruktor zur Initialisierung der Speicheradresse. Setzt die Adresse und initialisiert
     * die Register.
     *
     * @param adresse, die Adresse des Registers.
     */
    public Speicheradresse(int adresse) {
        this.adresse = adresse;
        register = new Register[8];
        for (int i = 0; i < register.length; i++) {
            register[i] = new Register();
        }
    }

    public String getAdresse() {
        return String.format("%02X ", adresse);
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public Register[] getRegister() {
        return register;
    }

    public void setRegister(Register[] register) {
        this.register = register;
    }

    public String getRegister0Wert() {
        return register[0].getHexWert();
    }

    public String getRegister1Wert() {
        return register[1].getHexWert();
    }

    public String getRegister2Wert() {
        return register[2].getHexWert();
    }

    public String getRegister3Wert() {
        return register[3].getHexWert();
    }

    public String getRegister4Wert() {
        return register[4].getHexWert();
    }

    public String getRegister5Wert() {
        return register[5].getHexWert();
    }

    public String getRegister6Wert() {
        return register[6].getHexWert();
    }

    public String getRegister7Wert() {
        return register[7].getHexWert();
    }

}
