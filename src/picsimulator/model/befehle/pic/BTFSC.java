package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Bit;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class BTFSC extends Operation implements Executable {

    public BTFSC(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        int registerIndex = opcodeBits + 3;
        String binBit = binaryString.substring(opcodeBits, registerIndex);
        String register = binaryString.substring(registerIndex);
        int bit = getRegisterService().binToInt(binBit);
        Bit selectedBit = memory.getFileRegister(getRegisterService().binToInt(register), false).getBits()[bit];

        if (selectedBit.getPin() == 0) {
            Controller.increaseRuntime();
            NOP nop = new NOP(binaryString, 14, memory);
            memory = nop.execute();
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
