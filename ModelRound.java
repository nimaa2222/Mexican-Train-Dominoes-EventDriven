package edu.ramapo.nnaghiza.Mround;

import java.util.Random;
import java.util.Vector;

import edu.ramapo.nnaghiza.Mcomputer.ModelComputer;
import edu.ramapo.nnaghiza.Mgame.ModelGame;
import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.Mdeck.ModelDeck;
import edu.ramapo.nnaghiza.Mturn.ModelTurn;
import edu.ramapo.nnaghiza.enums.coinSide;
import edu.ramapo.nnaghiza.enums.comparisonOutcome;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;

public class ModelRound {

    // *** data members *** //
    public static int m_num_trains = 3;
    private ModelPile m_boneyard;

    //computer hand, human hand
    private ModelPile[] m_hands;

    //computer train, human train, mexican train
    public ModelTrain[] m_trains;

    private ModelTile m_engine;
    private gamePlayer m_current_player;
    private comparisonOutcome m_winner;

    //set to true when player skips turn because boneyard is empty
    boolean m_comp_skips;
    boolean m_human_skips;

    //set to true when player chooses to quit or save game before the round is over
    boolean m_incomplete_round;

    //the turns taken in the round
    private Vector<ModelTurn> m_turns;

    //player points for the round
    private int[] m_player_points;


    // *** constructors *** //
    public ModelRound(ModelPile a_boneyard, ModelPile[] a_hands, ModelTrain[] a_trains, ModelTile a_engine, gamePlayer a_current_player)
    {
        m_boneyard = a_boneyard;
        m_hands = a_hands;
        m_trains = a_trains;
        m_engine = a_engine;
        m_current_player = a_current_player;
        m_winner = comparisonOutcome.tie;

        //default for loading from file
        m_comp_skips = false;
        m_human_skips = false;
        m_incomplete_round = false;
        m_turns = new Vector<ModelTurn>();
        m_player_points = new int[ModelGame.m_num_players];
    }

    public ModelRound()
    {
        m_boneyard = new ModelPile();

        m_hands = new ModelPile[ModelGame.m_num_players];
        for (int i=0; i<ModelGame.m_num_players; i++)
            m_hands[i] = new ModelPile();

        m_trains = new ModelTrain[m_num_trains];
        for (int i=0; i<m_num_trains; i++)
            m_trains[i] = new ModelTrain();

        m_engine = new ModelTile();

        //TBD by score comparison or coin toss
        m_current_player = gamePlayer.unknown;

        m_winner = comparisonOutcome.tie;
        m_comp_skips = false;
        m_human_skips = false;
        m_incomplete_round = false;
        m_turns = new Vector<ModelTurn>();
        m_player_points = new int[ModelGame.m_num_players];
    }

    // *** selectors *** //
    public ModelTile getEngine(){return m_engine;}
    public ModelPile  getBoneyard(){return m_boneyard;}
    public ModelPile[] getHands(){return m_hands;}
    public ModelTrain[] getTrains(){return m_trains;}
    public ModelPile getCompHand(){return m_hands[gamePlayer.comp_player.ordinal()];}
    public ModelPile getHumanHand(){return  m_hands[gamePlayer.human_player.ordinal()];}
    public int getCompPoints(){return m_player_points[gamePlayer.comp_player.ordinal()]; }
    public int getHumanPoints(){return m_player_points[gamePlayer.human_player.ordinal()];}
    public int[] getPoints(){return m_player_points;}
    public ModelTrain getCompTrain(){return m_trains[gameTrain.comp_train.ordinal()];}
    public ModelTrain getHumanTrain(){return  m_trains[gameTrain.human_train.ordinal()];}
    public ModelTrain getMexicanTrain(){return  m_trains[gameTrain.mexican_train.ordinal()];}
    public ModelTurn getCurrentTurn(){return m_turns.get(m_turns.size()-1);}
    public comparisonOutcome getWinner(){return m_winner;}
    public gamePlayer getCurrentPlayer() {return m_current_player;}

    public gamePlayer getNextPlayer()
    {
        if (m_current_player == gamePlayer.comp_player)
            return gamePlayer.human_player;

        return gamePlayer.comp_player;
    }

    public ModelTile getBoneyardTop()
    {
        if (m_boneyard.getSize() == 0)
            return new ModelTile(-1, -1);
        else
            return m_boneyard.getTileOnTop();
    }

    // *** mutators *** //
    public ModelTile removeBoneyardTop(){return m_boneyard.removeTileOnTop();}
    public void setCurrentPlayer(gamePlayer a_player){m_current_player = a_player;}

