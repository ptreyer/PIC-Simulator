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
    private int sync;

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

    public Speicheradresse[] getSpeicheradressen() {
        return speicheradressen;
    }

    public void setSpeicheradressen(Speicheradresse[] speicheradressen) {
        this.speicheradressen = speicheradressen;
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
        return speicheradressen[0].getRegister()[1];
    }

    public int getPrescaler() {
        return prescaler;
    }

    public void setPrescaler(int prescaler) {
        this.prescaler = prescaler;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
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
        Register timer0 = speicheradressen[0].getRegister()[1];
        timer0.setWert(timer0.getIntWert() + 1);
    }

    public void incrementWatchdogTimer() {
        watchdogTimer++;
    }

    public int getPrescalerMaxValue() {
        Register optionRegister = speicheradressen[16].getRegister()[1];
        StringBuilder builder = new StringBuilder();
        builder.append(optionRegister.getBits()[2].getPin());
        builder.append(optionRegister.getBits()[1].getPin());
        builder.append(optionRegister.getBits()[0].getPin());
        int value = getRegisterService().binToInt(builder.toString());
        if (value == 0) return 2;
        if (value == 1) return 4;
        if (value == 2) return 8;
        if (value == 3) return 16;
        if (value == 4) return 32;
        if (value == 5) return 64;
        if (value == 6) return 128;
        if (value == 7) return 256;
        return 0;
    }

    public Register getFileRegister(int nummer, boolean changePC) {
        if (nummer == 0) {
            nummer = getRegisterService().hexToInt(speicheradressen[0].getRegister()[4].getHexWert());
        }
        if (nummer == 1 && changePC) {
            prescaler = 0;
            //sync = 2;
        }
        if (nummer == 2 && changePC) {
            Controller.increaseRuntime();
        }

        if (nummer != 3 && speicheradressen[0].getRegister()[3].getBits()[5].getPin() == 1) {
            nummer = nummer + 128;
        }
        if (0 < nummer && nummer <= 7) return speicheradressen[0].getRegister()[nummer];
        if (7 < nummer && nummer <= 15) return speicheradressen[1].getRegister()[nummer - 8];
        if (15 < nummer && nummer <= 23) return speicheradressen[2].getRegister()[nummer - 16];
        if (23 < nummer && nummer <= 31) return speicheradressen[3].getRegister()[nummer - 24];
        if (31 < nummer && nummer <= 39) return speicheradressen[4].getRegister()[nummer - 32];
        if (39 < nummer && nummer <= 47) return speicheradressen[5].getRegister()[nummer - 40];
        if (47 < nummer && nummer <= 55) return speicheradressen[6].getRegister()[nummer - 48];
        if (55 < nummer && nummer <= 63) return speicheradressen[7].getRegister()[nummer - 56];
        if (63 < nummer && nummer <= 71) return speicheradressen[8].getRegister()[nummer - 64];
        if (71 < nummer && nummer <= 79) return speicheradressen[9].getRegister()[nummer - 72];
        if (79 < nummer && nummer <= 87) return speicheradressen[10].getRegister()[nummer - 80];
        if (87 < nummer && nummer <= 95) return speicheradressen[11].getRegister()[nummer - 88];
        if (95 < nummer && nummer <= 103) return speicheradressen[12].getRegister()[nummer - 96];
        if (103 < nummer && nummer <= 111) return speicheradressen[13].getRegister()[nummer - 104];
        if (111 < nummer && nummer <= 119) return speicheradressen[14].getRegister()[nummer - 112];
        if (119 < nummer && nummer <= 127) return speicheradressen[15].getRegister()[nummer - 120];
        if (127 < nummer && nummer <= 135) return speicheradressen[16].getRegister()[nummer - 128];
        if (135 < nummer && nummer <= 143) return speicheradressen[17].getRegister()[nummer - 136];
        if (143 < nummer && nummer <= 151) return speicheradressen[18].getRegister()[nummer - 144];
        if (151 < nummer && nummer <= 159) return speicheradressen[19].getRegister()[nummer - 152];
        if (159 < nummer && nummer <= 167) return speicheradressen[20].getRegister()[nummer - 160];
        if (167 < nummer && nummer <= 175) return speicheradressen[21].getRegister()[nummer - 168];
        if (175 < nummer && nummer <= 183) return speicheradressen[22].getRegister()[nummer - 176];
        if (183 < nummer && nummer <= 191) return speicheradressen[23].getRegister()[nummer - 184];
        if (191 < nummer && nummer <= 199) return speicheradressen[24].getRegister()[nummer - 192];
        if (199 < nummer && nummer <= 207) return speicheradressen[25].getRegister()[nummer - 200];
        if (207 < nummer && nummer <= 215) return speicheradressen[26].getRegister()[nummer - 208];
        if (215 < nummer && nummer <= 223) return speicheradressen[27].getRegister()[nummer - 216];
        if (223 < nummer && nummer <= 231) return speicheradressen[28].getRegister()[nummer - 224];
        if (231 < nummer && nummer <= 239) return speicheradressen[29].getRegister()[nummer - 232];
        if (239 < nummer && nummer <= 247) return speicheradressen[30].getRegister()[nummer - 240];
        if (247 < nummer && nummer <= 255) return speicheradressen[31].getRegister()[nummer - 248];
        return null;
    }

    public RegisterService getRegisterService() {
        return new RegisterService();
    }

}
