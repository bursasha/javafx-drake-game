package thedrake.ui;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import thedrake.models.positions.boards.BoardPos;
import thedrake.models.states.GameState;
import thedrake.models.moves.interfaces.Move;
import thedrake.models.positions.factories.PositionFactory;

public class BoardView extends GridPane implements TileViewContext {

    private GameState gameState;

    private ValidMoves validMoves;

    private TileView selected;

    public BoardView(GameState gameState) {
        this.gameState = gameState;
        this.validMoves = new ValidMoves(gameState);

        PositionFactory positionFactory = gameState.board().positionFactory();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                BoardPos boardPos = positionFactory.pos(x, 3 - y);
                add(new TileView(boardPos, gameState.tileAt(boardPos), this), x, y);
            }
        }

        setHgap(5);
        setVgap(5);
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
    }

    @Override
    public void tileViewSelected(TileView tileView) {
        if (this.selected != null && this.selected != tileView)
            this.selected.unselect();

        this.selected = tileView;

        clearMoves();
        showMoves(this.validMoves.boardMoves(tileView.position()));
    }

    @Override
    public void executeMove(Move move) {
        this.selected.unselect();
        this.selected = null;

        clearMoves();

        this.gameState = move.execute(this.gameState);
        this.validMoves = new ValidMoves(this.gameState);

        updateTiles();
    }

    private void updateTiles() {
        for (Node node : getChildren()) {
            TileView tileView = (TileView) node;
            tileView.setTile(this.gameState.tileAt(tileView.position()));
            tileView.update();
        }
    }

    private void clearMoves() {
        for (Node node : getChildren()) {
            TileView tileView = (TileView) node;
            tileView.clearMove();
        }
    }

    private void showMoves(List<Move> moveList) {
        for (Move move : moveList)
            tileViewAt(move.target()).setMove(move);
    }

    private TileView tileViewAt(BoardPos target) {
        int index = (3 - target.j()) * 4 + target.i();
        return (TileView) getChildren().get(index);
    }

    /**
     * Displays a dialog indicating the winner and provides options to start a new game or return to the main menu.
     * @param winner the name of the winning side
     */
    private void showWinner(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(winner + " has won the game!");

        ButtonType newGameButton = new ButtonType("New Game");
        ButtonType mainMenuButton = new ButtonType("Main Menu");

        alert.getButtonTypes().setAll(newGameButton, mainMenuButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == newGameButton) {
                startNewGame();
            } else if (response == mainMenuButton) {
                showMainMenu();
            }
        });
    }

    /**
     * Starts a new game.
     */
    private void startNewGame() {
        // Logic to start a new game
    }

    /**
     * Shows the main menu.
     */
    private void showMainMenu() {
        // Logic to show the main menu
    }
}
