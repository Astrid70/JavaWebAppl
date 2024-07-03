package de.aensin.logic.database;

import de.aensin.model.FitnessPlan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Führt Sql-Befehle in der Tabelle fitnessplan aus und gibt das Ergebnis an den DBManager zurück
 */
public class DaoFitnessPlan extends ASqlKeyWords {
    //region 0. Konstanten
    /**
     * Primaerschluessel
     */
    protected static final String COL_NAME_ID                    = "_id";
    protected static final String COL_NAME_ID_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_ID + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_NAME_ID                    = "name_id";
    protected static final String COL_NAME_NAME_ID_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_NAME_ID + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_DATUM                    = "datum";
    protected static final String COL_NAME_DATUM_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_DATUM + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_AKTIVITAET                    = "activity";
    protected static final String COL_NAME_AKTIVITAET_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_AKTIVITAET + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_GEWICHT                    = "gewicht";
    protected static final String COL_NAME_GEWICHT_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_GEWICHT + CHAR_COL_BACK_TICK;

    protected static final String COL_NAME_KCAL                    = "kcal";
    protected static final String COL_NAME_KCAL_INC_COL_BACK_TICKS =
            CHAR_COL_BACK_TICK + COL_NAME_KCAL + CHAR_COL_BACK_TICK;



    private static final String TABLE_NAME = "fitnessplan";
    //endregion


    //region 1. Insert fitnessplan

