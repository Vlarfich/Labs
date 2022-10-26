package com.epam.rd.autotasks;

import java.util.Arrays;

class CycleSwap {
    static void cycleSwap(int[] array) {
        if (array.length == 0)
            return;
        int t = array[0];
        array[0] = array[array.length - 1];
        for (int i = 1; i < array.length; i++) {
            int k = array[i];
            array[i] = t;
            t = k;
        }
    }

    static void cycleSwap(int[] array, int shift) {
        if (array.length == 0)
            return;
        if (shift >= array.length || shift <= 0)
            return;
        for (int j = 0; j < shift; j++) {
            int t = array[0];
            array[0] = array[array.length - 1];
            for (int i = 1; i < array.length; i++) {
                int k = array[i];
                array[i] = t;
                t = k;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 2, 7, 4};
        CycleSwap.cycleSwap(array, 5);
        System.out.println(Arrays.toString(array));
    }
}
