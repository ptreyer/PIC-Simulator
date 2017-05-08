package picsimulator.services;

import picsimulator.model.Bit;
import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by Edeka on 08.05.2017.
 */
public class InterruptService {

    public boolean checkInterrupt(Speicher speicher) {
        boolean interrupt = false;

        if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[7].getPin() == 1) {
            if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[2].getPin() == 1 && speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[5].getPin() == 1) {
                interrupt = true;
            }
            if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[1].getPin() == 1 && speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[4].getPin() == 1) {
                interrupt = true;
            }
            if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[0].getPin() == 1 && speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[3].getPin() == 1) {
                interrupt = true;
            }
            if (speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[6].getPin() == 1 && speicher.getSpeicheradressen()[17].getRegister()[0].getBits()[4].getPin() == 1) {
                interrupt = true;
            }
        }
        return interrupt;
    }

}

