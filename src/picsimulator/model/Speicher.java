package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicher {

    private Speicheradresse[] speicheradressen;
    private Stack[] stack;

    private byte registerW;
    private int watchdogTimer;


    public Speicher(){
        this.speicheradressen = new Speicheradresse[32];
        int adresse = 0;
        for (int i = 0; i<speicheradressen.length; i++) {
            speicheradressen[i] = new Speicheradresse(adresse);
            adresse = adresse + 8;
        }
        this.stack = new Stack[8];
        for (int i=0; i<stack.length; i++){
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

    public byte getRegisterW() {
        return registerW;
    }

    public void setRegisterW(byte registerW) {
        this.registerW = registerW;
    }

    public int getWatchdogTimer() {
        return watchdogTimer;
    }

    public void setWatchdogTimer(int watchdogTimer) {
        this.watchdogTimer = watchdogTimer;
    }

}
