package com.epam.rd.autotasks.arrays;

import java.util.Arrays;

public class LocalMaximaRemove {

    public static void main(String[] args) {
        int[] array = new int[]{18, 1, 3, 6, 7, -5};

        System.out.println(Arrays.toString(removeLocalMaxima(array)));
    }

    public static int[] removeLocalMaxima(int[] array) {
        int pos = 0;
        int[] res = new int[array.length];
        if (!(array[0] > array[1])) {
            res[pos] = array[0];
            pos++;
        }
        for (int i = 1; i < array.length - 1; i++) {
            if (!((array[i - 1] < array[i]) && (array[i] > array[i + 1]))) {
                res[pos] = array[i];
                pos++;
            }
        }

        if (!(array[array.length - 2] < array[array.length - 1])) {
            res[pos] = array[array.length - 1];
            pos++;
        }
        return Arrays.copyOf(res, pos);
    }
}
