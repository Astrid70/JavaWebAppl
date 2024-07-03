package de.aensin.gui;

import de.aensin.helper.DoubleDokument;
import de.aensin.helper.IntegerDokument;
import de.aensin.logic.database.DbManagerFP;
import de.aensin.logic.files.CsvFileHandler;
import de.aensin.model.FitnessPerson;
import de.aensin.model.FitnessPlan;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Verarbeitet die Events der MainFraime-Applikation und die Datenverarbeitung
 */

public class MainGuiController implements Initializable {


    //region 0. Konstanten

    final DateTimeFormatter FORMAT_DATUM = DateTimeFormatter.ofPattern("dd:MM:yy");
    final DateTimeFormatter FORMAT_DATUM_DB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final int TODO_FLAG_INSERT = 1;
    final int TODO_FLAG_UPDATE = 2;


    //endregion

    //region 2. Variablen
    /**
     * Variable zum Auslesen des aktuellen Datums und Formatierung für
     * die Ausgabe in der Gui und Einlesen in der Datenbank
     */
    public final String strAktDatum = FORMAT_DATUM.format(LocalDate.now());
    public final String strAktDatumdb = FORMAT_DATUM_DB.format(LocalDate.now());


    private int iToToFlagForDB = 0;

    private List<FitnessPlan> fitnessPlanList;
    @FXML
    javafx.scene.control.Label lblDatum;
    @FXML
    javafx.scene.control.Label lblName;
    @FXML
    javafx.scene.control.Button btnhinzufuegen;
    @FXML
    javafx.scene.control.Button btnaendern;
    @FXML
    javafx.scene.control.Button btnloeschen;
    @FXML
    javafx.scene.control.Button btngewicht;
    @FXML
    javafx.scene.control.Button btnbmi;
    @FXML
    javafx.scene.control.Button btnexport;
    @FXML
    javafx.scene.control.MenuItem menudrucken;
    @FXML
    javafx.scene.control.Label lblWillkommen;
    @FXML
    javafx.scene.control.MenuItem menuUser;


    @FXML
    private ListView<FitnessPlan> lvFitnessPlan;

    private ListViewAnzeigeInit listViewAnzeigeInit;
    // Textfelder der JOptionPane für Update und Insert der Fitnessplan-Daten
    private JTextField txtaktivity;
    private JTextField txtGewicht;
    private JTextField txtkcal;
    private TextField txtAktDatum;
    // JOptionPane Überprüfung der richtigen Eingabe
    private boolean bEingabekorrekt = false;

    private FitnessPlan fitnessplanupdate;
    // Person die aktuell den Fitnessplan nutzt
    private FitnessPerson myFitnessPerson;

    // Sekundärschlüssel für die Tabelle fitnessplan aus der Tablle person
    private int name_id = 0;

    //endregion

    //region 3.

    /**
     * Initialisieren der Gui mit Erstellung eines CellListeners für die ListView
     */
    public void initialize(URL location, ResourceBundle resources) {
        int option = 0;
        // setzt das aktuelle Datum im HainFrame-Window
        lblDatum.setText(strAktDatum);

        // Login des Benutzers durch einfache Eingabe des User-Names
        // bevor der Fitnessplan angezeigt werden kann
        String strInputName = JOptionPane.showInputDialog(new JFrame(), "Gib deinen Namen ein:\t");
        myFitnessPerson = DbManagerFP.getInstance().getFitnessPersonByName(strInputName);
        if (myFitnessPerson != null) {
            name_id = myFitnessPerson.getiIdPerson();
            this.updateListView();
        } else {

            // hat der Benutzer einen falschen Namen eingegeben,  wird eine Nachricht ausgeben
            // die Applikation wird gestartet, die Anzeige der Beaarbeitungs-Buttons wird deaktiviert
            // es wird keine Liste ausgegeben, der User hat nur die Möglichkeit das Programm zu schliessen,
            // oder einen neuen Benutzer anzulegen
            JOptionPane.showMessageDialog(new JFrame(), "Diesen Benutzer gibt es nicht!!", "Fehler",
                    JOptionPane.ERROR_MESSAGE);

                btnloeschen.setVisible(false);
                btnhinzufuegen.setVisible(false);
                btnaendern.setVisible(false);
                btnloeschen.setVisible(false);
                btngewicht.setVisible(false);
                btnbmi.setVisible(false);
                btnexport.setVisible(false);
                menudrucken.setVisible(false);
                lblWillkommen.setText("Lege einen neune Benutzer \nüber das Menü an.");
            }

        //Editierung deaktivieren
        this.fitnessplanupdate = null;

    }

