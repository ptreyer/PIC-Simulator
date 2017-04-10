package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Register {

    private String name;
    private Bit[] bits;

    public Register(String name){
        this.name = name;
        this.bits = new Bit[8];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bit[] getBits() {
        return bits;
    }

    public void setBits(Bit[] bits) {
        this.bits = bits;
    }
}
