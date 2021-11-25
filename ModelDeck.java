package edu.ramapo.nnaghiza.Mdeck;
import java.util.Random;

import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mtile.ModelTile;

public class ModelDeck {

    // *** data members *** //
    private static int m_maxSize = 55;
    private ModelPile m_tiles = new ModelPile();

    // *** constructors *** //
    public ModelDeck()
    {
        //the tile pips go from 0-9
        int tiles_num = 10;

        //create all possible 55 tiles
        for (int i=0; i<tiles_num; i++)
        {
            for (int j=i; j<tiles_num; j++)
            {
                ModelTile new_tile = new ModelTile(i,j);
                m_tiles.addTileToPile(new_tile);
            }
        }
    }

    // *** selectors *** //
    public ModelTile getTile(int a_index) { return m_tiles.getTile(a_index); }
    public int getCurrentSize() { return m_tiles.getSize(); }

    // *** mutators *** //
    public void removeTile(ModelTile a_tile) { m_tiles.removeTileFromPile(a_tile); }

    /****************************************************************************
     * Function Name: shuffle
     * Purpose: To shuffle the deck
     * Algorithm:
     *          (1) generate two random numbers between 0 and (size of deck - 1)
     * 	        (2) swap tiles in the deck matching the index of random numbers
     * 	        (3) repeat this (m_maxSize - 1) * 2 times
     * Assistance Received: None
     ************************************************************************** */
    public void shuffle()
    {
        //shuffle the tiles in the deck - engine has been removed

        //giving each tile 2 chances to be swapped by another tile
        int swap_times = (m_maxSize - 1) * 2;

        //instance of random class
        Random rand = new Random();

        //swap the tiles in the deck in pairs
        for (int i=0; i<swap_times; i++)
        {
            //generate 2 random numbers in range 0-53
            int first_rand = rand.nextInt(m_maxSize - 1);
            int second_rand = rand.nextInt(m_maxSize - 1);

            //swap the two tiles based on random indices
            m_tiles.swapTiles(first_rand, second_rand);
        }
    }
}
