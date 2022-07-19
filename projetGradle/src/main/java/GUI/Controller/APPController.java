package GUI.Controller;

import DataStructure.FileParser;
import DataStructure.Segment;
import GUI.Model.AppModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


/**
 * Application container with the Menu and the 3 slots in the app where are shown the Scene, the Heuristic choice panel and the representation of the solution view.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 2.0.0
 */
public class APPController{
    private AppModel model;
    private Stage parent;

    //view data
    @FXML
    private SceneController fxSceneController;
    @FXML
    private HeuristicChoiceController fxHeuristicChoiceController;
    @FXML
    private PaintingController fxPaintingController;

    //view callbacks

    /**
     * Callback called by the fxml view when the open button in the menu is pushed.
     * @throws Exception
     *      Handles exceptions when parsing the scene file.
     */
    @FXML
    public void onOpen() throws Exception{
        //create a file chooser object
        FileChooser fc = new FileChooser();
        fc.setTitle("Ouvrir une Scène");
        //set extension filter to be able to only see *.txt files (scene files format)
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );

        //get the file
        File file = fc.showOpenDialog(parent);
        //send the file to the model to store it
        if (file != null){
            model.setFile(file);

            FileParser parser = new FileParser(file.getPath());

            int a, b, abn[] = parser.getParameters();
            a = abn[0]; b = abn[1];

            Segment[] scene = parser.getData().toArray(new Segment[0]);

            //load scene into SceneController
            fxSceneController.setSceneSize(a,b);
            fxSceneController.setData(scene);
            fxSceneController.drawScene(scene);
        }
    }

    /**
     * Callback called by the fxml view in order to quit the application when the quit menu item is pushed.
     */
    @FXML
    public void onQuit(){
        parent.close();
    }

    /**
     * Callback called by the fxml view in order to show the information of the application.
     * @param event
     *      None
     */
    @FXML
    public void onInfo(Event event){
        event.consume();
        /*
        //idée de fonctionnement d'info
        String pathpdf = "/path/to/file.pdf";
        String[] params = {"cmd", "/c", pathpdf};
        try {
            Runtime.getRuntime().exec(params);
        } catch (Exception e) {}
        */
    }

    /**
     * Method called by the fxml view to create the controller.
     */
    //Constructor called by the fxml file
    public void initialize(){
        //create model
        model = new AppModel();

        //link Model with view

        //link controller to view - configurations

        //other controller initialisation
        fxPaintingController.setDataCallback(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //get all the segments
                if (fxSceneController.isDrawn() && fxSceneController.getEye() != null) {
                    fxPaintingController.setData(fxHeuristicChoiceController.getChoice(), fxSceneController.getData(), fxSceneController.getEye());
                }
            }
        });
    }

    //setters

    /**
     * Set the JavaFX Stage used by the FileChooser object.
     *
     * @param stage
     *      Stage of the application.
     */
    public void setStage(Stage stage){
        parent = stage;
    }
}
