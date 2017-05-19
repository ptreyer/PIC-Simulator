package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * MOVWF
 */
public class MOVWF extends Operation implements Executable {

    public MOVWF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String registerAdress = binaryString.substring(opcodeBits);
        Register fileRegister = memory.getFileRegister(getRegisterService().binToInt(registerAdress), true);
        if (fileRegister != null){
            fileRegister.setWert(new Integer(memory.getRegisterW()));
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
