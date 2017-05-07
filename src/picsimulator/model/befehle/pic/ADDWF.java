package picsimulator.model.befehle.pic;

import picsimulator.model.Bit;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class ADDWF extends Operation implements Executable {


    public ADDWF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String ziel = binaryString.substring(opcodeBits, opcodeBits + 1);
        String registerAdress = binaryString.substring(opcodeBits + 1);
        int registerNr = getRegisterService().binToInt(registerAdress);
        int result = memory.getFileRegister(registerNr).getIntWert() + memory.getRegisterW();


        System.out.println("RESSSSUULT: " + result);
        System.out.println("w: " + memory.getRegisterW());
        System.out.println("f: " + memory.getFileRegister(registerNr).getHexWert());


        /**
         *  Check Carry Flag
         */
        String wTwoCom = getRegisterService().leftPad32(Integer.toBinaryString(memory.getRegisterW()));
        wTwoCom = wTwoCom.substring(24);
        short wC = (short) (Short.parseShort(wTwoCom, 2) & 0xFF);
        short fileC = Short.parseShort((Integer.toString(memory.getFileRegister(registerNr).getIntWert() & 0xFF)));
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
        short fileDC = Short.parseShort((Integer.toString(memory.getFileRegister(registerNr).getIntWert() & 0xF)));
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

        if (Integer.parseInt(ziel) == 0) {
            memory.setRegisterW(result);
        } else {
            memory.getFileRegister(registerNr).setWert(result);
        }

        increaseProgrammCounter();
        return memory;
    }

}
