package picsimulator.model.befehle.pic;

import picsimulator.model.Speicher;
import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class ANDWF extends Operation implements Executable {


    public ANDWF(String binaryString, int opcodeBits, Speicher memory) {
        super(binaryString, opcodeBits, memory);
    }

    @Override
    public Speicher execute() {
        return null;
    }
}
