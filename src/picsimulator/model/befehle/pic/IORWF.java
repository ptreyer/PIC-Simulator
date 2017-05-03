package picsimulator.model.befehle.pic;

import picsimulator.model.Bit;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class IORWF extends Operation implements Executable {

    public IORWF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        Bit[] bits = memory.getFileRegister(registerNr).getBits();
        Register resultRegister = new Register();
        Register wRegister = new Register();
        wRegister.setWert(memory.getRegisterW());

        resultRegister.getBits()[0].setPin(bits[0].getPin() | wRegister.getBits()[0].getPin());
        resultRegister.getBits()[1].setPin(bits[0].getPin() | wRegister.getBits()[1].getPin());
        resultRegister.getBits()[2].setPin(bits[0].getPin() | wRegister.getBits()[2].getPin());
        resultRegister.getBits()[3].setPin(bits[0].getPin() | wRegister.getBits()[3].getPin());
        resultRegister.getBits()[4].setPin(bits[0].getPin() | wRegister.getBits()[4].getPin());
        resultRegister.getBits()[5].setPin(bits[0].getPin() | wRegister.getBits()[5].getPin());
        resultRegister.getBits()[6].setPin(bits[0].getPin() | wRegister.getBits()[6].getPin());
        resultRegister.getBits()[7].setPin(bits[0].getPin() | wRegister.getBits()[7].getPin());

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(resultRegister.getIntWert());
        } else {
            memory.getFileRegister(registerNr).setWert(resultRegister.getIntWert());
        }

        if (resultRegister.getIntWert() == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        increaseProgrammCounter();
        return memory;
    }
}
