package picsimulator.model.befehle;

import picsimulator.model.Speicher;

/**
 * Interface, welches die Struktur der Befehlsklassen vorgibt.
 */
public interface Executable {

    Speicher execute();

}
