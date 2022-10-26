package com.epam.rd.autotasks;

public class CarouselRun {
    int mas[];
    int cap = 0;
    int pos = 0;

    boolean halving = false;

    public CarouselRun(int m[], int c, boolean h) {
        mas = m;
        cap = c;
        halving = h;
    }

    public int next() {
        int res = -1;
        for (int i = 0; i < cap; i++) {
            if (mas[pos] > 0) {
                res = mas[pos];
                if (!halving)
                    mas[pos]--;
                else
                    mas[pos] /= 2;
                pos++;
                if (pos >= cap)
                    pos = 0;
                break;
            }
            pos++;
            if (pos >= cap)
                pos = 0;
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
