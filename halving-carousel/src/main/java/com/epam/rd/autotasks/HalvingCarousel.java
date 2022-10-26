package com.epam.rd.autotasks;

public class HalvingCarousel extends DecrementingCarousel {

    public HalvingCarousel(final int capacity) {
        super(capacity);
    }

    @Override
    public CarouselRun run(){
        if (!running) {
            running = true;
            CarouselRun r = new CarouselRun(mas, cap,true);
            return r;
        }
        else
            return null;
    }

}
