package com.epam.rd.autotasks.meetanagent;
import java.util.*;
public class MeetAnAgent {
    final static int PASSWORD = 133976; //You can change pass, but don't change the type

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int in = sc.nextInt();
        if(in == PASSWORD){
            System.out.println("Hello, Agent");
        }
        else{
            System.out.println("Access denied");
        }


    }
}