    /**********************************************************
     * Function Name: identifyRoundStarter
     * Purpose: To identify the player to start the round
     * @param a_comp_score, the computer score in the game
     * @param a_human_score, the human score in the game
     * Assistance Received: None
     ******************************************************* */
    public void identifyRoundStarter(int a_comp_score, int a_human_score)
    {
        //compare the player scores to identify first player
        comparisonOutcome outcome = ModelGame.compareScores(a_comp_score, a_human_score);

        if (outcome == comparisonOutcome.human_lower)
            m_current_player = gamePlayer.human_player;
        else if (outcome == comparisonOutcome.comp_lower)
            m_current_player = gamePlayer.comp_player;
    }

    /**********************************************************
     * Function Name: identifyWinner
     * Purpose: To identify the winner of the round
     * Assistance Received: None
     ******************************************************* */
    public void identifyWinner()
    {
        for (int i=0; i<ModelGame.m_num_players; i++)
            m_player_points[i] = m_hands[i].getPileSum();

        m_winner = ModelGame.compareScores(m_player_points[gamePlayer.comp_player.ordinal()],
                m_player_points[gamePlayer.human_player.ordinal()]);
    }

    // *** utility (private) methods *** //
    /**********************************************************************************
     * Function Name: performCoinToss
     * Purpose: To simulate a real life coin toss
     * @return an enum, indicating the player that won the coin toss
     * Algorithm:
     * 	 (1) generate a random number between 0 and 1
     * 	 (2) ask user to select between 0 and 1
     * 	 (3)
     * 	 	if human selection matches the random number
     * 	 		human guessed right
     * 	 	end if
     * 	 	else
     * 	 		human guessed wrong
     * 	 	end else
     * Assistance Received: None
     ********************************************************************************* */
    public static gamePlayer performCoinToss(coinSide a_human_selection)
    {
        Random random = new Random();
        boolean rand_selection = random.nextBoolean();

        //the side that the coin landed in
        coinSide landedSide;

        if (rand_selection)
            landedSide = coinSide.heads;
        else
            landedSide = coinSide.tails;

        //if human guessed right
        if (a_human_selection == landedSide)
            return gamePlayer.human_player;
        else
            return gamePlayer.comp_player;
    }

    /************************************************************************
     * Function Name: setUpRound
     * Purpose: To establish the primary elements of a round
     * @param a_deck, a deck object holding all tiles in game deck
     * @param a_round_number, an integer indicating the current round number
     * Algorithm:
     * 	 (1) identify the round engine
     * 	 (2) remove the engine from the deck
     * 	 (3) add engine to player trains
     * 	 (4) shuffle the deck
     * 	 (5) randomly distribute 16 tiles to each player from the deck
     * 	 (6) place the remaining tiles in the boneyard
     *
     * Assistance Received: None
     ********************************************************************* */
    public void setUpRound(ModelDeck a_deck, int a_round_number)
    {
        //the player to start the round is identified

        //identify the round engine
        int engine_num = identifyEngine(a_round_number);
        m_engine = new ModelTile(engine_num, engine_num);

        //remove engine from the deck
        a_deck.removeTile(m_engine);

        //add engine to player trains
        for (int i = 0; i< m_num_trains; i++)
            m_trains[i].addTile(m_engine);

        //shuffle the deck for this round
        a_deck.shuffle();

        //give 16 tiles to each player randomly
        int tiles_to_add = 16;

        //instance of random class
        Random rand = new Random();

        //as long as all the specified tiles are not added to the player's hand
        for (int i=0; i<ModelGame.m_num_players; i++)
        {
            while (m_hands[i].getSize() < tiles_to_add)
            {
                //generate a random number between 0 and (new size of the deck - 1)
                int rand_num = rand.nextInt(a_deck.getCurrentSize());

                //add the tile matching the index to the player's hand from the deck
                m_hands[i].addTileToPile(a_deck.getTile(rand_num));

                //remove the tile from the deck
                a_deck.removeTile(a_deck.getTile(rand_num));
            }
        }

        //add the remaining tiles to the boneyard
        for (int i=0; i<a_deck.getCurrentSize(); i++)
            m_boneyard.addTileToPile(a_deck.getTile(i));
    }

    /************************************************************************
     * Function Name: identifyEngine
     * Purpose: To identify the engine for the current round
     * @param a_round_number, an integer indicating the current round number
     * @return an integer, the engine value
     * Assistance Received: None
     ********************************************************************* */
    private int identifyEngine(int a_round_number)
    {
        if (a_round_number > 10)
            a_round_number -= 10;

        int engine_num = 10 - a_round_number;
        return engine_num;
    }

