package thedrake.controllers.mainmenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the main menu in the Drake game.
 * This class handles the interactions with the main menu buttons.
 */
public class MainMenuController implements Initializable {

    private static final String FONT_PATH = "/thedrake/fonts/Cinzel/static/Cinzel-SemiBold.ttf";

    @FXML
    private Button playerVSPlayerGameButton; // Button for starting a player vs player game

    @FXML
    private Button playerVSPCGameButton; // Button for starting a player vs computer game

    @FXML
    private Button onlineGameButton; // Button for starting an online game

    @FXML
    private Button exitButton; // Button for exiting the game

    /**
     * Initializes the controller class.
     * This method is automatically called after the fxml file has been loaded.
     *
     * @param url the location used to resolve relative paths for the root object,
     *            or null if the location is not known
     * @param resourceBundle the resources used to localize the root object,
     *                       or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load custom font for the application
        Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 16);
    }

    /**
     * Handles the action when the Player VS Player Game button is pressed.
     *
     * @param event the event triggered by pressing the button
     */
    @FXML
    public void handlePlayerVSPlayerGameButton(ActionEvent event) {
        System.out.println("Player VS Player Game Button Pressed!");
    }

    /**
     * Handles the action when the Player VS PC Game button is pressed.
     *
     * @param event the event triggered by pressing the button
     */
    @FXML
    public void handlePlayerVSPCGameButton(ActionEvent event) {
        System.out.println("Player VS PC Game Button Pressed!");
    }

    /**
     * Handles the action when the Online Game button is pressed.
     *
     * @param event the event triggered by pressing the button
     */
    @FXML
    public void handleOnlineGameButton(ActionEvent event) {
        System.out.println("Online Game Button Pressed!");
    }

    /**
     * Handles the action when the Exit button is pressed.
     * Shows a confirmation dialog and exits the game if confirmed.
     *
     * @param event the event triggered by pressing the button
     */
    @FXML
    public void handleExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Exit Game");
        alert.setHeaderText("You are going to exit the game!");
        alert.setContentText("Do you really want to exit the game?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Stage stage = (Stage) this.exitButton.getScene().getWindow();
            stage.close();
        }
    }
}
