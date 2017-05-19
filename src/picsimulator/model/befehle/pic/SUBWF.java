package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * SUBWF
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
        int result = memory.getFileRegister(registerNr, false).getIntWert() - memory.getRegisterW();

        /**
         *  Check Carry Flag
         */
        String wTwoCom = getRegisterService().leftPad32(Integer.toBinaryString(memory.getRegisterW() * -1));
        wTwoCom = wTwoCom.substring(24);
        short wC = (short) (Short.parseShort(wTwoCom, 2) & 0xFF);
        short fileC = Short.parseShort((Integer.toString(memory.getFileRegister(registerNr, false).getIntWert() & 0xFF)));
        short resultC = (short) (fileC + wC);
        if (Short.toUnsignedInt(resultC) > 255) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(0);
        }

        /**
         *  Check Digit Carry Flag
         */
        short wDC = (short) (Short.parseShort(wTwoCom, 2) & 0xF);
        short fileDC = Short.parseShort((Integer.toString(memory.getFileRegister(registerNr, false).getIntWert() & 0xF)));
        short resultDC = (short) (fileDC + wDC);
        if (Short.toUnsignedInt(resultDC) > 15) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[1].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[1].setPin(0);
        }

        /**
         *  Check Zero Flag
         */
        if (result == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        if (result < 0) {
            result = 256 - (result * -1);
        }

        /**
         * Prüft wohin das Ergebnis geschrieben werden soll
         */
        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(result);
        } else {
            memory.getFileRegister(registerNr, true).setWert(result);
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
