package edu.ramapo.nnaghiza.Mfile;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

import edu.ramapo.nnaghiza.Mgame.ModelGame;
import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mround.ModelRound;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;

public class ModelFile {

    // *** data members *** //

    //the game information read
    private int[] m_player_scores;
    private int m_round_number;

    //the round information read
    private ModelPile m_boneyard;
    private ModelPile[] m_hands;
    private ModelTrain[] m_trains;
    private ModelTile m_engine;
    private gamePlayer m_current_player;

    // *** constructors *** //
    public ModelFile(int[] a_player_scores, int a_round_number, ModelPile a_boneyard, ModelPile[] a_hands, ModelTrain[] a_trains, ModelTile a_engine, gamePlayer a_current_player)
    {
        m_player_scores = a_player_scores;
        m_round_number = a_round_number;
        m_boneyard = a_boneyard;
        m_hands = a_hands;
        m_trains = a_trains;
        m_engine = a_engine;
        m_current_player = a_current_player;
    }

    public ModelFile()
    {
        //the game elements
        m_player_scores = new int[ModelGame.m_num_players];
        m_round_number = 1;

        //the round elements
        m_boneyard = new ModelPile();

        m_hands = new ModelPile[ModelGame.m_num_players];
        for (int i=0; i<ModelGame.m_num_players; i++)
            m_hands[i] = new ModelPile();

        m_trains = new ModelTrain[ModelRound.m_num_trains];
        for (int i=0; i<ModelRound.m_num_trains; i++)
            m_trains[i] = new ModelTrain();

        m_engine = new ModelTile();
        m_current_player = gamePlayer.comp_player;
    }

    // *** selectors *** //
    public int[] getScores(){return m_player_scores;}
    public int getRoundNum(){return m_round_number;}
    public ModelPile getBoneyard(){return m_boneyard;}
    public ModelPile[] getHands(){return m_hands;}
    public ModelTrain[] getTrains(){return m_trains;}
    public ModelTile getEngine(){return m_engine;}
    public gamePlayer getCurrentPlayer(){return m_current_player;}

