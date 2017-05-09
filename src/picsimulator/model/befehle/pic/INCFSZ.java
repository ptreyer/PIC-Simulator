package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class INCFSZ extends Operation implements Executable {

    public INCFSZ(String binaryString, int opcodeBits, Speicher memory) {
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
            if (memory.getRegisterW() == 0) {
                Controller.increaseRuntime();
                NOP nop = new NOP(binaryString, 14, memory);
                memory = nop.execute();
            }
        } else {
            memory.getFileRegister(registerNr, false).setWert(inkrementierterWert);
            if (memory.getFileRegister(registerNr, false).getIntWert() == 0) {
                Controller.increaseRuntime();
                NOP nop = new NOP(binaryString, 14, memory);
                memory = nop.execute();
            } else {
                memory.getFileRegister(registerNr, true);
            }
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
