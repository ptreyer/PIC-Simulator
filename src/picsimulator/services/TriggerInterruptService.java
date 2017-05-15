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

    //RB0
    private static int rb0intAlt = 0;
    private static int rb0intNeu = 0;

    //Alte Werte der verschiedenen Faktoren fuer einen spaeteren Vergleich erhalten
    public static void saveOldValues(Speicher speicher) {
        Register optionRegister = speicher.getSpeicheradressen()[0].getRegister()[6];

        StringBuilder builder = new StringBuilder();
        builder.append(optionRegister.getBits()[7].getPin());
        builder.append(optionRegister.getBits()[6].getPin());
        builder.append(optionRegister.getBits()[5].getPin());
        builder.append(optionRegister.getBits()[5].getPin());

        portBAlt = getRegisterService().binToInt(builder.toString());
        rb0intAlt = optionRegister.getBits()[0].getPin();
    }

    public static Speicher analyseNewValues(Speicher speicher) {
        Register optionRegister = speicher.getSpeicheradressen()[0].getRegister()[6];

        StringBuilder builder = new StringBuilder();
        builder.append(optionRegister.getBits()[7].getPin());
        builder.append(optionRegister.getBits()[6].getPin());
        builder.append(optionRegister.getBits()[5].getPin());
        builder.append(optionRegister.getBits()[5].getPin());

        portBNeu = getRegisterService().binToInt(builder.toString());
        rb0intNeu = optionRegister.getBits()[0].getPin();

        //Bei Aenderung RBIF Bit setzen
        if (portBNeu != portBAlt) {
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);
        } else {
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(0);
        }

        //Bei Aenderung des rb0/int pruefen, ob steigende oder fallende Flanke
        if (rb0intNeu != rb0intAlt) {

            if (optionRegister.getBits()[5].getPin() == 1) {

                //Steigende Flanke
                if ((rb0intNeu > rb0intAlt) && optionRegister.getBits()[4].getPin() == 0) {
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);


                }
                //Fallende Flanke
                else if ((rb0intNeu < rb0intAlt) && optionRegister.getBits()[6].getPin() == 1) {
                    return getInterruptService().checkForTMR0TimerInterrupt(speicher, 1);
                }
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
