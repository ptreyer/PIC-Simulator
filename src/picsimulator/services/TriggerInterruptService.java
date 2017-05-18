package picsimulator.services;

import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by Edeka on 13.05.2017.
 */
public class TriggerInterruptService {

    //Variablen zum pruefen verschiedener Bestandteile auf eine Aenderung

    //PortB
    private static int portB4Alt = 0;
    private static int portB5Alt = 0;
    private static int portB6Alt = 0;
    private static int portB7Alt = 0;
    private static int portB4Neu = 0;
    private static int portB5Neu = 0;
    private static int portB6Neu = 0;
    private static int portB7Neu = 0;

    // PortA
    private static int portA4Alt = 0;
    private static int portA4Neu = 0;

    //RB0
    private static int rb0intAlt = 0;
    private static int rb0intNeu = 0;

    //Alte Werte der verschiedenen Faktoren fuer einen spaeteren Vergleich erhalten
    public static void saveOldValues(Speicher speicher) {
        Register aRegister = speicher.getSpeicheradressen()[0].getRegister()[5];
        Register bRegister = speicher.getSpeicheradressen()[0].getRegister()[6];

        portA4Alt = bRegister.getBits()[4].getPin();
        portB5Alt = bRegister.getBits()[5].getPin();
        portB6Alt = bRegister.getBits()[6].getPin();
        portB7Alt = bRegister.getBits()[7].getPin();

        portA4Alt = aRegister.getBits()[4].getPin();
        rb0intAlt = bRegister.getBits()[0].getPin();
    }

    public static Speicher analyseNewValues(Speicher speicher) {
        Register aRegister = speicher.getSpeicheradressen()[0].getRegister()[5];
        Register bRegister = speicher.getSpeicheradressen()[0].getRegister()[6];
        Register trisBRegister = speicher.getSpeicheradressen()[16].getRegister()[6];

        Register optionRegister = speicher.getSpeicheradressen()[16].getRegister()[1];

        portB4Neu = bRegister.getBits()[4].getPin();
        portB5Neu = bRegister.getBits()[5].getPin();
        portB6Neu = bRegister.getBits()[6].getPin();
        portB7Neu = bRegister.getBits()[7].getPin();

        portA4Neu = aRegister.getBits()[4].getPin();
        rb0intNeu = bRegister.getBits()[0].getPin();

        // Änderungen bei PortB prüfen und gleichzeitig schauen ob TRIS auf 1 = Input
        if((portB4Alt != portB4Neu) && trisBRegister.getBits()[4].getPin() == 1)
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);
        if((portB5Alt != portB5Neu) && trisBRegister.getBits()[5].getPin() == 1)
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);
        if((portB6Alt != portB6Neu) && trisBRegister.getBits()[6].getPin() == 1)
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);
        if((portB7Alt != portB7Neu) && trisBRegister.getBits()[7].getPin() == 1)
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);

        //Bei Aenderung des rb0/int pruefen, ob steigende oder fallende Flanke
        if (rb0intNeu != rb0intAlt) {

            // Steigende Flanke und INTEDG high
            if ((rb0intNeu > rb0intAlt) && optionRegister.getBits()[6].getPin() == 0) {
                speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[1].setPin(1);
            } else if ((rb0intNeu < rb0intAlt) && optionRegister.getBits()[6].getPin() == 1) {
                speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[1].setPin(1);
            }

            if (optionRegister.getBits()[5].getPin() == 1) {
                // Steigende Flanke an RBO für Timer
                if ((rb0intNeu > rb0intAlt) && optionRegister.getBits()[4].getPin() == 0) {
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
                }

                // Fallende Flanke an RB0 für Timer
                if ((rb0intNeu < rb0intAlt) && optionRegister.getBits()[4].getPin() == 1) {
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
                }
            }
        }
        //Bei Aenderung des a4 pruefen, ob steigende oder fallende Flanke
        else if (portA4Neu != portA4Alt) {
            if (optionRegister.getBits()[5].getPin() == 1) {
                //Steigende Flanke an A$
                if ((portA4Neu > portA4Alt) && optionRegister.getBits()[4].getPin() == 0)
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
                //Fallende Flanke an A4
                if ((portA4Neu < portA4Alt) && optionRegister.getBits()[4].getPin() == 1)
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
            }
        }


        return speicher;
    }


    public static RegisterService getRegisterService() {
        return new RegisterService();
    }

    public static InterruptService getInterruptService() {
        return new InterruptService();
    }

}
