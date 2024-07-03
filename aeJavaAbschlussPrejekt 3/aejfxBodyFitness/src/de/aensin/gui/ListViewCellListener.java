package de.aensin.gui;

import de.aensin.model.FitnessPlan;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

/**
 * überwacht die Einträge in der ListView und formaritert sie
 */

public class ListViewCellListener extends ListCell<FitnessPlan> {

    /**
     * Ist der Fitnessplan leer, wird eine leere Liste angezeigt,
     * sonst der Fitnessplan des Users {@link FitnessPlan}
     * die in der ListView aus der DB gelsenen wurde
     *
     * @param fitnessPlanCellContent : {@link FitnessPlan} : FitnessPlan der angezeigt werden soll
     * @param empty               :  boolean prüft ob die Zelle in der ListView leer sein soll, oder nicht
     */
    @Override
    protected void updateItem(FitnessPlan fitnessPlanCellContent, boolean empty) {
        super.updateItem(fitnessPlanCellContent, empty);

        if (empty || fitnessPlanCellContent == null) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            this.setFont(Font.font(16D));
            this.setText(fitnessPlanCellContent.toString());
        }
    }
}
