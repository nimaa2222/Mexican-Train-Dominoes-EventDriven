package edu.ramapo.nnaghiza.Mcomputer;
import java.util.*;

import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mplayer.ModelPlayer;
import edu.ramapo.nnaghiza.Mround.ModelRound;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;
import edu.ramapo.nnaghiza.enums.tileStrategy;
import edu.ramapo.nnaghiza.enums.trainStrategy;

public class ModelComputer extends ModelPlayer {

    // *** data members *** //
    private Map<tileStrategy, String> m_tile_strategies;
    private Map<trainStrategy, String> m_train_strategies;

    //the strategies used by the computer during this game
    private Vector<String> m_used_tile_str;
    private Vector<String> m_used_train_str;

    // *** constructors *** //
    public ModelComputer()
    {
        m_tile_strategies = new HashMap<tileStrategy, String>();
        m_train_strategies = new HashMap<trainStrategy, String>();

        m_used_tile_str = new Vector<>();
        m_used_train_str = new Vector<>();

        //the tile strategies
        m_tile_strategies.put(tileStrategy.one_double, "ONLY PLAYABLE DOUBLE");
        m_tile_strategies.put(tileStrategy.highest_double, "PLAYABLE DOUBLE WITH HIGHEST PIP SUM");
        m_tile_strategies.put(tileStrategy.one_matching_double, "ONLY PLAYABLE DOUBLE WITH MATCHING TILES");
        m_tile_strategies.put(tileStrategy.most_matching_double, "PLAYABLE DOUBLE WITH MOST NUMBER OF MATCHING TILES");
        m_tile_strategies.put(tileStrategy.most_matching_double_maxPip, "PLAYABLE DOUBLE WITH MOST NUMBER OF MATCHING TILES AND HIGHEST PIP SUM");
        m_tile_strategies.put(tileStrategy.one_tile, "ONLY PLAYABLE TILE");
        m_tile_strategies.put(tileStrategy.highest_tile, "PLAYABLE TILE WITH HIGHEST PIP SUM");
        m_tile_strategies.put(tileStrategy.one_double_played, "ONLY PLAYABLE NON-DOUBLE WITH ITS DOUBLE(S) PLAYED");
        m_tile_strategies.put(tileStrategy.double_played_maxPip, "PLAYABLE NON-DOUBLE WITH ITS DOUBLE(S) PLAYED AND HIGHEST PIP SUM");

        //the train strategies
        m_train_strategies.put(trainStrategy.one_eligible_matching, "ONLY ELIGIBLE TRAIN MATCHING SELECTED TILE");
        m_train_strategies.put(trainStrategy.other_opponent, "COULD BE UNMARKED AND NOT AVAILABLE IN THE NEXT TURN");
        m_train_strategies.put(trainStrategy.personal_mexican, "ALMOST ALWAYS AVAILABLE TO THE OPPONENT");
    }

    // *** selectors *** //
    public String getLastTileStr(){return m_used_tile_str.get(m_used_tile_str.size() - 1);}
    public String getLastTrainStr(){return m_used_train_str.get(m_used_train_str.size() - 1);}

    // *** mutators *** //
    public void addTileStr(String a_tile_str){m_used_tile_str.add(a_tile_str);}
    public void addTrainStr(String a_train_str){m_used_train_str.add(a_train_str);}

