package edu.ramapo.nnaghiza.Mpile;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtile.ViewTile;

public class ViewPile {

    // *** constants *** //

    //number of tiles to display in each grid
    static int num_tiles = 8;

    /**********************************************************************
     * Function Name: displayPile
     * Purpose: To display a collection of tiles on the screen
     * @param a_activity, the game activity
     * @param a_gridOne, the first grid allocated to the pile
     * @param a_gridTwo, the second grid allocated to the pile
     * @param a_listener, the listener to the tile buttons in the pile
     * @param a_pile, the pile model
     * @param a_enabled, set to true when tiles in the pile are clickable
     * @param a_background_col, the background color of the tile buttons
     * @param a_text_col, the text color of the tile buttons
     * @param a_helped, set to true when computer helps human
     * @param a_help_tile, the tile suggested by computer to be played
     * Assistance Received: None
     ********************************************************************** */
    public static void displayPile(Activity a_activity, androidx.gridlayout.widget.GridLayout a_gridOne,
                                   androidx.gridlayout.widget.GridLayout a_gridTwo, View.OnClickListener a_listener, ModelPile a_pile,
                                   boolean a_enabled, int a_background_col, int a_text_col, boolean a_helped, ModelTile a_help_tile)
    {
        int pile_size = a_pile.getSize();

        if (pile_size <= 8)
        {
            //display all tiles on the first grid
            for (int i=0; i<pile_size; i++)
            {
                if (a_enabled)
                {
                    if (a_helped && a_pile.getTile(i).isEqual(a_help_tile))
                        ViewTile.createClickableTile(a_activity, a_gridOne, a_listener, a_pile.getTile(i), i, Color.YELLOW, a_text_col);
                    else
                        ViewTile.createClickableTile(a_activity, a_gridOne, a_listener, a_pile.getTile(i), i, a_background_col, a_text_col);
                }
                else
                    ViewTile.createTile(a_activity, a_gridOne, a_pile.getTile(i), a_background_col, a_text_col);
            }
        }

        //if there is more than 8 tiles
        else
        {
            //display the first 8 tiles
            for (int i = 0; i< num_tiles; i++)
            {
                if (a_enabled)
                {
                    if (a_helped && a_pile.getTile(i).isEqual(a_help_tile))
                        ViewTile.createClickableTile(a_activity, a_gridOne, a_listener, a_pile.getTile(i), i, Color.YELLOW, a_text_col);
                    else
                        ViewTile.createClickableTile(a_activity, a_gridOne, a_listener, a_pile.getTile(i), i, a_background_col, a_text_col);
                }

                else
                    ViewTile.createTile(a_activity, a_gridOne, a_pile.getTile(i), a_background_col, a_text_col);
            }

            //display the remaining tiles on the second grid
            for (int i = num_tiles; i<pile_size; i++)
            {
                if (a_enabled)
                {
                    if (a_helped && a_pile.getTile(i).isEqual(a_help_tile))
                        ViewTile.createClickableTile(a_activity, a_gridTwo, a_listener, a_pile.getTile(i), i, Color.YELLOW, a_text_col);
                    else
                        ViewTile.createClickableTile(a_activity, a_gridTwo, a_listener, a_pile.getTile(i), i, a_background_col, a_text_col);
                }

                else
                    ViewTile.createTile(a_activity, a_gridTwo, a_pile.getTile(i), a_background_col, a_text_col);
            }
        }
    }
}