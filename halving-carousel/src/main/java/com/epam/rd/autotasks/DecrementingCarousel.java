package com.epam.rd.autotasks;

public class DecrementingCarousel {
    protected int cap = 0;
    int mas[];
    int pos = 0;
    boolean running = false;

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
            CarouselRun r = new CarouselRun(mas, cap,false);
            return r;
        }
        else
            return null;
    }
}
