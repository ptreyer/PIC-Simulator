package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicheradresse {

    private int adresse;
    private Bit[] bits;

    public Speicheradresse(int adresse) {
        this.adresse = adresse;
        bits = new Bit[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit(Byte.parseByte("00"), 0, 0);
        }
    }

    public String getAdresse() {
        return String.format("%02X ", adresse);
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }

    public String getBit0Wert() {
        return bits[0].getWert();
    }

    public String getBit1Wert() {
        return bits[1].getWert();
    }

    public String getBit2Wert() {
        return bits[2].getWert();
    }

    public String getBit3Wert() {
        return bits[3].getWert();
    }

    public String getBit4Wert() {
        return bits[4].getWert();
    }

    public String getBit5Wert() {
        return bits[5].getWert();
    }

    public String getBit6Wert() {
        return bits[6].getWert();
    }

    public String getBit7Wert() {
        return bits[7].getWert();
    }

}
