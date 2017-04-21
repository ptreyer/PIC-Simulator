package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Register {

    private Bit[] bits;

    public Register(){
        this.bits = new Bit[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit( 0, 0);
        }
    }

    public String getWert() {
        StringBuilder builder = new StringBuilder();
        builder.append(bits[7].getPin());
        builder.append(bits[6].getPin());
        builder.append(bits[5].getPin());
        builder.append(bits[4].getPin());
        builder.append(bits[3].getPin());
        builder.append(bits[2].getPin());
        builder.append(bits[1].getPin());
        builder.append(bits[0].getPin());
        int decimal = Integer.parseInt(builder.toString(),2);
        return Integer.toString(decimal,16).toUpperCase();
    }

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }
}