    /************************************************************************************
     * Function Name: prepareTurn
     * Purpose: To get the turn ready for a player to make a move
     * @param a_comp_player, the computer player in the game
     * @return true if player has a move to make, otherwise false
     * Algorithm:
     * if this is the very first turn
     *      establish orphan doubles
     * end if
     * if player has no moves to make
     *      if player has not yet picked from boneyard
     *          if boneyard is not empty
     *              add a tile from boneyard to player's hand
     *              remove the tile from boneyard
     *          end if
     *          else
     *              note that player has skipped turn because boneyard is empty
     *          end else
     *      end if
     *      else
     *          mark player 's train
     *      end else
     * end if
     *
     * Assistance Received: None
     ********************************************************************************* */
    public boolean prepareTurn(ModelComputer a_comp_player)
    {
        //to start the very first turn and new turns once a turn is complete
        ModelTurn thisTurn;

        //if this is the very first turn or a new turn
        if (m_turns.size() == 0 || getCurrentTurn().isOver())
        {
            establishOrphanDoubles();
            thisTurn = new ModelTurn();
            m_turns.add(thisTurn);
        }

        //if player has no possible moves to make
        if (!playerCanMove(m_hands[m_current_player.ordinal()], getCurrentTurn().getDoublesPlaced()))
        {
            if (m_current_player == gamePlayer.comp_player)
            {
                //add this as the computer move
                a_comp_player.addTileStr("NO PLAYABLE TILE");
                a_comp_player.addTrainStr("NO PLAYABLE TRAIN");
            }

            //if player has not yet picked from boneyard in previous turn
            if (!getCurrentTurn().pickedFromBoneyard())
            {
                //if boneyard is not empty
                if (m_boneyard.getSize() != 0)
                {
                    getCurrentTurn().setBoneyardPick(true);

                    //add a tile from boneyard to player's hand
                    m_hands[m_current_player.ordinal()].addTileToPile(m_boneyard.getTileOnTop());

                    //remove the tile from boneyard
                    removeBoneyardTop();

                    //player goes again - to try placing the tile just picked from boneyard on trains
                }

                //if boneyard is empty
                else
                {
                    if (m_current_player  == gamePlayer.comp_player)
                        m_comp_skips = true;
                    else
                        m_human_skips = true;

                    getCurrentTurn().markTurnComplete();
                    m_current_player = getNextPlayer();
                }
            }

            //if player has already picked from boneyard and can't play drawn tile
            else
            {
                //mark player's train
                m_trains[m_current_player.ordinal()].addMarker();

                getCurrentTurn().markTurnComplete();
                m_current_player = getNextPlayer();
            }

            return false;
        }

        return true;
    }

    /*****************************************************************************************
     * Function Name: establishOrphanDoubles
     * Purpose: To mark trains as orphan double before next player's turn
     * Assistance Received: None
     ***************************************************************************************** */
    private void establishOrphanDoubles()
    {
        for (int i = 0; i< m_num_trains; i++)
            m_trains[i].setOrphanDouble();
    }

    /*****************************************************************************************
     * Function Name: playerCanMove
     * Purpose: To determine if player has at least one possible move to make
     * @param a_hand, a pile object indicating the tiles in the player hand
     * @param a_doubles_placed, an integer indicating the number of doubles placed in the turn
     * @return true if play has a move, otherwise false
     * Assistance Received: None
     ***************************************************************************************** */
    public boolean playerCanMove(ModelPile a_hand, int a_doubles_placed)
    {
        //identify the eligible tiles
        ModelPile eligible_tiles = identifyTileCandidates(a_hand, a_doubles_placed);

        //there must be at least one eligible tile
        return (eligible_tiles.getSize()!= 0);
    }


    /************************************************************************
     * Function Name: promptCompSelectTile
     * Purpose: To prompt computer player to select a tile
     * @param a_comp_player, the computer player in the game
     * @return a tile, the selected tile by the computer
     * Assistance Received: None
     ********************************************************************* */
    public ModelTile promptCompSelectTile(ModelComputer a_comp_player)
    {
        //select a tile
        ModelPile eligible_tiles = identifyTileCandidates(m_hands[m_current_player.ordinal()], getCurrentTurn().getDoublesPlaced());
        ModelTile selected_tile = a_comp_player.selectTile(m_hands[m_current_player.ordinal()], eligible_tiles, m_current_player, m_trains);

        return selected_tile;
    }

