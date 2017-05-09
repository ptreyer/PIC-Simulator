package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class ADDLW extends Operation implements Executable {

    public ADDLW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String literal = binaryString.substring(opcodeBits);
        int registerW = memory.getRegisterW() + getRegisterService().binToInt(literal);
        memory.setRegisterW(registerW);

        /**
         *  Check Carry Flag
         */
        String wTwoCom = getRegisterService().leftPad32(Integer.toBinaryString(memory.getRegisterW()));
        wTwoCom = wTwoCom.substring(24);
        short wC = (short) (Short.parseShort(wTwoCom, 2) & 0xFF);
        short literalC = (short) (Short.parseShort(literal, 2) & 0xFF);
        short resultC = (short) (literalC + wC);
        if (Short.toUnsignedInt(resultC) > 255) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[0].setPin(0);
        }

        /**
         *  Check Digit Carry Flag
         */
        short wDC = (short) (Short.parseShort(wTwoCom, 2) & 0xF);
        short literalDC = (short) (Short.parseShort(literal, 2) & 0xF);
        short resultDC = (short) (literalDC + wDC);
        if (Short.toUnsignedInt(resultDC) > 15) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[1].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[1].setPin(0);
        }

        /**
         *  Check Zero Flag
         */
        if (registerW == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }

}