    // *** utility (private) methods *** //
    /*************************************************************************
     * Function Name: saveGame
     * Purpose: To write the current game status to a file
     * @param a_activity, an activity used to create the OutputStreamWriter
     * @param a_filename, a string indicating the name of the file to write to
     * Algorithm:
     *  (1) write the round number to the file
     * 	(2) write the computer score to the file
     * 	(3) write the computer hand to the file
     * 	(4) write the computer train to the file
     * 	(5) write the human score to the file
     * 	(6) write the human hand to the file
     * 	(7) write the human train to the file
     * 	(8) write the mexican train to the file
     * 	(9) write the boneyard to the file
     * 	(10) write the next player to the file
     *
     * Assistance Received: None
     *********************************************************************** */
    public void saveGame(Activity a_activity, String a_filename)
    {
        try {
            OutputStreamWriter myWriter = new OutputStreamWriter(a_activity.openFileOutput(a_filename + ".txt", Context.MODE_APPEND));

            myWriter.write("Round: " + m_round_number + "\n" + "\n");

            //write computer information
            myWriter.write("Computer:"+ "\n");
            myWriter.write("Score: "+ m_player_scores[gamePlayer.comp_player.ordinal()]  +"\n");

            myWriter.write("Hand: ");
            for (int i=0; i<m_hands[gamePlayer.comp_player.ordinal()].getSize(); i++)
            {
                ModelTile tile = m_hands[gamePlayer.comp_player.ordinal()].getTile(i);
                myWriter.write(tile.getFront() + "-" + tile.getBack() + " ");
            }
            myWriter.write("\n");

            myWriter.write("Train: ");
            if (m_trains[gameTrain.comp_train.ordinal()].isMarked())
                myWriter.write("M ");

            //write train in reverse order
            m_trains[gameTrain.comp_train.ordinal()].reverseTrain();
            for (int i=0; i<m_trains[gameTrain.comp_train.ordinal()].getSize(); i++)
            {
                ModelTile tile = m_trains[gameTrain.comp_train.ordinal()].getTile(i);
                myWriter.write(tile.getBack() + "-" + tile.getFront() + " ");
            }

            myWriter.write("\n" + "\n");

            //write human information
            myWriter.write("Human:"+ "\n");
            myWriter.write("Score: "+ m_player_scores[gamePlayer.human_player.ordinal()]  +"\n");

            myWriter.write("Hand: ");
            for (int i=0; i<m_hands[gamePlayer.human_player.ordinal()].getSize(); i++)
            {
                ModelTile tile = m_hands[gamePlayer.human_player.ordinal()].getTile(i);
                myWriter.write(tile.getFront() + "-" + tile.getBack() + " ");
            }
            myWriter.write("\n");

            myWriter.write("Train: ");
            for (int i=0; i<m_trains[gameTrain.human_train.ordinal()].getSize(); i++)
            {
                ModelTile tile = m_trains[gameTrain.human_train.ordinal()].getTile(i);
                myWriter.write(tile.getFront() + "-" + tile.getBack() + " ");
            }

            if (m_trains[gameTrain.human_train.ordinal()].isMarked())
                myWriter.write("M");

            myWriter.write("\n" + "\n");

            //write mexican train
            myWriter.write("Mexican Train: ");
            for (int i=0; i<m_trains[gameTrain.mexican_train.ordinal()].getSize(); i++)
            {
                ModelTile tile = m_trains[gameTrain.mexican_train.ordinal()].getTile(i);
                myWriter.write(tile.getFront() + "-" + tile.getBack() + " ");
            }

            myWriter.write("\n" + "\n");

            //write boneyard
            myWriter.write("Boneyard: ");
            for (int i=0; i<m_boneyard.getSize(); i++)
            {
                ModelTile tile = m_boneyard.getTile(i);
                myWriter.write(tile.getFront() + "-" + tile.getBack() + " ");
            }

            myWriter.write("\n" + "\n");

            //write next player
            myWriter.write("Next Player: ");
            if (m_current_player == gamePlayer.comp_player)
                myWriter.write("Computer");
            else
                myWriter.write("Human");

            myWriter.close();
            Log.d("outputs", "Game was Successfully Saved!");
        } catch (IOException e) {
            Log.d("outputs", "An error occurred.");
            e.printStackTrace();
        }
    }

