package GUI.Model;

import DataStructure.EHeuristic;
import javafx.collections.ObservableList;
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

    /**
     * Get the ToggleGroup associated to the group of check boxes.
     * @return
     *      ToggleGroup of the check boxes.
     */
    public ToggleGroup getToggleGroup(){
        return toggleGroup;
    }
}
