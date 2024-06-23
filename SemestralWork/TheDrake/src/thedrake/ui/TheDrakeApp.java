package thedrake.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import thedrake.models.boards.*;
import thedrake.models.positions.factories.*;
import thedrake.models.setups.*;
import thedrake.models.states.*;
import thedrake.models.tiles.interfaces.*;

public class TheDrakeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoardView boardView = new BoardView(createSampleGameState());
        primaryStage.setScene(new Scene(boardView));
        primaryStage.setTitle("The Drake");
        primaryStage.show();
    }

    private static GameState createSampleGameState() {
        Board board = new Board(4);
        PositionFactory positionFactory = board.positionFactory();
        board = board.withTiles(new Board.TileAt(positionFactory.pos(1, 1), BoardTile.MOUNTAIN));
        return new StandardDrakeSetup().startState(board)
            .placeFromStack(positionFactory.pos(0, 0))
            .placeFromStack(positionFactory.pos(3, 3))
            .placeFromStack(positionFactory.pos(0, 1))
            .placeFromStack(positionFactory.pos(3, 2))
            .placeFromStack(positionFactory.pos(1, 0))
            .placeFromStack(positionFactory.pos(2, 3));
    }
}
