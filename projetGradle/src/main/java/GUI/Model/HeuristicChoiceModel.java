package GUI.Model;

import DataStructure.EHeuristic;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * Model of HeuristicChoiceController.
 * Stores the heuristic radio buttons values.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class HeuristicChoiceModel {
    private ToggleGroup toggleGroup;

    //constructor

    /**
     * Constructor.
     */
    public HeuristicChoiceModel(){
        toggleGroup = new ToggleGroup();
    }

    //setters

    //getters

    /**
     * Get the heuristic associated to the currently chosen heuristic.
     * @return
     *      EHeuristic Object.
     */
    public EHeuristic getChoice(){
        ObservableList<Toggle> h = toggleGroup.getToggles();
        if (h.get(0) == toggleGroup.getSelectedToggle())
            return EHeuristic.H1;
        else if (h.get(1) == toggleGroup.getSelectedToggle())
            return EHeuristic.H2;
        else
            return EHeuristic.H3;
    }

    public ToggleGroup getToggleGroup(){
        return toggleGroup;
    }
}
