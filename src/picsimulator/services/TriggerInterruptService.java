package picsimulator.services;

import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by Edeka on 13.05.2017.
 */
public class TriggerInterruptService {

    //Variablen zum pruefen verschiedener Bestandteile auf eine Aenderung

    //PortB
    private static short portBAlt = 0;
    private static short portBNeu = 0;

    //RB0
    private static short rb0intAlt = 0;
    private static short rb0intNeu = 0;

    //Timer0
    private static short timer0Alt = 0;
    private static short timer0Neu = 0;

    //PCL
    public static short pclAlt = 0;
    public static short pclNeu = 0;

    //Speichert die Taktzyklen zum Zeitpunkt eines Interrupts (nach Interrupt +2)
    public static int cyclesAlt = 0;

    //Alte Werte der verschiedenen Faktoren fuer einen spaeteren Vergleich erhalten
    public static void saveOldValues(Speicher speicher) {
        portBAlt = (short) (speicher.getSpeicheradressen()[0].getRegister()[6].getIntWert() & 240);
        rb0intAlt = (short) (speicher.getSpeicheradressen()[0].getRegister()[6].getIntWert() & 1);
        timer0Alt = (short) (speicher.getSpeicheradressen()[0].getRegister()[1].getIntWert() & 255);
        pclAlt = (short) (speicher.getSpeicheradressen()[0].getRegister()[2].getIntWert() & 255);
    }

    public static Speicher analyseNewValues(Speicher speicher) {
        portBNeu = (short) (speicher.getSpeicheradressen()[0].getRegister()[6].getIntWert() & 240);
        rb0intNeu = (short) (speicher.getSpeicheradressen()[0].getRegister()[6].getIntWert() & 1);
        timer0Neu = (short) (speicher.getSpeicheradressen()[0].getRegister()[1].getIntWert() & 255);
        pclNeu = (short) (speicher.getSpeicheradressen()[0].getRegister()[2].getIntWert() & 255);

        //Bei Aenderung RBIF Bit setzen
        if (portBNeu != portBAlt) {
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].setPin(1);
        }

        //Bei Aenderung des rb0/int pruefen, ob steigende oder fallende Flanke
        if (rb0intNeu != rb0intAlt) {

            //Steigende Flanke
            if ((rb0intNeu > rb0intAlt) && speicher.getSpeicheradressen()[16].getRegister()[1].getBits()[6].getPin() == 1) {
                speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[1].setPin(1);
            }
            //Fallende Flanke
            else if ((rb0intNeu < rb0intAlt) && speicher.getSpeicheradressen()[16].getRegister()[1].getBits()[6].getPin() == 0) {
                speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[1].setPin(1);
            }
        }

        //Nur, wenn der Prescaler TMR0 zugewiesen ist
        if (speicher.getSpeicheradressen()[16].getRegister()[1].getBits()[3].getPin() == 0) {
            //Wird der Timer direkt gesetzt, wird der cycles_counter zurueckgesetzt
            if (timer0Neu != timer0Alt) {
                //TODO
                //Cycles Counter Reset
                //Timer0.cycles_counter = 0;
                //Timer0 aus Speicher lesen
                //Timer0.speicherInTimer();
            }
        }
        if (speicher.getSpeicheradressen()[16].getRegister()[1].getBits()[5].getPin() == 0) {
            //TODO
            //Cycles Counter erhöhen
            //Timer0.cycles_counter += (taktzyklenNeu - taktzyklenAlt);
        }

        //Programcounter zusammensetzen oder PCL aktualisiseren
        //TODO
        //Programcounter.pcZusammensetzen();

        return speicher;
    }

}