    /************************************************************************
     * Function Name: promptCompSelectTrain
     * Purpose: To prompt computer player to select a train
     * @param a_comp_player, the computer player in the game
     * @param a_selected_tile, the selected tile by the computer
     * @return a tile, the selected tile by the computer
     * Assistance Received: None
     ********************************************************************* */
    public gameTrain promptCompSelectTrain(ModelComputer a_comp_player, ModelTile a_selected_tile)
    {
        //select a train
        gameTrain selected_train = a_comp_player.selectTrain(m_trains, identifyEligibleTrains(), m_current_player, a_selected_tile);
        return selected_train;
    }

    /************************************************************************
     * Function Name: makeMove
     * Purpose: To place a selected tile on a selected train
     * @param a_selected_tile, the selected tile
     * @param a_selected_train, the selected train
     * @return true if tile is placed, false otherwise
     * Algorithm:
     * (1) identify the eligible tiles
     * (2) identify the eligible trains
     * if an eligible tile and train is selected
     *      if this tile matches this train
     *          add the tile to the train
     *          remove the tile from player hand
     *          remove orphan double marker for this train (if applicable)
     *          if tile was placed on personal train
     *              remove personal train marker
     *          end if
     *          if player placed a double
     *              note that player has placed a double
     *              reset the boneyard marker
     *          end if
     *      end if
     * end if
     *
     * Assistance Received: None
     ********************************************************************* */
    public boolean makeMove(ModelTile a_selected_tile, gameTrain a_selected_train)
    {
        //the eligible tiles
        ModelPile eligible_tiles = identifyTileCandidates(m_hands[m_current_player.ordinal()], getCurrentTurn().getDoublesPlaced());

        //the eligible trains
        Vector<Integer> eligible_trains = identifyEligibleTrains();

        //if an eligible tile and train is selected
        if (eligible_tiles.isTileInPile(a_selected_tile) && eligible_trains.contains(a_selected_train.ordinal()))
        {
            //if this tile matches this train
            if (m_trains[a_selected_train.ordinal()].matches(a_selected_tile))
            {
                //add the tile to the train
                appendTileToTrain(a_selected_tile, a_selected_train.ordinal());

                //remove tile from player hand
                m_hands[m_current_player.ordinal()].removeTileFromPile(a_selected_tile);

                //remove the orphan double marker for this train (if applicable)
                m_trains[a_selected_train.ordinal()].removeOrphanMarker();

                //if player placed a tile on their own train, remove personal train marker
                if (a_selected_train.ordinal() == m_current_player.ordinal())
                    m_trains[m_current_player.ordinal()].removeMarker();

                //if player placed a double
                if (a_selected_tile.isDouble())
                {
                    //note that a double is played
                    getCurrentTurn().doublePlaced();

                    //reset player's boneyard marker
                    getCurrentTurn().setBoneyardPick(false);

                    //player goes again since a double is played
                }

                else
                {
                    getCurrentTurn().markTurnComplete();
                    m_current_player = getNextPlayer();
                }

                return true;
            }
        }

        return false;
    }

    /*****************************************************************************************
     * Function Name: identifyTileCandidates
     * Purpose: To identify all the tiles that are playable in the turn
     * @param a_hand, a pile object indicating the tiles in the player hand
     * @param a_doubles_placed, an integer indicating the number of doubles placed in the turn
     * @return a pile object, holding all the playable tiles
     * Algorithm:
     *      for all the tiles in the hand
     *          if the tile matches any eligible trains
     *
     *              if 1 double is placed, this tile is a double, there is no playing non-double in hand
     *              and this is not the last tile in hand
     *                  skip the tile
     *              end if
     *
     *              if 2 doubles are placed and this tile is a double
     *                  skip the tile
     *              end if
     *
     *              add the tile as a candidate
     *
     *          end if
     *      end for
     *
     * Assistance Received: None
     ***************************************************************************************** */
    private ModelPile identifyTileCandidates(ModelPile a_hand, int a_doubles_placed)
    {
        ModelPile eligible_tiles = new ModelPile();

        for (int i=0; i<a_hand.getSize(); i++)
        {
            //if the tile matches any eligible trains
            if (doesTileMatchAnyTrain(a_hand.getTile(i)))
            {
                //if 1 double is placed, this tile is a double, there is no playing non-double in hand and this is not the last tile in hand
                if (a_doubles_placed == 1 && a_hand.getTile(i).isDouble() && !playingNonDoubleExists(a_hand) &&  a_hand.getSize() != 1)
                {
                    //do not add this tile because double candidates placed after first turn are only eligible if there is a playing non-double in hand
                    //unless it is the last the tile in hand which does not apply here
                    continue;
                }

                //if 2 doubles are placed and this tile is a double
                if (a_doubles_placed == 2 && a_hand.getTile(i).isDouble())
                {
                    //do not add this tile because player must now play a non-double
                    //all doubles are ineligible
                    continue;
                }

                //otherwise the tile is eligible
                eligible_tiles.addTileToPile(a_hand.getTile(i));
            }
        }

        return eligible_tiles;
    }

