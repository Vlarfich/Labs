package com.epam.rd.autotasks.meetstrangers;

import java.io.IOException;
import java.util.*;

public class HelloStrangers {
    public static void main(String[] args) throws IOException {
        //Write a program, asks for a number - amount of strangers to meet.
        //Then reads stranger names line by line and prints line by line "Hello, ...".
        Scanner sc = new Scanner(System.in);
        int amount = sc.nextInt();
        sc.nextLine();
        if(amount == 0){
            System.out.println("Oh, it looks like there is no one here");
        }
        if(amount < 0){
            System.out.println("Seriously? Why so negative?");
        }
        String t;
        for(int i = 0; i < amount; i++){
            t = sc.nextLine();
            System.out.println("Hello, " + t);
        }
    }
}
