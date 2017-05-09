package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class BCF extends Operation implements Executable {

    public BCF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        int registerIndex = opcodeBits + 3;
        String binBit = binaryString.substring(opcodeBits, registerIndex);
        String register = binaryString.substring(registerIndex);
        int bit = getRegisterService().binToInt(binBit);
        memory.getFileRegister(getRegisterService().binToInt(register), true).getBits()[bit].setPin(0);

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
