package com.epam.rd.autotasks.godutch;

import java.util.*;

public class GoDutch {

    public static void main(String[] args) {
        //Write code here
        Scanner sc = new Scanner(System.in);
        int bill = sc.nextInt();
        if (bill < 0) {
            System.out.println("Bill total amount cannot be negative");
        } else {
            int friends = sc.nextInt();
            if (friends <= 0) {
                System.out.println("Number of friends cannot be negative or zero");
            } else {
                if (bill == 0) {
                    System.out.println(0);
                } else {
                    bill *= 1.1;
                    System.out.println(bill / friends);
                }
            }
        }
    }
}
