package thedrake.ui;

import javafx.scene.image.Image;
import thedrake.models.boards.*;
import thedrake.models.troops.*;

import java.io.InputStream;

public class TroopImageSet {
    private final Image troopFrontB;
    private final Image troopBackB;
    private final Image troopFrontO;
    private final Image troopBackO;

    public TroopImageSet(String troopName) {
        this.troopFrontB = new Image(assetFromJAR("front" + troopName + "B"));
        this.troopBackB = new Image(assetFromJAR("back" + troopName + "B"));
        this.troopFrontO = new Image(assetFromJAR("front" + troopName + "O"));
        this.troopBackO = new Image(assetFromJAR("back" + troopName + "O"));
    }

    private InputStream assetFromJAR(String fileName) {
        return getClass().getResourceAsStream("/assets/" + fileName + ".png");
    }

    public Image get(PlayingSide side, TroopFace face) {
        if (side == PlayingSide.BLUE) {
            if (face == TroopFace.AVERS)
                return this.troopFrontB;
            else
                return this.troopBackB;
        } else {
            if (face == TroopFace.AVERS)
                return this.troopFrontO;
            else
                return this.troopBackO;
        }
    }
}
