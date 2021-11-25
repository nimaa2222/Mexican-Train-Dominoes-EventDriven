package edu.ramapo.nnaghiza.Mgame;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AlertDialog;

import edu.ramapo.nnaghiza.Mcomputer.ModelComputer;
import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mpile.ViewPile;
import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtile.ViewTile;
import edu.ramapo.nnaghiza.Mtrain.ModelTrain;
import edu.ramapo.nnaghiza.Mtrain.ViewTrain;
import edu.ramapo.nnaghiza.R;
import edu.ramapo.nnaghiza.enums.comparisonOutcome;
import edu.ramapo.nnaghiza.enums.gamePlayer;
import edu.ramapo.nnaghiza.enums.gameTrain;

public class ViewGame {

    // *** constants *** //

    //color for personal trains
    static String blue_mist  = "#82CFFD";
    public static int train_back_col = Color.parseColor(blue_mist);
    static int train_text_col = Color.BLACK;

    //color for player hands
    static String light_orange = "#FFC04D";
    static String dark_orange = "#FF6500";
    public static int hand_back_col = Color.parseColor(light_orange);
    static int hand_text_col = Color.BLACK;

    //color for move buttons
    static String dark_green = "#00FF00";
    public static int move_color = Color.parseColor(dark_green);
    static int save_text_col = Color.BLACK;

    //color for quit button
    static int quit_back_col = Color.RED;
    static int quit_text_col = Color.BLACK;

    //other buttons
    public static int other_back_disabled_col = Color.GRAY;
    public static int other_text_disabled_col = Color.BLACK;
    public static int other_back_enabled_col = Color.BLACK;
    public static int other_text_enabled_col = Color.WHITE;

    //regular button attributes
    public static int button_height = 50;
    public static int button_width = 200;
    public static int button_font = 10;

    //small button attributes
    public static int small_button_width = 151;
    public static int small_button_font = 8;

    public static int boneyard_width = 120;
    public static int move_width = 100;

    //for displaying computer strategy
    public static int reasoning_width = 406;
    public static int reasoning_font = 7;

    //height of the round/game result box
    public static int result_height = 120;
    public static int result_width = reasoning_width;

    public static int home_page_width = 452;

    //the main grid
    static GridLayout mainGrid = null;

    //the other grids
    public static androidx.gridlayout.widget.GridLayout roundGrid  = null;
    public static androidx.gridlayout.widget.GridLayout humanNameGrid = null;
    public static androidx.gridlayout.widget.GridLayout humanHandGrid1 = null;
    public static androidx.gridlayout.widget.GridLayout humanHandGrid2 = null;
    public static androidx.gridlayout.widget.GridLayout humanTrainTitleGrid = null;
    static androidx.gridlayout.widget.GridLayout humanTrainGrid1 = null;
    static androidx.gridlayout.widget.GridLayout humanTrainGrid2 = null;
    static androidx.gridlayout.widget.GridLayout boneyardGrid = null;
    static androidx.gridlayout.widget.GridLayout mexicanTrainTitleGrid = null;
    static androidx.gridlayout.widget.GridLayout mexicanTrainGrid1 = null;
    static androidx.gridlayout.widget.GridLayout mexicanTrainGrid2 = null;
    static androidx.gridlayout.widget.GridLayout compTrainTitleGrid = null;
    static androidx.gridlayout.widget.GridLayout compTrainGrid1 = null;
    static androidx.gridlayout.widget.GridLayout compTrainGrid2 = null;
    static androidx.gridlayout.widget.GridLayout compNameGrid = null;
    static androidx.gridlayout.widget.GridLayout compHandGrid1 = null;
    static androidx.gridlayout.widget.GridLayout compHandGrid2 = null;
    static androidx.gridlayout.widget.GridLayout postGrid1 = null;
    static androidx.gridlayout.widget.GridLayout postGrid2 = null;
    static androidx.gridlayout.widget.GridLayout postGrid3 = null;
    static androidx.gridlayout.widget.GridLayout postGrid4 = null;
    static androidx.gridlayout.widget.GridLayout postGrid5 = null;
    static androidx.gridlayout.widget.GridLayout postGrid6 = null;
    static androidx.gridlayout.widget.GridLayout postGrid7 = null;
    static androidx.gridlayout.widget.GridLayout postGrid8 = null;

