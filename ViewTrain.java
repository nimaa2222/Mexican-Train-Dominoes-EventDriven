package edu.ramapo.nnaghiza.Mtrain;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.GridLayout;

import edu.ramapo.nnaghiza.Mtile.ModelTile;
import edu.ramapo.nnaghiza.Mtile.ViewTile;

public class ViewTrain {

    // *** constants *** //
    static int max_train_tiles = 16;
    static int marker_back_col = Color.CYAN;
    static int marker_text_col = Color.BLACK;
    static int placed_tile_color = Color.YELLOW;

    // *** utility (private) methods *** //
    /********************************************************************************
     * Function Name: displayHumanTrain
     * Purpose: To display human train on the screen
     * @param a_activity, the game activity
     * @param a_grid_one, the first grid allocated to human train
     * @param a_grid_two, the second grid allocated to human train
     * @param a_train, the model train
     * @param a_background_col, the train tile button's background color
     * @param a_text_col, the train tile's text color
     * @param a_placed, set to true if a tile computer places tile on this train
     * Assistance Received: None
     **************************************************************************** */
    public static void displayHumanTrain(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid_one,
                                         androidx.gridlayout.widget.GridLayout a_grid_two, ModelTrain a_train,
                                         int a_background_col, int a_text_col, boolean a_placed)
    {
        if (a_train.getSize() > max_train_tiles)
        {
            for (int i=0; i<max_train_tiles; i++)
                ViewTile.createTile(a_activity, a_grid_one, a_train.getTile(i), a_background_col, a_text_col);

            for (int i=max_train_tiles; i<a_train.getSize() - 1; i++)
                ViewTile.createTile(a_activity, a_grid_two, a_train.getTile(i), a_background_col, a_text_col);

            //indicate tile placed (if applicable)
            int tile_color;
            if (a_placed)
                tile_color = placed_tile_color;
            else
                tile_color = a_background_col;

            ViewTile.createTile(a_activity, a_grid_two, a_train.getTile(a_train.getSize() - 1), tile_color, a_text_col);
        }

        else
        {
            for (int i=0; i<a_train.getSize() - 1; i++)
                ViewTile.createTile(a_activity, a_grid_one, a_train.getTile(i), a_background_col, a_text_col);

            //indicate tile placed (if applicable)
            int tile_color;
            if (a_placed)
                tile_color = placed_tile_color;
            else
                tile_color = a_background_col;

            ViewTile.createTile(a_activity, a_grid_one, a_train.getTile(a_train.getSize() - 1), tile_color, a_text_col);
        }

        //add marker (if applicable)
        if (a_train.isMarked())
            createMarker(a_activity, a_grid_one);
    }

    /********************************************************************************
     * Function Name: displayMexicanTrain
     * Purpose: To display mexican train on the screen
     * @param a_activity, the game activity
     * @param a_grid_one, the first grid allocated to mexican train
     * @param a_grid_two, the second grid allocated to mexican train
     * @param a_train, the model train
     * @param a_background_col, the train tile button's background color
     * @param a_text_col, the train tile's text color
     * @param a_placed, set to true if a tile computer places tile on this train
     * Assistance Received: None
     **************************************************************************** */
    public static void displayMexicanTrain(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid_one,
                                           androidx.gridlayout.widget.GridLayout a_grid_two, ModelTrain a_train,
                                           int a_background_col, int a_text_col, boolean a_placed)
    {
        if (a_train.getSize() > max_train_tiles)
        {
            for (int i=0; i<max_train_tiles; i++)
                ViewTile.createTile(a_activity, a_grid_one, a_train.getTile(i), a_background_col, a_text_col);

            for (int i=max_train_tiles; i<a_train.getSize() - 1; i++)
                ViewTile.createTile(a_activity, a_grid_two, a_train.getTile(i), a_background_col, a_text_col);

            //indicate tile placed (if applicable)
            int tile_color;
            if (a_placed)
                tile_color = placed_tile_color;
            else
                tile_color = a_background_col;

            ViewTile.createTile(a_activity, a_grid_two, a_train.getTile(a_train.getSize() - 1), tile_color, a_text_col);
        }

        else
        {
            for (int i=0; i<a_train.getSize() - 1; i++)
                ViewTile.createTile(a_activity, a_grid_one, a_train.getTile(i), a_background_col, a_text_col);

            //indicate tile placed (if applicable)
            int tile_color;
            if (a_placed)
                tile_color = placed_tile_color;
            else
                tile_color = a_background_col;

            ViewTile.createTile(a_activity, a_grid_one, a_train.getTile(a_train.getSize() - 1), tile_color, a_text_col);
        }
    }

