package picsimulator.model;

/**
 * Datenobjekt, welches einzelne Bits repräsentiert.
 */
public class Bit {

    private int pin;
    private int state;

    public Bit(int pin, int state) {
        this.pin = pin;
        this.state = state;
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
