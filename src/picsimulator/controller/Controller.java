package picsimulator.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Controller, welcher direkt von der grafischen Oberfläche angesteuert wird, mit
 * dieser interagiert und die entsprechende Logik in den Services ansteuert.
 */
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

    @FXML
    private TextField INTCON;
    @FXML
    private TextField GIE;
    @FXML
    private TextField EEIE;
    @FXML
    private TextField T0IE;
    @FXML
    private TextField INTE;
    @FXML
    private TextField RBIE;
    @FXML
    private TextField T0IF;
    @FXML
    private TextField INTF;
    @FXML
    private TextField RBIF;

    @FXML
    private TextField OPTION;
    @FXML
    private TextField RBPU;
    @FXML
    private TextField INTEDG;
    @FXML
    private TextField T0CS;
    @FXML
    private TextField T0SE;
    @FXML
    private TextField PSA;
    @FXML
    private TextField PS2;
    @FXML
    private TextField PS1;
    @FXML
    private TextField PS0;
    @FXML
    private Button btnToggleWDTimer;
    @FXML
    private Button btnToggleTakt;
    @FXML
    private TextField taktfrequenz;
    @FXML
    private TextField laufzeit;
    @FXML
    private TextField taktGenFrequenz;
    @FXML
    private ChoiceBox<String> taktgenerator;

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
    private boolean taktgeneratorEnabled;
    public static double laufzeitCalculated = 0;
    public static double taktfrequenzValue = 4000;

    /**
     * Methode zum Öffnen eines LST-Files. Sollte das File erfolgreich geöffnet werden, wird dies
     * entsprechend ausgewertet und die Felder in der Tabelle dargestellt. Anschließend wird der
     * Speicher initialisiert und die Oberfläche befüllt.
     */
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

        ObservableList<String> ports = FXCollections.observableArrayList(
                "RA0", "RA1", "RA2", "RA3", "RA4", "RA5", "RA6", "RA7", "RB0", "RB1", "RB2", "RB3", "RB4", "RB5", "RB6", "RB7");
        taktgenerator.setItems(ports);
        taktgenerator.setValue("RA0");

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

    /**
     * Solange die Ausführung nicht über den Stop-Button unterbrochen wird, wird das Programm
     * sequentiell bearbeitet. Die Befehle werden ausgeführt, PC und PCLATH gesetzt, Timer erhöht,
     * Interrupt geprüft und die Oberfkäche stetig auktualisiert.
     */
    public void run() {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }
        run = true;
        Thread th = new Thread(() -> {
            try {
                while (run) {
                    int pc = speicher.getProgrammCounter();
                    for (Befehl befehl : befehle) {
                        if (befehl.getZeigernummer() == pc && befehl.isAusfuehrbar()) {
                            currentRow = befehl.getZeilennummer();
                            Platform.runLater(() -> tableFileContent.scrollTo(currentRow - 2));
                            if (befehl.isBreakpoint()) {
                                run = false;
                            }
                            if (execute(befehl)) break;
                            TriggerInterruptService.saveOldValues(speicher);
                            Platform.runLater(this::updateView);
                            Thread.sleep((long) (4000 / Double.parseDouble(taktfrequenz.getText())) * 1000);
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

    /**
     * Navigiert zum nächsten Befehl anhand des PC und führt diesen aus.
     */
    public void next() {
        if (tableFileContent.getItems().isEmpty()) {
            return;
        }
        if (run) {
            return;
        }
        int pc = speicher.getProgrammCounter();
        for (Befehl befehl : befehle) {
            if (befehl.getZeigernummer() == pc && befehl.isAusfuehrbar()) {
                currentRow = befehl.getZeilennummer();
                tableFileContent.scrollTo(currentRow - 2);
                if (execute(befehl)) break;
                TriggerInterruptService.saveOldValues(speicher);
                debugTimer();
                updateView();
            }
        }
    }

    /**
     * Führt den übergebenen Befehl aus. Prüft dabei ob der SleepModus aktiviert ist, bzw der WDT
     * enabled ist. Erhöht dementsprechend die Timer.
     *
     * @param befehl, der Befehl der ausgeführt werden soll.
     * @return boolean, gibt an ob ein Interrupt aufgetreten ist.
     */
    private boolean execute(Befehl befehl) {
        String binaryString = getRegisterService().hexToBin(befehl.getBefehlscode());
        int cycles = 1;
        if (!speicher.isSleepModus()) {
            int cyclesOld = runtime;
            speicher = getBefehlSteuerungService().steuereBefehl(speicher, binaryString);
            speicher = TriggerInterruptService.analyseNewValues(speicher);
            int cyclesNew = runtime;
            cycles = cyclesNew - cyclesOld;
            if (checkInterrupt(cycles)) return true;
        }
        if (speicher.isWatchdogTimerEnabled()) {
            speicher = TriggerInterruptService.analyseNewValues(speicher);
            speicher = getInterruptService().checkForWatchDogInterrupt(speicher, cycles);
            runtime++;
            if (checkInterrupt(1)) return true;
        }
        return false;
    }

    /**
     * Prüft ob ein Interrupt aufgetreten ist. Erhöht hierbei auch den Timer0. Sollte ein Interrupt
     * auftereten sein, wird die Interrupt Service Routine ausgeführt und das Global Interrupt Bit
     * zurückgesetzt.
     *
     * @param cycles Anzahl der Taktzyklen
     * @return boolean, ob Interrupt aufgetreten ist.
     */
    private boolean checkInterrupt(int cycles) {
        speicher.setInterrupt(false);
        if (!speicher.isSleepModus()) {
            speicher = getInterruptService().checkForPortBInterrupt(speicher);
            speicher = getInterruptService().checkForINTInterrupt(speicher);
            if (speicher.getSpeicheradressen()[16].getRegister()[1].getBits()[5].getPin() == 0) {
                speicher = getInterruptService().checkForTMR0TimerInterrupt(speicher, cycles);
            }
        }
        debugTimer();
        if (speicher.isInterrupt()) {
            updateView();
            Register pclReg = speicher.getSpeicheradressen()[0].getRegister()[2];
            StackEintrag stackEintrag = new StackEintrag();
            stackEintrag.setWert(new Integer(pclReg.getIntWert() + 1));
            speicher.addToStack(stackEintrag);
            speicher.getSpeicheradressen()[0].getRegister()[2].setWert(4);
            // Interrupt deaktivieren
            speicher.getSpeicheradressen()[1].getRegister()[3].getBits()[7].setPin(0);
            return true;
        }
        return false;
    }

    /**
     * Erhöht die Laufzeit und Berechnet diese anhand der eingegebenen Taktfrequenz.
     */
    public static void increaseRuntime() {
        runtime++;
        laufzeitCalculated = laufzeitCalculated + (4000 / taktfrequenzValue);
        System.out.println(laufzeitCalculated);
    }

    private void debugTimer() {
       /* System.out.println("------------1--------------");
        System.out.println("Laufzeit: " + runtime);
        System.out.println("Prescaler: " + speicher.getPrescaler());
        System.out.println("Prescaler-MAX: " + speicher.getPrescalerMaxValue());
        System.out.println("TMR0: " + speicher.getTimer0().getIntWert());
        System.out.println("WatchdogTimer: " + speicher.getWatchdogTimer());
        System.out.println("WatchdogTimerEnabled: " + speicher.isWatchdogTimerEnabled());
        System.out.println("SLEEP: " + speicher.isSleepModus());
        System.out.println("------------2-------------"); */
    }

    /**
     * Aktualisiert die grafische Oberfläche nach jedem ausgeführten Befehl um die Änderungen
     * für den Benutzer sichtbar zu machen.
     */
    private void updateView() {
        taktfrequenzValue = Integer.parseInt(taktfrequenz.getText());

        tableFileContent.refresh();
        tableMemory.refresh();

        tableStack.getItems().clear();
        tableStack.getItems().addAll(speicher.getStack());

        WREG.setText(getRegisterService().intToHex(speicher.getRegisterW()));
        FSR.setText(speicher.getSpeicheradressen()[0].getRegister()[4].getHexWert());
        PCL.setText(speicher.getSpeicheradressen()[0].getRegister()[2].getHexWert());
        PCLATH.setText(speicher.getSpeicheradressen()[1].getRegister()[2].getHexWert());
        PC.setText(getRegisterService().intToHex(speicher.getFullProrammCounter()));

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

        Register intconReg = speicher.getSpeicheradressen()[1].getRegister()[3];
        INTCON.setText(intconReg.getHexWert());
        GIE.setText(Integer.toString(intconReg.getBits()[7].getPin()));
        EEIE.setText(Integer.toString(intconReg.getBits()[6].getPin()));
        T0IE.setText(Integer.toString(intconReg.getBits()[5].getPin()));
        INTE.setText(Integer.toString(intconReg.getBits()[4].getPin()));
        RBIE.setText(Integer.toString(intconReg.getBits()[3].getPin()));
        T0IF.setText(Integer.toString(intconReg.getBits()[2].getPin()));
        INTF.setText(Integer.toString(intconReg.getBits()[1].getPin()));
        RBIF.setText(Integer.toString(intconReg.getBits()[0].getPin()));

        Register optionReg = speicher.getSpeicheradressen()[16].getRegister()[1];
        OPTION.setText(optionReg.getHexWert());
        RBPU.setText(Integer.toString(optionReg.getBits()[7].getPin()));
        INTEDG.setText(Integer.toString(optionReg.getBits()[6].getPin()));
        T0CS.setText(Integer.toString(optionReg.getBits()[5].getPin()));
        T0SE.setText(Integer.toString(optionReg.getBits()[4].getPin()));
        PSA.setText(Integer.toString(optionReg.getBits()[3].getPin()));
        PS2.setText(Integer.toString(optionReg.getBits()[2].getPin()));
        PS1.setText(Integer.toString(optionReg.getBits()[1].getPin()));
        PS0.setText(Integer.toString(optionReg.getBits()[0].getPin()));

        laufzeit.setText(Double.toString(laufzeitCalculated));

        if (speicher.isWatchdogTimerEnabled()) {
            btnToggleWDTimer.setText("Disable WDT");
        } else {
            btnToggleWDTimer.setText("Enable WDT");
        }

        if (taktgeneratorEnabled) {
            btnToggleTakt.setText("Stoppe Takt");
            taktGenFrequenz.setDisable(true);
            taktgenerator.setDisable(true);
        } else {
            btnToggleTakt.setText("Starte Takt");
            taktGenFrequenz.setDisable(false);
            taktgenerator.setDisable(false);
        }

    }

    /**
     * Stoppt die Ausführung des Programmes.
     */
    public void stop() {
        run = false;
    }

    /**
     * Setzt alle Werte auf den Startzustand zurück.
     */
    public void reset() {
        run = false;
        currentRow = 1;
        runtime = 0;
        laufzeitCalculated = 0;
        laufzeit.setText("4000");
        initializeMemory();
        setRegisterA();
        setRegisterB();
        updateView();
        tableFileContent.refresh();
    }

    /**
     * Setzt alle Einstellungen zurück und setzt die Obefläche auf den initialen Zustand.
     */
    public void clear() {
        run = false;
        runtime = 0;
        laufzeitCalculated = 0;
        laufzeit.setText("4000");
        initializeMemory();
        setRegisterA();
        setRegisterB();
        updateView();
        tableFileContent.getItems().clear();
        tableMemory.getItems().clear();
    }

    /**
     * Initialisiert den Speicher und Ordnet die Werte den Elementen der grafischen Oberfläche zu.
     */
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

    /**
     * Toggelt den Wert des Bits 0 im Register A.
     */
    public void toggleA0() {
        Bit bit0 = getRegisterA().getBits()[0];
        getRegisterA().getBits()[0] = getRegisterService().toggleBit(bit0);
        tableMemory.refresh();
        Platform.runLater(() -> a0.setText(String.valueOf(getRegisterA().getBits()[0].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 1 im Register A.
     */
    public void toggleA1() {
        Bit bit1 = getRegisterA().getBits()[1];
        getRegisterA().getBits()[1] = getRegisterService().toggleBit(bit1);
        tableMemory.refresh();
        Platform.runLater(() -> a1.setText(String.valueOf(getRegisterA().getBits()[1].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 2 im Register A.
     */
    public void toggleA2() {
        Bit bit2 = getRegisterA().getBits()[2];
        getRegisterA().getBits()[2] = getRegisterService().toggleBit(bit2);
        tableMemory.refresh();
        Platform.runLater(() -> a2.setText(String.valueOf(getRegisterA().getBits()[2].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 3 im Register A.
     */
    public void toggleA3() {
        Bit bit3 = getRegisterA().getBits()[3];
        getRegisterA().getBits()[3] = getRegisterService().toggleBit(bit3);
        tableMemory.refresh();
        Platform.runLater(() -> a3.setText(String.valueOf(getRegisterA().getBits()[3].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 4 im Register A.
     */
    public void toggleA4() {
        Bit bit4 = getRegisterA().getBits()[4];
        getRegisterA().getBits()[4] = getRegisterService().toggleBit(bit4);
        tableMemory.refresh();
        Platform.runLater(() -> a4.setText(String.valueOf(getRegisterA().getBits()[4].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 5 im Register A.
     */
    public void toggleA5() {
        Bit bit5 = getRegisterA().getBits()[5];
        getRegisterA().getBits()[5] = getRegisterService().toggleBit(bit5);
        tableMemory.refresh();
        Platform.runLater(() -> a5.setText(String.valueOf(getRegisterA().getBits()[5].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 6 im Register A.
     */
    public void toggleA6() {
        Bit bit6 = getRegisterA().getBits()[6];
        getRegisterA().getBits()[6] = getRegisterService().toggleBit(bit6);
        tableMemory.refresh();
        Platform.runLater(() -> a6.setText(String.valueOf(getRegisterA().getBits()[6].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 7 im Register A.
     */
    public void toggleA7() {
        Bit bit7 = getRegisterA().getBits()[7];
        getRegisterA().getBits()[7] = getRegisterService().toggleBit(bit7);
        tableMemory.refresh();
        Platform.runLater(() -> a7.setText(String.valueOf(getRegisterA().getBits()[7].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 0 im Register B.
     */
    public void toggleB0() {
        Bit bit0 = speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[0];
        speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[0] = getRegisterService().toggleBit(bit0);
        tableMemory.refresh();
        Platform.runLater(() -> b0.setText(String.valueOf(getRegisterB().getBits()[0].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 1 im Register B.
     */
    public void toggleB1() {
        Bit bit1 = speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[1];
        speicher.getSpeicheradressen()[0].getRegister()[6].getBits()[1] = getRegisterService().toggleBit(bit1);
        tableMemory.refresh();
        Platform.runLater(() -> b1.setText(String.valueOf(getRegisterB().getBits()[1].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 2 im Register B.
     */
    public void toggleB2() {
        Bit bit2 = getRegisterB().getBits()[2];
        getRegisterB().getBits()[2] = getRegisterService().toggleBit(bit2);
        tableMemory.refresh();
        Platform.runLater(() -> b2.setText(String.valueOf(getRegisterB().getBits()[2].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 3 im Register B.
     */
    public void toggleB3() {
        Bit bit3 = getRegisterB().getBits()[3];
        getRegisterB().getBits()[3] = getRegisterService().toggleBit(bit3);
        tableMemory.refresh();
        Platform.runLater(() -> b3.setText(String.valueOf(getRegisterB().getBits()[3].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 4 im Register B.
     */
    public void toggleB4() {
        Bit bit4 = getRegisterB().getBits()[4];
        getRegisterB().getBits()[4] = getRegisterService().toggleBit(bit4);
        tableMemory.refresh();
        Platform.runLater(() -> b4.setText(String.valueOf(getRegisterB().getBits()[4].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 5 im Register B.
     */
    public void toggleB5() {
        Bit bit5 = getRegisterB().getBits()[5];
        getRegisterB().getBits()[5] = getRegisterService().toggleBit(bit5);
        tableMemory.refresh();
        Platform.runLater(() -> b5.setText(String.valueOf(getRegisterB().getBits()[5].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 6 im Register B.
     */
    public void toggleB6() {
        Bit bit6 = getRegisterB().getBits()[6];
        getRegisterB().getBits()[6] = getRegisterService().toggleBit(bit6);
        tableMemory.refresh();
        Platform.runLater(() -> b6.setText(String.valueOf(getRegisterB().getBits()[6].getPin())));
    }

    /**
     * Toggelt den Wert des Bits 7 im Register B.
     */
    public void toggleB7() {
        Bit bit7 = getRegisterB().getBits()[7];
        getRegisterB().getBits()[7] = getRegisterService().toggleBit(bit7);
        tableMemory.refresh();
        Platform.runLater(() -> b7.setText(String.valueOf(getRegisterB().getBits()[7].getPin())));
    }

    /**
     * Liefert das Register A.
     *
     * @return Register.
     */
    private Register getRegisterA() {
        if (speicher == null) {
            initializeMemory();
        }
        return speicher.getSpeicheradressen()[0].getRegister()[5];
    }

    /**
     * Ordnet die Ports des RegisterA den Oberflächenelementen zu.
     */
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

    /**
     * Liefert das Register B.
     *
     * @return Register
     */
    private Register getRegisterB() {
        if (speicher == null) {
            initializeMemory();
        }
        return speicher.getSpeicheradressen()[0].getRegister()[6];
    }

    /**
     * Ordnet die Ports des RegisterB den Oberflächenelementen zu.
     */
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

    /**
     * Methode zum Öffnen der Dokumentation.
     */
    public void openDocumentation() {
        try {
            java.awt.Desktop.getDesktop().browse(new URI(PicSimulatorConstants.LINK_DOCUMENTATION));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Liefert eine Instanz des MemoryInitializerService.
     *
     * @return MemoryInitializerService.
     */
    private MemoryInitializerService getMemoryInitializerService() {
        if (memoryInitializerService == null) {
            memoryInitializerService = new MemoryInitializerService();
        }
        return memoryInitializerService;
    }

    /**
     * Liefert eine Instanz des FileInputService.
     *
     * @return FileInputService.
     */
    private FileInputService getFileInputService() {
        if (fileInputService == null) {
            fileInputService = new FileInputService();
        }
        return fileInputService;
    }

    /**
     * Liefert eine Instanz des RegisterService.
     *
     * @return RegisterService.
     */
    private RegisterService getRegisterService() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }

    /**
     * Liefert eine Instant des BefehlSteuerungService.
     *
     * @return BefehSteuerungService.
     */
    private BefehlSteuerungService getBefehlSteuerungService() {
        if (befehlSteuerungService == null) {
            befehlSteuerungService = new BefehlSteuerungService();
        }
        return befehlSteuerungService;
    }

    /**
     * Liefert eine Instanz des InterruptService.
     *
     * @return InterruptService
     */
    private InterruptService getInterruptService() {
        if (interruptService == null) {
            interruptService = new InterruptService();
        }
        return interruptService;
    }

    /**
     * Aktiviert/Deaktiviert den Watchdog-Timer
     */
    public void toggleWDT() {
        speicher.setWatchdogTimerEnabled(!speicher.isWatchdogTimerEnabled());
        updateView();
    }

    /**
     * Thread der je nach Auwahl des Ports, sowie der Taktfrequenz einen
     * externen Taktgenerator steuert und den ausgwähten Port toggelt.
     */
    public void toggleTaktGenerator() {
        taktgeneratorEnabled = !taktgeneratorEnabled;

        if (!taktgeneratorEnabled) {
            updateView();
        }

        Thread thTakt = new Thread(() -> {
            try {
                while (taktgeneratorEnabled) {
                    if (taktgenerator.getValue() == "RA0") toggleA0();
                    if (taktgenerator.getValue() == "RA1") toggleA1();
                    if (taktgenerator.getValue() == "RA2") toggleA2();
                    if (taktgenerator.getValue() == "RA3") toggleA3();
                    if (taktgenerator.getValue() == "RA4") toggleA4();
                    if (taktgenerator.getValue() == "RA5") toggleA5();
                    if (taktgenerator.getValue() == "RA6") toggleA6();
                    if (taktgenerator.getValue() == "RA7") toggleA7();
                    if (taktgenerator.getValue() == "RB0") toggleB0();
                    if (taktgenerator.getValue() == "RB1") toggleB1();
                    if (taktgenerator.getValue() == "RB2") toggleB2();
                    if (taktgenerator.getValue() == "RB3") toggleB3();
                    if (taktgenerator.getValue() == "RB4") toggleB4();
                    if (taktgenerator.getValue() == "RB5") toggleB5();
                    if (taktgenerator.getValue() == "RB6") toggleB6();
                    if (taktgenerator.getValue() == "RB7") toggleB7();

                    Platform.runLater(this::updateView);
                    double sleepTime = (1 / Double.parseDouble(taktGenFrequenz.getText())) * 1000;
                    Thread.sleep((long) sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thTakt.setDaemon(true);
        thTakt.start();
    }

    /**
     * Methode, welche bei Änderung der Taktfrequenz diese als Double abspeichert.
     */
    public void onChangeTaktfrequenz() {
        taktfrequenzValue = Double.parseDouble(taktfrequenz.getText());
    }

    public void connectHardware() {
        int portA = getRegisterA().getIntWert();
        int portB = getRegisterB().getIntWert();
        int trisA = speicher.getSpeicheradressen()[16].getRegister()[5].getIntWert();
        int trisB = speicher.getSpeicheradressen()[16].getRegister()[6].getIntWert();

        System.out.println("---------- SENDEN ---------------");
        System.out.println("PORTA: " + portA);
        System.out.println("PORTB: " + portB);
        System.out.println("TRISA: " + trisA);
        System.out.println("TRISB: " + trisB);

        List<String> s = SeriellerPort.getAllPorts();
        if (s.size() == 0) {
            return;
        }

        SeriellerPort seriellerPort = new SeriellerPort(trisA, portA, trisB, portB);
        //SeriellerPort seriellerPort = new SeriellerPort(0, portA, 0, portB);
        try {
            seriellerPort.connect(SeriellerPort.getAllPorts().get(0));
            seriellerPort.writeOut();
            seriellerPort.readIn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

