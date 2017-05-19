package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * CLRF
 */
public class CLRF extends Operation implements Executable {

    public CLRF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String registerAdress = binaryString.substring(opcodeBits);
        memory.getFileRegister(getRegisterService().binToInt(registerAdress), true).setWert(0);

        /**
         *  Set Zero Flag
         */
        memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
