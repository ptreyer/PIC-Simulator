package picsimulator.services;

import picsimulator.constants.PicSimulatorConstants;
import picsimulator.model.Bit;
import picsimulator.model.Register;
import picsimulator.model.Speicher;

/**
 * Created by ptrey on 10.04.2017.
 */
public class MemoryInitializerService {

    public Speicher initializeMemory(Speicher speicher){
        Speicher initializedMemory = speicher;
        initializedMemory = initializeRegisterA(initializedMemory);
        initializedMemory = initializeRegisterB(initializedMemory);
        initializedMemory = initializeRegisterStatus(initializedMemory);
        initializedMemory = initializeRegister0007(initializedMemory);
        initializedMemory = initializeRegister0800(initializedMemory);
        initializedMemory = initializeRegister0801(initializedMemory);
        initializedMemory = initializeRegister8001(initializedMemory);
        initializedMemory = initializeRegister8003(initializedMemory);
        initializedMemory = initializeRegister8005(initializedMemory);
        initializedMemory = initializeRegister8006(initializedMemory);
        return initializedMemory;
    }

    private Speicher initializeRegisterA(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 1);
        bits[1] = new Bit( 0, 1);
        bits[2] = new Bit( 0, 1);
        bits[3] = new Bit( 0, 1);
        bits[4] = new Bit( 0, 1);
        bits[5] = new Bit( 0, 1);
        bits[6] = new Bit( 0, 1);
        bits[7] = new Bit( 1, 1);
        speicher.getSpeicheradressen()[0].getRegister()[5].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegisterB(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 1);
        bits[1] = new Bit( 0, 1);
        bits[2] = new Bit( 0, 1);
        bits[3] = new Bit( 0, 1);
        bits[4] = new Bit( 0, 1);
        bits[5] = new Bit( 0, 1);
        bits[6] = new Bit( 0, 1);
        bits[7] = new Bit( 1, 1);
        speicher.getSpeicheradressen()[0].getRegister()[6].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegisterStatus(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 0);
        bits[1] = new Bit( 0, 0);
        bits[2] = new Bit( 0, 0);
        bits[3] = new Bit( 1, 0);
        bits[4] = new Bit( 1, 0);
        bits[5] = new Bit( 0, 0);
        bits[6] = new Bit( 0, 0);
        bits[7] = new Bit( 0, 0);
        speicher.getSpeicheradressen()[0].getRegister()[3].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister0007(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 0);
        bits[1] = new Bit( 0, 0);
        bits[2] = new Bit( 0, 0);
        bits[3] = new Bit( 0, 0);
        bits[4] = new Bit( 0, 0);
        bits[5] = new Bit( 0, 0);
        bits[6] = new Bit( 0, 0);
        bits[7] = new Bit( 1, 0);
        speicher.getSpeicheradressen()[0].getRegister()[7].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister0800(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 0);
        bits[1] = new Bit( 0, 0);
        bits[2] = new Bit( 0, 0);
        bits[3] = new Bit( 0, 0);
        bits[4] = new Bit( 0, 0);
        bits[5] = new Bit( 0, 0);
        bits[6] = new Bit( 0, 0);
        bits[7] = new Bit( 1, 0);
        speicher.getSpeicheradressen()[1].getRegister()[0].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister0801(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 0);
        bits[1] = new Bit( 0, 0);
        bits[2] = new Bit( 0, 0);
        bits[3] = new Bit( 0, 0);
        bits[4] = new Bit( 0, 0);
        bits[5] = new Bit( 0, 0);
        bits[6] = new Bit( 0, 0);
        bits[7] = new Bit( 1, 0);
        speicher.getSpeicheradressen()[1].getRegister()[1].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister8001(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 1, 0);
        bits[1] = new Bit( 1, 0);
        bits[2] = new Bit( 1, 0);
        bits[3] = new Bit( 1, 0);
        bits[4] = new Bit( 1, 0);
        bits[5] = new Bit( 1, 0);
        bits[6] = new Bit( 1, 0);
        bits[7] = new Bit( 1, 0);
        speicher.getSpeicheradressen()[16].getRegister()[1].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister8003(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 0, 0);
        bits[1] = new Bit( 0, 0);
        bits[2] = new Bit( 0, 0);
        bits[3] = new Bit( 1, 0);
        bits[4] = new Bit( 1, 0);
        bits[5] = new Bit( 0, 0);
        bits[6] = new Bit( 0, 0);
        bits[7] = new Bit( 0, 0);
        speicher.getSpeicheradressen()[16].getRegister()[3].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister8005(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 1, 0);
        bits[1] = new Bit( 1, 0);
        bits[2] = new Bit( 1, 0);
        bits[3] = new Bit( 1, 0);
        bits[4] = new Bit( 1, 0);
        bits[5] = new Bit( 1, 0);
        bits[6] = new Bit( 1, 0);
        bits[7] = new Bit( 1, 0);
        speicher.getSpeicheradressen()[16].getRegister()[5].setBits(bits);
        return speicher;
    }

    private Speicher initializeRegister8006(Speicher speicher){
        Bit[] bits = new Bit[8];
        bits[0] = new Bit( 1, 0);
        bits[1] = new Bit( 1, 0);
        bits[2] = new Bit( 1, 0);
        bits[3] = new Bit( 1, 0);
        bits[4] = new Bit( 1, 0);
        bits[5] = new Bit( 1, 0);
        bits[6] = new Bit( 1, 0);
        bits[7] = new Bit( 1, 0);
        speicher.getSpeicheradressen()[16].getRegister()[6].setBits(bits);
        return speicher;
    }


}
