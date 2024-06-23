package thedrake.ui;

import thedrake.models.moves.interfaces.Move;

public interface TileViewContext {

    void tileViewSelected(TileView tileView);

    void executeMove(Move move);

}
