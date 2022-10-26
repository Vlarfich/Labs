package com.epam.rd.autotasks.snail;

import java.util.*;

public class Snail {
    public static void main(String[] args) {
        //Write a program that reads a,b and h (line by lyne in this order) and prints
        //the number of days for which the snail reach the top of the tree.
        //a - feet that snail travels up each day, b - feet that slides down each night, h - height of the tree
        Scanner sc = new Scanner(System.in);
        int a, b, h;
        a = sc.nextInt();
        b = sc.nextInt();
        h = sc.nextInt();
        if (a >= h) {
            System.out.println(1);
        } else if (b >= a) {
            System.out.println("Impossible");
        } else {
            int count = 0;
            while (true) {
                h -= a;
                count++;
                if (h <= 0)
                    break;
                h += b;
            }
            System.out.println(count);
        }
    }
}
