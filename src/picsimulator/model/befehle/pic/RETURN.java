package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * RETURN
 */
public class RETURN extends Operation implements Executable {

    public RETURN(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(memory.getFromStack().getIntWert());

        Controller.increaseRuntime();
        Controller.increaseRuntime();
        increaseProgrammCounter();
        memory.setCalculateProgramCounter(false);
        return memory;
    }
}
