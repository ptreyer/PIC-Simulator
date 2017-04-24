package picsimulator.model.befehle.picbefehle;

import picsimulator.model.befehle.Executable;
import picsimulator.model.befehle.Operation;

/**
 * Created by Edeka on 24.04.2017.
 */
public class MOVLW extends Operation implements Executable {

    public MOVLW(String binaryString, int opcodeBits) {
        super(binaryString, opcodeBits);
    }

    @Override
    public String execute() {
        return null;
    }
}
