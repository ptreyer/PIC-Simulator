package picsimulator.services;

import picsimulator.constants.PicSimulatorConstants;
import picsimulator.model.Bit;
import picsimulator.model.Register;

/**
 * Created by ptrey on 10.04.2017.
 */
public class MemoryInitializerService {

    public Register initializeRegisterA(){
        Register registerA = new Register(PicSimulatorConstants.REGISTER_A);
        Bit[] bits = registerA.getBits();
        bits[0] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[1] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[2] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[3] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[4] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[5] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[6] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[7] = new Bit(Byte.parseByte("00"), 0, 0);
        return registerA;
    }

    public Register initializeRegisterB(){
        Register registerB = new Register(PicSimulatorConstants.REGISTER_B);
        Bit[] bits = registerB.getBits();
        bits[0] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[1] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[2] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[3] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[4] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[5] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[6] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[7] = new Bit(Byte.parseByte("00"), 0, 1);
        return registerB;

    }

    public Register initializeRegisterStatus(){
        Register registerStatus = new Register(PicSimulatorConstants.REGISTER_STATUS);
        Bit[] bits = registerStatus.getBits();
        bits[0] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[1] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[2] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[3] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[4] = new Bit(Byte.parseByte("00"), 0, 1);
        bits[5] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[6] = new Bit(Byte.parseByte("00"), 0, 0);
        bits[7] = new Bit(Byte.parseByte("00"), 0, 0);
        return registerStatus;
    }

}
