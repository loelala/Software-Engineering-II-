package edu.hm.wedoit.model;

/**
 * Created by Joncn on 31.10.2015.
 */
public class SupplierWithScore extends Supplier {

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    private int score;
}
