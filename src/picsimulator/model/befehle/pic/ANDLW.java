package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * ANDLW
 */
public class ANDLW extends Operation implements Executable {

    public ANDLW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String reverseliteral = binaryString.substring(opcodeBits);
        String literal = new StringBuilder(reverseliteral).reverse().toString();

        Register resultRegister = new Register();
        Register wRegister = new Register();
        wRegister.setWert(memory.getRegisterW());

        resultRegister.getBits()[0].setPin(Character.getNumericValue(literal.charAt(0)) & wRegister.getBits()[0].getPin());
        resultRegister.getBits()[1].setPin(Character.getNumericValue(literal.charAt(1)) & wRegister.getBits()[1].getPin());
        resultRegister.getBits()[2].setPin(Character.getNumericValue(literal.charAt(2)) & wRegister.getBits()[2].getPin());
        resultRegister.getBits()[3].setPin(Character.getNumericValue(literal.charAt(3)) & wRegister.getBits()[3].getPin());
        resultRegister.getBits()[4].setPin(Character.getNumericValue(literal.charAt(4)) & wRegister.getBits()[4].getPin());
        resultRegister.getBits()[5].setPin(Character.getNumericValue(literal.charAt(5)) & wRegister.getBits()[5].getPin());
        resultRegister.getBits()[6].setPin(Character.getNumericValue(literal.charAt(6)) & wRegister.getBits()[6].getPin());
        resultRegister.getBits()[7].setPin(Character.getNumericValue(literal.charAt(7)) & wRegister.getBits()[7].getPin());

        memory.setRegisterW(resultRegister.getIntWert());

        /**
         *  Check Zero Flag
         */
        if (resultRegister.getIntWert() == 0) {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(1);
        } else {
            memory.getSpeicheradressen()[0].getRegister()[3].getBits()[2].setPin(0);
        }

        Controller.increaseRuntime();
        increaseProgrammCounter();
        return memory;
    }
}