    // *** utility (private) methods *** //
    /*********************************************************
     * Function Name: displayHomePage
     * Purpose: To display the home page to user
     * @param a_activity, the game activity
     * @param a_listener_one, a listener for new game/load buttons
     * @param a_listener_two, a listener for the quit button
     * Assistance Received: None
     ***************************************************** */
    public static void displayHomePage(Activity a_activity, View.OnClickListener a_listener_one, View.OnClickListener a_listener_two)
    {
        establishGrids(a_activity);
        clearScreen();

        //create elements of home screen
        ViewGame.createPrompt(a_activity, roundGrid, a_activity.getString(R.string.welcomePrompt),
                button_height, home_page_width, button_font, other_back_disabled_col, other_text_disabled_col);

        ViewGame.createButton(a_activity, humanNameGrid, a_listener_one, R.id.newGameButton, a_activity.getString(R.string.newGameOption),
                button_height, home_page_width, button_font, true, hand_back_col, other_text_disabled_col);

        ViewGame.createButton(a_activity, humanHandGrid1, a_listener_one, R.id.loadButton, a_activity.getString(R.string.loadGameOption),
                button_height, home_page_width, button_font, true, train_back_col, other_text_disabled_col);

        ViewGame.createButton(a_activity, humanHandGrid2, a_listener_two, R.id.quitGameButton, "QUIT",
                button_height, home_page_width, button_font, true, quit_back_col, quit_text_col);
    }

    /**********************************************************************************
     * Function Name: establishGrids
     * Purpose: To establish the location of all game grids
     * @param a_activity, the game activity
     * Assistance Received: None
     ********************************************************************************* */
    public static void establishGrids(Activity a_activity)
    {
        mainGrid = (GridLayout) a_activity.findViewById(R.id.MainGrid);

        roundGrid = a_activity.findViewById(R.id.RoundNumGrid);
        humanNameGrid = a_activity.findViewById(R.id.HumanNameGrid);
        humanHandGrid1 = a_activity.findViewById(R.id.HumanHandGrid1);
        humanHandGrid2 = a_activity.findViewById(R.id.HumanHandGrid2);
        humanTrainTitleGrid = a_activity.findViewById(R.id.HumanTrainTitleGrid);
        boneyardGrid = a_activity.findViewById(R.id.BoneyardGrid);
        humanTrainGrid1 = a_activity.findViewById(R.id.HumanTrainGrid1);
        humanTrainGrid2 = a_activity.findViewById(R.id.HumanTrainGrid2);
        mexicanTrainTitleGrid = a_activity.findViewById(R.id.MexicanTrainTitleGrid);
        mexicanTrainGrid1 = a_activity.findViewById(R.id.MexicanTrainGrid1);
        mexicanTrainGrid2 = a_activity.findViewById(R.id.MexicanTrainGrid2);
        compTrainTitleGrid = a_activity.findViewById(R.id.CompTrainTitleGrid);
        compTrainGrid1 = a_activity.findViewById(R.id.CompTrainGrid1);
        compTrainGrid2 = a_activity.findViewById(R.id.CompTrainGrid2);
        compNameGrid = a_activity.findViewById(R.id.CompNameGrid);
        compHandGrid1 = a_activity.findViewById(R.id.CompHandGrid1);
        compHandGrid2 = a_activity.findViewById(R.id.CompHandGrid2);
        postGrid1 = a_activity.findViewById(R.id.PostGrid1);
        postGrid2 = a_activity.findViewById(R.id.PostGrid2);
        postGrid3 = a_activity.findViewById(R.id.PostGrid3);
        postGrid4 = a_activity.findViewById(R.id.PostGrid4);
        postGrid5 = a_activity.findViewById(R.id.PostGrid5);
        postGrid6 = a_activity.findViewById(R.id.PostGrid6);
        postGrid7 = a_activity.findViewById(R.id.PostGrid7);
        postGrid8 = a_activity.findViewById(R.id.PostGrid8);
    }

