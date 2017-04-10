package picsimulator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import picsimulator.model.Befehl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private TableView<Befehl> tableFileContent;
    @FXML
    private TableColumn<Befehl, Integer> tableColumnZeilennummer;
    @FXML
    private TableColumn<Befehl, String> tableColumnBefehlscode;
    @FXML
    private TableColumn<Befehl, String> tableColumnBefehl;
    @FXML
    private TableColumn<Befehl, String> tableColumnKommentar;

    public void openFile(ActionEvent actionEvent) {
        try {
            tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
            tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("befehlscode"));
            tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
            tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("kommentar"));

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LST-Files", "*.LST"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile == null) {
                return;
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
                    befehl.setBefehlscode(subline.substring(4, 8));
                    befehl.setZeilennummer(Integer.parseInt(subline.substring(8, 13)));
                } else {
                    String subline = line.substring(0, 25);
                    subline = subline.replaceAll("\\s+", "");
                    befehl.setBefehlscode("-");
                    befehl.setZeilennummer(Integer.parseInt(subline.substring(0, 5)));
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

            tableFileContent.getItems().addAll(befehle);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