    // *** utility (private) methods *** //
    /**********************************************************************
     * Function Name: selectTile
     * Purpose: To allow computer player to select a tile from hand
     * @param a_hand, a pile object holding tiles in player hand
     * @param a_eligible_tiles, a pile object holding eligible tiles
     * @param a_current_player, an enum indicating player taking turn now
     * @param a_trains, an array of train objects holding the game trains
     * @return a tile object, the selected tile
     * Algorithm:
     * 		(1) identify the eligible trains
     * 		(2) identify the playable tiles
     * 		(3) identify the playable double tiles (if applicable)
     * 		(4)
     * 			if there is at least one double
     * 				if there is more than one double
     * 					identify all doubles that there is a matching tile for them in hand
     * 					if there is at least one double with a matching tile
     * 						if there is more than one double with matching tile(s)
     * 							identify the max number of matching tiles for the doubles
     * 							identify all doubles with matching tiles = max
     * 							if there is more than one double with matching tiles = max
     * 								select the double with the highest pip sum
     * 								justify selection
     * 							end if
     *
     * 							else
     * 								select the one double with matching tiles = max
     * 							end else
     * 						end if
     *
     * 						else
     * 							select the one double with matching tile(s)
     * 							justify selection
     * 						end else
     * 					end if
     * 					else
     * 						select this one double with the highest pip sum
     * 						justify selection
     * 					end else
     * 				end if
     * 				else
     * 					select the one playable double in hand
     * 				end else
     * 			end if
     * 			else
     * 				if there is more than one non-double
     * 					establish the doubles that have been played in the game
     * 					establish the non-doubles that at least one of their doubles is played
     * 					if there is at least one non-double for which its double(s) is played
     * 						if there is more than one non-double for which one of its doubles is played
     * 							select the non-double with the highest pip sum
     * 							justify selection
     * 						end if
     * 						else
     * 							select the one non-double that its double(s) is played
     * 						end else
     * 					end if
     * 					else
     * 						select the non-double with the highest pip sum
     * 					end else
     * 				end if
     * 				else
     * 					select the one playable tile
     * 				end else
     * 			end else
     * Assistance Received: None
     ******************************************************************** */
    public ModelTile selectTile(ModelPile a_hand, ModelPile a_eligible_tiles, gamePlayer a_current_player, ModelTrain[] a_trains)
    {
        //the tile to be selected
        ModelTile selected_tile = new ModelTile();

        //extract double tiles
        ModelPile double_tiles = new ModelPile();
        for (int i=0; i<a_eligible_tiles.getSize(); i++)
        {
            if (a_eligible_tiles.getTile(i).isDouble())
                double_tiles.addTileToPile(a_eligible_tiles.getTile(i));
        }

        //if there is at least one double
        if (double_tiles.getSize() != 0)
        {
            //if there is more than one double
            if (double_tiles.getSize() > 1)
            {
                //identify all doubles that there is a matching tile for them in hand
                ModelPile doubles_matching = new ModelPile();
                for (int i=0; i<double_tiles.getSize(); i++)
                {
                    //if there is at least one tile that matches this double in hand
                    if (getNumMatchingTiles(a_hand, double_tiles.getTile(i)) != 0)
                        doubles_matching.addTileToPile(double_tiles.getTile(i));
                }

                //if there is at least one double with a matching tile
                if (doubles_matching.getSize() != 0)
                {
                    //if there is more than one double with matching tile(s)
                    if (doubles_matching.getSize() > 1)
                    {
                        //identify the max number of matching tiles for the doubles
                        int max_matches = 0;
                        for (int i=0; i<doubles_matching.getSize(); i++)
                        {
                            if (max_matches < getNumMatchingTiles(a_hand, doubles_matching.getTile(i)))
                                max_matches = getNumMatchingTiles(a_hand, doubles_matching.getTile(i));
                        }

                        //identify all doubles with matching tiles = max
                        ModelPile doubles_matching_max = new ModelPile();

                        for (int i=0; i<doubles_matching.getSize(); i++)
                        {
                            if (max_matches == getNumMatchingTiles(a_hand, doubles_matching.getTile(i)))
                                doubles_matching_max.addTileToPile(doubles_matching.getTile(i));
                        }

                        //if there is more than one double with matching tiles = max
                        if (doubles_matching_max.getSize() > 1)
                        {
                            //select the double with the highest pip sum
                            selected_tile = doubles_matching_max.getHighestTile();

                            //Code 4: playable double with the most number of matching tiles in hand and highest pip sum
                            recordTileStrategy(tileStrategy.most_matching_double_maxPip);
                        }

                        //if there is only one double with matching tiles = max
                        else
                        {
                            //select this one double with matching tiles = max
                            selected_tile = doubles_matching_max.getTile(0);

                            //Code 3: playable double with the most number of tiles in hand that could match it
                            recordTileStrategy(tileStrategy.most_matching_double);
                        }
                    }

                    //if there is only one double with matching tile(s)
                    else
                    {
                        //select this one double with matching tile(s)
                        selected_tile = doubles_matching.getTile(0);

                        //Code 2: only playable double that has one or more matching tiles in hand
                        recordTileStrategy(tileStrategy.one_matching_double);
                    }
                }

                //if there is no double with a matching tile
                else
                {
                    //select the double with the highest pip sum
                    selected_tile = double_tiles.getHighestTile();

                    //Code 1: playable double with the highest pip sum in hand
                    recordTileStrategy(tileStrategy.highest_double);
                }
            }

            //if there is only one double
            else
            {
                //select this one and only playable double in hand
                selected_tile = double_tiles.getTile(0);

                //Code 0: only playable double in hand
                recordTileStrategy(tileStrategy.one_double);
            }
        }

        //if there is no doubles
        else
        {
            //if there is more than one non-double
            if (a_eligible_tiles.getSize() > 1)
            {
                //establish the doubles that have been played in the game (including the engine)
                ModelPile played_doubles = getPlayedDoubles(a_trains);

                //establish the non-doubles that at least one of their doubles is played
                ModelPile non_doubles_played = getNonDoublesPlayed(a_eligible_tiles, played_doubles);

                //if there is at least one non-double for which its double(s) is played
                if (non_doubles_played.getSize() != 0)
                {
                    //if there is more than one non-double for which one of its doubles is played
                    if (non_doubles_played.getSize() > 1)
                    {
                        //select the non-double with the highest pip sum
                        selected_tile = non_doubles_played.getHighestTile();

                        //Code 8: playable non-double with its double(s) played and highest pip sum
                        recordTileStrategy(tileStrategy.double_played_maxPip);
                    }

                    //if there is only one non-double for which one of its doubles is played
                    else
                    {
                        //select this one non-double that its double(s) is played
                        selected_tile = non_doubles_played.getTile(0);

                        //Code 7: only playable non-double with its double(s) played
                        recordTileStrategy(tileStrategy.one_double_played);
                    }
                }

                //if there is no non-double for which one of its doubles is played
                else
                {
                    //select the non-double with the highest pip sum
                    selected_tile = a_eligible_tiles.getHighestTile();

                    //Code 6: playable tile with the highest pip sum
                    recordTileStrategy(tileStrategy.highest_tile);
                }
            }

            //if there is only one non-double
            else
            {
                //select this one and only playable tile
                selected_tile = a_eligible_tiles.getTile(0);

                //Code 5: only playable tile
                recordTileStrategy(tileStrategy.one_tile);
            }
        }

        return selected_tile;
    }