    /*****************************************************************
     * Function Name: clearScreen
     * Purpose: To remove all elements of the game from the screen
     * Assistance Received: None
     ***************************************************************** */
    public static void clearScreen()
    {
        roundGrid.removeAllViews();
        humanNameGrid.removeAllViews();
        humanHandGrid1.removeAllViews();
        humanHandGrid2.removeAllViews();
        humanTrainTitleGrid.removeAllViews();
        humanTrainGrid1.removeAllViews();
        humanTrainGrid2.removeAllViews();
        boneyardGrid.removeAllViews();
        mexicanTrainTitleGrid.removeAllViews();
        mexicanTrainGrid1.removeAllViews();
        mexicanTrainGrid2.removeAllViews();
        compTrainTitleGrid.removeAllViews();
        compTrainGrid1.removeAllViews();
        compTrainGrid2.removeAllViews();
        compNameGrid.removeAllViews();
        compHandGrid1.removeAllViews();
        compHandGrid2.removeAllViews();
        postGrid1.removeAllViews();
        postGrid2.removeAllViews();
        postGrid3.removeAllViews();
        postGrid4.removeAllViews();
        postGrid5.removeAllViews();
        postGrid6.removeAllViews();
        postGrid7.removeAllViews();
        postGrid8.removeAllViews();
    }

    /**********************************************************************************
     * Function Name: displayFilenameLoadPrompt
     * Purpose: To ask user for filename to load game
     * @param a_activity, the game activity
     * @param a_listener, a listener for the load game button
     * Assistance Received: None
     ********************************************************************************* */
    public static void displayFilenameLoadPrompt(Activity a_activity, View.OnClickListener a_listener)
    {
        clearHomePage();

        //ask for the name of the file
        createEditText(a_activity, roundGrid, R.id.filenameLoadField , a_activity.getString(R.string.filenamePrompt), button_height, button_width, button_font);

        //button to submit filename
        createButton(a_activity, humanNameGrid, a_listener, R.id.loadGameButton, a_activity.getString(R.string.loadGameButtonText),
                button_height, button_width, button_font, true, other_back_enabled_col, other_text_enabled_col);
    }

    /**********************************************************************************
     * Function Name: displayFilenameSavePrompt
     * Purpose: To ask user for filename to save game
     * @param a_activity, the game activity
     * @param a_listener, a listener for the save game button
     * Assistance Received: None
     ********************************************************************************* */
    public static void displayFilenameSavePrompt(Activity a_activity, View.OnClickListener a_listener)
    {
        clearScreen();

        //ask for the name of the file
        createEditText(a_activity, roundGrid, R.id.filenameSaveField , a_activity.getString(R.string.filenamePrompt),
                button_height, button_width, button_font);

        //button to submit filename
        createButton(a_activity, humanNameGrid, a_listener, R.id.saveGameFileButton, "SAVE GAME",
                button_height, button_width, button_font, true, other_back_enabled_col, other_text_enabled_col);
    }

    /**********************************************************************************
     * Function Name: clearHomePage
     * Purpose: To clear elements of the home page
     * Assistance Received: None
     ********************************************************************************* */
    public static void clearHomePage()
    {
        roundGrid.removeAllViews();
        humanNameGrid.removeAllViews();
        humanHandGrid1.removeAllViews();
        humanHandGrid2.removeAllViews();
    }

