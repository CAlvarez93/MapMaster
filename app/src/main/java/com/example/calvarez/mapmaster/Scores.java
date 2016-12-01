package com.example.calvarez.mapmaster;

/**
 * Created by Adam Dau "The Greatest"  on 11/17/2016.
 */
public class Scores{

    /**
     * These are the two variables that make up the Scores object. They store the entered
     * user name of the player. And depending on which mode the player is using either the
     * time they finished in, or the amount of cities they guessed right.
     */
    private int score;
    private String name;

    public Scores(){}

    /**
     * This funciton creates new Scores objects by storing the given score and name in
     * the variables located in this class. Now if we need to use a getter it will be
     * able to reference the score and name in this class.
     * @param score
     * @param name
     */
    public Scores(int score,String name){
        this.score = score;
        this.name = name;
    }

    /**
     * Getters and Setters for scores and name variables.
     * @param new_score
     */
    public void setScore(int new_score){
        this.score = new_score;
    }
    public void setName(String new_name){
        this.name = new_name;
    }
    public int getScore(){
        return score;
    }
    public String getName(){
        return name;
    }

}
