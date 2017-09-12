/*  
    Michael Clemens
    JavaFXPaintApplication

    This Class is used to load the FXML Controller which is the main controller
    for this project.  This class sets up the stage for the FXML project.
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXSprint1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFX Paint Application");
        Parent root = FXMLLoader.load(getClass().getResource("FXMLStyleSheet.fxml"));
        
        // Sets the scene within the stage for the application
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