    /*******************************************************************************
     * Function Name: displayGame
     * Purpose: To display the game on screen
     * @param a_activity, the game activity
     * @param a_game, the game model
     * @param a_tile_listener, a listener for tile buttons
     * @param a_train_listener, a listener for train buttons
     * @param a_move_listener, a listener for move buttons
     * @param a_other_listener, a listener for other buttons
     * @param a_human_move, boolean set to true once human move button is clicked
     * @param a_tile_selected, boolean set to true once human selects a tile
     * @param a_comp_moved, boolean set to true once computer makes a move
     * @param a_comp_helps, boolean set to true once computer helps human
     * @param a_comp_selected_tile, the tile selected by computer strategy
     * @param a_comp_selected_train, the train selected by computer strategy
     * Algorithm:
     *     (1) Display the round number
     *     (2) Display human human name, score and hand
     *     (3) Display the human train
     *     (4) Display the boneyard
     *     (5) Display the mexican train
     *     (6) Display the computer train
     *     (7) Display the computer hand, name and score
     *     (8) Display the other buttons
     * Assistance Received: None
     ****************************************************************************** */
    public static void displayGame(Activity a_activity, ModelGame a_game, View.OnClickListener a_tile_listener,
                                   View.OnClickListener a_train_listener, View.OnClickListener a_move_listener,
                                   View.OnClickListener a_other_listener, boolean a_human_move, boolean a_tile_selected,
                                   boolean a_comp_moved, boolean a_comp_helps, ModelTile a_comp_selected_tile,
                                   gameTrain a_comp_selected_train)
    {
        ViewGame.clearScreen();

        //set to true when round is over
        boolean round_over = false;
        if (a_game.getCurrentRound().roundIsCompleted())
            round_over = true;

        //set to true when it's computer's turn
        boolean comp_turn = false;
        if (a_game.getCurrentRound().getCurrentPlayer() == gamePlayer.comp_player)
            comp_turn = true;

        //display the round number
        String round_prompt = "ROUND " + a_game.getRoundNumber();
        createPrompt(a_activity, roundGrid, round_prompt, button_height, small_button_width, small_button_font,
                other_back_disabled_col, other_text_disabled_col);

        //display the human name and score
        String human_score = "SCORE: " + a_game.getHumanScore();
        createPrompt(a_activity, humanNameGrid, "HUMAN", button_height, small_button_width,
                small_button_font, other_back_disabled_col, other_text_disabled_col);
        createPrompt(a_activity, humanNameGrid, human_score, button_height, small_button_width,
                small_button_font, other_back_disabled_col, other_text_disabled_col);

        //the color of the move buttons
        int human_move_col;
        int comp_move_col;

        if (!round_over)
        {
            if (!comp_turn)
            {
                human_move_col = Color.parseColor(dark_green);
                comp_move_col = other_back_disabled_col;
            }
            else
            {
                human_move_col = other_back_disabled_col;
                comp_move_col = Color.parseColor(dark_green);
            }
        }
        else
        {
            human_move_col = other_back_disabled_col;
            comp_move_col = other_back_disabled_col;
        }

        //display human move button
        String human_move_prompt = "MOVE";
        createButton(a_activity, humanNameGrid, a_move_listener, R.id.humanMoveButton, human_move_prompt,
                button_height, move_width, small_button_font, !comp_turn && !round_over, human_move_col, save_text_col);

        //the color of the tiles in human hand
        int human_hand_col;
        if (a_human_move)
            human_hand_col = Color.parseColor(dark_orange);
        else
            human_hand_col = Color.parseColor(light_orange);

        //display the human hand
        ModelPile human_hand = a_game.getCurrentRound().getHumanHand();
        ViewPile.displayPile(a_activity, humanHandGrid1, humanHandGrid2, a_tile_listener, human_hand, a_human_move,
                human_hand_col, hand_text_col, a_comp_helps, a_comp_selected_tile);

        //the color of the game trains
        int train_title_back_col;
        int train_title_text_col;
        if (a_tile_selected)
        {
            train_title_back_col = other_back_enabled_col;
            train_title_text_col = other_text_enabled_col;
        }
        else
        {
            train_title_back_col = other_back_disabled_col;
            train_title_text_col = other_text_disabled_col;
        }

        //the train that computer placed its tile on (used to find placed tile)
        boolean human_t = false;
        boolean comp_t = false;
        boolean mexican_t = false;
        if (a_comp_moved)
        {
            if (a_comp_selected_train == gameTrain.human_train)
                human_t = true;
            else if (a_comp_selected_train == gameTrain.comp_train)
                comp_t = true;
            else
                mexican_t = true;
        }

        //the background color for the train titles
        int human_train_col = train_title_back_col;
        int mexican_train_col = train_title_back_col;
        int comp_train_col = train_title_back_col;

        //the text color for the train titles
        int human_train_text_col = train_title_text_col;
        int mexican_train_text_col = train_title_text_col;
        int comp_train_text_col = train_title_text_col;
        if (a_comp_helps)
        {
            if (a_comp_selected_train == gameTrain.human_train)
            {
                human_train_col = Color.YELLOW;
                human_train_text_col = Color.BLACK;
            }
            else if (a_comp_selected_train == gameTrain.comp_train)
            {
                comp_train_col = Color.YELLOW;
                comp_train_text_col = Color.BLACK;
            }
            else
            {
                mexican_train_col = Color.YELLOW;
                mexican_train_text_col = Color.BLACK;
            }
        }

        //display the human train
        String human_train_prompt = "HUMAN TRAIN";
        createButton(a_activity, humanTrainTitleGrid, a_train_listener, R.id.humanTrainButton, human_train_prompt,
                button_height, small_button_width, small_button_font, a_tile_selected, human_train_col, human_train_text_col);
        ModelTrain human_train = a_game.getCurrentRound().getHumanTrain();
        ViewTrain.displayHumanTrain(a_activity, humanTrainGrid1, humanTrainGrid2, human_train, train_back_col, train_text_col, human_t);

        //display the boneyard
        String space = " ";
        String boneyard_prompt = new String();

        for(int i=0; i<12; i++)
            boneyard_prompt += space;
        boneyard_prompt = "\nBONEYARD -->\n";
        for(int i=0; i<12; i++)
            boneyard_prompt += space;

        createPrompt(a_activity, boneyardGrid, boneyard_prompt, ViewTile.tile_height, boneyard_width, ViewTile.font_size,
                other_back_disabled_col, other_text_disabled_col);
        ViewTile.createTile(a_activity, boneyardGrid, a_game.getCurrentRound().getBoneyardTop(), train_back_col, train_text_col);

        //display the mexican train
        String mexican_train_prompt = "MEXICAN TRAIN";
        createButton(a_activity, mexicanTrainTitleGrid, a_train_listener, R.id.mexicanTrainButton, mexican_train_prompt,
                button_height, small_button_width, small_button_font, a_tile_selected, mexican_train_col, mexican_train_text_col);
        ModelTrain mexican_train = a_game.getCurrentRound().getMexicanTrain();
        ViewTrain.displayMexicanTrain(a_activity, mexicanTrainGrid1, mexicanTrainGrid2, mexican_train, train_back_col, train_text_col, mexican_t);

        //display the computer train
        String comp_train_prompt = "COMPUTER TRAIN";
        createButton(a_activity, compTrainTitleGrid, a_train_listener, R.id.compTrainButton, comp_train_prompt,
                button_height, small_button_width, small_button_font, a_tile_selected, comp_train_col, comp_train_text_col);
        ModelTrain comp_train = a_game.getCurrentRound().getCompTrain();
        ViewTrain.displayCompTrain(a_activity, compTrainGrid1, compTrainGrid2, comp_train, train_back_col, train_text_col, comp_t);

        //display the computer name and score
        String comp_score = "SCORE: " + a_game.getCompScore();
        createPrompt(a_activity, compNameGrid, "COMPUTER", button_height, small_button_width, small_button_font,
                other_back_disabled_col, other_text_disabled_col);
        createPrompt(a_activity, compNameGrid, comp_score, button_height, small_button_width, small_button_font,
                other_back_disabled_col, other_text_disabled_col);

        //display computer move button
        String comp_move_prompt = "MOVE";
        createButton(a_activity, compNameGrid, a_move_listener, R.id.compMoveButton, comp_move_prompt,
                button_height, move_width, small_button_font, comp_turn && !round_over, comp_move_col, save_text_col);

        //display the computer hand
        ModelPile comp_hand = a_game.getCurrentRound().getCompHand();
        ViewPile.displayPile(a_activity, compHandGrid1, compHandGrid2, a_tile_listener, comp_hand, false,
                hand_back_col, hand_text_col, a_comp_helps, a_comp_selected_tile);

        //display computer strategies (if applicable)
        if (a_comp_moved || a_comp_helps)
        {
            String tile_str = ((ModelComputer)a_game.getCompPlayer()).getLastTileStr();
            String train_str = ((ModelComputer)a_game.getCompPlayer()).getLastTrainStr();
            createPrompt(a_activity, postGrid1, tile_str, button_height, reasoning_width, reasoning_font, other_back_disabled_col, other_text_disabled_col);
            createPrompt(a_activity, postGrid2, train_str, button_height, reasoning_width, reasoning_font, other_back_disabled_col, other_text_disabled_col);
        }

        //the color of the save game button
        int save_col;
        if (!round_over)
            save_col = Color.parseColor(dark_green);
        else
            save_col = other_back_disabled_col;

        //display the other buttons
        String save_prompt = "SAVE GAME";
        createButton(a_activity, postGrid3, a_other_listener, R.id.saveGameButton, save_prompt,
                button_height, small_button_width, small_button_font, !round_over, save_col, save_text_col);

        //the color of the get help button
        int help_col;
        if (!comp_turn && !round_over && a_human_move)
            help_col = Color.parseColor(dark_green);
        else
            help_col = other_back_disabled_col;

        String help_prompt = "GET HELP";
        createButton(a_activity, postGrid3, a_other_listener, R.id.getHelpButton, help_prompt,
                button_height, small_button_width, small_button_font, !comp_turn && !round_over && a_human_move,
                help_col, save_text_col);

        String quit_prompt = "QUIT GAME";
        createButton(a_activity, postGrid3, a_other_listener, R.id.quitGameButton, quit_prompt,
                button_height, move_width, small_button_font, true, quit_back_col, quit_text_col);
    }

