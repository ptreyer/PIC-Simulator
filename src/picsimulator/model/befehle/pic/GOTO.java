package picsimulator.model.befehle.pic;

import picsimulator.controller.Controller;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class GOTO extends Operation implements Executable {

    public GOTO(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String addresse = binaryString.substring(opcodeBits);
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(addresse.substring(3));
        memory.getSpeicheradressen()[1].getRegister()[2].getBits()[2].setPin(Character.getNumericValue(addresse.charAt(0)));
        memory.getSpeicheradressen()[1].getRegister()[2].getBits()[1].setPin(Character.getNumericValue(addresse.charAt(1)));
        memory.getSpeicheradressen()[1].getRegister()[2].getBits()[0].setPin(Character.getNumericValue(addresse.charAt(2)));

        Controller.increaseRuntime();
        Controller.increaseRuntime();
        return memory;
    }
}
