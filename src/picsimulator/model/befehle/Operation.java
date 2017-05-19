package picsimulator.model.befehle;

import picsimulator.model.Speicher;
import picsimulator.services.RegisterService;

/**
 * Vaterklasse der einzelnen Befehle, welche alle von dieser erben. Hier werden
 * allgemeingültige Variablen sowie Methooden definiert, die von allen Befehlen
 * benötigt werden.
 */
public class Operation {

    protected String binaryString;
    protected int opcodeBits;
    protected Speicher memory;

    private RegisterService registerService;

    /**
     * Konstruktor, welcher den binaryString, die Anzahl der opcode-Bits sowie
     * den Speicher entgegennimmt und diese setzt.
     *
     * @param binaryString
     * @param opcodeBits
     * @param memory
     */
    public Operation(String binaryString, int opcodeBits, Speicher memory) {
        this.binaryString = binaryString;
        this.opcodeBits = opcodeBits;
        this.memory = memory;
    }

    /**
     * Methode zur Inkrementierung des ProgrammCounters. Hier wird bei der Erhöhung der
     * Überlauf geprüft.
     */
    protected void increaseProgrammCounter() {
        int pc = memory.getSpeicheradressen()[0].getRegister()[2].getIntWert() + 1;
        memory.getSpeicheradressen()[0].getRegister()[2].setWert(pc);
        if (pc > 255) {
            int pcL = memory.getSpeicheradressen()[1].getRegister()[2].getIntWert() + 1;
            memory.getSpeicheradressen()[1].getRegister()[2].setWert(pcL);
        }
    }

    /**
     *
     * @return
     */
    public RegisterService getRegisterService() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }
}
