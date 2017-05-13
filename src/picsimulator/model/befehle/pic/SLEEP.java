package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class SLEEP extends Operation implements Executable {

    public SLEEP(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        memory.setSleepModus(true);
        memory.setWatchdogTimer(0);

        if(memory.getFileRegister(1, false).getBits()[3].getPin() == 1){
            memory.setPrescaler(0);
        }

        memory.getFileRegister(3, false).getBits()[3].setPin(0);
        memory.getFileRegister(3, false).getBits()[4].setPin(1);

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
