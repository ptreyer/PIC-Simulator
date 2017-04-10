package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Bit {

    private byte wert;
    private int pin;
    private int state;

    public Bit(byte wert, int pin, int state) {
        this.wert = wert;
        this.pin = pin;
        this.state = state;
    }

    public String getWert() {
        return String.format("%02X ", wert);
    }

    public void setWert(byte wert) {
        this.wert = wert;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
