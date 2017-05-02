package picsimulator.services;

import javafx.stage.FileChooser;
import picsimulator.model.Befehl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptrey on 10.04.2017.
 */
public class FileInputService {

    public List<Befehl> importFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LST-Files", "*.LST"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                return new ArrayList<>();
            }
            InputStream inputStream = new FileInputStream(selectedFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<Befehl> befehle = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                Befehl befehl = new Befehl();
                char ch = line.charAt(0);

                if (ch != ' ' && ch != '\t' && ch != '\r' && ch != '\n') {
                    befehl.setAusfuehrbar(true);
                }

                if (befehl.isAusfuehrbar()) {
                    String subline = line.substring(0, 25);
                    subline = subline.replaceAll("\\s+", "");
                    befehl.setZeigernummer(Integer.parseInt(subline.substring(0, 4), 16));
                    befehl.setBefehlscode(subline.substring(4, 8));
                    befehl.setZeilennummer(Integer.parseInt(subline.substring(8, 13)));
                } else {
                    String subline = line.substring(0, 25);
                    subline = subline.replaceAll("\\s+", "");
                    befehl.setBefehlscode("-");
                    befehl.setZeilennummer(Integer.parseInt(subline.substring(0, 5)));
                    befehl.setZeigernummer(0);
                }

                String subline2 = line.substring(25);
                String[] strings = subline2.split(";");
                for (int i = 0; i < strings.length; i++) {
                    if (i == 0) {
                        befehl.setBefehl(strings[0]);
                        continue;
                    }
                    befehl.setKommentar(strings[i]);
                }
                befehle.add(befehl);
            }
            in.close();
            return befehle;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
