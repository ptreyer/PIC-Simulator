package picsimulator.model.befehle.pic;

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
        return memory;
    }

}
