package com.epam.rd.autotasks.meetautocode;

import java.util.Date;
import java.util.Scanner;
import java.text.*;

public class ElectronicWatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int seconds = scanner.nextInt();

        seconds %= 86400;

        int hours = seconds / 3600;
        seconds -= hours * 3600;
        int minutes = seconds / 60;
        seconds -= minutes * 60;

        String res = String.format("%d:%02d:%02d",hours,minutes,seconds);

        System.out.println(res);


    }
}
