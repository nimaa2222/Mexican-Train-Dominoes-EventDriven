package edu.ramapo.nnaghiza.Mturn;

public class ModelTurn {

    private int m_doubles_placed;
    boolean m_boneyard_pick;

    //set to true when it's time for next player to go
    boolean m_isOver;

    public ModelTurn()
    {
        m_doubles_placed = 0;
        m_boneyard_pick = false;
        m_isOver = false;
    }

    // *** selectors *** //
    public int getDoublesPlaced(){return m_doubles_placed;}
    public boolean pickedFromBoneyard(){return m_boneyard_pick;}
    public boolean isOver(){return m_isOver;}

    // *** mutators *** //
    public void doublePlaced(){m_doubles_placed++;}
    public void setBoneyardPick(boolean a_bool){m_boneyard_pick = a_bool;}
    public void markTurnComplete(){m_isOver = true;}
}
