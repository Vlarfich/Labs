package com.epam.rd.autotasks;

public class CarouselRun {
    int mas[];
    int cap = 0;
    int pos = 0;

    private int limit = 0;

    public CarouselRun(int m[], int c, int l) {
        mas = m;
        cap = c;
        limit = l;
    }

    public int next() {
        if (limit != -1 && limit == 0)
            return -1;
        int res = -1;
        for (int i = 0; i < cap; i++) {
            if (mas[pos] > 0) {
                res = mas[pos];
                mas[pos]--;
                pos++;
                if (pos >= cap)
                    pos = 0;
                break;
            }
            pos++;
            if (pos >= cap)
                pos = 0;
        }
        if (limit != -1)
            limit--;

        return res;
    }

    public boolean isFinished() {
        if (limit <= 0 && limit != -1)
            return true;
        for (int i = 0; i < cap; i++) {
            if (mas[i] > 0)
                return false;
        }
        return true;
    }

}
