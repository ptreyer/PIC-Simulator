package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * MOVF
 */
public class MOVF extends Operation implements Executable {

    public MOVF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        if (memory.getFileRegister(registerNr, false).getIntWert() == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        /**
         * Prüft wohin das Ergebnis geschrieben werden soll
         */
        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(memory.getFileRegister(registerNr, false).getIntWert());
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
