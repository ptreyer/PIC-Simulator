package picsimulator.model;

/**
 * Created by ptrey on 26.04.2017.
 */
public class StackEintrag {

    private Bit[] bits;

    public StackEintrag(){
        this.bits = new Bit[13];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit( 0, 0);
        }
    }

    public int getIntWert() {
        StringBuilder builder = new StringBuilder();
        builder.append(bits[7].getPin());
        builder.append(bits[12].getPin());
        builder.append(bits[11].getPin());
        builder.append(bits[10].getPin());
        builder.append(bits[9].getPin());
        builder.append(bits[8].getPin());
        builder.append(bits[7].getPin());
        builder.append(bits[6].getPin());
        builder.append(bits[5].getPin());
        builder.append(bits[4].getPin());
        builder.append(bits[3].getPin());
        builder.append(bits[2].getPin());
        builder.append(bits[1].getPin());
        builder.append(bits[0].getPin());
        return Integer.parseInt(builder.toString(), 2);
    }

    public String getHexWert() {
        StringBuilder builder = new StringBuilder();
        builder.append(bits[12].getPin());
        builder.append(bits[11].getPin());
        builder.append(bits[10].getPin());
        builder.append(bits[9].getPin());
        builder.append(bits[8].getPin());
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

    public void setWert(int wert) {
        resetBits();
        String reverseBinaryString = new StringBuilder(Integer.toBinaryString(wert)).reverse().toString();
        char[] chars = reverseBinaryString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 13) return;
            bits[i].setPin(Character.getNumericValue(chars[i]));
        }
    }

    public void setWert(String binaryString) {
        resetBits();
        String reverseBinaryString = new StringBuilder(binaryString).reverse().toString();
        char[] chars = reverseBinaryString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 13) return;
            bits[i].setPin(Character.getNumericValue(chars[i]));
        }
    }

    private void resetBits() {
        bits = new Bit[13];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit( 0, 0);
        }
    }

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }
}
