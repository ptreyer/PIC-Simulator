package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class ADDLW extends Operation implements Executable {

    public ADDLW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String literal = binaryString.substring(opcodeBits);
        int registerW = memory.getRegisterW() + getRegisterService().binToInt(literal);
        memory.setRegisterW(registerW);
        if(registerW>255){
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(1);
        }else{
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(0);
        }
        if (registerW)
        if(registerW==0){
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        }else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        increaseProgrammCounter();
        return memory;
    }

}
