package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class SUBWF extends Operation implements Executable {

    public SUBWF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        int result = memory.getFileRegister(registerNr).getIntWert() - memory.getRegisterW();

        if (result > 255 | result < 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(0);
        }

        // TODO DC

        if (result == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        if (result < 0) {
            result = 256 - (result * -1);
        }

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(result);
        } else {
            memory.getFileRegister(registerNr).setWert(result);
        }

        increaseProgrammCounter();
        return memory;
    }
}
