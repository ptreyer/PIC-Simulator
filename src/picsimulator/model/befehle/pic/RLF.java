package picsimulator.model.befehle.pic;

import picsimulator.model.Bit;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class RLF extends Operation implements Executable {

    public RLF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        Bit[] bits = memory.getFileRegister(registerNr).getBits();
        Register shiftedRegister = new Register();
        shiftedRegister.getBits()[0] = memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0];
        shiftedRegister.getBits()[1] = bits[0];
        shiftedRegister.getBits()[2] = bits[1];
        shiftedRegister.getBits()[3] = bits[2];
        shiftedRegister.getBits()[4] = bits[3];
        shiftedRegister.getBits()[5] = bits[4];
        shiftedRegister.getBits()[6] = bits[5];
        shiftedRegister.getBits()[7] = bits[5];

        memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0] = bits[7];

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(shiftedRegister.getIntWert());
        } else {
            memory.getFileRegister(registerNr).setWert(shiftedRegister.getIntWert());
        }

        increaseProgrammCounter();
        return memory;
    }
}
