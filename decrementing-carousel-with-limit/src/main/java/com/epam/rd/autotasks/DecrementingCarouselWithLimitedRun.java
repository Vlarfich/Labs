package com.epam.rd.autotasks;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DecrementingCarouselWithLimitedRun extends DecrementingCarousel{
    public DecrementingCarouselWithLimitedRun(final int capacity, final int actionLimit) {
        super(capacity);
        lim = actionLimit;
    }

    public static void main(String[] args) {
        for(int i = 0; i < args.length; i++){
            System.out.println(" *   " + args[i]);
        }
    }
}
