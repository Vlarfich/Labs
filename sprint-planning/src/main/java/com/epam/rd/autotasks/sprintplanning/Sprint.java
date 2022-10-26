package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Sprint {

    private int capacity;
    private int ticketsLimit;
    private int currentCapacity = 0;

    private Ticket[] tickets;
    private int pos = 0;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        tickets = new Ticket[ticketsLimit];
    }

    private boolean checkDependencies(UserStory userStory) {
        for (UserStory us : userStory.getDependencies()) {
            boolean is = false;
            if (!us.isCompleted()) {
                for (int i = 0; i < pos; i++) {
                    if (tickets[i].equals(us)) {
                        is = true;
                        break;
                    }
                }
            }else is = true;
            if (!is)
                return false;
        }
        return true;
    }

    public boolean addUserStory(UserStory userStory) {
        if (userStory == null ||
                userStory.getEstimate() > capacity ||
                userStory.isCompleted() ||
                currentCapacity + userStory.getEstimate() > capacity ||
                pos >= ticketsLimit ||
                !checkDependencies(userStory))
            return false;

        tickets[pos] = userStory;
        currentCapacity += userStory.getEstimate();
        pos++;

        return true;
    }

    public boolean addBug(Bug bugReport) {
        if (bugReport == null ||
                bugReport.getEstimate() > capacity ||
                bugReport.isCompleted() ||
                currentCapacity + bugReport.getEstimate() > capacity ||
                pos >= ticketsLimit)
            return false;

        tickets[pos] = bugReport;
        currentCapacity += bugReport.getEstimate();
        pos++;

        return true;
    }

    public Ticket[] getTickets() {
        Ticket[] res = Arrays.copyOf(tickets, pos);
        return res;
    }

    public int getTotalEstimate() {
        int sum = 0;
        for (int i = 0; i < pos; i++) {
            sum += tickets[i].getEstimate();
        }
        return sum;
    }


    public static void main(String[] args) {
        int nextId = 1;
        Sprint sprint = new Sprint(40, 30);

        UserStory us1 = new UserStory(nextId++, "User Registration Entity", 8);
        UserStory us2 = new UserStory(nextId++, "User Registration Form", 8);

        us1.complete();

        Bug bug1 = Bug.createBug(nextId++, "Bug 1", 4, us1);
        Bug bug2 = Bug.createBug(nextId++, "Bug 2", 4, us1);

        bug1.complete();

        assertFalse(sprint.addUserStory(null));
        assertFalse(sprint.addUserStory(us1));
        assertFalse(sprint.addBug(null));
        assertFalse(sprint.addBug(bug1));

        assertTrue(sprint.addUserStory(us2));
        assertTrue(sprint.addBug(bug2));
    }
}
