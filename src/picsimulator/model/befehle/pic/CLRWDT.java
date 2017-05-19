package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * CLRWDT
 */
public class CLRWDT extends Operation implements Executable {

    public CLRWDT(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        memory.setWatchdogTimer(0);

        if(memory.getFileRegister(1, false).getBits()[3].getPin() == 1){
            memory.setPrescaler(0);
        }

        memory.getFileRegister(3, false).getBits()[3].setPin(1);
        memory.getFileRegister(3, false).getBits()[4].setPin(1);

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
