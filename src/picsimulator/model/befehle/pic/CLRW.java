package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * CLRW
 */
public class CLRW extends Operation implements Executable {

    public CLRW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        memory.setRegisterW(0);
        memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
