package com.epam.rd.autotasks;

import java.util.Scanner;

public class Average {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Use Scanner methods to read input
        int amount = 0;
        int t, sum = 0;
        while ((t = scanner.nextInt()) != 0){
            amount++;
            sum += t;
        }
        System.out.println(sum / amount);

    }

}