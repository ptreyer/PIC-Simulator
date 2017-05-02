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
        System.out.println(memory.getSpeicheradressen()[0].getRegister()[0].getWert());
        return memory;
    }
}
