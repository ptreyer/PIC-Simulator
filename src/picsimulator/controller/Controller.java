package picsimulator.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import picsimulator.model.*;
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

    @FXML
    private TableView<Speicheradresse> tableMemory;
    @FXML
    private TableColumn<Speicheradresse, String> tableColumnMemoryName;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit0;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit1;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit2;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit3;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit4;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit5;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit6;
    @FXML
    private TableColumn<Bit, Byte> tableColumnMemoryBit7;

    private FileInputService fileInputService;
    private MemoryInitializerService memoryInitializerService;
    private List<Befehl> befehle;
    private Speicher speicher;
    private Register registerA;
    private Register registerB;
    private Register registerStatus;
    private int currentRow = 1;

    public void openFile(ActionEvent actionEvent) {
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("befehlscode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("kommentar"));
        befehle = getFileInputService().importFile();
        initializeMemory();
        tableFileContent.getItems().addAll(befehle);
        tableFileContent.setRowFactory(tv -> new TableRow<Befehl>() {
            @Override
            public void updateItem(Befehl item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getZeilennummer() == currentRow) {
                    setStyle("-fx-background-color: #f5ff5c;");
                } else {
                    setStyle("");
                }
            }
        });
    }


    public void run(ActionEvent actionEvent) {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }
        Thread th = new Thread(() -> {
            try {
                for (currentRow = 1; currentRow <= befehle.size(); currentRow++) {
                    Befehl befehl = tableFileContent.getItems().get(currentRow - 1);
                    if (befehl.isAusfuehrbar()) {
                        System.out.println(befehl.getBefehlscode());
                    }
                    Platform.runLater(() -> tableFileContent.refresh());
                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th.setDaemon(true);
        th.start();
    }

    public void next(ActionEvent actionEvent) {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }
        currentRow++;
        tableFileContent.refresh();
    }

    public void reset(ActionEvent actionEvent) {
        currentRow = 1;
        initializeMemory();
        tableFileContent.refresh();
    }

    public void clear(ActionEvent actionEvent) {
        tableFileContent.getItems().clear();
        tableMemory.getItems().clear();
    }

    private void initializeMemory() {
        speicher = new Speicher();
        registerA = getMemoryInitializerService().initializeRegisterA();
        registerB = getMemoryInitializerService().initializeRegisterB();
        registerStatus = getMemoryInitializerService().initializeRegisterStatus();
        tableColumnMemoryName.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tableColumnMemoryBit0.setCellValueFactory(new PropertyValueFactory<>("bit0Wert"));
        tableColumnMemoryBit1.setCellValueFactory(new PropertyValueFactory<>("bit1Wert"));
        tableColumnMemoryBit2.setCellValueFactory(new PropertyValueFactory<>("bit2Wert"));
        tableColumnMemoryBit3.setCellValueFactory(new PropertyValueFactory<>("bit3Wert"));
        tableColumnMemoryBit4.setCellValueFactory(new PropertyValueFactory<>("bit4Wert"));
        tableColumnMemoryBit5.setCellValueFactory(new PropertyValueFactory<>("bit5Wert"));
        tableColumnMemoryBit6.setCellValueFactory(new PropertyValueFactory<>("bit6Wert"));
        tableColumnMemoryBit7.setCellValueFactory(new PropertyValueFactory<>("bit7Wert"));
        tableMemory.getItems().setAll(speicher.getSpeicheradressen());
    }

    private MemoryInitializerService getMemoryInitializerService() {
        if (memoryInitializerService == null) {
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
