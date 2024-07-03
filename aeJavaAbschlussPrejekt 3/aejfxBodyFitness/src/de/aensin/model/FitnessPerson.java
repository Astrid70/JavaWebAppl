package de.aensin.model;

/**
 * Die Klasse FitnessPerson speichert die Grunddaten der Person, die einen Fitnessplan erstellt;
 * dazu gehören Alter , Name, Grösse und Gewicht, der BMI wird aus Grösse und Gewicht berechnet
 **/

public class FitnessPerson {

    //region 0. Attribute

    private int iIdPerson;
    private String strName;
    private String strGeschlecht;
    private int  iAlter;
    private double dblGroesse;
    private double dblGewicht;
    private double dblBmi;

    //endregion

    //region 2. Constructoren

    public FitnessPerson(){

    };

    public FitnessPerson(String strName, String strGeschlecht, double dblGroesse, double dblGewicht) {
        this.strName = strName;
        this.strGeschlecht = strGeschlecht;
        this.dblGroesse = dblGroesse;
        this.dblGewicht = dblGewicht;
        this.dblBmi = (this.dblGewicht / Math.pow(this.dblGroesse/100,2)) ;
    }

    //endregion

    //region 3. Getter und Setter

    public int getiIdPerson() {
        return iIdPerson;
    }

    public String getStrName() {
        return strName;
    }

    public String getstrGeschlecht() {
        return strGeschlecht;
    }

    public int getiAlter() {
        return iAlter;
    }

    public double getDblGroesse() {
        return dblGroesse;
    }

    public double getDblGewicht() {
        return dblGewicht;
    }

    public double getDblBmi(){

        this.dblBmi = this.dblGewicht / Math.pow(this.dblGroesse/100,2);
    return dblBmi;
    }

    public void setiIdPerson(int iIdPerson) {
        this.iIdPerson = iIdPerson;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public void setstrGeschlecht(String strGeschlecht) {
        this.strGeschlecht = strGeschlecht;
    }

    public void setDblBmi(double dblGewicht, double dblGroesse) {

        this.dblBmi = this.dblGewicht / Math.pow(this.dblGroesse/100,2);
    }

    public void setiAlter(int iAlter) {
        this.iAlter = iAlter;
    }

    public void setDblGroesse(double dblGroesse) {
        this.dblGroesse = dblGroesse;
    }

    public void setDblGewicht(double dblGewicht) {
        this.dblGewicht = dblGewicht;
    }

    //endregion


}

