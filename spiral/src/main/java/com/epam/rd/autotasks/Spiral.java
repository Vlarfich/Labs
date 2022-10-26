package com.epam.rd.autotasks;

class Spiral {
    static int[][] spiral(int rows, int columns) {
        int[][] mas = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mas[i][j] = 0;
            }
        }
        int horizontal = columns, vertical = rows;
        int count = 1;

        while (count <= rows * columns) {
            for (int j = columns - horizontal; j < horizontal; j++) {
                mas[rows - vertical][j] = count++;
            }
            horizontal--;
            count--;
            for (int i = rows - vertical; i < vertical; i++) {
                mas[i][horizontal] = count++;
            }
            vertical--;
            count--;
            for (int j = horizontal; j >= columns - horizontal; j--) {
                mas[vertical][j] = count++;
            }

            for (int i = vertical; i >= rows - vertical; i--) {
                mas[i][columns - horizontal - 1] = count++;
            }
        }


        return mas;
    }

}
