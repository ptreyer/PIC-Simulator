package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * DECFSZ
 */
public class DECFSZ extends Operation implements Executable {

    public DECFSZ(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);

        int aktuellerWert = memory.getFileRegister(registerNr, false).getIntWert();
        int dekrementierterWert = aktuellerWert - 1;

        /**
         * Prüft wohin das Ergebnis geschrieben werden soll
         */
        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(dekrementierterWert);
        } else {
            memory.getFileRegister(registerNr, false).setWert(dekrementierterWert);
        }

        if (dekrementierterWert == 0) {
            Controller.increaseRuntime();
            NOP nop = new NOP(binaryString, 14, memory);
            memory = nop.execute();
        } else {
            memory.getFileRegister(registerNr, true);
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
