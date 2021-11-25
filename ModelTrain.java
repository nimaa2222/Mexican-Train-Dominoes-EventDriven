package edu.ramapo.nnaghiza.Mtrain;

import edu.ramapo.nnaghiza.Mpile.ModelPile;
import edu.ramapo.nnaghiza.Mtile.ModelTile;

public class ModelTrain {

    // *** data members *** //
    private ModelPile m_tiles;
    private boolean m_marker;
    private boolean m_orphan_double;

    // *** constructors *** //
    public ModelTrain()
    {
        m_tiles = new ModelPile();
        m_marker = false;
        m_orphan_double = false;
    }

    // *** selectors *** //
    public ModelTile getTile(int a_index) { return m_tiles.getTile(a_index); }
    public int getSize() {return m_tiles.getSize();}
    public int getEndVal() {return m_tiles.getTile(m_tiles.getSize() - 1).getBack(); }
    public boolean isMarked() { return m_marker; }
    public boolean isOrphanDouble(){return m_orphan_double;}
    public boolean endsWithDouble(){return m_tiles.getTile(m_tiles.getSize() - 1).isDouble();}

    /****************************************************************************
     * Function Name: getDoublesInTrain
     * Purpose: To identify the double tiles in the train
     * @return a pile object, the double tiles in the train
     * Assistance Received: None
     ************************************************************************** */
    public ModelPile getDoublesInTrain()
    {
        ModelPile double_tiles = new ModelPile();

        //skip engine (i = 1)
        for (int i=1; i<m_tiles.getSize(); i++)
        {
            if (m_tiles.getTile(i).isDouble())
                double_tiles.addTileToPile(m_tiles.getTile(i));
        }

        return double_tiles;
    }

    // *** mutators *** //
    public void addTile(ModelTile a_tile) { m_tiles.addTileToPile(a_tile); }
    public void addMarker() {m_marker = true; }
    public void removeMarker() {m_marker = false; }
    public void reverseTrain() {m_tiles.reverse();}

    /****************************************************************************
     * Function Name: setOrphanDouble
     * Purpose: To establish orphan doubles before the next player takes turn
     * Assistance Received: None
     ************************************************************************** */
    public void setOrphanDouble()
    {
        //an orphan double ends with a double
        if (endsWithDouble())
            m_orphan_double = true;

        else
            m_orphan_double = false;
    }

    /****************************************************************************
     * Function Name: removeOrphanMarker
     * Purpose: To remove the orphan double marker from a train
     * Assistance Received: None
     ************************************************************************** */
    public void removeOrphanMarker()
    {
        if (!endsWithDouble())
            m_orphan_double = false;
    }

    // *** utility (private) methods *** //
    /****************************************************************************
     * Function Name: matches
     * Purpose: To check whether a tile matches the end of the train
     * @param a_tile, a tile object to check the train end with
     * @return true if tile matches train, otherwise false
     * Assistance Received: None
     ************************************************************************** */
    public boolean matches(ModelTile a_tile)
    {
        if (a_tile.getFront() == getEndVal() || a_tile.getBack() == getEndVal() )
            return true;

        return false;
    }
}
