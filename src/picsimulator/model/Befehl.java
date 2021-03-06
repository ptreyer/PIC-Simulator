package picsimulator.model;

/**
 * Klasse Befehl, auf welche dir importierten LST-Files gemapped werden.
 */
public class Befehl {

    private boolean breakpoint;
    private boolean ausfuehrbar;
    private int zeilennummer;
    private int zeigernummer;
    private String befehlscode;
    private String befehl;
    private String kommentar;

    public boolean isBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
    }

    public boolean isAusfuehrbar() {
        return ausfuehrbar;
    }

    public void setAusfuehrbar(boolean ausfuehrbar) {
        this.ausfuehrbar = ausfuehrbar;
    }

    public int getZeilennummer() {
        return zeilennummer;
    }

    public void setZeilennummer(int zeilennummer) {
        this.zeilennummer = zeilennummer;
    }

    public int getZeigernummer() {
        return zeigernummer;
    }

    public void setZeigernummer(int zeigernummer) {
        this.zeigernummer = zeigernummer;
    }

    public String getBefehlscode() {
        return befehlscode;
    }

    public void setBefehlscode(String befehlscode) {
        this.befehlscode = befehlscode;
    }

    public String getBefehl() {
        return befehl;
    }

    public void setBefehl(String befehl) {
        this.befehl = befehl;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
