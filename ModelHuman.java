package edu.ramapo.nnaghiza.Mhuman;

import java.util.Vector;

import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mplayer.ModelPlayer;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;

public class ModelHuman extends ModelPlayer {

    // *** utility (private) methods *** //
    public ModelTile selectTile(ModelPile a_hand, ModelPile a_eligible_tiles, gamePlayer a_current_player, ModelTrain[] a_trains){return new ModelTile();}
    public gameTrain selectTrain(ModelTrain[] a_trains, Vector<Integer> a_eligible_trains, gamePlayer a_current_player, ModelTile a_selected_tile){return gameTrain.mexican_train;}
}
