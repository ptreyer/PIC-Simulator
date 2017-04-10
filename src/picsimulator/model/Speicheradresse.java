package picsimulator.model;

import static javafx.scene.input.KeyCode.H;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicheradresse {

    private byte adresse;
    private Bit[] bits;

    public Speicheradresse(int adresse){
        this.adresse = Byte.parseByte(Integer.toHexString(adresse));
        bits = new Bit[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit(Byte.parseByte("00"), 0, 0);
        }
    }

    public String getAdresse() {
        return String.format("%02X ", adresse);
    }

    public void setAdresse(byte adresse) {
        this.adresse = adresse;
    }

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }
}
