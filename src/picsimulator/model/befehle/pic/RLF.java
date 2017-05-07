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

        int pin = new Integer(memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].getPin());
        shiftedRegister.getBits()[0] = new Bit(pin, 0);
        shiftedRegister.getBits()[1] = new Bit(bits[0].getPin(), 0);
        shiftedRegister.getBits()[2] = new Bit(bits[1].getPin(), 0);
        shiftedRegister.getBits()[3] = new Bit(bits[2].getPin(), 0);
        shiftedRegister.getBits()[4] = new Bit(bits[3].getPin(), 0);
        shiftedRegister.getBits()[5] = new Bit(bits[4].getPin(), 0);
        shiftedRegister.getBits()[6] = new Bit(bits[5].getPin(), 0);
        shiftedRegister.getBits()[7] = new Bit(bits[6].getPin(), 0);

        memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0] = new Bit(bits[7].getPin(), 0);

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(shiftedRegister.getIntWert());
        } else {
            memory.getFileRegister(registerNr).setWert(shiftedRegister.getIntWert());
        }

        increaseProgrammCounter();
        return memory;
    }
}
