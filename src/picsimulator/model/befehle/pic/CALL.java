package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class CALL extends Operation implements Executable {

    public CALL(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        memory.getStack()[0].setWert(getRegisterService().hexToBinNoLeadingZeros(memory.getSpeicheradressen()[0].getRegister()[2].getHexWert()));
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(binaryString.substring(opcodeBits));
        return memory;
    }
}
