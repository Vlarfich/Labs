package com.epam.rd.autotasks;

import java.util.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Direction {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<Integer>(List.of(new Integer[]{}));
        ArrayList<Integer> b = new ArrayList<Integer>(List.of(new Integer[]{1, 2, 3}));
        merge(a, b);
        System.out.println("First array: " + a);
        System.out.println("Second array: " + b);
    }


    public static void merge(ArrayList<Integer> a, ArrayList<Integer> b) {
        if(a == null || b == null){
            //throw new NullPointerException("Null List");

            return;
        }
        int size = a.size() + b.size();
        // т.к. по условию одинаковые могли просто умножить a.size() на 2
        a.ensureCapacity(size);
        ArrayList<Integer> newArray = new ArrayList<>(size);

        int posA = 0;
        int posB = 0;
        for (int i = 0; i < size; i++) {
            if (posA >= a.size()) {
                newArray.add(b.get(posB++));
            } else if (posB >= b.size()) {
                newArray.add(a.get(posA++));
            } else if (a.get(posA) < b.get(posB)) {
                newArray.add(a.get(posA));
                posA++;
            } else {
                newArray.add(b.get(posB));
                posB++;
            }
        }
        a.clear();
        a.addAll(newArray);
    }

    // Вариант для List-ов, в параметры передаём ссылки типа List на объекты ArrayList
    //  List<Integer> a = new ArrayList<Integer>(...);
    //  так можно работать и с LinkedList
//    public static void merge(List<Integer> a, List<Integer> b) {
//        if(a == null || b == null){
//            //throw new NullPointerException("Null List");
//            return;
//        }
//        int size = a.size() + b.size();
//        List<Integer> newArray = new ArrayList<>(size);
//
//        int posA = 0;
//        int posB = 0;
//        for (int i = 0; i < size; i++) {
//            if (posA >= a.size()) {
//                newArray.add(b.get(posB++));
//            } else if (posB >= b.size()) {
//                newArray.add(a.get(posA++));
//            } else if (a.get(posA) < b.get(posB)) {
//                newArray.add(a.get(posA));
//                posA++;
//            } else {
//                newArray.add(b.get(posB));
//                posB++;
//            }
//        }
//        a.clear();
//        a.addAll(newArray);
//    }

    private static List<Integer> createIntegerList() {
        HashMap<Integer, String> m;
        Hashtable<Integer, String> t;


        List<Integer> result = new ArrayList<>();
        Random random = new Random();
        int minSize = 3;
        int value = 0;
        for (int i = 0; i < Math.max(minSize, random.nextInt(20)); i++) {
            value += random.nextInt(10);
            result.add(value);
        }
        System.out.println("List created. Size = " + result.size() + ". List: " + result + '\n');
        Collections.sort(result);
        return result;
    }

    enum planet{
        Mars("Mars"), Pluto("Pluto");
        String name;
        planet(String name) {
            this.name = name;
        }
    }

}