    /*****************************************************************
     * Function Name: displayEventResult
     * Purpose: To display the outcome of the round/game
     * @param a_activity, the game activity
     * @param a_listener, a listener for button menus
     * @param a_game, the game model
     * @param a_event, the event (round or game)
     * Assistance Received: None
     ***************************************************************** */
    public static void displayEventResult(Activity a_activity, View.OnClickListener a_listener, ModelGame a_game, String a_event)
    {
        comparisonOutcome winner;
        int comp_score, human_score;
        if (a_event.equalsIgnoreCase("GAME"))
        {
            winner = a_game.getWinner();
            comp_score = a_game.getCompScore();
            human_score = a_game.getHumanScore();
        }
        else
        {
            winner = a_game.getCurrentRound().getWinner();
            comp_score = a_game.getCurrentRound().getCompPoints();
            human_score = a_game.getCurrentRound().getHumanPoints();
        }

        String winner_name;
        if (winner == comparisonOutcome.comp_lower)
            winner_name = "COMPUTER";
        else if (winner == comparisonOutcome.human_lower)
            winner_name = "HUMAN";
        else
            winner_name = "TIE";

        String prompt_msg = a_event + " RESULT" + "\n" + "WINNER: " + winner_name + "\n" +
                "COMPUTER SCORE: " + comp_score + "\n" + "HUMAN SCORE: " + human_score;

        if (a_event.equalsIgnoreCase("GAME"))
        {
            clearScreen();
            createPrompt(a_activity, roundGrid, prompt_msg, result_height, home_page_width, button_font, train_back_col, other_text_disabled_col);
            createButton(a_activity, humanNameGrid, a_listener, R.id.homePageButton, "MAIN MENU",
                    button_height, home_page_width, button_font, true, other_back_enabled_col, other_text_enabled_col);
            createButton(a_activity, humanHandGrid1, a_listener, R.id.quitGameButton, "QUIT",
                    button_height, home_page_width, button_font, true, quit_back_col, quit_text_col);
        }

        else
            createPrompt(a_activity, postGrid4, prompt_msg, result_height, result_width, button_font, train_back_col,
                    other_text_disabled_col);
    }

