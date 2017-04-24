package picsimulator.model.befehle.pic;

import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class XORLW extends Operation implements Executable {

    public XORLW(String binaryString, int opcodeBits) {
        super(binaryString, opcodeBits);
    }

    @Override
    public String execute() {
        return null;
    }
}
