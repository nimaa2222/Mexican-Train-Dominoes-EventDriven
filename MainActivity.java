/*
************************************************************
* Name:     Nima Naghizadehbaee                            *
* Project:  Project 3 - Java Mexican Train                 *
* Class:    CMPS 366 - OPL                                 *
* Date:     4/6/21                                         *
************************************************************
*/


package edu.ramapo.nnaghiza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.ramapo.nnaghiza.Mgame.ModelGame;
import edu.ramapo.nnaghiza.Mgame.ViewGame;
import edu.ramapo.nnaghiza.Mround.ModelRound;
import edu.ramapo.nnaghiza.Mround.ViewRound;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.enums.coinSide;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;

public class MainActivity extends AppCompatActivity {

    // *** constants *** //

    //makes human hand tiles clickable when set to true
    boolean enable_tiles = false;

    //makes train titles clickable when set to true
    boolean enable_trains = false;

    //the tile and train selected by human player
    ModelTile human_selected_tile = new ModelTile();
    gameTrain human_selected_train;

    //the tile and train selected by computer player
    ModelTile comp_selected_tile = new ModelTile();
    gameTrain comp_selected_train;

    //the maximum number of characters of a filename
    int max_filename_size = 10;

    //set to true when computer makes a tile/train selection for itself
    boolean comp_moves = false;

    //set to true when computer makes a tile/train selection for human
    boolean comp_helps = false;

