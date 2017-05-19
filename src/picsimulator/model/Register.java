package picsimulator.model;

/**
 * Datenmodell zur Abbildung eines Registers.
 */
public class Register implements Cloneable {

    private Bit[] bits;

    /**
     * Konstruktor, welcher dem Register 8 Bits zuordnet und diese initalisiert.
     */
    public Register() {
        this.bits = new Bit[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit(0, 0);
        }
    }

    /**
     * Gibt den Hex-Wert des Registers zurück.
     *
     * @return hexWert als String
     */
    public String getHexWert() {
        StringBuilder builder = new StringBuilder();
        builder.append(bits[7].getPin());
        builder.append(bits[6].getPin());
        builder.append(bits[5].getPin());
        builder.append(bits[4].getPin());
        builder.append(bits[3].getPin());
        builder.append(bits[2].getPin());
        builder.append(bits[1].getPin());
        builder.append(bits[0].getPin());
        int decimal = Integer.parseInt(builder.toString(), 2);
        return String.format("%02X", decimal);
    }

    /**
     * Gibt den Wert des Registers als Integer zurück.
     *
     * @return intWert als Integer
     */
    public int getIntWert() {
        StringBuilder builder = new StringBuilder();
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

    /**
     * Befüllt die Bits des Registers mit dem übergebenen Integer-Wert. Checkt hierbei den
     * Wertebereich
     *
     * @param wert, int
     */
    public void setWert(int wert) {
        resetBits();
        String reverseBinaryString = new StringBuilder(Integer.toBinaryString(wert)).reverse().toString();
        char[] chars = reverseBinaryString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 8) return;
            bits[i].setPin(Character.getNumericValue(chars[i]));
        }
    }

    /**
     * Befüllt die Bits des Registers mit dem übergebenen binary-String. Checkt hierbei den
     * Wertebereich
     *
     * @param binaryString, String
     */
    public void setWert(String binaryString) {
        resetBits();
        String reverseBinaryString = new StringBuilder(binaryString).reverse().toString();
        char[] chars = reverseBinaryString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 8) return;
            bits[i].setPin(Character.getNumericValue(chars[i]));
        }
    }

    /**
     * Setzt alle Bits des Registers auf 0.
     */
    private void resetBits() {
        bits = new Bit[8];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit(0, 0);
        }
    }

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }
}