    private void updateListView() {


        List<FitnessPlan> allFitnessItemsFromDb = DbManagerFP.getInstance().getAllFitnessItemsFromDb(name_id);
        //vertikales Scrollen ein
        this.lvFitnessPlan.setOrientation(Orientation.VERTICAL);

        //Generiert den CallBack mit den Items in den Zellen
        this.listViewAnzeigeInit = new ListViewAnzeigeInit();

        //FitnessplanEintrag zu Observable List hinzufügen  und an ListView anhaengen
        ObservableList<FitnessPlan> fitnessPlanObservableList = FXCollections.observableList(allFitnessItemsFromDb);

        //Alle  Enträge in der Listview löschen
        this.lvFitnessPlan.getItems().clear();

        //Neue aktualsierte Liste
        this.lvFitnessPlan.setItems(fitnessPlanObservableList);

        /*
         * Cellfactory setzen der  Gui-Ansicht
         */
        this.lvFitnessPlan.setCellFactory(this.listViewAnzeigeInit);

        //Listener zur Auswahl eines ListViewItems
        this.lvFitnessPlan.getSelectionModel().selectedItemProperty().addListener(this::onItemCellClick);

        lblName.setText(myFitnessPerson.getStrName());


    }

    private void onItemCellClick(ObservableValue<? extends FitnessPlan> observableFitnessPlan, FitnessPlan previousSelectedFitnessPlan, FitnessPlan currentSelectedFitnessPlan) {
        //Überprüft ob eine markiertes FitnessPlanElement da ist und selektiert sie
        if ((currentSelectedFitnessPlan != null) && (!currentSelectedFitnessPlan.equals(previousSelectedFitnessPlan))) {

            //Selektieren Fitnessplan merken
            this.fitnessplanupdate = currentSelectedFitnessPlan;

        }
    }
    //endregion


    //region 4.

    /**
     * löscht einen Eintrag au der ListView und der DB
     */

