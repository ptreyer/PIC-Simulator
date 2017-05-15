package picsimulator.services;

import picsimulator.controller.Controller;
import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by Edeka on 08.05.2017.
 */
public class InterruptService {

    public Speicher checkForTMR0TimerInterrupt(Speicher speicher) {
        Register optionRegister = speicher.getSpeicheradressen()[16].getRegister()[1];

        if (optionRegister.getBits()[5].getPin() == 0) {
            speicher = incrementTimer0(speicher);
        }
        return checkForTMR0Interrupt(speicher);
    }

    public Speicher checkForTMR0Interrupt(Speicher speicher) {
        Register intconRegister = speicher.getSpeicheradressen()[1].getRegister()[3];

        if (intconRegister.getBits()[7].getPin() == 1 &&
                intconRegister.getBits()[2].getPin() == 1 &&
                intconRegister.getBits()[5].getPin() == 1) {
            speicher.setInterrupt(true);
        }
        return speicher;
    }

    private Speicher incrementTimer0(Speicher speicher) {
        Register optionRegister = speicher.getSpeicheradressen()[16].getRegister()[1];

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
            System.out.println("ZERO");
            //set T0IF flag
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[2].setPin(1);
        } else {
            System.out.println("NOT ZERO");
            //clear T0IF flag
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[2].setPin(0);
        }

        return speicher;
    }

    public Speicher checkForINTInterrupt(Speicher speicher) {
        Register intconRegister = speicher.getSpeicheradressen()[1].getRegister()[3];
        if (intconRegister.getBits()[7].getPin() == 1 &&
                intconRegister.getBits()[4].getPin() == 1 &&
                intconRegister.getBits()[1].getPin() == 1) {
            speicher.setInterrupt(true);
        }
        return speicher;
    }

    public Speicher checkForPortBInterrupt(Speicher speicher) {
        Register intconRegister = speicher.getSpeicheradressen()[1].getRegister()[3];
        if (intconRegister.getBits()[7].getPin() == 1 &&
                intconRegister.getBits()[0].getPin() == 1 &&
                intconRegister.getBits()[3].getPin() == 1) {
            speicher.setInterrupt(true);
            speicher.setSleepModus(false);
        }
        return speicher;
    }

    public Speicher checkForWatchDogInterrupt(Speicher speicher) {
        Register optionRegister = speicher.getSpeicheradressen()[16].getRegister()[1];

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
                        Controller.runtime = 0;
                    }
                }
            } else {
                if (speicher.isSleepModus()) {
                    speicher.setSleepModus(false);
                } else {
                    speicher = new Speicher();
                    Controller.runtime = 0;
                }
            }
            speicher.setWatchdogTimer(0);
        }
        return speicher;
    }
}

