package picsimulator.model.befehle.pic;

import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class ADDWF extends Operation implements Executable {

    public ADDWF(String binaryString, int opcodeBits) {
        super(binaryString, opcodeBits);
    }

    @Override
    public String execute() {
        return null;
    }

}