    /*****************************************************************
     * Function Name: promptForAnotherRound
     * Purpose: To ask user to play another round
     * @param a_activity, the game activity
     * @param a_other_listener, a listener for other buttons
     * Assistance Received: None
     ***************************************************************** */
    public static void promptForAnotherRound(Activity a_activity, View.OnClickListener a_other_listener)
    {
        createPrompt(a_activity, postGrid5, a_activity.getString(R.string.selectionPrompt), button_height, result_width, button_font, other_back_disabled_col, other_text_disabled_col);
        createButton(a_activity, postGrid6, a_other_listener, R.id.anotherRoundButton , "Play Another Round",
                button_height, result_width, button_font, true, other_back_enabled_col, other_text_enabled_col);
        createButton(a_activity, postGrid7, a_other_listener, R.id.finalResultButton , "Get Final Result",
                button_height, result_width, button_font, true, other_back_enabled_col, other_text_enabled_col);
    }

    /*****************************************************************
     * Function Name: createButton
     * Purpose: To create buttons
     * @param a_activity, the game activity
     * @param a_grid, the grid to display button in
     * @param a_listener, the button's listeners
     * @param a_id, the button's id
     * @param a_name, the button's text information
     * @param a_height, the button's vertical height
     * @param a_width, the button's horizontal width
     * @param a_font, the text font of the button
     * @param a_enabled, set to true if button is clickable
     * @param a_background_col, the background color of the button
     * @param a_text_col, the text color of the button
     * Assistance Received: None
     ***************************************************************** */
    public static void createButton(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid, View.OnClickListener a_listener,
                                    int a_id, String a_name, int a_height, int a_width, int a_font, boolean a_enabled,
                                    int a_background_col, int a_text_col)
    {
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(a_grid.getLayoutParams());
        params1.height = a_height;
        params1.width = a_width;
        params1.setMargins(1, 0, 1, 0);

        Button button = new Button(a_activity);

        //set the button attributes
        button.setLayoutParams(params1);
        button.setId(a_id);
        button.setText(a_name);
        button.setTextSize(a_font);
        button.setOnClickListener(a_listener);
        button.setEnabled(a_enabled);
        button.setBackgroundColor(a_background_col);
        button.setTextColor(a_text_col);

        //add it to the screen
        a_grid.addView(button);
    }

