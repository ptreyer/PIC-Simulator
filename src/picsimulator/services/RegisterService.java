package picsimulator.services;

import picsimulator.model.Bit;

import java.math.BigInteger;

/**
 * Service, welcher nützliche Funktionen für Register bereitstellt.
 */
public class RegisterService {

    /**
     * Toggelt das übergebene Bit
     *
     * @param bit, das zu toggelnde Bit
     * @return Bit, das getoggelte Bit,
     */
    public Bit toggleBit(Bit bit) {
        Bit result = new Bit(0, 0);
        if (bit.getPin() == 0) {
            result.setPin(1);
        } else {
            result.setPin(0);
        }
        return result;
    }

    /**
     * Wandelt einen Hex-String in einen Binary-String um mit führenden Nullen.
     *
     * @param hex, der hex-String
     * @return String, der binary String
     */
    public String hexToBin(String hex) {
        String value = new BigInteger(hex, 16).toString(2);
        String formatPad = "%" + (14) + "s";
        return String.format(formatPad, value).replace(" ", "0");
    }

    /**
     * Wandelt einen Hex-String in einen Binary-String um ohne führende Nullen.
     *
     * @param hex, der hex-String
     * @return String, der binary String
     */
    public String hexToBinNoLeadingZeros(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return bin;
    }

    /**
     * Wandelt einen Hex-String in einen Integer um.
     *
     * @param hex, der hex-String
     * @return int, der entsprechende Integer-Wert.
     */
    public int hexToInt(String hex) {
        return Integer.parseInt(hex, 16);
    }

    /**
     * Wandelt einen Integer in einen Binary-String um.
     *
     * @param wert, der Integer-Wert
     * @return String, der entsprechende Binary-String.
     */
    public String intToBin(int wert) {
        return Integer.toBinaryString(wert);
    }

    /**
     * Wandelt einen binary-String in einen Integer um.
     *
     * @param binary, der binary-String
     * @return int, der entsprechende Integer-Wert.
     */
    public int binToInt(String binary) {
        return Integer.parseInt(binary, 2);
    }

    /**
     * Wandelt einen Integer in einen Hex-String um.
     *
     * @param wert, der Integer-Wert
     * @return String, der entsprechende Hex-String.
     */
    public String intToHex(int wert) {
        return String.format("%02X", wert);
    }

    /**
     * Füllt einen String mit führenden Nullen auf.
     *
     * @param input, der aufzufüllenden Input-String.
     * @return der aufgefüllte Input-String.
     */
    public String leftPad32(String input) {
        StringBuffer buf = new StringBuffer(input);
        while (buf.length() < 32) {
            buf.insert(0, '0');
        }
        return buf.toString();
    }

}