    /********************************************************************************
     * Function Name: displayCompTrain
     * Purpose: To display computer train on the screen
     * @param a_activity, the game activity
     * @param a_grid_one, the first grid allocated to computer train
     * @param a_grid_two, the second grid allocated to computer train
     * @param a_train, the model train
     * @param a_background_col, the train tile button's background color
     * @param a_text_col, the train tile's text color
     * @param a_placed, set to true if a tile computer places tile on this train
     * Assistance Received: None
     **************************************************************************** */
    public static void displayCompTrain(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid_one,
                                        androidx.gridlayout.widget.GridLayout a_grid_two, ModelTrain a_train,
                                        int a_background_col, int a_text_col, boolean a_placed)
    {
        //add marker (if applicable)
        if (a_train.isMarked())
            createMarker(a_activity, a_grid_one);

        if (a_train.getSize() > max_train_tiles)
        {
            //display the last tile
            //indicate tile placed (if applicable)
            int tile_color;
            if (a_placed)
                tile_color = placed_tile_color;
            else
                tile_color = a_background_col;

            ModelTile last_tile = a_train.getTile(a_train.getSize() - 1);
            last_tile.flip();
            ViewTile.createTile(a_activity, a_grid_one, last_tile, tile_color, a_text_col);
            last_tile.flip();

            //display the last 16 tiles
            int index = a_train.getSize() - 2;
            for (int i=0; i<max_train_tiles - 1; i++)
            {
                ModelTile tile = a_train.getTile(index);
                tile.flip();
                ViewTile.createTile(a_activity, a_grid_one, tile, a_background_col, a_text_col);
                tile.flip();
                index--;
            }

            //display the remaining tiles
            for (int i=0; i<a_train.getSize() - max_train_tiles; i++)
            {
                ModelTile tile = a_train.getTile(index);
                tile.flip();
                ViewTile.createTile(a_activity, a_grid_two, tile, a_background_col, a_text_col);
                tile.flip();
                index--;
            }
        }

        else
        {
            //display the last tile
            //indicate tile placed (if applicable)
            int tile_color;
            if (a_placed)
                tile_color = placed_tile_color;
            else
                tile_color = a_background_col;

            ModelTile last_tile = a_train.getTile(a_train.getSize() - 1);
            last_tile.flip();
            ViewTile.createTile(a_activity, a_grid_one, last_tile, tile_color, a_text_col);
            last_tile.flip();

            for (int i=a_train.getSize() - 2; i>=0; i--)
            {
                ModelTile tile = a_train.getTile(i);
                tile.flip();
                ViewTile.createTile(a_activity, a_grid_one, tile, a_background_col, a_text_col);
                tile.flip();
            }
        }
    }

    /*****************************************************************************
     * Function Name: displayCompTrain
     * Purpose: To add a marker to personal trains when they are marked
     * @param a_activity, the game activity
     * @param a_grid, the grid to display the marker in
     * Assistance Received: None
     **************************************************************************** */
    public static void createMarker(Activity a_activity, androidx.gridlayout.widget.GridLayout a_grid)
    {
        GridLayout.LayoutParams params1 = new GridLayout.LayoutParams(a_grid.getLayoutParams());
        params1.height = ViewTile.tile_height;
        params1.width = ViewTile.tile_width;
        params1.setMargins(1, 1, 1, 1);

        //create a new button
        Button button = new Button(a_activity);

        String space = " ";
        String marker_text = space + space + space + "\n" + space + "M" + space + "\n" + space + space + space;

        //set the button attributes
        button.setLayoutParams(params1);
        button.setText(marker_text);
        button.setTextSize(ViewTile.font_size);
        button.setEnabled(false);

        button.setBackgroundColor(marker_back_col);
        button.setTextColor(marker_text_col);

        //add it to the screen
        a_grid.addView(button);
    }
}
