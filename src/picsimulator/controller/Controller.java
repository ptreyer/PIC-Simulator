package picsimulator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import picsimulator.constants.PicSimulatorConstants;
import picsimulator.model.Befehl;
import picsimulator.model.Register;
import picsimulator.model.Speicher;
import picsimulator.services.FileInputService;
import picsimulator.services.MemoryInitializerService;

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

    private FileInputService fileInputService;
    private MemoryInitializerService memoryInitializerService;
    private List<Befehl> befehle;
    private Speicher speicher;
    private Register registerA;
    private Register registerB;
    private Register registerStatus;

    public void openFile(ActionEvent actionEvent) {
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("befehlscode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("kommentar"));
        befehle = getFileInputService().importFile();
        initializeMemory();
        tableFileContent.getItems().addAll(befehle);
    }

    private void initializeMemory() {
        speicher = new Speicher();
        registerA = getMemoryInitializerService().initializeRegisterA();
        registerB = getMemoryInitializerService().initializeRegisterB();
        registerStatus = getMemoryInitializerService().initializeRegisterStatus();
    }

    private MemoryInitializerService getMemoryInitializerService() {
        if(memoryInitializerService == null){
            memoryInitializerService = new MemoryInitializerService();
        }
        return memoryInitializerService;
    }

    private FileInputService getFileInputService() {
        if (fileInputService == null) {
            fileInputService = new FileInputService();
        }
        return fileInputService;
    }
}
