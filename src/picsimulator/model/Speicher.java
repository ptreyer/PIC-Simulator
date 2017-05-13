package picsimulator.model;

import picsimulator.controller.Controller;
import picsimulator.services.RegisterService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicher {

    private Speicheradresse[] speicheradressen;
    private List<StackEintrag> stack;

    private boolean interrupt;

    private int registerW;
    private int watchdogTimer;
    private int prescaler;
    private boolean sleepModus;
    private boolean watchdogTimerEnabled;

    public Speicher() {
        this.speicheradressen = new Speicheradresse[32];
        int adresse = 0;
        for (int i = 0; i < speicheradressen.length; i++) {
            speicheradressen[i] = new Speicheradresse(adresse);
            adresse = adresse + 8;
        }
        this.stack = new ArrayList<>();
        watchdogTimer = 0;
        prescaler = 0;
        sleepModus = false;
        watchdogTimerEnabled = false;
    }

    public Speicheradresse[] getSpeicheradressen() {
        return speicheradressen;
    }

    public void setSpeicheradressen(Speicheradresse[] speicheradressen) {
        this.speicheradressen = speicheradressen;
    }

    public List<StackEintrag> getStack() {
        return stack;
    }

    public void setStack(List<StackEintrag> stack) {
        this.stack = stack;
    }

    public StackEintrag getFromStack() {
        StackEintrag result = stack.get(0);
        stack.remove(0);
        return result;
    }

    public void addToStack(StackEintrag newStackEntry) {
        stack.add(0, newStackEntry);
        if (stack.size() > 7) {
            stack = stack.subList(0, 7);
        }
    }

    public int getRegisterW() {
        return registerW;
    }

    public void setRegisterW(int registerW) {
        if (registerW < 0) {
            registerW = 256 - (registerW * -1);
        } else if (registerW > 255) {
            registerW = registerW - 256;
        }
        this.registerW = registerW;
    }

    public int getWatchdogTimer() {
        return watchdogTimer;
    }

    public void setWatchdogTimer(int watchdogTimer) {
        this.watchdogTimer = watchdogTimer;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    public void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public Register getTimer0() {
        return getFileRegister(1, false);
    }

    public int getPrescaler() {
        return prescaler;
    }

    public void setPrescaler(int prescaler) {
        this.prescaler = prescaler;
    }

    public boolean isSleepModus() {
        return sleepModus;
    }

    public void setSleepModus(boolean sleepModus) {
        this.sleepModus = sleepModus;
    }

    public boolean isWatchdogTimerEnabled() {
        return watchdogTimerEnabled;
    }

    public void setWatchdogTimerEnabled(boolean watchdogTimerEnabled) {
        this.watchdogTimerEnabled = watchdogTimerEnabled;
    }

    public void incrementPrescaler() {
        prescaler++;
    }

    public void incrementTimer0() {
        Register timer0 = getFileRegister(1, false);
        timer0.setWert(timer0.getIntWert() + 1);
    }

    public void incrementWatchdogTimer() {
        watchdogTimer++;
    }

    public int getPrescalerMaxValue() {
        Register optionRegister = getFileRegister(11, false);
        StringBuilder builder = new StringBuilder();
        builder.append(optionRegister.getBits()[2].getPin());
        builder.append(optionRegister.getBits()[1].getPin());
        builder.append(optionRegister.getBits()[0].getPin());
        return getRegisterService().binToInt(builder.toString());
    }

    public Register getFileRegister(int nummer, boolean changePC) {
        if (nummer == 0) {
            nummer = getRegisterService().hexToInt(getSpeicheradressen()[0].getRegister()[4].getHexWert());
        }
        if (nummer == 2 && changePC) {
            Controller.increaseRuntime();
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
