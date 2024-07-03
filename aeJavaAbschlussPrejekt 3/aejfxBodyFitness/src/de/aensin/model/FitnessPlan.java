package de.aensin.model;

/**
 * Der Fitnessplan erfasst fortlaufende Daten, wie Gewicht, Aktivitäten und Kalorienaufnahme pro Tag
 * */

public class FitnessPlan {

    //region 1. Konstanten
    /**
     * Standardwert fuer alle String Attribute für CSV-Datei
     */

    private static final String SPLIT_CHAR = ";";

    private static final int SPLIT_INDEX_DATUM  = 0;
    private static final int SPLIT_INDEX_GEWICHT = 1;
    private static final int SPLIT_INDEX_AKTIVITAET = 2;
    private static final int SPLIT_INDEX_KCAL = 3;
    //endregion

    //region 2. Attribute

    private int iIdPlan;
    private FitnessPerson myFitnessPerson;
    private int iIdName;
    private String strDatum;
    private double dblGewichtAenderung;
    private double dblKcal;
    private double dblActivity;

    //endregion

    //region 3. Constructoren


    public FitnessPlan()
    {

    }

    public FitnessPlan(int iIdName) {
        this.iIdName = iIdName;
    }

    //endregion

    //region 3. Getter und Setter


    public int getiIdPlan() {
        return iIdPlan;
    }

    public FitnessPerson getMyFitnessPerson() {
        return myFitnessPerson;
    }

    public String getStrDatum() {
        return strDatum;
    }

    public double getDblGewichtPlan() {
        return dblGewichtAenderung;
    }

    public double getDblKcal() {
        return dblKcal;
    }

    public double getDblActivity() {
        return dblActivity;
    }

    public int getiIdName() {
        return iIdName;
    }

    public void setiIdPlan(int iIdPlan) {
        this.iIdPlan = iIdPlan;
    }

    public void setMyFitnessPerson(FitnessPerson myFitnessPerson) {
        this.myFitnessPerson = myFitnessPerson;
    }

    public void setStrDatum(String strDatum) {
        this.strDatum = strDatum;
    }

    public void setDblGewichtPlan(double dblGewichtPlan) {
        this.dblGewichtAenderung = dblGewichtPlan;
    }

    public void setDblKcal(double dblKcal) {
        this.dblKcal = dblKcal;
    }

    public void setDblActivity(double dblActivity) {
        this.dblActivity = dblActivity;
    }

    public void setiIdName(int iIdName) {
        this.iIdName = iIdName;
    }

    //endregion

    //region 4. Methoden und Funktionen

    @Override
    public String toString() {
        return  "\tDatum:\t" + strDatum +
                "\t\tGewicht:\t" + dblGewichtAenderung +
                "\t\tKcal:\t" + dblKcal +
                "\t\tAktivität:\t" + dblActivity ;

    }

    /**
     * Alle Attribute als ein String mit
     * einem Semikolon als Trennzeichen<br>
     * @return strAllAttributesAsCsvString : {@link String} : Alle Attribute mit einem Semikoln getrennt als String
     */
    public String getAllAttributesAsCsvString() {
        return this.strDatum + SPLIT_CHAR + this.dblGewichtAenderung + SPLIT_CHAR +
                this.dblActivity + SPLIT_CHAR + this.dblKcal;
    }

    /**
     * Verarbeitet die aus dem CSV-FileHanlder {@link FitnessPlan#getAllAttributesAsCsvString()} generierten
     * CSV-String und splittet ihn nach dem verwendeten Trennzeichen
     * {@link FitnessPlan#SPLIT_CHAR} auf und gibt sie an Attributwerte der Fitnessplaners zurück.
     *
     * @param strReadCsvLine : {@link String} : Ausgelesener CSV-String aus {@link de/aensin/resource/fitnessplan.csv}
     */
    public void setAllAttributesFromCsvLine(String strReadCsvLine) {
        //Alle Attribute mit Semikolon splitten
        String[] strAllAttributes = strReadCsvLine.split(SPLIT_CHAR);

        //Attribute setzen
        this.strDatum = strAllAttributes[SPLIT_INDEX_DATUM];
        this.dblGewichtAenderung = Double.parseDouble(strAllAttributes[SPLIT_INDEX_GEWICHT]);
        this.dblActivity = Double.parseDouble(strAllAttributes[SPLIT_INDEX_AKTIVITAET]);
        this.dblKcal = Double.parseDouble(strAllAttributes[SPLIT_INDEX_KCAL]);

    }
    //endregion
}
