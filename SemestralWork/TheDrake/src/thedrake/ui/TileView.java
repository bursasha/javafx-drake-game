package thedrake.ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import thedrake.models.positions.boards.*;
import thedrake.models.tiles.interfaces.*;
import thedrake.models.moves.interfaces.*;

public class TileView extends Pane {

    private BoardPos boardPos;

    private Tile tile;

    private TileBackgrounds backgrounds = new TileBackgrounds();

    private Border selectBorder = new Border(
        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    private TileViewContext tileViewContext;

    private Move move;

    private final ImageView moveImage;

    public TileView(BoardPos boardPos, Tile tile, TileViewContext tileViewContext) {
        this.boardPos = boardPos;
        this.tile = tile;
        this.tileViewContext = tileViewContext;

        setPrefSize(100, 100);
        update();

        setOnMouseClicked(e -> onClick());

        this.moveImage = new ImageView(getClass().getResource("/assets/move.png").toString());
        this.moveImage.setVisible(false);
        getChildren().add(this.moveImage);
    }

    private void onClick() {
        if (this.move != null)
            this.tileViewContext.executeMove(this.move);
        else if (this.tile.hasTroop())
            select();
    }

    public void select() {
        setBorder(this.selectBorder);
        this.tileViewContext.tileViewSelected(this);
    }

    public void unselect() {
        setBorder(null);
    }

    public void update() {
        setBackground(this.backgrounds.get(this.tile));
    }

    public void setMove(Move move) {
        this.move = move;
        this.moveImage.setVisible(true);

    }

    public void clearMove() {
        this.move = null;
        this.moveImage.setVisible(false);
    }

    public BoardPos position() {
        return this.boardPos;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

}
