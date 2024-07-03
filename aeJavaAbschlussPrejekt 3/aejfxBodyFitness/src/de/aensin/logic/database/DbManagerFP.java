package de.aensin.logic.database;

import de.aensin.model.FitnessPerson;
import de.aensin.model.FitnessPlan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;

/**
 * Threadsicher Zugriff auf die Datenbank
 * verschiedene Methoden und Funktionen zur Datenverarbeitung in der DB
 * die dann in der Klasse DaoFitnessPlan und DaoPerson ausgeführt werden
 */

public class DbManagerFP {

    //region 0.Konstanten
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    private static final String DB_LOCAL_SERVER_IP_ADDRESS = "127.0.0.1";
    private static final String DB_LOCAL_NAME              = "/personal_fitness";

    private static final String DB_LOCAL_CONNECTION_URL =
            "jdbc:mariadb://" + DB_LOCAL_SERVER_IP_ADDRESS + DB_LOCAL_NAME;

    private static final String DB_LOCAL_USER_NAME = "root";
    private static final String DB_LOCAL_USER_PW   = "";


    //endregion

    //region 1. Decl. and Init Attribute
    private static DbManagerFP instance;
    /**
     * Zugriff auf die Datenbanktablle tbl_personalfitness und person
     */
    private DaoFitnessPlan daoFitnessPlan;
    private DaoFitnessPerson daoFitnessPerson;
    //endregion

    //region 2. Standardkonstruktor deklarieren
    /**
     * Standardkonstruktor
     */
    private DbManagerFP() {
        this.daoFitnessPlan = new DaoFitnessPlan();
        this.daoFitnessPerson = new DaoFitnessPerson();
    }

    //endregion

    //region 2.
    /**
     * Verbindung zur Datenbank herstellen und prüfen ob DB online und erreichbar ist
      **/
    private Connection getRwDbConnection() throws Exception {
        Connection rwDbConnection = null;

        try {
            //: Registeren des  JDBC driver
            Class.forName(JDBC_DRIVER);

            //2. Offenen einer Verbindung
            rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);

        } catch (SQLNonTransientConnectionException sqlNoConnectionEx) {
            throw new Exception("Keine Datenbankverbindung");
        } catch (ClassNotFoundException classNotFoundEx) {
            throw new Exception("JDBC Treiber konnte nicht geladen werden");
        }

        return rwDbConnection;
    }

    /**
     * Checkt ob die Datenbank erreichbar ist oder nicht
     */
    public boolean isDatabaseOnline() {
        boolean isOnline = true;
        try {
            this.getRwDbConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
            isOnline = false;
        }
        return isOnline;
    }

    //endregion

    //region 3.

    /** EinFügen eines Datensatzes in die Tabelle fitnessplan
     *
     * @param FitnessPlanInsert
     */
    public void insertFitnessPlanItemIntoDbTbl(FitnessPlan FitnessPlanInsert) {
        //Neue Verbindung erstellen

        try {
            if (this.isDatabaseOnline()) {
                this.daoFitnessPlan.insertDataRecordIntoDbTbl(this.getRwDbConnection(), FitnessPlanInsert);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }
//endregion

//region 4.
    /**
     * liest alle Datensätze aus der Tabelle fitnessplan für den eingeloggten User aus
     * @return
     */
    public List<FitnessPlan> getAllFitnessItemsFromDb(int name_id) {
         //Neue Verbindung erstellen
        List<FitnessPlan> allFitnessItemsFromDb = new ArrayList<>();

        try {
            if (this.isDatabaseOnline()) {
                allFitnessItemsFromDb = this.daoFitnessPlan.getAllDataRecordsFromDbTbl(this.getRwDbConnection(),name_id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return allFitnessItemsFromDb;
    }

    //endregion


    //region 5.
    /**
     * Liest die Daten der "FitnessPerson", also des aktuellen Users, aus der Datenbank aus.
     */

    public FitnessPerson getFitnessPersonByName(String name) {
        //Neue Verbindung erstellen
        FitnessPerson FinessPersonFromDbByName = new FitnessPerson();

        try {
            if (this.isDatabaseOnline()) {
                FinessPersonFromDbByName = this.daoFitnessPerson.getSpecificDataRecordFromDbTblById(this.getRwDbConnection(),name);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

      return FinessPersonFromDbByName;
    }

    //endregion


    // region 6.
    /**
     * Liest das letze Gewicht des Users aus dem Fitnessplan
     */

    public FitnessPlan getletzterGewichtseintrag(int name_id) {
        //Neue Verbindung erstellen
        FitnessPlan lastWeightItem = new FitnessPlan();

        try {
            if (this.isDatabaseOnline()) {
                lastWeightItem = this.daoFitnessPlan.getLastWeight(this.getRwDbConnection(),name_id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return lastWeightItem;
    }
    //endregion


// region 7.
    /**
     * Update der Datenbank.fitnessplan
     *
     * @param fitnessPlanToChange : {@link de.aensin.model.FitnessPlan} :FitnessPlan
     */
    public void updateFitnessPlanInDbTbl(FitnessPlan fitnessPlanToChange) {
        //Neue Verbindung erstellen
        try {
            if (this.isDatabaseOnline()) {
                this.daoFitnessPlan.updateDataRecordIntoDbTbl(this.getRwDbConnection(), fitnessPlanToChange);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //endregion

    // region 8.
    /**
     * Loescht eine Zeile im Fitnessplan aus der Tabelle fitnessplan
     * @param iId : int : Id
     * @link FitnessPlan} die geloescht werden soll
     */
    public void deleteFitnessItemFromTblById(int iId) {
        //Neue Verbindung erstellen
        try {
            if (this.isDatabaseOnline()) {
                this.daoFitnessPlan.deleteDataRecordInDbTblById(this.getRwDbConnection(), iId);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //endregion

    // region 9.

    /**
     * fügt einen neunen User in die Tabelle person ein, die
     * zum späteren erstellen von Fitnessplänen notwendig ist
     * @param FitnessPersonInsert
     * @return
     */

    public boolean insertFitnessPersontoDbTbl(FitnessPerson FitnessPersonInsert) {
        //Neue Verbindung erstellen
        boolean bdoppelterEintrag = false;

        try {
            if (this.isDatabaseOnline()) {
                bdoppelterEintrag=daoFitnessPerson.insertPersonToDB(this.getRwDbConnection(), FitnessPersonInsert);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return bdoppelterEintrag;
    }

    // region 10.

    /**
     * erstellt und gibt die Instanz der Klasse DbManagerFP zurück
     * @return DbManagerFP instance
     */
    public static synchronized DbManagerFP getInstance() {
        if (instance == null) {
            instance = new DbManagerFP();
        }

        return instance;
    }
    //endregion

}
