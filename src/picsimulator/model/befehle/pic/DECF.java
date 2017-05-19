package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * DECF
 */
public class DECF extends Operation implements Executable {

    public DECF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        int aktuellerWert = memory.getFileRegister(registerNr, false).getIntWert();
        int dekrementierterWert = aktuellerWert - 1;

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(dekrementierterWert);
        } else {
            memory.getFileRegister(registerNr, true).setWert(dekrementierterWert);
        }

        if (dekrementierterWert == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