    /**********************************************************************
     * Function Name: printTileStrategy
     * Purpose: To record logistics of computer's tile selection
     * @param a_code, an enum indicating the strategy used to make selection
     * Assistance Received: None
     ******************************************************************* */
    private void recordTileStrategy(tileStrategy a_code)
    {
        //look up the strategy
        String msg = m_tile_strategies.get(a_code);

        //record the strategy
        m_used_tile_str.add(msg);
    }

    /********************************************************************************
     * Function Name: selectTrain
     * Purpose: To allow computer player to select a train
     * @param a_trains, an array of train objects holding the game trains
     * @param a_eligible_trains, an array of integers indicating eligible trains
     * @param a_current_player, an enum indicating player taking turn now
     * @param a_selected_tile, a til object indicating tile selected by human to play
     * @return an enum, indicating the selected train
     * Algorithm:
     * 	(1) identify the eligible matching trains
     * 	(2)
     * 		if there is only one matching eligible train
     * 			justify selection
     * 			select this train
     * 		end if
     *
     * 		else
     * 			if there is 2 eligible trains and they are personal and mexican
     * 				justify selection
     * 				select mexican train
     * 			else
     * 				justify selection
     * 				select opponent train
     * 			end else
     * 		end else
     *
     * Assistance Received: None
     ****************************************************************************** */
    public gameTrain selectTrain(ModelTrain[] a_trains, Vector<Integer> a_eligible_trains, gamePlayer a_current_player, ModelTile a_selected_tile)
    {
        gameTrain selected_train;

        //identify matching eligible trains - train that is eligible and matches the selected tile
        Vector<Integer> eligible_matching = new Vector<Integer>();

        for (int i=0; i<a_eligible_trains.size(); i++)
        {
            if (a_trains[a_eligible_trains.get(i)].matches(a_selected_tile))
                eligible_matching.add(a_eligible_trains.get(i));
        }

        //if there is only one matching eligible train
        if (eligible_matching.size() == 1)
        {
            //select the only eligible train
            selected_train = gameTrain.values()[eligible_matching.get(0)];

            //Code 0: ONLY ELIGIBLE TRAIN THAT MATCHES THE SELECTED TILE
            recordTrainStrategy(trainStrategy.one_eligible_matching);
        }

        //if there is more than one matching eligible trains
        else
        {
            gameTrain opponent_train;
            gameTrain personal_train;

            if (a_current_player == gamePlayer.comp_player)
            {
                personal_train = gameTrain.comp_train;
                opponent_train = gameTrain.human_train;
            }

            else
            {
                personal_train = gameTrain.human_train;
                opponent_train = gameTrain.comp_train;
            }

            //sort to identify correctly
            Collections.sort(eligible_matching);

            //if there is 2 eligible trains and they are personal and mexican
            if (eligible_matching.size() == 2 && gameTrain.values()[eligible_matching.get(0)] == personal_train &&
                    gameTrain.values()[eligible_matching.get(1)] == gameTrain.mexican_train)
            {
                //select mexican train
                selected_train = gameTrain.mexican_train;

                //Code 2: MEXICAN IS ALMOST ALWAYS AVAILABLE TO THE OPPONENT
                recordTrainStrategy(trainStrategy.personal_mexican);
            }

            //in all other cases
            else
            {
                //select opponent train
                selected_train = opponent_train;

                //Code 1: OPPONENT TRAIN COULD BE UNMARKED AND NOT AVAILABLE IN THE NEXT TURN
                recordTrainStrategy(trainStrategy.other_opponent);
            }
        }

        return selected_train;
    }

