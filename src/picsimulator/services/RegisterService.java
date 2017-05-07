package picsimulator.services;

import picsimulator.model.Bit;

import java.math.BigInteger;

/**
 * Created by ptrey on 24.04.2017.
 */
public class RegisterService {

    public Bit toggleBit (Bit bit) {
        Bit result = new Bit(0,0);
        if(bit.getPin() == 0){
            result.setPin(1);
        }else{
            result.setPin(0);
        }
        return result;
    }

    public String hexToBin(String hex) {
        String value = new BigInteger(hex, 16).toString(2);
        String formatPad = "%" + (14) + "s";
        return String.format(formatPad, value).replace(" ", "0");
    }

    public String hexToBinNoLeadingZeros(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return bin;
    }

    public int hexToInt(String hex){
        return Integer.parseInt(hex, 16);
    }

    public String intToBin(int wert){
        return Integer.toBinaryString(wert);
    }

    public int binToInt(String binary){
        return Integer.parseInt(binary, 2);
    }

    public String intToHex(int wert){
        return String.format("%02X", wert);
    }


    public String leftPad32(String input){
        StringBuffer buf = new StringBuffer(input);
        while (buf.length() < 32) {
            buf.insert(0, '0');
        }
        return buf.toString();
    }

}
