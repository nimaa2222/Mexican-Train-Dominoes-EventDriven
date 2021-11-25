package edu.ramapo.nnaghiza.Mgame;

import android.app.Activity;
import java.util.Vector;

import edu.ramapo.nnaghiza.Mcomputer.ModelComputer;
import edu.ramapo.nnaghiza.Mdeck.ModelDeck;
import edu.ramapo.nnaghiza.Mfile.ModelFile;
import edu.ramapo.nnaghiza.Mhuman.ModelHuman;
import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mplayer.ModelPlayer;
import edu.ramapo.nnaghiza.Mround.ModelRound;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.enums.comparisonOutcome;
import edu.ramapo.nnaghiza.enums.gamePlayer;

public class ModelGame {

    // *** data members *** //
    public static int m_num_players = 2;
    private ModelDeck m_deck;
    private ModelPlayer[] m_players;
    private int[] m_player_scores;
    private Vector<ModelRound> m_rounds;
    private int m_round_number;
    private comparisonOutcome m_winner;

    // *** constructors *** //
    public ModelGame()
    {
        m_deck = new ModelDeck();

        m_players = new ModelPlayer[m_num_players];
        m_players[gamePlayer.comp_player.ordinal()] = new ModelComputer();
        m_players[gamePlayer.human_player.ordinal()] = new ModelHuman();

        m_player_scores = new int[m_num_players];
        m_rounds = new Vector<ModelRound>();
        m_round_number = 1;
        m_winner = comparisonOutcome.tie;
    }

    // *** selectors *** //
    public ModelDeck getDeck(){return m_deck;}
    public ModelComputer getCompPlayer(){return (ModelComputer)m_players[gamePlayer.comp_player.ordinal()];}
    public int getCompScore() {return m_player_scores[gamePlayer.comp_player.ordinal()];}
    public int getHumanScore() {return m_player_scores[gamePlayer.human_player.ordinal()];}
    public ModelRound getCurrentRound() {return m_rounds.get(m_rounds.size() - 1);}
    public int getRoundNumber(){return m_round_number;}
    public comparisonOutcome getWinner(){return m_winner;}

    // *** mutators *** //
    public void updateRoundNum(int a_round_num) { m_round_number = a_round_num; }
    public void incrementRoundNum(){m_round_number++;}
    public void identifyGameWinner() { m_winner = compareScores(m_player_scores[gamePlayer.comp_player.ordinal()],
            m_player_scores[gamePlayer.human_player.ordinal()]); }

    // *** utility (private) methods *** //
    /**********************************************************************************
     * Function Name: newGameSelected
     * Purpose: To set up a new game
     * Assistance Received: None
     ********************************************************************************* */
    public void newGameSelected()
    {
        ModelRound newRound = new ModelRound();
        m_rounds.add(newRound);
        m_deck = new ModelDeck();
    }

    /**********************************************************************************
     * Function Name: loadGameSelectced
     * Purpose: To write current state of the game to a file
     * @param a_activity, an activity used to create the InputStream
     * @param a_filename, a string indicating the name of the file to read from
     * Assistance Received: None
     ********************************************************************************* */
    public boolean loadGameSelected(Activity a_activity, String a_filename)
    {
        ModelFile myFile = new ModelFile();

        //identify the components of the game
        boolean file_found = myFile.loadGame(a_activity, a_filename + ".txt");

        if (file_found)
        {
            //update the game elements
            updateScores(myFile.getScores());
            updateRoundNum(myFile.getRoundNum());

            //get the round elements
            ModelPile boneyard = myFile.getBoneyard();
            ModelPile[] hands = myFile.getHands();
            ModelTrain[] trains = myFile.getTrains();
            ModelTile engine = myFile.getEngine();
            gamePlayer current_player = myFile.getCurrentPlayer();

            ModelRound newRound = new ModelRound(boneyard, hands, trains, engine, current_player);

            //record this round as part of the game
            m_rounds.add(newRound);
        }

        return file_found;
    }

    /**********************************************************************************
     * Function Name: saveGame
     * Purpose: To write current state of the game to a file
     * @param a_activity, an activity used to create the OutputStreamWriter
     * @param a_filename, a string indicating the name of the file to write to
     * Assistance Received: None
     ********************************************************************************* */
    public void saveGame(Activity a_activity, String a_filename)
    {
        ModelFile write_file = new ModelFile(m_player_scores, m_round_number,
                getCurrentRound().getBoneyard(), getCurrentRound().getHands(),
                getCurrentRound().getTrains(), getCurrentRound().getEngine(), getCurrentRound().getCurrentPlayer());

        //write current game status to a file
        write_file.saveGame(a_activity, a_filename);
    }

    /**********************************************************************************
     * Function Name: updateScores
     * Purpose: To update player scores at the end of each round
     * @param a_scores, an array of integers holding player scores for this round
     * Assistance Received: None
     ********************************************************************************* */
    public void updateScores(int[] a_scores)
    {
        for (int i=0; i<m_num_players; i++)
            m_player_scores[i] += a_scores[i];
    }

    /**********************************************************************************
     * Function Name: updateScores
     * Purpose: To update player scores and ronund number at the end of each round
     * Assistance Received: None
     ********************************************************************************* */
    public void updateGame()
    {
        updateScores(getCurrentRound().getPoints());
        incrementRoundNum();
    }

    /**********************************************************************************
     * Function Name: compareScores
     * Purpose: To compare computer and human scores
     * @param a_comp_score, an integer indicating computer score
     * @param a_human_score, an integer indicating human score
     * @return an enum, indicating the result of the comparison
     * Assistance Received: None
     ********************************************************************************* */
    public static comparisonOutcome compareScores(int a_comp_score, int a_human_score)
    {
        if (a_human_score < a_comp_score)
            return comparisonOutcome.human_lower;

        else if (a_human_score > a_comp_score)
            return comparisonOutcome.comp_lower;

        return comparisonOutcome.tie;
    }
}
