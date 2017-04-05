package picsimulator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.*;

public class Controller {

    @FXML
    private ListView listViewFileContent;

    public void openFile(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LST-Files", "*.LST"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile == null) {
                return;
            }
            listViewFileContent.getItems().clear();
            InputStream inputStream = new FileInputStream(selectedFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = in.readLine()) != null)
                listViewFileContent.getItems().add(line);
            in.close();



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
