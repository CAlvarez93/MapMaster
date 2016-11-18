package com.example.calvarez.mapmaster;

/**
 * Created by adau on 11/17/2016.
 */
public class Scores {
    private int score;
    private String name;

    public Scores(){

    }
    public Scores(int score,String name){
        this.score = score;
        this.name = name;
    }
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
