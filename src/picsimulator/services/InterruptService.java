package picsimulator.services;

import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Service zur Überprüfung von Interrupts.
 */
public class InterruptService {

    /**
     * Erhöht den Timer und prüft ob ein Interrupt am Timer0 vorliegt.
     *
     * @param speicher, der aktuelle Speicherzustand
     * @param cycles, die Anzahl der Taktzyklen
     *
     * @return der aktualisierte Speicher.
     */
    public Speicher checkForTMR0TimerInterrupt(Speicher speicher, int cycles) {
        speicher = incrementTimer0(speicher, cycles);
        return checkForTMR0Interrupt(speicher);
    }

    /**
     * Prüft ob ein Interrupt am Timer0 vorliegt, falls ja werden die entsprechenden
     * Flags gesetzt.
     *
     * @param speicher, der aktuelle Speicherzustand
     *
     * @return der aktualisierte Speicher.
     */
    private Speicher checkForTMR0Interrupt(Speicher speicher) {
        Register intconRegister = speicher.getSpeicheradressen()[1].getRegister()[3];

        if (intconRegister.getBits()[7].getPin() == 1 &&
                intconRegister.getBits()[2].getPin() == 1 &&
                intconRegister.getBits()[5].getPin() == 1) {
            speicher.setInterrupt(true);
        }
        return speicher;
    }

    /**
     * Inkrementiert den Timer0.
     *
     * @param speicher, der aktuelle Speicherzustand
     * @param cycles, die Anzahl der Taktzyklen
     *
     * @return der aktualisierte Speicher.
     */
    private Speicher incrementTimer0(Speicher speicher, int cycles) {
        Register optionRegister = speicher.getSpeicheradressen()[16].getRegister()[1];

        // Prescaler ist Timer0 zugeordnet
        if (optionRegister.getBits()[3].getPin() == 0) {
            for (int i = 0; i < cycles; i++) {
                speicher.incrementPrescaler();
                if (speicher.getPrescaler() >= speicher.getPrescalerMaxValue()) {
                    speicher.setPrescaler(0);

                    if (speicher.getSync() != 0) {
                        speicher.setSync(speicher.getSync() - 1);
                    } else {
                        speicher.incrementTimer0();
                    }
                }
            }
        } else {
            for (int i = 0; i < cycles; i++) {
                if (speicher.getSync() != 0) {
                    speicher.setSync(speicher.getSync() - 1);
                } else {
                    speicher.incrementTimer0();
                }
            }
        }

        //check for TMR0 Overflow
        if (speicher.getTimer0().getIntWert() == 0) {
            //set T0IF flag
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[2].setPin(1);
        } else {
            //clear T0IF flag
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[2].setPin(0);
        }

        return speicher;
    }

    /**
     * Prüft ob ein INT-Interrupt vorliegt.
     *
     * @param speicher, der aktuelle Speicherzustand
     *
     * @return der aktualisierte Speicher.
     */
    public Speicher checkForINTInterrupt(Speicher speicher) {
        Register intconRegister = speicher.getSpeicheradressen()[1].getRegister()[3];
        if (intconRegister.getBits()[7].getPin() == 1 &&
                intconRegister.getBits()[4].getPin() == 1 &&
                intconRegister.getBits()[1].getPin() == 1) {
            speicher.setInterrupt(true);
            speicher.setSleepModus(false);
        }
        return speicher;
    }

    /**
     * Prüft ob ein Interrupt an PortB vorliegt.
     *
     * @param speicher, der aktuelle Speicherzustand
     *
     * @return der aktualisierte Speicher.
     */
    public Speicher checkForPortBInterrupt(Speicher speicher) {
        Register intconRegister = speicher.getSpeicheradressen()[1].getRegister()[3];
        if (intconRegister.getBits()[7].getPin() == 1 &&
                intconRegister.getBits()[0].getPin() == 1 &&
                intconRegister.getBits()[3].getPin() == 1) {
            speicher.setInterrupt(true);
        }
        return speicher;
    }

    /**
     * Erhöht den Watchdog-Timer und prüft ob ein Interrupt vorliegt. Je nach Zustand des Sleep-Modus
     * wird ein Reset des Speichers durchgeführt.
     *
     * @param speicher, der aktuelle Speicherzustand
     *
     * @return der aktualisierte Speicher.
     */
    public Speicher checkForWatchDogInterrupt(Speicher speicher, int cycles) {
        for (int i = 0; i < cycles; i++) {
            speicher.incrementWatchdogTimer();
            System.out.println("WatchdogTimer: " + speicher.getWatchdogTimer());
            if (speicher.getWatchdogTimer() >= 18000 * speicher.getPrescalerWatchdogMaxValue())//Default instruction time = 1ms
            {
                if (speicher.isSleepModus()) {
                    speicher.setSleepModus(false);
                } else {
                    speicher = new Speicher();
                }
                speicher.setWatchdogTimer(0);
            }
        }
        return speicher;
    }
}