    public void loeschen(ActionEvent actionEvent) {

        //prüft ob eine Zeile aus der ListView ausgewählt wurde
        if (fitnessplanupdate != null) {

            DbManagerFP.getInstance().deleteFitnessItemFromTblById(this.fitnessplanupdate.getiIdPlan());

            //Anzeige der gesamten Fitnessplan - Einträge
            this.updateListView();

            //Loeschen deakttivieren
            this.fitnessplanupdate = null;

            //UserMsg Erfolg
            //        this.showUserMsgOnInfoLabel("Erfolgreich gelöscht", false);
            JOptionPane.showMessageDialog(new JFrame(), "Erfolgreich gelöscht!!", "Löschen",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Sie haben keinen Eintrag ausgewählt !!", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    //endregion

    //region 5.

    /**
     * Fügt einen Eintrag im Fitnessplan in der DB hinzu
     */
    public void hinzufuegen(ActionEvent actionEvent) {

        if (getItemsFromJOptionPane(1)) {

            FitnessPlan addFitnessPlan = new FitnessPlan();
            addFitnessPlan.setStrDatum(strAktDatumdb);
            try {
                addFitnessPlan.setDblActivity(Double.parseDouble(txtaktivity.getText()));
                addFitnessPlan.setDblGewichtPlan(Double.parseDouble(txtGewicht.getText()));
                addFitnessPlan.setDblKcal(Double.parseDouble(txtkcal.getText()));
                addFitnessPlan.setiIdName(name_id);

                DbManagerFP.getInstance().insertFitnessPlanItemIntoDbTbl(addFitnessPlan);
                //Alles anzeigen
                this.updateListView();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(new JFrame(), "Die Eingabe war nicht korrekt !!", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }



        }
    }

    //endregion

    // region 6.

    /**
     * Ändert einen Eintrag im Fitnessplan
     */
    public void aendern(ActionEvent actionEvent) {

        //prüft ob eine Zeile aus der ListView ausgewählt wurde
        if (fitnessplanupdate != null) {
            if (getItemsFromJOptionPane(2)) {

                //Eingabedaten aus JoptionPane auslesen

                FitnessPlan changeFitnessPlan = new FitnessPlan();
                changeFitnessPlan.setiIdPlan(fitnessplanupdate.getiIdPlan());
                try {
                    changeFitnessPlan.setDblActivity(Double.parseDouble(txtaktivity.getText()));
                    changeFitnessPlan.setDblGewichtPlan(Double.parseDouble(txtGewicht.getText()));
                    changeFitnessPlan.setDblKcal(Double.parseDouble(txtkcal.getText()));

                    DbManagerFP.getInstance().updateFitnessPlanInDbTbl(changeFitnessPlan);
                    //Alles anzeigen
                    this.updateListView();
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Die Eingabe war nicht korrekt !!", "Fehler",
                            JOptionPane.ERROR_MESSAGE);
                }


            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Sie haben keinen Eintrag ausgewählt !!", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //endregion

    // region 7. JOptionPane Aufruf mit Insert oder Update aus Textfeldern lesen

    /**
     * Öffnet ein neues Eingabefenster zum hinzufügen oder Ändern des Fitnessplans
     */
    public boolean getItemsFromJOptionPane(int iToToFlagForDB) {

        txtaktivity = new JTextField();
        txtGewicht = new JTextField();
        txtkcal = new JTextField();

        txtaktivity.setDocument(new DoubleDokument());
        txtGewicht.setDocument(new DoubleDokument());
        txtkcal.setDocument(new DoubleDokument());

        int option = 0;


        //  Flag prüft, ob Insert oder Update, und die JOptionPaneMessage wird angepasst

        if (iToToFlagForDB == TODO_FLAG_INSERT) {
            txtAktDatum = new TextField(strAktDatum);
            txtAktDatum.setEditable(false);

            Object[] message =
                    {
                            "aktuelles Datum", txtAktDatum,
                            "Aktivität in km:\t", txtaktivity,
                            "Gewicht in kg:\t", txtGewicht,
                            "Kalorienaufnahme:\t", txtkcal
                    };
            option = JOptionPane.showConfirmDialog(null, message,
                    "Fitnessdaten hinzufügen", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                if (txtaktivity.getText().isEmpty() || txtGewicht.getText().isEmpty() || txtkcal.getText().isEmpty()) {
                    //UserMsg keine korrekten Eingabedaten
                    JOptionPane.showMessageDialog(new JFrame(), "Die Eingabe war nicht korrekt!", "Falsche EIngabe",
                            JOptionPane.ERROR_MESSAGE);
                    bEingabekorrekt = false;
                    return bEingabekorrekt;
                } else {
                    bEingabekorrekt = true;
                    return bEingabekorrekt;
                }

            } else {
                bEingabekorrekt = false;
                return bEingabekorrekt;
            }

        } else {
            txtAktDatum = new TextField(fitnessplanupdate.getStrDatum());
            txtAktDatum.setEditable(false);

            txtaktivity.setText(Double.toString(fitnessplanupdate.getDblActivity()));
            txtGewicht.setText(Double.toString(fitnessplanupdate.getDblGewichtPlan()));
            txtkcal.setText(Double.toString(fitnessplanupdate.getDblKcal()));

            Object[] message =
                    {
                            "Datum", txtAktDatum,
                            "Aktivität in km:\t", txtaktivity,
                            "Gewicht in kg:\t", txtGewicht,
                            "Kalorienaufnahme:\t", txtkcal
                    };

            option = JOptionPane.showConfirmDialog(null, message,
                    "Fitnessdaten ändern", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (txtaktivity.getText().isEmpty() || txtGewicht.getText().isEmpty()
                        || txtkcal.getText().isEmpty()) {
                    //UserMsg keine korrekten Eingabedaten
                    JOptionPane.showMessageDialog(new JFrame(), "Die Eingabe war nicht korrekt!", "Falsche EIngabe",
                            JOptionPane.ERROR_MESSAGE);
                    bEingabekorrekt = false;
                    return bEingabekorrekt;
                } else {
                    bEingabekorrekt = true;
                    return bEingabekorrekt;
                }
            } else {
                bEingabekorrekt = false;
                return bEingabekorrekt;
            }
        }
    }

    //endregion

// region 8. Export Fitnessplan in CSV-File

    /**
     * Export
     * liest die Daten des Fitnessplans aus der DB und exportiert sie
     * in das CSV-File
     */
    public void exportCsv(ActionEvent actionEvent) {

        List<FitnessPlan> allFitnessItemsFromDb = DbManagerFP.getInstance().getAllFitnessItemsFromDb(name_id);

        //Daten in eine CSV-Datei exportieren
        CsvFileHandler.getInstance().saveToCsvFile(allFitnessItemsFromDb);


    }
    //endregion

    // region 9.Druck bzw. Consolenausgabe des CSV-Files

    /**
     * Gibt die CSV-Datei auf der Console aus, bei einem späteren Release erfolgt die Erwiterung
     * zur Ausgabe an den Drücker
     *
     * @param actionEvent
     */
    public void drucken(ActionEvent actionEvent) {
        //update der aktuellen Daten vor dem Druck in die Datei
        List<FitnessPlan> allFitnessItemsFromDb = DbManagerFP.getInstance().getAllFitnessItemsFromDb(name_id);
        CsvFileHandler.getInstance().saveToCsvFile(allFitnessItemsFromDb);
        //        Import
        List<FitnessPlan> allFitnessItemsFromFile = CsvFileHandler.getInstance().readFromCsvFile();

        for (FitnessPlan fitnessPlanOut : allFitnessItemsFromFile) {
            System.out.println(fitnessPlanOut.toString());
        }

    }
    //endregion


    // region 10. Gewichtsreduktion anzeigen

    /**
     * Gibt die Gewichtsdifferenz bei Start des Useres/FitnessPerson und dem letzten Gewichtseintag zurück
     *
     * @param actionEvent
     */
    public void gewichtsDifferenz(ActionEvent actionEvent) {

        FitnessPlan lastweightFP = DbManagerFP.getInstance().getletzterGewichtseintrag(name_id);
        double dblGewichtsDifferenz = myFitnessPerson.getDblGewicht() - lastweightFP.getDblGewichtPlan();
        JOptionPane.showMessageDialog(new JFrame(), "Du hast eine Gewichtsdifferenz von " + dblGewichtsDifferenz + "  kg erreicht!",
                "Gewichtsänderung",
                JOptionPane.INFORMATION_MESSAGE);

    }

    //endregion


    // region 11. aktuellen BMI  anzeigen

    /**
     * Gibt dem  Benutzer seinen aktuellen BMI zurück
     *
     * @param actionEvent
     */
    public void anzeigenBMI(ActionEvent actionEvent) {
        FitnessPlan lastweightFP = DbManagerFP.getInstance().getletzterGewichtseintrag(name_id);
        double dblBmi = (lastweightFP.getDblGewichtPlan() / Math.pow(myFitnessPerson.getDblGroesse() / 100, 2));
        int iBmi = (int) dblBmi;


        JOptionPane.showMessageDialog(new JFrame(), "Dein BMI beträgt zur Zeit " + iBmi + "  !",
                "Aktueller BMI",
                JOptionPane.INFORMATION_MESSAGE);
    }
    //endregion


    // region 12. neuen Benutzer in der Tabelle person anlegen
    public void benutzerAnlegen(ActionEvent actionEvent) {

        boolean bdoppelterEintrag = false;

        JTextField txtName = new JTextField();
        JTextField txtAlter = new JTextField();
        JTextField txtGeschlecht = new JTextField();
        JTextField txtGewicht = new JTextField();
        JTextField txtGroesse = new JTextField();

        txtGroesse.setDocument(new DoubleDokument());
        txtGewicht.setDocument(new DoubleDokument());
        txtAlter.setDocument(new IntegerDokument());


        int option = 0;

        Object[] message1 =
                {
                        "Name:\t", txtName,
                        "Geschlecht:\t", txtGeschlecht,
                        "Alter:\t", txtAlter,
                        "Gewicht in kg:\t", txtGewicht,
                        "Größe in cm:\t", txtGroesse
                };
        option = JOptionPane.showConfirmDialog(null, message1,
                "Benutzer hinzufügen", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (txtName.getText().isEmpty() || txtGewicht.getText().isEmpty() ||
                    txtGeschlecht.getText().isEmpty() || txtGewicht.getText().isEmpty() || txtAlter.getText().isEmpty()) {
                //UserMsg keine korrekten Eingabedaten
                JOptionPane.showMessageDialog(new JFrame(), "Die Eingabe war nicht korrekt!", "Falsche EIngabe",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                FitnessPerson addNewFitnessPerson = new FitnessPerson();
                addNewFitnessPerson.setStrName(txtName.getText());
                addNewFitnessPerson.setstrGeschlecht(txtGeschlecht.getText());
                addNewFitnessPerson.setDblGewicht(Double.parseDouble(txtGewicht.getText()));
                addNewFitnessPerson.setDblGroesse(Double.parseDouble(txtGroesse.getText()));
                addNewFitnessPerson.setiAlter(Integer.parseInt(txtAlter.getText()));
                addNewFitnessPerson.setDblBmi(addNewFitnessPerson.getDblGewicht(), addNewFitnessPerson.getDblGroesse());
                bdoppelterEintrag = DbManagerFP.getInstance().insertFitnessPersontoDbTbl(addNewFitnessPerson);

                // prüft ob es den Benutzer in der Tabelle person schon gibt, da name in Tabelle person unique
                // falls ja wird eine Fehlermeldung ausgegeben
                if (bdoppelterEintrag) {
                    JOptionPane.showMessageDialog(new JFrame(), "Diesen Benutzer gibt es bereits!", "Doppelter Eintrag",
                            JOptionPane.ERROR_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Benutzer " + txtName.getText().toString() +
                                    " wurde erfolgreich angelegt.\n" + "Starte die Anwendung neu um einen Fitnessplan anzulegen!", "Benutzer erfolgreich angelegt",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }


        }
    }

    public void programmBeenden(ActionEvent actionEvent) {
        System.exit(0);
    }
//endregion
}
