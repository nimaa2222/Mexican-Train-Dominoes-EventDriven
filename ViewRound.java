package edu.ramapo.nnaghiza.Mround;

import android.app.Activity;
import android.view.View;

import edu.ramapo.nnaghiza.Mgame.ViewGame;
import edu.ramapo.nnaghiza.R;
import edu.ramapo.nnaghiza.enums.gamePlayer;

public class ViewRound {

    /**********************************************************
     * Function Name: displayCoinToss
     * Purpose: To perform a coin toss with user
     * @param a_activity, the game activity
     * @param a_listener, the coin toss listener
     * Assistance Received: None
     ******************************************************* */
    public static void displayCoinToss(Activity a_activity, View.OnClickListener a_listener)
    {
        ViewGame.clearScreen();

        //create elements of the coin toss and add to screen
        ViewGame.createPrompt(a_activity, ViewGame.roundGrid, a_activity.getString(R.string.selectionPrompt), ViewGame.button_height,
                ViewGame.button_width, ViewGame.button_font, ViewGame.other_back_disabled_col, ViewGame.other_text_disabled_col);

        ViewGame.createButton(a_activity, ViewGame.humanNameGrid, a_listener, R.id.headsButton, a_activity.getString(R.string.headsButtonText), ViewGame.button_height,
                ViewGame.button_width, ViewGame.button_font, true, ViewGame.hand_back_col, ViewGame.other_text_disabled_col);

        ViewGame.createButton(a_activity, ViewGame.humanHandGrid1, a_listener, R.id.tailsButton, a_activity.getString(R.string.tailsButtonText), ViewGame.button_height,
                ViewGame.button_width, ViewGame.button_font, true, ViewGame.train_back_col, ViewGame.other_text_disabled_col);
    }

    /**********************************************************
     * Function Name: displayCoinTossResult
     * Purpose: To display the outcome of the coin toss
     * @param a_activity, the game activity
     * @param a_listener, the proceed button listener
     * @param a_winner, the winner of the coin toss
     * Assistance Received: None
     ******************************************************* */
    public static void displayCoinTossResult(Activity a_activity, View.OnClickListener a_listener, gamePlayer a_winner)
    {
        //disable heads and tails buttons
        a_activity.findViewById(R.id.headsButton).setBackgroundColor(ViewGame.other_back_disabled_col);
        a_activity.findViewById(R.id.tailsButton).setBackgroundColor(ViewGame.other_back_disabled_col);
        a_activity.findViewById(R.id.headsButton).setEnabled(false);
        a_activity.findViewById(R.id.tailsButton).setEnabled(false);

        String prompt = "YOU ";
        if (a_winner == gamePlayer.human_player)
            prompt += "WON";
        else
            prompt += "LOST";
        prompt += " THE COIN TOSS";

        ViewGame.createPrompt(a_activity, ViewGame.humanHandGrid2, prompt, ViewGame.button_height, ViewGame.button_width,
                ViewGame.button_font, ViewGame.other_back_disabled_col, ViewGame.other_text_disabled_col);

        ViewGame.createButton(a_activity, ViewGame.humanTrainTitleGrid, a_listener, R.id.proceedButton, "PROCEED",
                ViewGame.button_height, ViewGame.button_width, ViewGame.button_font, true, ViewGame.move_color,
                ViewGame.other_text_disabled_col);
    }
}
