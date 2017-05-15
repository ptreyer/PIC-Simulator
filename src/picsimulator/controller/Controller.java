package picsimulator.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import picsimulator.constants.PicSimulatorConstants;
import picsimulator.model.*;
import picsimulator.services.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Controller {

    @FXML
    private TableView<Befehl> tableFileContent;

    @FXML
    private TableColumn<Befehl, CheckBox> tableColumnBreakpoint;
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

    @FXML
    private TableView<StackEintrag> tableStack;
    @FXML
    private TableColumn<StackEintrag, String> tableColumnStack;

    @FXML
    private ToggleButton b0;
    @FXML
    private ToggleButton b1;
    @FXML
    private ToggleButton b2;
    @FXML
    private ToggleButton b3;
    @FXML
    private ToggleButton b4;
    @FXML
    private ToggleButton b5;
    @FXML
    private ToggleButton b6;
    @FXML
    private ToggleButton b7;

    @FXML
    private ToggleButton a0;
    @FXML
    private ToggleButton a1;
    @FXML
    private ToggleButton a2;
    @FXML
    private ToggleButton a3;
    @FXML
    private ToggleButton a4;
    @FXML
    private ToggleButton a5;
    @FXML
    private ToggleButton a6;
    @FXML
    private ToggleButton a7;

    @FXML
    private TextField WREG;
    @FXML
    private TextField FSR;
    @FXML
    private TextField PCL;
    @FXML
    private TextField PCLATH;
    @FXML
    private TextField PC;
    @FXML
    private TextField STATUS;
    @FXML
    private TextField Carry;
    @FXML
    private TextField DigitCarry;
    @FXML
    private TextField Zero;
    @FXML
    private TextField PD;
    @FXML
    private TextField TO;
    @FXML
    private TextField RP0;
    @FXML
    private TextField RP1;
    @FXML
    private TextField IRP;

    private FileInputService fileInputService;
    private MemoryInitializerService memoryInitializerService;
    private RegisterService registerService;
    private BefehlSteuerungService befehlSteuerungService;
    private InterruptService interruptService;
    private List<Befehl> befehle;
    private Speicher speicher;

    private int currentRow = 1;
    private boolean run = false;
    public static int runtime = 0;

    public void openFile() {
        tableColumnBreakpoint.setCellValueFactory(arg0 -> {
            Befehl befehl = arg0.getValue();
            if (!befehl.isAusfuehrbar()) return null;
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().setValue(befehl.isBreakpoint());
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> befehl.setBreakpoint(new_val));
            return new SimpleObjectProperty<>(checkBox);
        });
        tableColumnZeilennummer.setCellValueFactory(new PropertyValueFactory<>("zeilennummer"));
        tableColumnBefehlscode.setCellValueFactory(new PropertyValueFactory<>("befehlscode"));
        tableColumnBefehl.setCellValueFactory(new PropertyValueFactory<>("befehl"));
        tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<>("kommentar"));
        if (befehle != null) {
            befehle.clear();
            tableFileContent.getItems().clear();
            tableFileContent.refresh();
        }
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
        updateView();
    }

    public void run() {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }
        run = true;
        Thread th = new Thread(() -> {
            try {
                while (run) {
                    int pcl = speicher.getSpeicheradressen()[0].getRegister()[2].getIntWert();
                    for (Befehl befehl : befehle) {
                        if (befehl.getZeigernummer() == pcl && befehl.isAusfuehrbar()) {
                            currentRow = befehl.getZeilennummer();
                            Platform.runLater(() -> tableFileContent.scrollTo(currentRow - 2));
                            if (befehl.isBreakpoint()) {
                                run = false;
                            }
                            TriggerInterruptService.saveOldValues(speicher);
                            if (execute(befehl)) break;
                            Platform.runLater(() -> updateView());
                            Thread.sleep(50);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th.setDaemon(true);
        th.start();
    }

    private boolean execute(Befehl befehl) {
        String binaryString = getRegisterService().hexToBin(befehl.getBefehlscode());
        if (!speicher.isSleepModus()) {
            speicher = getBefehlSteuerungService().steuereBefehl(speicher, binaryString);
            speicher = TriggerInterruptService.analyseNewValues(speicher);
            if (checkInterrupt()) return true;
        }
        if (speicher.isWatchdogTimerEnabled()) {
            speicher = TriggerInterruptService.analyseNewValues(speicher);
            speicher = getInterruptService().checkForWatchDogInterrupt(speicher);
            if (checkInterrupt()) return true;
        }
        return false;
    }

    private boolean checkInterrupt() {
        speicher.setInterrupt(false);
        if (!speicher.isSleepModus()) {
            speicher = getInterruptService().checkForPortBInterrupt(speicher);
            speicher = getInterruptService().checkForINTInterrupt(speicher);
            System.out.println("11111: " + speicher.isInterrupt());
            speicher = getInterruptService().checkForTMR0TimerInterrupt(speicher);
            System.out.println("22222: " + speicher.isInterrupt());
        }
        debugTimer();
        if (speicher.isInterrupt()) {
            System.out.println("INTERRUPT");
            updateView();
            Register pclReg = speicher.getSpeicheradressen()[0].getRegister()[2];
            // TODO Referenz pruefen
            StackEintrag stackEintrag = new StackEintrag();
            stackEintrag.setWert(new Integer(pclReg.getIntWert()+1));
            speicher.addToStack(stackEintrag);
            speicher.getSpeicheradressen()[0].getRegister()[2].setWert(4);
            // Interrupt deaktivieren
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[7].setPin(0);
            return true;
        }
        return false;
    }

    public static void increaseRuntime() {
        runtime++;
    }

    private void debugTimer() {
        System.out.println("------------1--------------");
        System.out.println("Laufzeit: " + runtime);
        System.out.println("Prescaler: " + speicher.getPrescaler());
        System.out.println("Prescaler-MAX: " + speicher.getPrescalerMaxValue());
        System.out.println("TMR0: " + speicher.getTimer0().getIntWert());
        System.out.println("WatchdogTimer: " + speicher.getWatchdogTimer());
        System.out.println("WatchdogTimerEnabled: " + speicher.isWatchdogTimerEnabled());
        System.out.println("SLEEP: " + speicher.isSleepModus());
        System.out.println("------------2-------------");
    }

    public void next() {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }
        if (run) {
            return;
        }
        int pcl = speicher.getSpeicheradressen()[0].getRegister()[2].getIntWert();
        for (Befehl befehl : befehle) {
            if (befehl.getZeigernummer() == pcl && befehl.isAusfuehrbar()) {
                currentRow = befehl.getZeilennummer();
                tableFileContent.scrollTo(currentRow - 2);
                TriggerInterruptService.saveOldValues(speicher);
                if (execute(befehl)) break;
                debugTimer();
                updateView();
            }
        }
    }

    private void updateView() {
        tableFileContent.refresh();
        tableMemory.refresh();

        tableStack.getItems().clear();
        tableStack.getItems().addAll(speicher.getStack());

        WREG.setText(getRegisterService().intToHex(speicher.getRegisterW()));
        FSR.setText(speicher.getSpeicheradressen()[0].getRegister()[4].getHexWert());
        PCL.setText(speicher.getSpeicheradressen()[0].getRegister()[2].getHexWert());
        PCLATH.setText(speicher.getSpeicheradressen()[1].getRegister()[2].getHexWert());
        int pc = speicher.getSpeicheradressen()[1].getRegister()[2].getIntWert() + speicher.getSpeicheradressen()[0].getRegister()[2].getIntWert();
        PC.setText(getRegisterService().intToHex(pc));
        Register statusReg = speicher.getSpeicheradressen()[0].getRegister()[3];
        STATUS.setText(statusReg.getHexWert());
        Carry.setText(Integer.toString(statusReg.getBits()[0].getPin()));
        DigitCarry.setText(Integer.toString(statusReg.getBits()[1].getPin()));
        Zero.setText(Integer.toString(statusReg.getBits()[2].getPin()));
        PD.setText(Integer.toString(statusReg.getBits()[3].getPin()));
        TO.setText(Integer.toString(statusReg.getBits()[4].getPin()));
        RP0.setText(Integer.toString(statusReg.getBits()[5].getPin()));
        RP1.setText(Integer.toString(statusReg.getBits()[6].getPin()));
        IRP.setText(Integer.toString(statusReg.getBits()[7].getPin()));
    }

    public void stop() {
        run = false;
    }

    public void reset() {
        run = false;
        currentRow = 1;
        runtime = 0;
        initializeMemory();
        setRegisterA();
        setRegisterB();
        updateView();
        tableFileContent.refresh();
    }

    public void clear() {
        run = false;
        runtime = 0;
        initializeMemory();
        setRegisterA();
        setRegisterB();
        updateView();
        tableFileContent.getItems().clear();
        tableMemory.getItems().clear();
    }

    private void initializeMemory() {
        speicher = new Speicher();
        speicher = getMemoryInitializerService().initializeMemory(speicher);
        tableColumnMemoryName.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tableColumnMemoryBit0.setCellValueFactory(new PropertyValueFactory<>("register0Wert"));
        tableColumnMemoryBit1.setCellValueFactory(new PropertyValueFactory<>("register1Wert"));
        tableColumnMemoryBit2.setCellValueFactory(new PropertyValueFactory<>("register2Wert"));
        tableColumnMemoryBit3.setCellValueFactory(new PropertyValueFactory<>("register3Wert"));
        tableColumnMemoryBit4.setCellValueFactory(new PropertyValueFactory<>("register4Wert"));
        tableColumnMemoryBit5.setCellValueFactory(new PropertyValueFactory<>("register5Wert"));
        tableColumnMemoryBit6.setCellValueFactory(new PropertyValueFactory<>("register6Wert"));
        tableColumnMemoryBit7.setCellValueFactory(new PropertyValueFactory<>("register7Wert"));
        setRegisterA();
        setRegisterB();
        tableMemory.getItems().setAll(speicher.getSpeicheradressen());
        tableColumnStack.setCellValueFactory(new PropertyValueFactory<>("hexWert"));
        runtime = 0;
    }

    public void toggleA0() {
        Bit bit0 = getRegisterA().getBits()[0];
        getRegisterA().getBits()[0] = getRegisterService().toggleBit(bit0);
        tableMemory.refresh();
        a0.setText(String.valueOf(getRegisterA().getBits()[0].getPin()));
    }

    public void toggleA1() {
        Bit bit1 = getRegisterA().getBits()[1];
        getRegisterA().getBits()[1] = getRegisterService().toggleBit(bit1);
        tableMemory.refresh();
        a1.setText(String.valueOf(getRegisterA().getBits()[1].getPin()));
    }

    public void toggleA2() {
        Bit bit2 = getRegisterA().getBits()[2];
        getRegisterA().getBits()[2] = getRegisterService().toggleBit(bit2);
        tableMemory.refresh();
        a2.setText(String.valueOf(getRegisterA().getBits()[2].getPin()));
    }

    public void toggleA3() {
        Bit bit3 = getRegisterA().getBits()[3];
        getRegisterA().getBits()[3] = getRegisterService().toggleBit(bit3);
        tableMemory.refresh();
        a3.setText(String.valueOf(getRegisterA().getBits()[3].getPin()));
    }

    public void toggleA4() {
        Bit bit4 = getRegisterA().getBits()[4];
        getRegisterA().getBits()[4] = getRegisterService().toggleBit(bit4);
        tableMemory.refresh();
        a4.setText(String.valueOf(getRegisterA().getBits()[4].getPin()));
    }

    public void toggleA5() {
        Bit bit5 = getRegisterA().getBits()[5];
        getRegisterA().getBits()[5] = getRegisterService().toggleBit(bit5);
        tableMemory.refresh();
        a5.setText(String.valueOf(getRegisterA().getBits()[5].getPin()));
    }

    public void toggleA6() {
        Bit bit6 = getRegisterA().getBits()[6];
        getRegisterA().getBits()[6] = getRegisterService().toggleBit(bit6);
        tableMemory.refresh();
        a6.setText(String.valueOf(getRegisterA().getBits()[6].getPin()));
    }

    public void toggleA7() {
        Bit bit7 = getRegisterA().getBits()[7];
        getRegisterA().getBits()[7] = getRegisterService().toggleBit(bit7);
        tableMemory.refresh();
        a7.setText(String.valueOf(getRegisterA().getBits()[7].getPin()));
    }

    public void toggleB0() {
        Bit bit0 = speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[0];
        speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[0] = getRegisterService().toggleBit(bit0);
        tableMemory.refresh();
        b0.setText(String.valueOf(getRegisterB().getBits()[0].getPin()));
    }

    public void toggleB1() {
        Bit bit1 = speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[1];
        speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[1] = getRegisterService().toggleBit(bit1);
        tableMemory.refresh();
        b1.setText(String.valueOf(getRegisterB().getBits()[1].getPin()));
    }

    public void toggleB2() {
        Bit bit2 = getRegisterB().getBits()[2];
        getRegisterB().getBits()[2] = getRegisterService().toggleBit(bit2);
        tableMemory.refresh();
        b2.setText(String.valueOf(getRegisterB().getBits()[2].getPin()));
    }

    public void toggleB3() {
        Bit bit3 = getRegisterB().getBits()[3];
        getRegisterB().getBits()[3] = getRegisterService().toggleBit(bit3);
        tableMemory.refresh();
        b3.setText(String.valueOf(getRegisterB().getBits()[3].getPin()));
    }

    public void toggleB4() {
        Bit bit4 = getRegisterB().getBits()[4];
        getRegisterB().getBits()[4] = getRegisterService().toggleBit(bit4);
        tableMemory.refresh();
        b4.setText(String.valueOf(getRegisterB().getBits()[4].getPin()));
    }

    public void toggleB5() {
        Bit bit5 = getRegisterB().getBits()[5];
        getRegisterB().getBits()[5] = getRegisterService().toggleBit(bit5);
        tableMemory.refresh();
        b5.setText(String.valueOf(getRegisterB().getBits()[5].getPin()));
    }

    public void toggleB6() {
        Bit bit6 = getRegisterB().getBits()[6];
        getRegisterB().getBits()[6] = getRegisterService().toggleBit(bit6);
        tableMemory.refresh();
        b6.setText(String.valueOf(getRegisterB().getBits()[6].getPin()));
    }

    public void toggleB7() {
        Bit bit7 = getRegisterB().getBits()[7];
        getRegisterB().getBits()[7] = getRegisterService().toggleBit(bit7);
        tableMemory.refresh();
        b7.setText(String.valueOf(getRegisterB().getBits()[7].getPin()));
    }

    private Register getRegisterA() {
        if (speicher == null) {
            initializeMemory();
        }
        return speicher.getSpeicheradressen()[0].getRegister()[5];
    }

    private void setRegisterA() {
        a0.setText(String.valueOf(getRegisterA().getBits()[0].getPin()));
        a1.setText(String.valueOf(getRegisterA().getBits()[1].getPin()));
        a2.setText(String.valueOf(getRegisterA().getBits()[2].getPin()));
        a3.setText(String.valueOf(getRegisterA().getBits()[3].getPin()));
        a4.setText(String.valueOf(getRegisterA().getBits()[4].getPin()));
        a5.setText(String.valueOf(getRegisterA().getBits()[5].getPin()));
        a6.setText(String.valueOf(getRegisterA().getBits()[6].getPin()));
        a7.setText(String.valueOf(getRegisterA().getBits()[7].getPin()));
    }

    private Register getRegisterB() {
        if (speicher == null) {
            initializeMemory();
        }
        return speicher.getSpeicheradressen()[0].getRegister()[6];
    }

    private void setRegisterB() {
        b0.setText(String.valueOf(getRegisterB().getBits()[0].getPin()));
        b1.setText(String.valueOf(getRegisterB().getBits()[1].getPin()));
        b2.setText(String.valueOf(getRegisterB().getBits()[2].getPin()));
        b3.setText(String.valueOf(getRegisterB().getBits()[3].getPin()));
        b4.setText(String.valueOf(getRegisterB().getBits()[4].getPin()));
        b5.setText(String.valueOf(getRegisterB().getBits()[5].getPin()));
        b6.setText(String.valueOf(getRegisterB().getBits()[6].getPin()));
        b7.setText(String.valueOf(getRegisterB().getBits()[7].getPin()));
    }

    public void openDocumentation() {
        try {
            java.awt.Desktop.getDesktop().browse(new URI(PicSimulatorConstants.LINK_DOCUMENTATION));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
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

    private RegisterService getRegisterService() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }

    private BefehlSteuerungService getBefehlSteuerungService() {
        if (befehlSteuerungService == null) {
            befehlSteuerungService = new BefehlSteuerungService();
        }
        return befehlSteuerungService;
    }

    private InterruptService getInterruptService() {
        if (interruptService == null) {
            interruptService = new InterruptService();
        }
        return interruptService;
    }


}
