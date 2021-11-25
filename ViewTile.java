package edu.ramapo.nnaghiza.Mtile;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class ViewTile {

    // *** constants *** //
    public static int tile_width = 49;
    public static int tile_height = 75;
    public static int font_size = 6;

    // *** utility (private) methods *** //
    /*********************************************************
     * Function Name: getTileText
     * Purpose: To get the content of a tile button
     * @param a_tile, the tile to get its content
     * @return a string holding the button's content
     * Assistance Received: None
     ***************************************************** */
    public static String getTileText(ModelTile a_tile)
    {
        String space = " ";
        String newLine = "\n";
        String pipe = "|";
        String dash = "-";

        if (a_tile.getFront() == -1)
            return space + space + space + newLine + space + space + space + newLine + space + space + space;
        if (a_tile.isDouble())
            return space + a_tile.getFront() + space + newLine + space + pipe + space + newLine + space + a_tile.getFront() + space;
        else
            return space + space + space + newLine + a_tile.getFront() + dash + a_tile.getBack() + newLine + space + space + space;
    }

    /*******************************************************************
     * Function Name: createClickableTile
     * Purpose: To create a tile button
     * @param a_activity, the game activity
     * @param a_grid, the grid to display the tile in
     * @param a_listener, the tile button listener
     * @param a_tile, the tile model
     * @param a_id, the tile button's id
     * @param a_background_col, the tile button's background color
     * @param a_text_col, the tile's text color
     * Assistance Received: None
     *************************************************************** */
    public static void createClickableTile(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid,
                                           View.OnClickListener a_listener, ModelTile a_tile, int a_id,
                                           int a_background_col, int a_text_col)
    {
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(a_grid.getLayoutParams());
        params1.height = tile_height;
        params1.width = tile_width;
        params1.setMargins(1, 1, 1, 1);

        //create a new button
        Button button = new Button(a_activity);

        //set the button attributes
        button.setLayoutParams(params1);
        button.setText(getTileText(a_tile));
        button.setTextSize(font_size);
        button.setId(a_id);
        button.setOnClickListener(a_listener);
        button.setBackgroundColor(a_background_col);
        button.setTextColor(a_text_col);

        //add it to the screen
        a_grid.addView(button);
    }

    /*******************************************************************
     * Function Name: createTile
     * Purpose: To create a non-clickable tile button
     * @param a_activity, the game activity
     * @param a_grid, the grid to display the tile in
     * @param a_tile, the tile model
     * @param a_background_col, the tile button's background color
     * @param a_text_col, the tile's text color
     * Assistance Received: None
     *************************************************************** */
    public static void createTile(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid,
                                  ModelTile a_tile, int a_background_col, int a_text_col)
    {
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(a_grid.getLayoutParams());
        params1.height = tile_height;
        params1.width = tile_width;
        params1.setMargins(1, 1, 1, 1);

        //create a new button
        Button button = new Button(a_activity);

        //set the button attributes
        button.setLayoutParams(params1);
        button.setText(getTileText(a_tile));
        button.setTextSize(font_size);
        button.setEnabled(false);
        button.setBackgroundColor(a_background_col);
        button.setTextColor(a_text_col);

        //add it to the screen
        a_grid.addView(button);
    }
}
