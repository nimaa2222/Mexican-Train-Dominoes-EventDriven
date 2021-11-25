package edu.ramapo.nnaghiza.Mpile;

import java.util.Collections;
import java.util.Vector;

import edu.ramapo.nnaghiza.Mtile.ModelTile;

public class ModelPile {

    // *** data members *** //
    private Vector<ModelTile> m_tiles;

    // *** constructors *** //
    public ModelPile() {m_tiles = new Vector<ModelTile>();}

    // *** selectors *** //
    public ModelTile getTile(int a_index) { return m_tiles.get(a_index); }
    public ModelTile getTileOnTop() { return m_tiles.get(0); }
    public int getSize() {return m_tiles.size();}

    /**********************************************************
     * Function Name: getPileSum
     * Purpose: To calculate the pip sum of all tiles in a pile
     * @return an integer, the pip sum of all tiles in the pile
     * Assistance Received: None
     ******************************************************* */
    public int getPileSum()
    {
        int sum = 0;
        for (int i=0; i<m_tiles.size(); i++)
            sum += m_tiles.get(i).getTileSum();

        return sum;
    }

    /**************************************************************
     * Function Name: getHighestTile
     * Purpose: To identify the tile with the highest pip sum
     * @return a tile object, the tile with the highest pip sum
     * Assistance Received: None
     *********************************************************** */
    public ModelTile getHighestTile()
    {
        ModelTile highest_tile = new ModelTile();
        for (int i=0; i<m_tiles.size(); i++)
        {
            if (highest_tile.getTileSum() < m_tiles.get(i).getTileSum())
                highest_tile = m_tiles.get(i);
        }

        return highest_tile;
    }

    // *** mutators *** //
    public void addTileToPile(ModelTile a_tile) { m_tiles.add(a_tile); }
    public void swapTiles(int a_index_one, int a_index_two) { Collections.swap(m_tiles, a_index_one, a_index_two); }
    public void reverse () { Collections.reverse(m_tiles); }

    /**************************************************************
     * Function Name: removeTileFromPile
     * Purpose: To remove a tile from the pile
     * @param a_tile, a tile object to be removed from the pile
     * Assistance Received: None
     *********************************************************** */
    public void removeTileFromPile(ModelTile a_tile)
    {
        //find the index of the tile in the deck to be removed
        int tile_index = 0;
        for (int i=0; i<m_tiles.size(); i++)
        {
            if (m_tiles.get(i).isEqual(a_tile))
                tile_index = i;
        }

        m_tiles.remove(tile_index);
    }

    /**************************************************************
     * Function Name: getTileOnTop
     * Purpose: To get the tile on top of the pile
     * @return a tile object, the tile on top of the pile
     * Assistance Received: None
     *********************************************************** */
    public ModelTile removeTileOnTop()
    {
        //identify the tile on top of the pile
        ModelTile copy = new ModelTile();
        copy = m_tiles.get(0);

        //remove the tile from the pile
        m_tiles.remove(0);

        return copy;
    }

    // *** utility (private) methods *** //
    /**************************************************************
     * Function Name: isTileInPile
     * Purpose: To check whether a tile exists in a pile
     * @param a_tile, a tile object to check in the pile
     * @return true if the tile is in the pile, otherwise false
     * Assistance Received: None
     *********************************************************** */
    public boolean isTileInPile(ModelTile a_tile)
    {
        for (int i=0; i<m_tiles.size(); i++)
        {
            if (m_tiles.get(i).isEqual(a_tile))
                return true;
        }

        return false;
    }
}