    /***********************************************************************
     * Function Name: printTrainStrategy
     * Purpose: To record logistics of computer's train selection
     * @param a_code, an enum indicating the strategy used to make selection
     * Assistance Received: None
     ******************************************************************** */
    private void recordTrainStrategy(trainStrategy a_code)
    {
        //look up the strategy
        String msg = m_train_strategies.get(a_code);

        //record the strategy
        m_used_train_str.add(msg);
    }

    /*****************************************************************************
     * Function Name: getNumMatchingTiles
     * Purpose: To find the number of tiles in hand that match a given double tile
     * @param a_hand, a pile object holding tiles in the player hand
     * @param a_tile, a tile object to check the hand against
     * @return an integer, the number of tiles that match the given double tile
     * Assistance Received: None
     *************************************************************************** */
    private int getNumMatchingTiles(ModelPile a_hand, ModelTile a_tile)
    {
        int num_matching_tiles = 0;
        for (int i=0; i<a_hand.getSize(); i++)
        {
            //if this tile in hand matches the double and it's not the double itself
            if (a_hand.getTile(i).tileMatchesValue(a_tile.getFront()) && !a_hand.getTile(i).isEqual(a_tile))
                num_matching_tiles++;
        }

        return num_matching_tiles;
    }

    /*****************************************************************************
     * Function Name: getPlayedDoubles
     * Purpose: To identify the doubles played on trains so far in the round
     * @param a_trains, an array of train objects holding tiles in all trains
     * @return a pile object, holding all the played double tiles
     * Assistance Received: None
     *************************************************************************** */
    private ModelPile getPlayedDoubles(ModelTrain[] a_trains)
    {
        ModelPile played_doubles = new ModelPile();

        for (int i = 0; i< ModelRound.m_num_trains; i++)
        {
            //get the doubles in this train
            ModelPile doubles_in_train = a_trains[i].getDoublesInTrain();

            //add them to the list
            for (int j=0; j<doubles_in_train.getSize(); j++)
                played_doubles.addTileToPile(doubles_in_train.getTile(j));
        }

        //add the engine
        played_doubles.addTileToPile(a_trains[0].getTile(0));

        return played_doubles;
    }

    /*************************************************************************************
     * Function Name: getNonDoublesPlayed
     * Purpose: To identify the non-doubles in hand that "their doubles" has been played
     * @param a_non_doubles, a pile object holding the non-double tiles in hand
     * @param a_played_doubles, a pile object holding the played double tiles
     * @return a pile object, holding all non-doubles that "their doubles" has been played
     * Assistance Received: None
     *********************************************************************************** */
    private ModelPile getNonDoublesPlayed(ModelPile a_non_doubles, ModelPile a_played_doubles)
    {
        ModelPile non_doubles_played = new ModelPile();

        for (int i=0; i<a_non_doubles.getSize(); i++)
        {
            for (int j=0; j<a_played_doubles.getSize(); j++)
            {
                //if the double(s) of this non-double is played
                if (a_non_doubles.getTile(i).tileMatchesValue(a_played_doubles.getTile(j).getFront()))
                    non_doubles_played.addTileToPile(a_non_doubles.getTile(i));
            }
        }

        return non_doubles_played;
    }
}
