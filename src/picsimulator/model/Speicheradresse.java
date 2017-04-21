package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicheradresse {

    private int adresse;
    private Register[] register;

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
        return register[0].getWert();
    }

    public String getRegister1Wert() {
        return register[1].getWert();
    }

    public String getRegister2Wert() {
        return register[2].getWert();
    }

    public String getRegister3Wert() {
        return register[3].getWert();
    }

    public String getRegister4Wert() {
        return register[4].getWert();
    }

    public String getRegister5Wert() {
        return register[5].getWert();
    }

    public String getRegister6Wert() {
        return register[6].getWert();
    }

    public String getRegister7Wert() {
        return register[7].getWert();
    }

}
