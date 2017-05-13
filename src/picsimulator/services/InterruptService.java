package picsimulator.services;

import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by Edeka on 08.05.2017.
 */
public class InterruptService {

    public Speicher checkForTMR0TimerInterrupt(Speicher speicher) {
        Register optionRegister = speicher.getFileRegister(11, false);

        if (optionRegister.getBits()[5].getPin() == 0) {
            speicher = incrementTimer0(speicher);
        }
        return checkForTMR0Interrupt(speicher);
    }

    public Speicher checkForTMR0Interrupt(Speicher speicher) {
        Register statusRegister = speicher.getFileRegister(3, false);

        if (statusRegister.getBits()[7].getPin() == 1 &&
                statusRegister.getBits()[2].getPin() == 1 &&
                statusRegister.getBits()[5].getPin() == 1) {
            speicher.setInterrupt(true);
        }
        return speicher;
    }

    private Speicher incrementTimer0(Speicher speicher) {
        Register optionRegister = speicher.getFileRegister(11, false);

        // Prescaler ist Timer0 zugeordnet
        if (optionRegister.getBits()[3].getPin() == 0) {
            speicher.incrementPrescaler();

            if (speicher.getPrescaler() >= speicher.getPrescalerMaxValue()) {
                speicher.setPrescaler(0);
                speicher.incrementTimer0();
            }
        } else {
            speicher.incrementTimer0();
        }

        //check for TMR0 Overflow
        if (speicher.getTimer0().getIntWert() == 0) {
            //set T0IF flag
            optionRegister.getBits()[2].setPin(1);
        } else {
            //clear T0IF flag
            optionRegister.getBits()[2].setPin(0);
        }

        return speicher;
    }

    public Speicher checkForINTInterrupt(Speicher speicher) {
        Register statusRegister = speicher.getFileRegister(3, false);
        if (statusRegister.getBits()[7].getPin() == 1 &&
                statusRegister.getBits()[4].getPin() == 1 &&
                statusRegister.getBits()[1].getPin() == 1) {
            speicher.setInterrupt(true);
        }
        return speicher;
    }

    public Speicher checkForPortBInterrupt(Speicher speicher) {
        Register statusRegister = speicher.getFileRegister(3, false);
        if (statusRegister.getBits()[7].getPin() == 1 &&
                statusRegister.getBits()[0].getPin() == 1 &&
                statusRegister.getBits()[3].getPin() == 1) {
            speicher.setInterrupt(true);
            speicher.setSleepModus(false);
        }
        return speicher;
    }

    public Speicher checkForWatchDogInterrupt(Speicher speicher) {
        Register optionRegister = speicher.getFileRegister(11, false);

        speicher.incrementWatchdogTimer();
        if (speicher.getWatchdogTimer() >= 24)//Default instruction time = 1ms
        {
            if (optionRegister.getBits()[3].getPin() == 1) {
                speicher.incrementPrescaler();
                if (speicher.getPrescaler() >= speicher.getPrescalerMaxValue()) {
                    if (speicher.isSleepModus()) {
                        speicher.setSleepModus(false);
                    } else {
                        speicher = new Speicher();
                    }
                }
            } else {
                if (speicher.isSleepModus()) {
                    speicher.setSleepModus(false);
                } else {
                    speicher = new Speicher();
                }
            }
            speicher.setWatchdogTimer(0);
        }
        return speicher;
    }

    public boolean checkInterrupt(Speicher speicher) {
        boolean interrupt = false;

        if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[7].getPin() == 1) {
        }
        if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[6].getPin() == 1 && speicher.getSpeicheradressen()[17].getRegister()[0].getBits()[4].getPin() == 1) {
            interrupt = true;
        }
        return false;
    }

}

