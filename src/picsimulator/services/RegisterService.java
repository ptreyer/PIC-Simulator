package picsimulator.services;

import picsimulator.model.Bit;

import java.math.BigInteger;

/**
 * Created by ptrey on 24.04.2017.
 */
public class RegisterService {

    public Bit toggleBit (Bit bit) {
        if(bit.getPin() == 0){
            bit.setPin(1);
        }else{
            bit.setPin(0);
        }
        return bit;
    }

    public String hexToBin(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return bin;
    }


}
