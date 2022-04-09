package GUI;

import GUI.Controller.APPController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppView.fxml"));
        Parent root = (Parent)loader.load();
        APPController controller = (APPController) loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("GUI Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
