package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException{
        game g = new game();
        g.play();
    }
}
class toy implements Cloneable{
    String name;
    toy(String n){
        name = n;
    }
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
class tile{
    int number;
    toy t;

    tile(String name, int num){
        this.number = num;
        this.t = new toy(name);
    }
}
class game{
    private int chance;
    private int maxChance;
    private Scanner sc;
    private bucket b;
    private int currentPosition;
    private calculator c;

    private ArrayList <tile>floor;

    game(){
        this.chance = 0;
        this.maxChance = 5;
        this.currentPosition = 0;
        this.b = new bucket();
        this.sc = new Scanner(System.in);
        this.c = new calculator();
        this.floor = new ArrayList<tile>(20);

        this.floor.add(new tile("Mickey Mouse",1));
        this.floor.add(new tile("Mini Mouse",2));
        this.floor.add(new tile("Tom",3));
        this.floor.add(new tile("Jerry",4));
        this.floor.add(new tile("Donald Duck",5));
        this.floor.add(new tile("Daffy Duck",6));
        this.floor.add(new tile("Dennis the Menace",7));
        this.floor.add(new tile("Garfield",8));
        this.floor.add(new tile("Bugs Bunny",9));
        this.floor.add(new tile("Betty Boop",10));
        this.floor.add(new tile("Charlie Brown",11));
        this.floor.add(new tile("Scooby-Doo",12));
        this.floor.add(new tile("Popeye",13));
        this.floor.add(new tile("Snoopy",14));
        this.floor.add(new tile("Minions",15));
        this.floor.add(new tile("Teddy",16));
        this.floor.add(new tile("Mr. Bean",17));
        this.floor.add(new tile("Noddy",18));
        this.floor.add(new tile("Peppa Pig",19));
        this.floor.add(new tile("Johnny Bravo",20));
    }
    private int jump(){
        Random rn = new Random();
        int i = rn.nextInt() % 19;
        if (i<0) {
            return -i+1;
        }
        return i+1;
    }
    public void play()throws CloneNotSupportedException{

        System.out.print("Hit enter to initialize the game...");
        sc.nextLine();
        System.out.println("Game is ready...");


        while(chance<maxChance){
            chance++;
            System.out.println("---------------------------------------");
            System.out.println("Hit enter to hop");
            sc.nextLine();
            System.out.println();
            this.currentPosition = jump();
            if (currentPosition == 0){
                System.out.println("You are too energetic and zoomed past all the tiles. Muddy Puddle Splash!");
            }
            else{
                System.out.println("You landed on tile "+currentPosition);

                if (this.currentPosition % 2 == 0) {
                    b.addToBucket(floor.get(currentPosition).t);
//                    System.out.print("\n");
                    System.out.println("You won a "+floor.get(currentPosition).t.name+" soft toy!!");
                }
                else{
                    if(c.ask()){
                        b.addToBucket(floor.get(currentPosition).t);
//                        System.out.print("\n");
                        System.out.println("Correct answer");
                        System.out.println("You won a "+floor.get(currentPosition).t.name+" soft toy!!");
                    }
                    else {
                        System.out.println("Wrong answer");
                        System.out.println("You did not win any soft toy");
                    }
                }
            }


        }
        System.out.println("---------------------------------------");
        System.out.println("-------------GAME OVER-----------------");
        System.out.println("---------------------------------------");
        System.out.println("Soft toys won by you are:");
        for (int i = 0; i < b.bucketElement.size(); i++){
            System.out.println("->"+b.bucketElement.get(i).name);
        }
    }
}
class calculator{
    Scanner sc;
    String input;
    Random rn;
    int a;
    int b;
    int ans;
    String a1;
    String b1;
    String ans1;

    calculator(){
        this.sc = new Scanner(System.in);
        this.rn = new Random();
        this.input = "";

    }
    private void generateRandomNumber(){
        a = rn.nextInt() % 50;
        b = rn.nextInt() % 20;
        ans = 0;
        if(a<0){
            a=a*(-1);
        }
        if(b<0){
            b=b*(-1);
        }
        a++;
        b++;
    }
    private String generateRandomString(){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";
        StringBuilder s1 = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            int index = (int)(52 * Math.random());
            s1.append(str.charAt(index));
        }
        return s1.toString();
    }
    public boolean ask(){
        System.out.println("Question answer round. Integer or strings?");
        input = sc.next();
        if (input.equals("Integer")||input.equals("integer")){
            generateRandomNumber();
            System.out.println("Calculate the result of "+a*b+" divided by "+b);
            try{
                ans = sc.nextInt();
                if (ans == a){

                    return true;
                }
                else{
                    return false;
                }
            }
            catch (java.util.InputMismatchException e1){
                System.out.println("EXCEPTION : Invalid Input type");
            }
            catch (Exception e2){
                System.out.println("Some Exception encountered...");
            }
            return false;
        }
        else if (input.equals("String")||input.equals("string")){
            a1 = generateRandomString();
            b1 = generateRandomString();
            System.out.println("Calculate the concatenation of strings "+a1+" and "+b1);
            ans1 = sc.next();
            if (ans1.equals(a1+b1)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            System.out.println("Please enter a valid option [Interer/String]");
            ask();
        }
        return false;
    }
}
class bucket {
    ArrayList <toy>bucketElement =new ArrayList<toy>();

    public void bucket(){
//        this.bucketElement = new ArrayList<toy>(5);
    }

    public void addToBucket (toy t1) throws CloneNotSupportedException {
        toy t = (toy)t1.clone();
//        System.out.println(t.name);
        this.bucketElement.add(t);
    }
}
