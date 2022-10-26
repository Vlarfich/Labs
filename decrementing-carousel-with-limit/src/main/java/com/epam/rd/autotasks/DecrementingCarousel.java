package com.epam.rd.autotasks;

public class DecrementingCarousel {
    private int cap = 0;
    int mas[];
    int pos = 0;
    boolean running = false;

    int lim = -1;

    public DecrementingCarousel(int capacity) {
        cap = capacity;
        mas = new int[cap];
    }

    public boolean addElement(int element) {
        if (!running && pos < cap && element > 0) {
            mas[pos++] = element;
            return true;
        }
        return false;
    }

    public CarouselRun run() {
        if (!running) {
            running = true;
            CarouselRun r = new CarouselRun(mas, cap, lim);
            return r;
        } else
            return null;
    }


}
