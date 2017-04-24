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
        return new BigInteger(hex, 16).toString(2);
    }


}
