package com.epam.rd.autotasks;

public class CompleteByRequestTask implements Task {

    private boolean finished = false;
    private boolean check;
    @Override
    public void execute() {
        if(check)
            finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public void complete() {
        check = true;
    }

}
