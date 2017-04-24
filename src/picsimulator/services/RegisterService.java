package picsimulator.services;

import picsimulator.model.Bit;

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


}
