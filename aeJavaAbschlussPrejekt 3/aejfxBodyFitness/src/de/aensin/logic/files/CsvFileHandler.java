package de.aensin.logic.files;

import de.aensin.model.FitnessPlan;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Verarbeitet die Ein- und Ausgabe des CSV-Files fitnessplan.csv
 * liest die Datei in die List</fitnessPlan> unter Verwendung der Klasse FitnessPlan
 */

public class CsvFileHandler {

    //region 0. Konstanten

    private static final String CSV_FILE_PATH = "src/de/aensin/resource/fitnessplan.csv";
    //endregion

    //region 1. Decl and Init Attribute
    /**
     * es gibt nur eine Instanz der Klasse
     * getInstance() einmal generiert
     * da Contructor privat ist
     */
    private static CsvFileHandler instance;
    //endregion

    //region 2. Konstruktoren

    /**
     * Privater Konstruktor
     * Funktion getInstance();
     */
    private CsvFileHandler() {
        System.out.println(CsvFileHandler.class.getSimpleName() + " generiert");
    }

    //endregion

    //region 3. GetInstance

    /**
     * Instanziiert beim ersten Aufruf
     * die einzige Instanz die es zur gesamten Laufzeit des Programmes gibt
     * So dass die Funktionen und Methoden dieser Klasse  als synchronisierter
     *  Thread und Zugriffssicher genutzt werden kann
     * @return :instance: {@link CsvFileHandler} : Es gibt nur eine  Instanz zur Laufzeit !
     */
    public static synchronized CsvFileHandler getInstance() {

        //Prüft ob das Objekt schonmal generiert wurde, falls nicht wird die Instanz erstellt
        if (instance == null) {

            //Generiert diese Instanz nur einmal
            instance = new CsvFileHandler();
        }

        return instance;
    }
    //endregion

    //region 4. Speichern

    /**
     * Speichert die mitgelieferte {@link List} von {@link FitnessPlan}s
     * in eine CSV-Datei
     *
     * @param fitnessplanListtoSava : {@link List} {@link FitnessPlan}s : die gespeichert werden sollen
     */
    public void saveToCsvFile(List<FitnessPlan> fitnessplanListtoSava) {

        // Anlegen eins Dateiobjektes mit Dateipfad
        File csvFile = new File(CSV_FILE_PATH);

        // Fileoutputstream in Bytes schreiben
        FileOutputStream fos = null;

        // OutputStream-Writer, zum setzen des Formats
        OutputStreamWriter osw = null;

        // Schreibt Strings im Format osw
        BufferedWriter out = null;

        try {
            //prüft ob die Datei bereits existiert
            if (!csvFile.exists()) {

                //existiert die Datei nicht , wird eine neue Datei angelgt
                csvFile.createNewFile();
            }

            // Fos Fileoutputstream für csv-File generieren
            fos = new FileOutputStream(csvFile);

            // ows Zeichensatz setzen für outputstream
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

            // out mit osw generieren
            out = new BufferedWriter(osw);

            // Gesamte Liste verarbeiten
            for (FitnessPlan f : fitnessplanListtoSava) {

                //In Zwischenspeicher von BufferedWriter schreiben
                out.write(f.getAllAttributesAsCsvString() + "\n");
            }


        } catch (Exception e) {
            System.err.println(CSV_FILE_PATH);
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    //5. finally liest immer, auvh wenn ein Fehler auftritt
                    out.flush();

                    //6.  Datei schließen
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    //endregion

    //region 5. Lesen der Csv-Datei

    /**
     * Liest die Datei {@link CsvFileHandler#CSV_FILE_PATH}
     * komplett aus und gibt eine {@link List} zurück
     * @return fitnessPlanFromFile : {@link List} - {@link de.aensin.model.FitnessPlan} : Fitnessplan aus der Datei
     */
    public List<FitnessPlan> readFromCsvFile() {
        // Liste FitnessPlan zum Auslesen des Csv-Files
        List<FitnessPlan> fitnessplanListFromFile = new ArrayList<>();

        // Anlegen eins Dateiobjektes
        File csvFile = new File(CSV_FILE_PATH);

        // Fileinputstream zum lesen in Bytes
        FileInputStream fis = null;

        // Liest Datei und setzt den Zeichensatz
        InputStreamReader isr = null;

        // Liest Strings
        BufferedReader in = null;

        try {
            //prüft ob die Datei existiert
            if (csvFile.exists()) {

                //1. Fis generieren mit CSV-File
                fis = new FileInputStream(csvFile);

                //2. isr Zeichensatz setzen mit dem fis
                isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

                //3. in mit isr generieren
                in = new BufferedReader(isr);

                //End Of File
                boolean eof = false;

                //4. Gesamte Liste verarbeiten
                while (!eof) {

                    //Aktuelle Zeile der Datei auslesen
                    String strReadCsvLine = in.readLine();

                    //Checken ob das Ende der Datei erreicht ist
                    if (strReadCsvLine == null) {
                        eof = true;
                    } else {
                        //Ende der Datei noch nicht erreicht

                        //Neues Objekt anlegen
                        FitnessPlan currentFitnessPlan = new FitnessPlan();

                        //Mit Daten in das Objekt schreiben
                        currentFitnessPlan.setAllAttributesFromCsvLine(strReadCsvLine);

                        //Objekt zur Liste hinzufügen
                        fitnessplanListFromFile.add(currentFitnessPlan);
                    }

                }
            }

        } catch (Exception e) {
            System.err.println(CSV_FILE_PATH);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    // finally wird immer ausgeführt, die Datei wird immer geschlossen
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return fitnessplanListFromFile;
    }
    //endregion
}
