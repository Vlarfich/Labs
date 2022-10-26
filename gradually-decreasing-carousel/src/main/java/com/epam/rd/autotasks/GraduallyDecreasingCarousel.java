package com.epam.rd.autotasks;

public class GraduallyDecreasingCarousel extends DecrementingCarousel{
    public GraduallyDecreasingCarousel(final int capacity) {
        super(capacity);
    }

    @Override
    public CarouselRun run() {
        if (!running) {
            running = true;
            CarouselRun r = new CarouselRun(mas, cap, true);
            return r;
        }
        else
            return null;
    }
}
