package picsimulator.model.befehle.pic;

import picsimulator.model.Bit;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class SWAPF extends Operation implements Executable {

    public SWAPF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        Bit[] bits = memory.getFileRegister(registerNr).getBits();
        Register swappedRegister = new Register();
        swappedRegister.getBits()[0] = bits[4];
        swappedRegister.getBits()[1] = bits[5];
        swappedRegister.getBits()[2] = bits[6];
        swappedRegister.getBits()[3] = bits[7];
        swappedRegister.getBits()[4] = bits[0];
        swappedRegister.getBits()[5] = bits[1];
        swappedRegister.getBits()[6] = bits[2];
        swappedRegister.getBits()[7] = bits[3];

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(swappedRegister.getIntWert());
        } else {
            memory.getFileRegister(registerNr).setWert(swappedRegister.getIntWert());
        }

        increaseProgrammCounter();
        return memory;
    }
}

