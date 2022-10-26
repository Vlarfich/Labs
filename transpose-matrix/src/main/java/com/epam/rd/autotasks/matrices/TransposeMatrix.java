package com.epam.rd.autotasks.matrices;

import java.util.Arrays;

public class TransposeMatrix {
    public static int[][] transpose(int[][] matrix) {
        int[][] mas = new int[matrix[0].length][matrix.length];
        for(int i = 0; i < mas.length; i++){
            for(int j = 0; j <mas[i].length; j++){
                mas[i][j] = matrix[j][i];
            }
        }

        return mas;
    }

    public static void main(String[] args) {

        System.out.println("Test your code here!\n");

        // Get a result of your code

        int[][] a = { {-4, -65, 56}, {78, -13, 32} };

        int[][] result = transpose(a);
        System.out.println(Arrays.deepToString(result).replace("],", "]\n"));
    }

}
