package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        game g = new game();
    }
}

class player{

    private int score;
    private String name;

    player(String name){
        this.name = name;
        this.score = 0;
    }
    int getScore(){
        return this.score;
    }

    void setScore(int score){
        this.score = score;
    }
    public String toString(){
        return this.name;
    }

    String getName(){
        return this.name;
    }
}


// class dice copied from Lecture 1 slide.
class Dice {
    private final int numFaces; //maximum face value
    private int faceValue; //current value showing on the dice
    private Random rand;

    // Constructor: Sets the initial face value.
    public Dice(int _numFaces) {
        rand = new Random();
        numFaces = _numFaces;
        roll();
    }
    // Rolls the dice
    public void roll() {
        int curr_faceValue = 1 + rand.nextInt(numFaces);
        setFaceValue(curr_faceValue);
    }
    // Face value setter/mutator.
    private void setFaceValue (int value) {
        if (value <= numFaces)
            faceValue = value;
        }

    // Face value getter/setter.
    public int getFaceValue() {
        return faceValue;
    }

    // Face value getter/setter.
    public int getNumFaces() {
        return numFaces;
    }

    // Returns a string representation of this dice
    public String toString() {
        return ("number of Faces " + numFaces +"current face value " + faceValue);
    }
} // End of Dice class

abstract class floor{

    protected int point;

    floor(){
        this.point = 0;
    }
    abstract int getLevel();
    
}
class emptyFloor extends floor{
    emptyFloor (player p){
        point = 1;
        System.out.print("\n"+p.toString()+" has reached an Empty Floor");
        p.setScore(p.getScore()+point);
    }

    @Override
    int getLevel() {
        return 0;
    }
}
class ladderFloor extends floor{
    ladderFloor(player p){
        point = 2;
        System.out.print("\n"+p.toString()+" has reached on Ladder Floor");
        p.setScore(p.getScore()+point);
    }
    @Override
    int getLevel() {
        return 4;
    }

}
class snakeFloor extends floor{
    snakeFloor(player p){
        point = -2;
        System.out.print("\n"+p.toString()+" has reached on snake Floor");
        p.setScore(p.getScore()+point);
    }
    @Override
    int getLevel() {
        return -4;
    }
}
class elevatorFloor extends floor{
    elevatorFloor(player p){
        point = 4;
        System.out.print("\n"+p.toString()+" has reached on Elevator Floor");
        p.setScore(p.getScore()+point);
    }
    @Override
    int getLevel() {
        return 8;
    }
}
class kingCobraFloor extends floor{
    kingCobraFloor(player p){
        point = -4;
        System.out.print("\n"+p.toString()+" has reached an King Cobra Floor");
        p.setScore(p.getScore()+point);
    }
    @Override
    int getLevel() {
        return -8;
    }
}


class game{

    private player p;
    private Dice d;
    private Scanner sc;
    private int level;
    private int highscore;

    game (){
        sc = new Scanner(System.in);
        System.out.print("Enter the player name and hit enter\n");
        p = new player(sc.nextLine());

        System.out.print("The game setup is ready\n");
        d = new Dice(2);
        this.level = -1;
        highscore = 0;
        play();
    }

    private void play(){

        while(true){
            nextInput();
            if (d.getFaceValue() == 1){
                break;
            }
            System.out.print("\nGame cannot start until you get 1");
        }

        this.level++;
        int changeInLevel;
        while (this.level < 13){
            System.out.print("\nPlayer position "+level);
            changeInLevel = 0;
            switch (level){
                case 2:
                    // Elevator floor
                    floor f1 = new elevatorFloor(p);
                    changeInLevel = f1.getLevel();
                    System.out.print("\nTotal points "+p.getScore());
                    break;
                case 5:
                    // Normal Snake Floor
                    floor f2 = new snakeFloor(p);
                    changeInLevel = f2.getLevel();
                    System.out.print("\nTotal points "+p.getScore());
                    break;
                case 8:
                    // Normal Ladder Floor
                    floor f3 = new ladderFloor(p);
                    changeInLevel = f3.getLevel();
                    System.out.print("\nTotal points "+p.getScore());
                    break;
                case 11:
                    // King Cobra Floor
                    floor f4 = new kingCobraFloor(p);
                    changeInLevel = f4.getLevel();
                    System.out.print("\nTotal points "+p.getScore());
                    break;
                case 12:
                    if(d.getFaceValue()==2){
                        break;
                    }
                    else{
                        floor f5 = new emptyFloor(p);
                        changeInLevel = f5.getLevel();
                        System.out.print("\nTotal points "+p.getScore());
                    }

                default:
                    // Empty Floor
                    floor f5 = new emptyFloor(p);
                    changeInLevel = f5.getLevel();
                    System.out.print("\nTotal points "+p.getScore());
            }
            if (changeInLevel == 0){
                nextInput();
                if (level + d.getFaceValue() > 13){
                    System.out.print("\nCan not move");
                    continue;
                }
                else{
                    level += d.getFaceValue();
                }
            }
            if (level + changeInLevel <= 13){
                level += changeInLevel;
            }
        }
        System.out.print("\nGame over\n" + p.toString()+" accumulated "+(p.getScore()+1)+" points");
        if (highscore < p.getScore()+1){
            System.out.print("\nCongratulations "+p.toString()+", you have set a new high score "+(p.getScore()+1));
            highscore = p.getScore()+1;
        }
        System.out.print("\nDo you want to play again?[y/n]");
        String c = sc.next();
        if (c.equals("Y")||c.equals("y")){
            System.out.println("___________________________");
            System.out.println("***************************");
            System.out.println("Starting a new game");
            System.out.println("***************************");
            level = -1;
            p.setScore(0);
            play();
        }
        else if (c.equals("N")||c.equals("n")){
            return;
        }
        else {
            System.out.print("\nInvalid Selection Exiting.....");
        }

    }
    private void nextInput(){
        System.out.print("\n===========================");
        System.out.print("\nHit enter to roll the dice ");
        sc.nextLine();
        d.roll();
        System.out.print("Dice gave "+d.getFaceValue());
    }

}
