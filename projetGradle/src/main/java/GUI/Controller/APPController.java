package GUI.Controller;

import DataStructure.EColor;
import DataStructure.Segment;
import GUI.Model.AppModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Application container with the Menu and the 3 slots in the app where are shown the Scene, the Heuristic choice panel and the representation of the solution view.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class APPController{
    private AppModel model;
    private Stage parent;
    private File recentFile;

    //view data
    @FXML
    private Menu fxRecentMenu;
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
        openFile(file);
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
        String pathpdf = "/path/to/mode/d/emploi.pdf";
        String[] params = {"cmd", "/c", pathpdf};
        try {
            Runtime.getRuntime().exec(params);
        } catch (Exception e) {}
        */
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez regarder dans le rapport du projet, section \"Mode d'emploi de l'application\"");

        alert.showAndWait();
    }

    private void openFile(File file) throws Exception{
        //send the file to the model to store it
        if (file != null){
            model.setFile(file);

            //parse data from file
            Scanner scanner = new Scanner(file);
            //read first line
            String[] fl = scanner.nextLine().split(" ");
            //get principal data from the file
            int a = Integer.parseInt(fl[1]); //max abs
            int b = Integer.parseInt(fl[2]); //max ord
            int n = Integer.parseInt(fl[3]); //#segment in the scene

            Segment[] scene = new Segment[n];
            int i = 0;

            while(scanner.hasNextLine()){
                //get data from the file
                fl = scanner.nextLine().split(" ");
                double x1, x2, y1, y2;
                EColor color;
                //convert data
                x1 = Double.parseDouble(fl[0]); y1 = Double.parseDouble(fl[1]);
                x2 = Double.parseDouble(fl[2]); y2 = Double.parseDouble(fl[3]);
                color = EColor.getEColorByName(fl[4]);

                //create the segment from a line of data and add it to the scene
                Segment segment = new Segment((x1+a)/2,(y1+b)/2, (x2+a)/2, (y2+b)/2, color);
                scene[i] = segment;
                i++;
            }

            FileWriter recentFilesWriter = new FileWriter(recentFile);
            recentFilesWriter.write(file.getAbsolutePath());
            addRecentFile(file);

            //load scene into SceneController
            fxSceneController.setSceneSize(a,b);
            fxSceneController.setData(scene);
            fxSceneController.drawScene(scene);
        }
    }

    /**
     * Method called by the fxml view to create the controller.
     */
    //Constructor called by the fxml file
    public void initialize() throws Exception{
        //create model
        model = new AppModel();

        //link Model with view

        //link controller to view - configurations

        //other controller initialisation
        fxPaintingController.setDataCallback(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //get all the segments
                if (fxSceneController.isDrawn() && fxSceneController.getPOVPosition() != null) {
                    double[] position = fxSceneController.getPOVPosition();
                    double[] POVData = {
                            position[0],
                            position[1],
                            fxSceneController.getFOV(),
                            fxSceneController.getFOVDirection()
                    };
                    fxPaintingController.setData(fxHeuristicChoiceController.getChoice(), fxSceneController.getData(), POVData);
                }
            }
        });

        String filePath = getClass().getResource("/").getPath() + "recent_files.txt";
        recentFile = new File(filePath);
        if (!recentFile.exists()) {
            recentFile.createNewFile();
            System.out.println("file created");
        }

        ArrayList<File> fileList = new ArrayList<>();

        Scanner scanner = new Scanner(recentFile);
        while(scanner.hasNext())
            fileList.add(new File(scanner.nextLine()));

        for (File f : fileList){
            addRecentFile(f);
        }
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

    private void addRecentFile(File f){
        MenuItem newRecentFile;
        newRecentFile = new MenuItem(f.getAbsolutePath());
        newRecentFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    openFile(f);
                }catch(Exception e){

                }
            }
        });
        fxRecentMenu.getItems().add(newRecentFile);
    }
}
