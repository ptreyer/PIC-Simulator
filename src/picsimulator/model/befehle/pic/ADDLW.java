package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class ADDLW extends Operation implements Executable {

    public ADDLW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String literal = binaryString.substring(opcodeBits);
        int registerW = memory.getRegisterW() + getRegisterService().binToInt(literal);
        memory.setRegisterW(registerW);
        memory.setFlags(registerW);
        increaseProgrammCounter();
        return memory;
    }

}
