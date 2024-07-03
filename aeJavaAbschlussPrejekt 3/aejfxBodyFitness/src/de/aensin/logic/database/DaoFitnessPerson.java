package de.aensin.logic.database;

import de.aensin.model.FitnessPerson;
import de.aensin.model.FitnessPlan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Führt Sql-Befehle in der Tabelle person aus und gibt das Ergebnis an den DBManager zurück
 */

public class DaoFitnessPerson extends ASqlKeyWords{

    //region 0. Konstanten
    /**
     * Primaerschluessel
     */
    protected static final String COL_NAME_ID                    = "_id";
    protected static final String COL_NAME_ID_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_ID + CHAR_COL_BACK_TICK;
    /**
     * Sekundärschlüssel auf fitnessplan Tabelle name
     * unique
     */
    protected static final String COL_NAME_NAME                   = "name";
    protected static final String COL_NAME_NAME_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_NAME + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_ALTER                   = "jahrealt";
    protected static final String COL_NAME_ALTER_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_ALTER + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_GROESSE = "groesse";
    protected static final String COL_NAME_GROESSE_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_GROESSE + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_GEWICHT                    = "gewicht";
    protected static final String COL_NAME_GEWICHT_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_GEWICHT + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_GESCHLECHT                   = "geschlecht";
    protected static final String COL_NAME_GESCHLECHT_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_GESCHLECHT + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_BMI                   = "bmi";
    protected static final String COL_NAME_BMI_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_BMI + CHAR_COL_BACK_TICK;



    private static final String TABLE_NAME = "person";
    //endregion


    //region 1. liest den aktuellen user aus der Tabelle person
    public FitnessPerson getSpecificDataRecordFromDbTblById(Connection dbConnection, String name)

    {
       String strNamefordb = "\"" + name + "\"";
        FitnessPerson specificFitnessPerson = null;

        //Declaration and Init

        Statement dbStatementToExecute = null;

        try {

            // Geneiere Statenements
            dbStatementToExecute = dbConnection.createStatement();

            String strSqlStmtGetById =
                    SELECT_ALL_DATA_FROM + TABLE_NAME + WHERE_CONDITION + COL_NAME_NAME + EQUALS_OPERATOR + strNamefordb;

            ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetById);

            // ResultSet durchsuchen bis Datensatz gefunden ist
            if (resultSetFromExecutedQuery.first()) {

                // Datensatz merken
                specificFitnessPerson = this.getModelFromResultSet(resultSetFromExecutedQuery);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (dbStatementToExecute != null) {
                // Schliessen des Statements
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }

            if (dbConnection != null) {
                // Schliessen der DB-Verbindung
                try {
                    dbConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }

        return specificFitnessPerson;
    }
    //endregion

    // 2. region Objekt FitnessPerson aus ResultSet erstellen

    /**
     * Nimmt die Ergebnismenge und formt ein konkretes Model daraus
     * @param currentResultSet : {@link ResultSet} : Ergebnismenge der Abfrage
     * @return note : {@link FitnessPlan}
     * @throws Exception
     */
    protected FitnessPerson getModelFromResultSet(ResultSet currentResultSet) throws Exception {
        //Index auslesen
        final int iColumnIndexId      = currentResultSet.findColumn(COL_NAME_ID);
        final int iColumnIndexName     = currentResultSet.findColumn(COL_NAME_NAME);
        final int iColumnIndexAlter  = currentResultSet.findColumn(COL_NAME_ALTER);
        final int iColumnIndexGroesse = currentResultSet.findColumn(COL_NAME_GROESSE);
        final int iColumnIndexGewicht = currentResultSet.findColumn(COL_NAME_GEWICHT);
        final int iColumnIndexGeschlecht = currentResultSet.findColumn(COL_NAME_GESCHLECHT);


        // auselsen der Daten der DB in entsprechende Variablen
        final int iId = currentResultSet.getInt(iColumnIndexId);
        final String strName = currentResultSet.getString(iColumnIndexName);
        final int iAlter = currentResultSet.getInt(iColumnIndexAlter);
        final double iGroesse   = currentResultSet.getDouble(iColumnIndexGroesse);
        final double dblGewicht   = currentResultSet.getDouble(iColumnIndexGewicht);
        final String strGeschlecht   = currentResultSet.getString(iColumnIndexGeschlecht);


        // Neues Objekt FitnessPerson generieren
        FitnessPerson fitnessPerson = new FitnessPerson();

        fitnessPerson.setiIdPerson(iId);
        fitnessPerson.setStrName(strName);
        fitnessPerson.setiAlter(iAlter);
        fitnessPerson.setDblGroesse(iGroesse);
        fitnessPerson.setDblGewicht(dblGewicht);
        fitnessPerson.setstrGeschlecht(strGeschlecht);


        return fitnessPerson;
    }
    //endregion

 // 3. region erstellt einen neuen Benutzer in der Tabelle person
    public boolean insertPersonToDB(Connection dbRwConnection, FitnessPerson addfitnessPerson) {

        //Decl and Init
        Statement dbStatementToExecute = null;
        boolean bdoppelterEintag = false;

        try {
            // Statementobjekt  als String generiertes SQL-Statements
            dbStatementToExecute = dbRwConnection.createStatement();


            String strSqlStmtInsert = INSERT_TBL + TABLE_NAME + CHAR_OPEN_BRACKET
                    + COL_NAME_NAME_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_GESCHLECHT_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_ALTER_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_GROESSE_INC_COL_BACK_TICKS+ CHAR_COMMA
                    + COL_NAME_GEWICHT_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_BMI_INC_COL_BACK_TICKS
                    + CHAR_CLOSE_BRACKET
                    + VALUES_OPERATOR + CHAR_OPEN_BRACKET
                    + CHAR_VALUE_BACK_TICK + addfitnessPerson.getStrName()+ CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + addfitnessPerson.getstrGeschlecht() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + addfitnessPerson.getiAlter() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + addfitnessPerson.getDblGroesse() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + addfitnessPerson.getDblGewicht() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + addfitnessPerson.getDblBmi() + CHAR_VALUE_BACK_TICK

                    + CHAR_CLOSE_BRACKET_SEMICOLON;
            //Ausgabe Console SQL-Query
            System.out.println(">>>>>>>> " + strSqlStmtInsert);

            // SQL Statement ausführen
            dbStatementToExecute.execute(strSqlStmtInsert);

        } catch (Exception e) {
            e.printStackTrace();
            bdoppelterEintag = true;
        } finally {

            if (dbStatementToExecute != null) {
                // Schliessen des SQL Statements
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }

            if (dbRwConnection != null) {
                // Schliessen der Connection
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }

    return bdoppelterEintag;
    }
//endregion
}
