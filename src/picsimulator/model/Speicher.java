package picsimulator.model;

/**
 * Created by ptrey on 10.04.2017.
 */
public class Speicher {

    private Speicheradresse[] speicheradressen;

    public Speicher(){
        this.speicheradressen = new Speicheradresse[32];
        int adresse = 0;
        for (int i = 0; i<speicheradressen.length; i++) {
            speicheradressen[i] = new Speicheradresse(adresse);
            adresse = adresse + 8;
        }
    }

    public Speicheradresse[] getSpeicheradressen() {
        return speicheradressen;
    }

    public void setSpeicheradressen(Speicheradresse[] speicheradressen) {
        this.speicheradressen = speicheradressen;
    }
}
