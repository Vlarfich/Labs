package com.epam.rd.autotasks.sprintplanning.tickets;

import java.util.Arrays;

public class UserStory extends Ticket {

    UserStory[] mas;

    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        mas = dependsOn;
    }

    @Override
    public void complete() {
        for(UserStory e : mas){
            if(!e.isCompleted())
                return;
        }
        super.complete();
    }

    public UserStory[] getDependencies() {
        UserStory[] res = Arrays.copyOf(mas, mas.length);
        return res;
    }

    @Override
    public String toString() {
        return "[US " + getId() + "] " + getName();
    }
}
