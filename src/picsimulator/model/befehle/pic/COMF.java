package picsimulator.model.befehle.pic;

import picsimulator.model.Bit;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class COMF extends Operation implements Executable {

    public COMF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String registerAdress = binaryString.substring(opcodeBits);
        int registerNr = getRegisterService().binToInt(registerAdress);

        Bit[] bits = memory.getFileRegister(registerNr).getBits();
        for (int i = 0; i < bits.length; i++) {
            bits[i] = getRegisterService().toggleBit(bits[i]);
        }
        memory.getFileRegister(registerNr).setBits(bits);

        if (memory.getFileRegister(registerNr).getIntWert() == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        increaseProgrammCounter();
        return memory;
    }
}
