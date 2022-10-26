package com.epam.rd.autotasks;

public class CarouselRun {
    int mas[];
    int cap = 0;
    int pos = 0;

    private int decrease = 1;

    boolean grad = false;

    public CarouselRun(int m[], int c, boolean g) {
        mas = m;
        cap = c;
        grad = g;
    }

    public int next() {
        int res = -1;
        for (int i = 0; i < cap; i++) {
            if (mas[pos] > 0) {
                res = mas[pos];
                mas[pos] -= decrease;
                pos++;
                if (pos >= cap) {
                    pos = 0;
                    if (grad)
                        decrease++;
                }
                break;
            }
            pos++;
            if (pos >= cap) {
                pos = 0;
                if (grad)
                    decrease++;
            }
        }

        return res;
    }

    public boolean isFinished() {
        for (int i = 0; i < cap; i++) {
            if (mas[i] > 0)
                return false;
        }
        return true;
    }

}
