package edu.ramapo.nnaghiza.Mplayer;
import java.util.Vector;

import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;

public abstract class ModelPlayer {

    //*** pure virtual functions ***//
    public abstract ModelTile selectTile(ModelPile a_hand, ModelPile a_eligible_tiles, gamePlayer a_current_player, ModelTrain[] a_trains);
    public abstract gameTrain selectTrain(ModelTrain[] a_trains, Vector<Integer> a_eligible_trains, gamePlayer a_current_player, ModelTile a_selected_tile);
}
