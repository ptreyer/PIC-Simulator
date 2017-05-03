package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
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

        int aktuellerWert = memory.getFileRegister(registerNr).getIntWert();
        int dekrementierterWert = aktuellerWert - 1;

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(dekrementierterWert);
        } else {
            memory.getFileRegister(registerNr).setWert(dekrementierterWert);
        }

        if (dekrementierterWert == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        if(dekrementierterWert == 0){
            NOP nop = new NOP(binaryString, 14, memory);
            memory =  nop.execute();
        }
        increaseProgrammCounter();
        return memory;
    }
}
