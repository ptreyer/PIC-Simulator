package picsimulator.services;

import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by Edeka on 13.05.2017.
 */
public class TriggerInterruptService {

    //Variablen zum pruefen verschiedener Bestandteile auf eine Aenderung

    //PortB
    private static int portBAlt = 0;
    private static int portBNeu = 0;

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

        StringBuilder builder = new StringBuilder();
        builder.append(bRegister.getBits()[7].getPin());
        builder.append(bRegister.getBits()[6].getPin());
        builder.append(bRegister.getBits()[5].getPin());
        builder.append(bRegister.getBits()[5].getPin());

        portBAlt = getRegisterService().binToInt(builder.toString());
        portA4Alt = aRegister.getBits()[4].getPin();
        rb0intAlt = bRegister.getBits()[0].getPin();
    }

    public static Speicher analyseNewValues(Speicher speicher) {
        Register aRegister = speicher.getSpeicheradressen()[0].getRegister()[5];
        Register bRegister = speicher.getSpeicheradressen()[0].getRegister()[6];
        Register optionRegister = speicher.getSpeicheradressen()[16].getRegister()[1];

        StringBuilder builder = new StringBuilder();
        builder.append(bRegister.getBits()[7].getPin());
        builder.append(bRegister.getBits()[6].getPin());
        builder.append(bRegister.getBits()[5].getPin());
        builder.append(bRegister.getBits()[5].getPin());


        portBNeu = getRegisterService().binToInt(builder.toString());
        portA4Neu = aRegister.getBits()[4].getPin();
        rb0intNeu = bRegister.getBits()[0].getPin();

        //Bei Aenderung RBIF Bit setzen
        if (portBNeu != portBAlt) {
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);
        } else {
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(0);
        }

        //Bei Aenderung des rb0/int pruefen, ob steigende oder fallende Flanke
        if (rb0intNeu != rb0intAlt) {
            if (optionRegister.getBits()[5].getPin() == 1) {
                // Steigende Flanke an RBO
                if ((rb0intNeu > rb0intAlt) && optionRegister.getBits()[4].getPin() == 0)
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
                    // Fallende Flanke an RB0
                else if ((rb0intNeu < rb0intAlt) && optionRegister.getBits()[4].getPin() == 1)
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
            }
        }
        //Bei Aenderung des a4 pruefen, ob steigende oder fallende Flanke
        else if (portA4Neu != portA4Alt) {
            if (optionRegister.getBits()[5].getPin() == 1) {
                //Steigende Flanke an A$
                if ((portA4Neu > portA4Alt) && optionRegister.getBits()[4].getPin() == 0)
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
                    //Fallende Flanke an A4
                else if ((portA4Neu < portA4Alt) && optionRegister.getBits()[4].getPin() == 1)
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
