import util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize SceneManager with global dimensions
        SceneManager.init(primaryStage);
        
        // Load first scene using advanced method
        SceneManager.loadAndSwitch("/view/Login.fxml");
        
        // Window configuration
        primaryStage.setTitle("Library Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}