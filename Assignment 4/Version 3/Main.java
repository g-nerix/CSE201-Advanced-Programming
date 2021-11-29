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
    private String name;
    toy(String n){
        name = n;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String getName() {
        return name;
    }
}
class tile{
    private int number;
    private toy t;

    tile(String name, int num){
        this.number = num;
        this.t = new toy(name);
    }
    public toy getT() {
        return t;
    }
}
class game{
    private int chance;
    private int maxChance;
    private Scanner sc;
    private bucket b;
    private int currentPosition;

    private ArrayList <tile>floor;

    game(){
        this.chance = 0;
        this.maxChance = 5;
        this.currentPosition = 0;
        this.b = new bucket();
        this.sc = new Scanner(System.in);
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
        int i = rn.nextInt(24);
        if (i<0) {
            return -i+1;
        }
        return i+1;
    }
    private int generateRandomNumber(){
        Random r = new Random();
        int a = r.nextInt(19);
        return a+1;
    }

    private String generateRandomString(){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";
        String output ="";
        Random r = new Random();

        for(int i = 0; i < 4; i++){
            int n = r.nextInt(str.length());
            output = output.concat(str.substring(n,n+1));
        }
        return output;
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

            if (currentPosition > 20){
                System.out.println("You are too energetic and zoomed past all the tiles. Muddy Puddle Splash!");
            }
            else{
                System.out.println("You landed on tile "+currentPosition);

                if (this.currentPosition % 2 == 0) {
                    b.addToBucket(floor.get(currentPosition).getT());
                    System.out.println("You won a "+floor.get(currentPosition).getT().getName()+" soft toy!!");
                }
                else{
                    System.out.println("Question answer round. Integer or strings?");
                    String input = sc.next();
                    if (input.equals("Integer")||input.equals("integer")){
                        calculator<Integer>c = new calculator<Integer>();
                        int a = generateRandomNumber();
                        int b = generateRandomNumber();
                        System.out.println("Calculate the result of "+a*b+" divided by "+b);
                        try{
                            Integer ans = sc.nextInt();
                            if (ans == c.calculate(a*b,b)){
                                System.out.println("Correct answer");
                                System.out.println("You won a "+floor.get(currentPosition).getT().getName()+" soft toy!!");
                            }
                            else{
                                System.out.println("Wrong answer");
                                System.out.println("You did not win any soft toy");
                            }
                        }
                        catch (java.util.InputMismatchException e1){
                            System.out.println("EXCEPTION : Invalid Input type");
                        }
                        catch (Exception e2){
                            System.out.println("Some Exception encountered...");
                        }

                    }
                    else if (input.equals("String")||input.equals("string")){
                        calculator<String> c = new calculator<String>();

                        String s1 = generateRandomString();
                        String s2 = generateRandomString();

                        System.out.println("Calculate the concatenation of strings "+s1+" and "+s2);
                        String s3 = sc.next();

                        if (s3.equals(c.calculate(s1,s2))){
                            System.out.println("Correct answer");
                            System.out.println("You won a "+floor.get(currentPosition).getT().getName()+" soft toy!!");
                        }
                        else{
                            System.out.println("Wrong answer");
                            System.out.println("You did not win any soft toy");
                        }
                    }
                    else{
                        System.out.println("Please enter a valid option [Interer/String]");
                    }
                }
            }
        }

        System.out.println("---------------------------------------");
        System.out.println("-------------GAME OVER-----------------");
        System.out.println("---------------------------------------");
        System.out.println("Soft toys won by you are:");
        for (int i = 0; i < b.getBucketElement().size(); i++){
            System.out.println("\t->"+b.getBucketElement().get(i).getName());
        }
    }

}

class calculator<t> {
    public Object calculate(Object o1, Object o2) {
        if(o1 instanceof Integer && o2 instanceof Integer){
            int output = 0;
            try {
                output = ((Integer)o1/(Integer)o2);
            }
            catch(ArithmeticException e){
                System.out.println("Exception : Divide by Zero not allowed");
            }
            return output;
        }
        else if(o1 instanceof String && o2 instanceof String){
            String s1=(String)o1;
            String s2=(String)o2;
            return s1.concat(s2);
        }
        return null;
    }
}

class bucket {
    private ArrayList <toy>bucketElement =new ArrayList<toy>();

    public void addToBucket (toy t1) throws CloneNotSupportedException {
        toy t = (toy)t1.clone();
        this.bucketElement.add(t);
    }
    public ArrayList<toy> getBucketElement() {
        return bucketElement;
    }
}
