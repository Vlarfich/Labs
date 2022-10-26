package com.epam.rd.autotasks;

import java.util.ArrayList;

public class TaskCarousel {

    private int capacity;
    private int current = 0;
    private ArrayList<Task> tasks;
    private int pos = 0;

    public TaskCarousel(int capacity) {
        capacity = Math.max(0, capacity);
        this.capacity = capacity;
        tasks = new ArrayList<>(capacity);
    }

    public boolean addTask(Task task) {
        if (task == null ||
                task.isFinished() ||
                pos == capacity)
            return false;

        tasks.add(task);
        pos++;

        return true;
    }

    public boolean execute() {
        if(tasks.isEmpty())
            return false;
        tasks.get(current).execute();
        if(tasks.get(current).isFinished()) {
            tasks.remove(current);
            current--;
            pos--;
        }

        current++;
        if(current>=pos)
            current = 0;
        return true;
    }

    public boolean isFull() {
        return pos == capacity;
    }

    public boolean isEmpty() {
        return pos == 0;
    }


    public static void main(String[] args) {
        TaskCarousel carousel = new TaskCarousel(3);

        CountDownTask task1 = new CountDownTask(2);
        CountDownTask task2 = new CountDownTask(2);
        CompleteByRequestTask task3 = new CompleteByRequestTask();

        System.out.println(carousel.addTask(task1)); //true
        System.out.println(carousel.addTask(task2)); //true
        System.out.println(carousel.addTask(task3)); //true

        System.out.println(carousel.isFull()); // true

        for(int i = 0; i < 100; i++){
            System.out.println(carousel.execute()); // true
        }

        System.out.println(task1.isFinished()); // true
        System.out.println(task2.isFinished()); // true
        System.out.println(task3.isFinished()); // false

        task3.complete();

        System.out.println(task3.isFinished()); // false
        System.out.println(carousel.execute()); // true
        System.out.println(task3.isFinished()); // true

        System.out.println(carousel.isEmpty()); // true
    }

}
