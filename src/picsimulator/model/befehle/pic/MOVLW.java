package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class MOVLW extends Operation implements Executable {

    public MOVLW(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        increaseProgrammCounter();
        String literal = binaryString.substring(opcodeBits);
        memory.setRegisterW(Integer.parseInt(literal, 2));
        return memory;
    }
}