    /***********************************************************************************
     * Function Name: doesTileMatchAnyTrain
     * Purpose: To check whether a tile matches the end of any eligible trains
     * @param a_tile, a tile object to check against train ends
     * @return true if there is a match for the tile, otherwise false
     * Assistance Received: None
     ********************************************************************************* */
    public boolean doesTileMatchAnyTrain(ModelTile a_tile)
    {
        Vector<Integer> eligible_trains = identifyEligibleTrains();

        for (int i=0; i<eligible_trains.size(); i++)
        {
            if (m_trains[eligible_trains.get(i)].matches(a_tile))
                return true;
        }

        return false;
    }

    /***********************************************************************
     * Function Name: playingNonDoubleExists
     * Purpose: To check whether a non-double in player hand is playable
     * @param a_hand, a pile object indicating the tiles in the player hand
     * @return true if there is a playable non-double, otherwise false
     * Assistance Received: None
     ********************************************************************* */
    private boolean playingNonDoubleExists(ModelPile a_hand)
    {
        for (int i=0; i<a_hand.getSize(); i++)
        {
            //if there is a non-double that matches an eligible train
            if (!a_hand.getTile(i).isDouble() && (doesTileMatchAnyTrain(a_hand.getTile(i))))
            {
                //there is a playing non-double in hand
                return true;
            }
        }

        return false;
    }

    /***********************************************************************************
     * Function Name: identifyEligibleTrains
     * Purpose: To check which trains are eligible to play on
     * @return an array of integers, indicating the index of the eligible trains
     * Algorithm:
     *      (1) identify the orphan double trains
     *      if there is any orphan doubles
     *          these trains are eligible
     *      end if
     * 		else
     * 			personal train is eligible
     * 			mexican train is eligible
     * 			if opponent train is marked
     * 				opponent train is also eligible
     * 			end if
     * 		end else
     *
     * Assistance Received: None
     ********************************************************************************* */
    private Vector<Integer> identifyEligibleTrains()
    {
        Vector<Integer> eligible_trains = new Vector<Integer>();

        for (int i = 0; i< m_num_trains; i++)
        {
            if (m_trains[i].isOrphanDouble())
            {
                int train_size = m_trains[i].getSize();

                //if orphan double is not the engine
                if (!m_trains[i].getTile(train_size - 1).isEqual(m_engine))
                    eligible_trains.add(i);
            }
        }

        //if we have any orphan doubles, no other train is eligible
        if (eligible_trains.size() != 0)
            return eligible_trains;

        //otherwise personal train and mexican train are eligible
        eligible_trains.add(m_current_player.ordinal());
        eligible_trains.add(gameTrain.mexican_train.ordinal());

        //if opponent train is marked, that is another eligible train
        if (m_trains[getNextPlayer().ordinal()].isMarked())
            eligible_trains.add(getNextPlayer().ordinal());

        return eligible_trains;
    }

    /*****************************************************************************
     * Function Name: appendTileToTrain
     * Purpose: To add a tile to the end of a train
     * @param a_tile, a tile object to add to train end
     * @param a_train, an integer indicating the index of the selected train
     * Assistance Received: None
     ************************************************************************** */
    private void appendTileToTrain(ModelTile a_tile, int a_train)
    {
        //if the back side of a non-double matches the end of train
        if (!a_tile.isDouble() && a_tile.getFront() != m_trains[a_train].getEndVal())
            a_tile.flip();

        //add tile to the train
        m_trains[a_train].addTile(a_tile);
    }

    /*****************************************************************************************
     * Function Name: roundIsCompleted
     * Purpose: To check whether the round is over
     * @return true if the round is over, otherwise false
     * Assistance Received: None
     ***************************************************************************************** */
    public boolean roundIsCompleted()
    {
        //if there is no more tiles in a player hand
        for (int i=0; i<ModelGame.m_num_players; i++)
        {
            if (m_hands[i].getSize() == 0)
                return true;
        }

        //if both players have tiles in hand, then...
        //if both players skipped turns because boneyard is empty
        if (m_comp_skips && m_human_skips)
            return true;

        return false;
    }
}
