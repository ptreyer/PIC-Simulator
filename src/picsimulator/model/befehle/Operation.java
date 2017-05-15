package picsimulator.model.befehle;

import picsimulator.model.Speicher;
import picsimulator.services.RegisterService;

/**
 * Created by ptrey on 24.04.2017.
 */
public class Operation {

    protected String binaryString;
    protected int opcodeBits;
    protected Speicher memory;

    private RegisterService registerService;

    public Operation(String binaryString, int opcodeBits, Speicher memory) {
        this.binaryString = binaryString;
        this.opcodeBits = opcodeBits;
        this.memory = memory;
    }

    protected void increaseProgrammCounter() {
        int pc = memory.getSpeicheradressen()[0].getRegister()[2].getIntWert() + 1;
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(pc);
        if (pc > 255) {
            int pcL = memory.getSpeicheradressen()[1].getRegister()[2].getIntWert() + 1;
            memory.getSpeicheradressen()[1].getRegister()[2].setWert(pcL);
        }
    }

    public RegisterService getRegisterService() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }
}
