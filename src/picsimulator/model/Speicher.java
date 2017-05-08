package picsimulator.model;

import picsimulator.services.RegisterService;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicher {

    private Speicheradresse[] speicheradressen;
    private Stack[] stack;

    private int registerW;
    private int watchdogTimer;

    public Speicher() {
        this.speicheradressen = new Speicheradresse[32];
        int adresse = 0;
        for (int i = 0; i < speicheradressen.length; i++) {
            speicheradressen[i] = new Speicheradresse(adresse);
            adresse = adresse + 8;
        }
        this.stack = new Stack[8];
        for (int i = 0; i < stack.length; i++) {
            stack[i] = new Stack();
        }
    }

    public Speicheradresse[] getSpeicheradressen() {
        return speicheradressen;
    }

    public void setSpeicheradressen(Speicheradresse[] speicheradressen) {
        this.speicheradressen = speicheradressen;
    }

    public Stack[] getStack() {
        return stack;
    }

    public void setStack(Stack[] stack) {
        this.stack = stack;
    }

    public int getRegisterW() {
        return registerW;
    }

    public void setRegisterW(int registerW) {
        if (registerW < 0) {
            registerW = 256 - (registerW * -1);
        } else if (registerW > 255) {
            registerW = registerW -256;
        }
        this.registerW = registerW;
    }

    public int getWatchdogTimer() {
        return watchdogTimer;
    }

    public void setWatchdogTimer(int watchdogTimer) {
        this.watchdogTimer = watchdogTimer;
    }

    public Register getFileRegister(int nummer) {
        if (nummer == 0) {
            nummer = getRegisterService().hexToInt(getSpeicheradressen()[0].getRegister()[4].getHexWert());
        }
        if (0 < nummer && nummer <= 7) {
            return getSpeicheradressen()[0].getRegister()[nummer];
        }
        if (7 < nummer && nummer <= 15) {
            return getSpeicheradressen()[1].getRegister()[nummer - 8];
        }
        if (15 < nummer && nummer <= 23) {
            return getSpeicheradressen()[2].getRegister()[nummer - 16];
        }
        if (23 < nummer && nummer <= 31) {
            return getSpeicheradressen()[3].getRegister()[nummer - 24];
        }
        if (31 < nummer && nummer <= 39) {
            return getSpeicheradressen()[4].getRegister()[nummer - 32];
        }
        return null;
    }

    public RegisterService getRegisterService() {
        return new RegisterService();
    }

}
