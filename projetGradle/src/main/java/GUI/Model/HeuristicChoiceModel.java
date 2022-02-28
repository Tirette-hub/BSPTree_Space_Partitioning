package GUI.Model;

import DataStructure.EHeuristic;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
    private BooleanProperty h1, h2, h3;

    //constructor

    /**
     * Constructor.
     */
    public HeuristicChoiceModel(){
        h1 = new SimpleBooleanProperty(true);
        h2 = new SimpleBooleanProperty(false);
        h3 = new SimpleBooleanProperty(false);

        //creating listeners on booleans value to bind variables with view
        h1.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    h2.set(false);
                    h3.set(false);
                }
                else{
                    h1.set(true);
                    h2.set(false);
                    h3.set(false);
                }
            }
        });
        h2.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    h1.set(false);
                    h3.set(false);
                }
                else{
                    h1.set(false);
                    h2.set(true);
                    h3.set(false);
                }
            }
        });
        h3.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    h1.set(false);
                    h2.set(false);
                }
                else{
                    h1.set(false);
                    h2.set(false);
                    h3.set(true);
                }
            }
        });
    }

    //setters

    /**
     * Set the flag associated to the heuristic 1 radio button.
     * @param h1
     *      Flag value.
     */
    public void setH1(boolean h1) {
        this.h1.set(h1);
    }

    /**
     * Set the flag associated to the heuristic 2 radio button.
     * @param h2
     *      Flag value.
     */
    public void setH2(boolean h2) {
        this.h2.set(h2);
    }


    /**
     * Set the flag associated to the heuristic 3 radio button.
     * @param h3
     *      Flag value.
     */
    public void setH3(boolean h3) {
        this.h3.set(h3);
    }

    //getters
    /**
     * Get the property object associated to the heuristic 1 radio button in order ro bind it.
     * @return
     *      Property.
     */
    public BooleanProperty h1Property() {
        return h1;
    }

    /**
     * Get the property object associated to the heuristic 2 radio button in order ro bind it.
     * @return
     *      Property.
     */
    public BooleanProperty h2Property() {
        return h2;
    }

    /**
     * Get the property object associated to the heuristic 3 radio button in order ro bind it.
     * @return
     *      Property.
     */
    public BooleanProperty h3Property() {
        return h3;
    }

    /**
     * Get the flag associated to the heuristic 1 radio button.
     * @return
     *      Flag value.
     */
    public boolean getH1() {
        return h1.get();
    }

    /**
     * Get the flag associated to the heuristic 2 radio button.
     * @return
     *      Flag value.
     */
    public boolean getH2() {
        return h2.get();
    }

    /**
     * Get the flag associated to the heuristic 3 radio button.
     * @return
     *      Flag value.
     */
    public boolean getH3() {
        return h3.get();
    }

    /**
     * Get the heuristic associated to the currently chosen heuristic.
     * @return
     *      EHeuristic Object.
     */
    public EHeuristic getChoice(){
        if (h1.get())
            return EHeuristic.H1;
        else if (h2.get())
            return EHeuristic.H2;
        else
            return EHeuristic.H3;
    }
}
