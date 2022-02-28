package GUI.Controller;

import DataStructure.EHeuristic;
import GUI.Model.HeuristicChoiceModel;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

/**
 * Panel where it is possible to choose the heuristic to create the BSP Tree.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class HeuristicChoiceController {
    private HeuristicChoiceModel model;

    @FXML
    private RadioButton fxH1, fxH2, fxH3;

    //constructor called by the fxml file
    /**
     * Method called by the fxml view to create the controller.
     */
    public void initialize(){
        //create model
        model = new HeuristicChoiceModel();

        //link Model with view
        fxH1.selectedProperty().bindBidirectional(model.h1Property());
        fxH2.selectedProperty().bindBidirectional(model.h2Property());
        fxH3.selectedProperty().bindBidirectional(model.h3Property());

        //link controller to view - configurations

        //other controller initialisation
    }

    /**
     * Get the choice of the heuristic.
     * @return
     *      Enum Object representing the choice.
     */
    public EHeuristic getChoice(){
        return model.getChoice();
    }
}
