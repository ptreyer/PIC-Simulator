package picsimulator.model.befehle.pic;

import picsimulator.model.Bit;
import picsimulator.model.Register;
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
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        Register register = memory.getFileRegister(registerNr);
        Register fileRegister = new Register();
        for (int i = 0; i < fileRegister.getBits().length; i++) {
            fileRegister.getBits()[i] = getRegisterService().toggleBit(register.getBits()[i]);
        }

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(fileRegister.getIntWert());
        } else {
            memory.getFileRegister(registerNr).setBits(fileRegister.getBits());
        }

        if (memory.getFileRegister(registerNr).getIntWert() == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        increaseProgrammCounter();
        return memory;
    }
}
