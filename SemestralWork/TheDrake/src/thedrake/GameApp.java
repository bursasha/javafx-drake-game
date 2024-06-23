package thedrake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Main application class for The Drake game.
 * This class initializes and starts the JavaFX application.
 */
public class GameApp extends Application {

    private static final String BACKGROUND_IMAGE_PATH = "./images/background.jpg";
    private static final String MAIN_MENU_LAYOUT_PATH = "./views/layouts/MainMenu.fxml";
    private static final String STYLE_SHEET_PATH = "./views/styles/style.css";
    private static final String APPLICATION_TITLE = "The Drake Game";
    private static final double WINDOW_WIDTH = 1200;
    private static final double WINDOW_HEIGHT = 1200;

    /**
     * Entry point for JavaFX application.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @throws Exception if there is an issue during loading the FXML or resources
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_IMAGE_PATH)).toExternalForm());
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setFitHeight(WINDOW_HEIGHT);
        imageView.setFitWidth(WINDOW_WIDTH);
        imageView.setPreserveRatio(true);

        // Load main menu layout from FXML
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MAIN_MENU_LAYOUT_PATH)));

        // Create and set up the scene
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(STYLE_SHEET_PATH)).toExternalForm());

        // Set up the primary stage
        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method which serves as the entry point for the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

