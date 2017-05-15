package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class RETLW extends Operation implements Executable {

    public RETLW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String literal = binaryString.substring(opcodeBits);
        memory.setRegisterW(getRegisterService().binToInt(literal));
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(memory.getFromStack().getIntWert());

        Controller.increaseRuntime();
        increaseProgrammCounter();
        memory.setCalculateProgramCounter(false);
        return memory;
    }
}
