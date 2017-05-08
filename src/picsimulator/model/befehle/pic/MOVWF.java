package picsimulator.model.befehle.pic;

import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class MOVWF extends Operation implements Executable {

    public MOVWF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        String registerAdress = binaryString.substring(opcodeBits);
        Register fileRegister = memory.getFileRegister(getRegisterService().binToInt(registerAdress));
        if (fileRegister != null){
            fileRegister.setWert(new Integer(memory.getRegisterW()));
        }
        increaseProgrammCounter();
        return memory;
    }
}
