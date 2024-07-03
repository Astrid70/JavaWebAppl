package de.aensin.gui;

import de.aensin.model.FitnessPlan;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Die Klasse ruft den CellListener für die ListView auf und übergibt die ListView, die vorher schon
 * aus der DB eingelesen wurde
 */

public class ListViewAnzeigeInit implements Callback<ListView<FitnessPlan>, ListCell<FitnessPlan>> {

    public ListCell<FitnessPlan> call(ListView<FitnessPlan> lvFitnessPlan) {
        return new ListViewCellListener();
    }

}
