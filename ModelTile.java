package edu.ramapo.nnaghiza.Mtile;

public class ModelTile {

    // *** data members *** //
    private int m_front, m_back;

    // *** constructors *** //
    public ModelTile()
    {
        m_front = 0;
        m_back = 0;
    }

    public ModelTile(int a_front, int a_back)
    {
        m_front = a_front;
        m_back = a_back;
    }

    // *** selectors *** //
    public int getFront() { return m_front; }
    public int getBack() { return m_back; }
    public int getTileSum() {return m_front + m_back; }

    // *** mutators *** //
    public void flip()
    {
        int temp = m_front;
        m_front = m_back;
        m_back = temp;
    }

    // *** utility (private) methods *** //
    public boolean isDouble() {return m_front == m_back; }

    /*********************************************************
     * Function Name: isEqual
     * Purpose: To check whether two tiles are the same tiles
     * @param a_tile, a tile object to check against this tile
     * @return true if the tiles are the same, otherwise false
     * Assistance Received: None
      ***************************************************** */
    public boolean isEqual(ModelTile a_tile)
    {
        //case 1
        if (m_front == a_tile.m_front && m_back == a_tile.m_back)
            return true;

        //case 2
        if (m_front == a_tile.m_back && m_back == a_tile.m_front)
            return true;

        return false;
    }

    /****************************************************************
     * Function Name: tileMatchesValue
     * Purpose: To check whether a value matches either pip of a tile
     * @param a_value, a value to check the pip values against
     * @return true if it is a match, otherwise false
     * Assistance Received: None
     ************************************************************* */
    public boolean tileMatchesValue(int a_value)
    {
        if (m_front == a_value || m_back == a_value)
            return true;

        return false;
    }
}
