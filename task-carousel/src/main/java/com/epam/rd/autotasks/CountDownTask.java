package com.epam.rd.autotasks;

public class CountDownTask implements Task {


    private int countDown;
    private boolean finished = false;

    public CountDownTask(int value) {
        countDown = Math.max(value, 0);
        finished = countDown == 0 ? true : false;
    }

    public int getValue() {
        if(countDown <= 0 )
            return 0;
        return countDown;
    }


    @Override
    public void execute() {
        countDown--;
        if (countDown <= 0)
            finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