    /*************************************************************************
     * Function Name: loadGame
     * Purpose: To load the game from a file
     * @param a_activity, an activity used to create the InputStream
     * @param a_filename, a string indicating the name of the file to read from
     * Algorithm:
     *  (1) read the round number from the file
     * 	(2) read the computer score from the file
     * 	(3) read the computer hand from the file
     * 	(4) read the computer train from the file
     * 	(5) read the human score from the file
     * 	(6) read the human hand from the file
     * 	(7) read the human train from the file
     * 	(8) read the mexican train from the file
     * 	(9) read the boneyard from the file
     * 	(10) read the next player from the file
     *
     * Assistance Received: None
     *********************************************************************** */
    public boolean loadGame(Activity a_activity, String a_filename)
    {
        try {
            InputStream myReader = a_activity.openFileInput(a_filename);

            if (myReader != null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(myReader);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data = new String();

                //set to true once computer information is read
                boolean comp_info_read = false;

                while ((data = bufferedReader.readLine()) != null)
                {

                    //remove before and after space
                    data = data.trim();

                    if (!data.isEmpty())
                    {
                        //split the string into words
                        String[] sp = data.split(" ");
                        String first_word = sp[0];

                        if (first_word.equalsIgnoreCase("Round:"))
                            m_round_number = Integer.parseInt(sp[1]);

                        else if (first_word.equalsIgnoreCase("Score:"))
                        {
                            int score = Integer.parseInt(sp[1]);

                            if (!comp_info_read)
                                m_player_scores[gamePlayer.comp_player.ordinal()] = score;
                            else
                                m_player_scores[gamePlayer.human_player.ordinal()] = score;
                        }

                        else if (first_word.equalsIgnoreCase("Hand:"))
                        {
                            ModelPile hand = new ModelPile();

                            //start at index 1 and add the tiles to the hand
                            for (int i = 1; i < sp.length; i++) {
                                int tile_front = Character.getNumericValue(sp[i].charAt(0));
                                int tile_back = Character.getNumericValue(sp[i].charAt(2));
                                hand.addTileToPile(new ModelTile(tile_front, tile_back));
                            }

                            if (!comp_info_read)
                                m_hands[gamePlayer.comp_player.ordinal()] = hand;
                            else
                                m_hands[gamePlayer.human_player.ordinal()] = hand;
                        }

                        else if (first_word.equalsIgnoreCase("Train:"))
                        {
                            if (!comp_info_read)
                            {
                                int start_index = 1;

                                String second_word = sp[1];

                                //if the train is marked
                                if (second_word.equalsIgnoreCase("M"))
                                {
                                    m_trains[gameTrain.comp_train.ordinal()].addMarker();
                                    start_index = 2;
                                }

                                for (int i = start_index; i < sp.length; i++)
                                {
                                    int tile_front = Character.getNumericValue(sp[i].charAt(0));
                                    int tile_back = Character.getNumericValue(sp[i].charAt(2));
                                    m_trains[gameTrain.comp_train.ordinal()].addTile(new ModelTile(tile_back, tile_front));
                                }

                                //reverse the train
                                m_trains[gameTrain.comp_train.ordinal()].reverseTrain();
                            }

                            else
                            {
                                //record the engine
                                int engine_val = Character.getNumericValue(sp[1].charAt(0));
                                m_engine = new ModelTile(engine_val, engine_val);

                                int end_index = sp.length;

                                String last_word = sp[end_index - 1];

                                if (last_word.equalsIgnoreCase("M"))
                                {
                                    m_trains[gameTrain.human_train.ordinal()].addMarker();
                                    end_index--;
                                }

                                for (int i = 1; i < end_index; i++)
                                {
                                    int tile_front = Character.getNumericValue(sp[i].charAt(0));
                                    int tile_back = Character.getNumericValue(sp[i].charAt(2));
                                    m_trains[gameTrain.human_train.ordinal()].addTile(new ModelTile(tile_front, tile_back));
                                }
                            }
                        }

                        else if (first_word.equalsIgnoreCase("Mexican"))
                        {
                            for (int i = 2; i < sp.length; i++)
                            {
                                int tile_front = Character.getNumericValue(sp[i].charAt(0));
                                int tile_back = Character.getNumericValue(sp[i].charAt(2));
                                m_trains[gameTrain.mexican_train.ordinal()].addTile(new ModelTile(tile_front, tile_back));
                            }
                        }

                        else if (first_word.equalsIgnoreCase("Boneyard:"))
                        {
                            for (int i = 1; i < sp.length; i++)
                            {
                                int tile_front = Character.getNumericValue(sp[i].charAt(0));
                                int tile_back = Character.getNumericValue(sp[i].charAt(2));
                                m_boneyard.addTileToPile(new ModelTile(tile_front, tile_back));
                            }
                        }

                        else if (first_word.equalsIgnoreCase("Next"))
                        {
                            String third_word = sp[2];
                            if (third_word.equalsIgnoreCase("Computer"))
                                m_current_player = gamePlayer.comp_player;
                            else
                                m_current_player = gamePlayer.human_player;
                        }

                        else if (first_word.equalsIgnoreCase("Human:"))
                        {
                            //note that human game information should be read now
                            comp_info_read = true;
                        }
                    }
                }

                myReader.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            Log.e("outputs", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("outputs", "Cannot read file: " + e.toString());
        }

        return false;
    }
}
