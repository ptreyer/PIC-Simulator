package picsimulator.model.befehle;

import picsimulator.model.Speicher;

/**
 * Created by ptrey on 24.04.2017.
 */
public class Operation {

    protected String binaryString;
    protected int opcodeBits;
    protected Speicher memory;

    public Operation(String binaryString, int opcodeBits, Speicher memory) {
        this.binaryString = binaryString;
        this.opcodeBits = opcodeBits;
        this.memory = memory;
    }
}