    /*****************************************************************
     * Function Name: createEditText
     * Purpose: To create input boxes
     * @param a_activity, the game activity
     * @param a_grid, the grid to display box in
     * @param a_id, the box's id
     * @param a_prompt, the text content of the box
     * @param a_height, the box's vertical height
     * @param a_width, the box's horizontal width
     * @param a_font, the text font of the box
     * Assistance Received: None
     ***************************************************************** */
    public static void createEditText(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid,
                               int a_id, String a_prompt, int a_height, int a_width, int a_font)
    {
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(a_grid.getLayoutParams());
        params1.height = a_height;
        params1.width = a_width;

        EditText myText = new EditText(a_activity);

        //setting the attributes
        myText.setLayoutParams(params1);
        myText.setTextSize(a_font);
        myText.setText(a_prompt);
        myText.setId(a_id);

        a_grid.addView(myText);
    }

    /*****************************************************************
     * Function Name: createPrompt
     * Purpose: To create titles
     * @param a_activity, the game activity
     * @param a_grid, the grid to display title in
     * @param a_prompt, the text content of the title
     * @param a_height, the title's vertical height
     * @param a_width, the title's horizontal width
     * @param a_font, the text font of the title
     * @param a_background_col, the background color of the title
     * @param a_text_col, the text color of the title
     * Assistance Received: None
     ***************************************************************** */
    public static void createPrompt(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid, String a_prompt,
                                    int a_height, int a_width, int a_font, int a_background_col, int a_text_col)
    {
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(a_grid.getLayoutParams());
        params1.height = a_height;
        params1.width = a_width;
        params1.setMargins(1, 0, 1, 0);

        Button button = new Button(a_activity);

        //set the button attributes
        button.setLayoutParams(params1);
        button.setText(a_prompt);
        button.setTextSize(a_font);
        button.setEnabled(false);
        button.setBackgroundColor(a_background_col);
        button.setTextColor(a_text_col);

        //add it to the screen
        a_grid.addView(button);
    }

    /*****************************************************************
     * Function Name: createDialog
     * Purpose: To create dialogs
     * @param a_activity, the game activity
     * @param a_dialog, the dialog's listener
     * @param a_title, the tile of the dialog
     * @param a_button_one, the first button of the dialog
     * @param a_button_two, the second button of the dialog
     * Assistance Received: None
     ***************************************************************** */
    public static void createDialog(Activity a_activity, DialogInterface.OnClickListener a_dialog,
                                    String a_title, String a_button_one, String a_button_two)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(a_activity);
        builder.setMessage(a_title);
        builder.setPositiveButton(a_button_one, a_dialog);
        builder.setNegativeButton(a_button_two, a_dialog);
        builder.show();
    }

    /*****************************************************************
     * Function Name: displaySaveGameDialog
     * Purpose: To make sure user wants to save and quit the game
     * @param a_activity, the game activity
     * @param a_dialog, the dialog's listener
     * Assistance Received: None
     ***************************************************************** */
    public static void displaySaveGameDialog(Activity a_activity, DialogInterface.OnClickListener a_dialog)
    {
        createDialog(a_activity,a_dialog,"Are you sure you want to save and quit?", "YES", "NO");
    }

    /*****************************************************************
     * Function Name: displayQuitDialog
     * Purpose: To make sure user wants to quit game
     * @param a_activity, the game activity
     * @param a_dialog, the dialog's listener
     * Assistance Received: None
     ***************************************************************** */
    public static void displayQuitDialog(Activity a_activity, DialogInterface.OnClickListener a_dialog)
    {
        createDialog(a_activity,a_dialog,"Are you sure you want to quit?", "QUIT", "CANCEl");
    }
}
