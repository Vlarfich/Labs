package com.epam.rd.autotasks.sequence;
import java.util.Scanner;

public class FindMaxInSeq {
    public static int max() {
        Scanner sc = new Scanner(System.in);
        int t, max = - Integer.MAX_VALUE;
        while(true){
            if((t = sc.nextInt()) == 0)
                break;
            max = Math.max(max, t);
        }
        // Put your code here

        return max;
    }

    public static void main(String[] args) {

        System.out.println("Test your code here!\n");

        // Get a result of your code

        System.out.println(max());
    }
}
