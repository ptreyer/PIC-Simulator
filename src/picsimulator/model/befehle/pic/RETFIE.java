package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * RETFIE
 */
public class RETFIE extends Operation implements Executable {

    public RETFIE(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        int pc = memory.getFromStack().getIntWert();
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(new Integer(pc));
        Controller.increaseRuntime();
        Controller.increaseRuntime();
        return memory;
    }
}