    /**
     * Fuegt einen einzelen Datensatz in die Datebanktabelle fitnessplan ein
     *
     * @param dbRwConnection          : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
     * @param fitnessPlanItemToInsertIntoDbTable : {@link de.aensin.model.FitnessPlan
     */
    public void insertDataRecordIntoDbTbl(Connection dbRwConnection, FitnessPlan fitnessPlanItemToInsertIntoDbTable) {

        //Decl and Init
        Statement dbStatementToExecute = null;

        try {
            // Statementobjekt  als String generiertes SQL-Statements
            dbStatementToExecute = dbRwConnection.createStatement();


            String strSqlStmtInsert = INSERT_TBL + TABLE_NAME + CHAR_OPEN_BRACKET
                    + COL_NAME_NAME_ID_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_DATUM_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_AKTIVITAET_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_GEWICHT_INC_COL_BACK_TICKS + CHAR_COMMA
                    + COL_NAME_KCAL_INC_COL_BACK_TICKS
                    + CHAR_CLOSE_BRACKET
                    + VALUES_OPERATOR + CHAR_OPEN_BRACKET
                    + CHAR_VALUE_BACK_TICK + fitnessPlanItemToInsertIntoDbTable.getiIdName() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + fitnessPlanItemToInsertIntoDbTable.getStrDatum() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + fitnessPlanItemToInsertIntoDbTable.getDblActivity() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + fitnessPlanItemToInsertIntoDbTable.getDblGewichtPlan() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + CHAR_VALUE_BACK_TICK + fitnessPlanItemToInsertIntoDbTable.getDblKcal() + CHAR_VALUE_BACK_TICK
                    + CHAR_CLOSE_BRACKET_SEMICOLON;
            //Ausgabe Console SQL-Query
            System.out.println(">>>>>>>> " + strSqlStmtInsert);

            // SQL - Mit genereriertem SQL Statement ausführen
            dbStatementToExecute.execute(strSqlStmtInsert);

        } catch (Exception e) {
            e.printStackTrace();
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


    }

    //region 2. Update der Tabelle fitnessplan

    /**
     * Aendert einen einzelen Datensatz in der Datebanktabelle fitnessplan
     *
     * @param dbRwConnection        : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
     * @param FitnessPlanUpdateInDbTable : {@link FitnessPlan} : Model welches geaendert werden soll
     */
    public void updateDataRecordIntoDbTbl(Connection dbRwConnection, FitnessPlan FitnessPlanUpdateInDbTable) {

        Statement dbStatementToExecute = null;
        try {

            // Statementobjek generieren
            dbStatementToExecute = dbRwConnection.createStatement();

            String strSqlStmtUpdate = UPDATE_TBL + TABLE_NAME
                    + SET_OPERATOR
                    + COL_NAME_AKTIVITAET_INC_COL_BACK_TICKS
                    + EQUALS_OPERATOR
                    + CHAR_VALUE_BACK_TICK + FitnessPlanUpdateInDbTable.getDblActivity() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + COL_NAME_GEWICHT_INC_COL_BACK_TICKS
                    + EQUALS_OPERATOR
                    + CHAR_VALUE_BACK_TICK + FitnessPlanUpdateInDbTable.getDblGewichtPlan() + CHAR_VALUE_BACK_TICK + CHAR_COMMA
                    + COL_NAME_KCAL_INC_COL_BACK_TICKS
                    + EQUALS_OPERATOR
                    + CHAR_VALUE_BACK_TICK + FitnessPlanUpdateInDbTable.getDblKcal() + CHAR_VALUE_BACK_TICK
                    + WHERE_CONDITION + COL_NAME_ID_INC_COL_BACK_TICKS + EQUALS_OPERATOR +
                    FitnessPlanUpdateInDbTable.getiIdPlan();

            // SQL - Statement ausfuerhren
            dbStatementToExecute.executeUpdate(strSqlStmtUpdate);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (dbStatementToExecute != null) {
                // Schliessen SQL Statement
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }

            if (dbRwConnection != null) {
                // Schliessen der Verbindung
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }
    }

    //region 3.  Read

    /**
     * Gibt alle Datensaetze aus der Tabelle für den aktuellen User als {@link List} zurueck
     *
     * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
     *
     * @return allDataRecordsFromDbTbl : {@link List} Objects extended from {@link FitnessPlan} : Liste aller Datensaetze
     */
    public List<FitnessPlan> getAllDataRecordsFromDbTbl(Connection dbRwConnection, int name_id) {
        List<FitnessPlan> allFitnessPlanItemsFromDbTable = new ArrayList<>();

        //Decl. and Init

        Statement dbStatementToExecute = null;

        try {
            // Geneiert Statenements
            dbStatementToExecute = dbRwConnection.createStatement();

            //3. Query generieren und in String sichern
            String strSqlStmtGetAll = SELECT_ALL_DATA_FROM + TABLE_NAME + WHERE_CONDITION + COL_NAME_NAME_ID
                    + EQUALS_OPERATOR + name_id;


            ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetAll);

            // gesamtes ResultSet durchlaufen und im Objekt speichern
            while (resultSetFromExecutedQuery.next()) {

                FitnessPlan fitnessPlanFromDbTable = this.getModelFromResultSet(resultSetFromExecutedQuery);

                // erstelltes Objekt  zur  Liste hinzufügen
                allFitnessPlanItemsFromDbTable.add(fitnessPlanFromDbTable);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (dbStatementToExecute != null) {
                //Schliessen des Statements
                try {
                    dbStatementToExecute.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }

            if (dbRwConnection != null) {
                // Schliessen der DB - Verbindung
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }

        return allFitnessPlanItemsFromDbTable;
    }
    //endregion

    //region 4.  Lese letzen Gewichtseintrag aus fitnessplan tabelle

    /**
     * liest den letzen Gewichtseintarg aus der Tabelle fitnessplan
     */
    public  FitnessPlan getLastWeight(Connection dbRwConnection,int name_id)
    {

        FitnessPlan specifitnessPlan = null;

        //Declaration and Init

        Statement dbStatementToExecute = null;

        try {

            // Geneiere Statenements
            dbStatementToExecute = dbRwConnection.createStatement();

            String strSqlStmtGetById =
                    SELECT_ALL_DATA_FROM + TABLE_NAME + WHERE_CONDITION + COL_NAME_NAME_ID + EQUALS_OPERATOR + name_id
                            + ORDER_TBL + COL_NAME_ID + DESC + LIMIT1;

            ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetById);

            // ResultSet durchsuchen bis Datensatz gefunden ist
            if (resultSetFromExecutedQuery.first()) {

                // Datensatz merken
                specifitnessPlan = this.getModelFromResultSet(resultSetFromExecutedQuery);

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

            if (dbRwConnection != null) {
                // Schliessen der DB-Verbindung
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }

        return specifitnessPlan;

    }
    //endregion

    //region 5.  Delete aus fitnessplan Tabelle

    /**
     * Loescht einen bestimmten Datensatz aus der Datenbanktabelle
     *
     * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
     * @param iId            : int : Id des Objektes welches in der DbTabelle geloscht werden soll
     */
    public void deleteDataRecordInDbTblById(Connection dbRwConnection, int iId) {
        Statement dbStatementToExecute = null;

        try {

            // Generieren des Statenements
            dbStatementToExecute = dbRwConnection.createStatement();

            String strSqlDeleteUserById = ASqlKeyWords.DELETE_FROM_TBL + TABLE_NAME + WHERE_CONDITION
                    + COL_NAME_ID_INC_COL_BACK_TICKS
                    + ASqlKeyWords.EQUALS_OPERATOR + iId;

            dbStatementToExecute.executeUpdate(strSqlDeleteUserById);


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

            if (dbRwConnection != null) {
                // Schliessen der DB-Connection
                try {
                    dbRwConnection.close();
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                }
            }
        }
    }
    //endregion

    //region 6. Objekt FitnessPlan aus ResultSet erstellen

    /**
     * Nimmt die Ergebnismenge und formt ein konkretes Model daraus
     * @param currentResultSet : {@link ResultSet} : Ergebnismenge der Abfrage
     * @return note : {@link FitnessPlan}
     * @throws Exception
     */
    protected FitnessPlan getModelFromResultSet(ResultSet currentResultSet) throws Exception {
        //Index auslesen
        final int iColumnIndexId      = currentResultSet.findColumn(COL_NAME_ID);
        final int iColumnIndexIdName     = currentResultSet.findColumn(COL_NAME_NAME_ID);
        final int iColumnIndexDatum  = currentResultSet.findColumn(COL_NAME_DATUM);
        final int iColumnIndexAktivitaet = currentResultSet.findColumn(COL_NAME_AKTIVITAET);
        final int iColumnIndexGewicht = currentResultSet.findColumn(COL_NAME_GEWICHT);
        final int iColumnIndexKcal = currentResultSet.findColumn(COL_NAME_KCAL);


        // auselsen der Daten der DB in entsprechende Variablen
        final int iId = currentResultSet.getInt(iColumnIndexId);
        final int iIdName = currentResultSet.getInt(iColumnIndexIdName);
        final String strDatum = currentResultSet.getDate(iColumnIndexDatum).toString();
        final double dblAktivitaet   = currentResultSet.getDouble(iColumnIndexAktivitaet);
        final double dblGewicht   = currentResultSet.getDouble(iColumnIndexGewicht);
        final double dblKcal   = currentResultSet.getDouble(iColumnIndexKcal);


        // Neues Objekt FitnessPlan generieren
        FitnessPlan fitnessPlan = new FitnessPlan(iIdName);

        fitnessPlan.setiIdPlan(iId);
     //   fitnessPlan.setiIdName(iIdName);
        fitnessPlan.setStrDatum(strDatum);
        fitnessPlan.setDblActivity(dblAktivitaet);
        fitnessPlan.setDblGewichtPlan(dblGewicht);
        fitnessPlan.setDblKcal(dblKcal);


        return fitnessPlan;
    }
    //endregion
}
