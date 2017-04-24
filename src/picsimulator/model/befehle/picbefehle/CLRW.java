package picsimulator.model.befehle.picbefehle;

import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by ptrey on 24.04.2017.
 */
public class CLRW extends Operation implements Executable {

    public CLRW(String binaryString, int opcodeBits) {
        super(binaryString, opcodeBits);
    }

    @Override
    public String execute() {
        return null;
    }
}
