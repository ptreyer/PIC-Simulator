package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * INCF
 */
public class INCF extends Operation implements Executable {

    public INCF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        int aktuellerWert = memory.getFileRegister(registerNr, false).getIntWert();
        int inkrementierterWert = aktuellerWert + 1;

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(inkrementierterWert);
        } else {
            memory.getFileRegister(registerNr, true).setWert(inkrementierterWert);
        }

        /**
         *  Check Zero Flag
         */
        if (inkrementierterWert == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
