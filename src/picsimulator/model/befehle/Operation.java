package picsimulator.model.befehle;

/**
 * Created by ptrey on 24.04.2017.
 */
public class Operation {

    protected String binaryString;
    protected int opcodeBits;

    public Operation(String binaryString, int opcodeBits) {
        this.binaryString = binaryString;
        this.opcodeBits = opcodeBits;
    }
}