    //the mexican train game (model)
    ModelGame myGame = new ModelGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //prompt user to start a new game or load game
        ViewGame.displayHomePage(this, this::homePageListener, this::otherOptionsListener);
    }

    // *** the button listeners *** //
    /********************************************************************************
     * Function Name: homePageListener
     * Purpose: To handle user requests in the home page
     * @param a_view, a button click
     * Assistance Received: None
     **************************************************************************** */
    public void homePageListener(View a_view)
    {
        switch(a_view.getId())
        {
            case R.id.newGameButton:
                startNewRound();
                break;

            case R.id.loadButton:
                ViewGame.displayFilenameLoadPrompt(this, this::fileActionListener);
                break;
        }
    }

    /********************************************************************************
     * Function Name: coinTossListener
     * Purpose: To handle user coin toss selection
     * @param a_view, a button click
     * Assistance Received: None
     **************************************************************************** */
    public void coinTossListener(View a_view)
    {
        //the side selected by human
        coinSide sideSelected;

        //the player to start the round
        gamePlayer roundStarter;

        switch (a_view.getId())
        {
            case R.id.headsButton:
                sideSelected = coinSide.heads;

                //identify the player starting the round by coin toss
                roundStarter = ModelRound.performCoinToss(sideSelected);
                myGame.getCurrentRound().setCurrentPlayer(roundStarter);

                //display the winner
                ViewRound.displayCoinTossResult(this, this::coinTossListener, roundStarter);
                break;
            case R.id.tailsButton:
                sideSelected = coinSide.tails;

                //identify the player starting the round by coin toss
                roundStarter = ModelRound.performCoinToss(sideSelected);
                myGame.getCurrentRound().setCurrentPlayer(roundStarter);

                //display the winner
                ViewRound.displayCoinTossResult(this, this::coinTossListener, roundStarter);

                break;
            case R.id.proceedButton:

                //set up player hands and boneyard
                myGame.getCurrentRound().setUpRound(myGame.getDeck(), myGame.getRoundNumber());
                displayGameInfo();
                break;
        }
    }

    /********************************************************************************
     * Function Name: fileActionListener
     * Purpose: To handle user request to save or load game
     * @param a_view, a button click
     * Assistance Received: None
     **************************************************************************** */
    public void fileActionListener(View a_view)
    {
        switch (a_view.getId())
        {
            case R.id.loadGameButton:

                //name of file to load from
                String filename_load = ((EditText) findViewById(R.id.filenameLoadField)).getText().toString();

                //filename must be less than 10 characters
                if (filename_load.length() < max_filename_size)
                {
                    //if the read of all game components from file is successful
                    if (myGame.loadGameSelected(this, filename_load))
                        displayGameInfo();
                }

                break;
            case R.id.saveGameFileButton:

                //name of file to save to
                String filename_save = ((EditText) findViewById(R.id.filenameSaveField)).getText().toString();

                //filename must be less than 10 characters
                if (filename_save.length() < max_filename_size)
                {
                    myGame.saveGame(this, filename_save);
                    this.finish();
                }
                break;
        }
    }

    /********************************************************************************
     * Function Name: moveListener
     * Purpose: To handle the move button clicks
     * @param a_view, a move button click
     * Assistance Received: None
     **************************************************************************** */
    public void moveListener(View a_view)
    {
        //check whether player can move (needs to draw from boneyard, etc...)
        boolean player_can_move = myGame.getCurrentRound().prepareTurn(myGame.getCompPlayer());

        switch (a_view.getId())
        {
            case R.id.compMoveButton:

                if (player_can_move)
                {
                    comp_moves = true;

                    //select a tile
                    comp_selected_tile = myGame.getCurrentRound().promptCompSelectTile(myGame.getCompPlayer());

                    //select a train
                    comp_selected_train = myGame.getCurrentRound().promptCompSelectTrain(myGame.getCompPlayer(), comp_selected_tile);

                    //place tile on train
                    myGame.getCurrentRound().makeMove(comp_selected_tile, comp_selected_train);
                }

                break;

            case R.id.humanMoveButton:
                comp_moves = false;

                if (player_can_move)
                    enable_tiles = true;
                break;
        }

        displayGameInfo();

        //if the round is over
        if (myGame.getCurrentRound().roundIsCompleted())
            postRoundOperations();
    }

    /********************************************************************************
     * Function Name: tileListener
     * Purpose: To handle the button click on human hand tiles for human selection
     * @param a_view, a button click
     * Assistance Received: None
     **************************************************************************** */
    public void tileListener(View a_view)
    {
        //enable train buttons
        enable_trains = true;

        String tile_content = ((Button)findViewById(a_view.getId())).getText().toString();
        human_selected_tile = parseTile(tile_content);
        displayGameInfo();
    }

    /********************************************************************************
     * Function Name: trainListener
     * Purpose: To handle the button click on trains by placing tile on them
     * @param a_view, a button click
     * Assistance Received: None
     **************************************************************************** */
    public void trainListener(View a_view)
    {
        switch (a_view.getId())
        {
            case R.id.humanTrainButton:
                human_selected_train = gameTrain.human_train;
                break;
            case R.id.mexicanTrainButton:
                human_selected_train = gameTrain.mexican_train;
                break;
            case R.id.compTrainButton:
                human_selected_train = gameTrain.comp_train;
                break;
        }

        //place the tile on train (if selections are valid)
        boolean moved = myGame.getCurrentRound().makeMove(human_selected_tile, human_selected_train);

        //if the tile was placed on train
        if (moved)
        {
            comp_helps = false;
            comp_moves = false;
        }

        //disable human move button
        enable_tiles = false;

        //disable train buttons
        enable_trains = false;

        displayGameInfo();

        //if the round is over
        if (myGame.getCurrentRound().roundIsCompleted())
            postRoundOperations();
    }

    /********************************************************************************
     * Function Name: otherOptionsListener
     * Purpose: To handle the button clicks of the other buttons
     * @param a_view, a button click
     * Assistance Received: None
     **************************************************************************** */
    public void otherOptionsListener(View a_view)
    {
        switch (a_view.getId())
        {
            case R.id.homePageButton:

                //reset the game
                myGame = new ModelGame();

                ViewGame.displayHomePage(this, this::homePageListener, this::otherOptionsListener);
                break;

            case R.id.saveGameButton:
                ViewGame.displaySaveGameDialog(this, this::saveGameListener);
                break;
            case R.id.getHelpButton:
                comp_helps = true;

                //select a tile
                comp_selected_tile = myGame.getCurrentRound().promptCompSelectTile(myGame.getCompPlayer());

                //select a train
                comp_selected_train = myGame.getCurrentRound().promptCompSelectTrain(myGame.getCompPlayer(), comp_selected_tile);

                displayGameInfo();
                break;
            case R.id.quitGameButton:
                ViewGame.displayQuitDialog(this, this::quitDialogListener);
                break;
            case R.id.anotherRoundButton:
                startNewRound();
                break;
            case R.id.finalResultButton:
                myGame.identifyGameWinner();
                ViewGame.displayEventResult(this, this::otherOptionsListener, myGame, "GAME");
                break;
        }
    }

    // *** the dialog listeners *** //
    /********************************************************************************
     * Function Name: saveGameListener
     * Purpose: To handle user request to save the game and quit
     * @param a_dialog, the dialog for user to select from
     * @param a_which, the option selected by the user
     * Assistance Received: None
     **************************************************************************** */
    public void saveGameListener(DialogInterface a_dialog, int a_which)
    {
        boolean save_game = false;
        switch(a_which)
        {
            case DialogInterface.BUTTON_POSITIVE:
                save_game = true;
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                save_game = false;
                break;
        }

        if (save_game)
            ViewGame.displayFilenameSavePrompt(this, this::fileActionListener);
    }

    /********************************************************************************
     * Function Name: quitDialogListener
     * Purpose: To handle user request to quit the game
     * @param a_dialog, the dialog for user to select from
     * @param a_which, the option selected by the user
     * Assistance Received: None
     **************************************************************************** */
    public void quitDialogListener(DialogInterface a_dialog, int a_which)
    {
        boolean quit = false;
        switch(a_which)
        {
            case DialogInterface.BUTTON_POSITIVE:
                quit = true;
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                quit = false;
                break;
        }

        if (quit)
            this.finish();
    }

    // *** utility methods *** //
    /********************************************************************************
     * Function Name: startNewRound
     * Purpose: To set up initial elements of a round
     * Assistance Received: None
     **************************************************************************** */
    public void startNewRound()
    {
        comp_helps = false;
        comp_moves = false;

        //create a round with a new deck
        myGame.newGameSelected();

        //identify the player to start the round
        myGame.getCurrentRound().identifyRoundStarter(myGame.getCompScore(), myGame.getHumanScore());

        //if scores are tied
        if (myGame.getCurrentRound().getCurrentPlayer() == gamePlayer.unknown)
            ViewRound.displayCoinToss(this, this::coinTossListener);

        else
        {
            //set up player hands and boneyard
            myGame.getCurrentRound().setUpRound(myGame.getDeck(), myGame.getRoundNumber());

            displayGameInfo();
        }
    }

    /********************************************************************************
     * Function Name: displayGameInfo
     * Purpose: To display the updated game on the screen
     * Assistance Received: None
     **************************************************************************** */
    public void displayGameInfo()
    {
        ViewGame.displayGame(this, myGame, this::tileListener, this::trainListener, this::moveListener,
                this::otherOptionsListener, enable_tiles, enable_trains, comp_moves, comp_helps, comp_selected_tile, comp_selected_train);
    }

    /********************************************************************************
     * Function Name: parseTile
     * Purpose: To get the front and back value of a tile from its button
     * Assistance Received: None
     **************************************************************************** */
    public ModelTile parseTile(String a_tile_text)
    {
        String tile_num = new String();

        for (int i=0; i<a_tile_text.length(); i++)
        {
            if (Character.isDigit(a_tile_text.charAt(i)))
                tile_num += a_tile_text.charAt(i);
        }

        int front_tile = Character.getNumericValue(tile_num.charAt(0));
        int back_tile = Character.getNumericValue(tile_num.charAt(1));
        ModelTile tile = new ModelTile(front_tile, back_tile);
        return tile;
    }

    /********************************************************************************
     * Function Name: postRoundOperations
     * Purpose: To perform a series of actions after a round is completed
     * Assistance Received: None
     **************************************************************************** */
    public void postRoundOperations()
    {
        //establish the winner
        myGame.getCurrentRound().identifyWinner();
        ViewGame.displayEventResult(this, this::otherOptionsListener, myGame,"ROUND");

        //update the round number and the scores
        myGame.updateGame();

        //prompt user to play another round
        ViewGame.promptForAnotherRound(this, this::otherOptionsListener);
    }
}