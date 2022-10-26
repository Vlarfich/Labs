package com.epam.rd.autotasks.house;

import com.epam.rd.autotasks.residents.cats.Cat;
import com.epam.rd.autotasks.residents.cats.Kitten;
import com.epam.rd.autotasks.residents.dogs.Dog;
import com.epam.rd.autotasks.residents.dogs.Puppy;

import java.util.ArrayList;
import java.util.List;

public class House<T> {

    public void enter(T resident) {
        if (residents.isEmpty()) {
            residents.add(resident);
            return;
        }
        else {
            if(resident instanceof Cat && residents.get(0) instanceof Cat){
                residents.add(resident);
                return;
            }
            if(resident instanceof Dog && residents.get(0) instanceof Dog){
                residents.add(resident);
                return;
            }
        }
    }

    private final List<T> residents = new ArrayList<T>();


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("There are following residents in the house:\n");
        for (Object resident : residents) {
            builder.append(resident.toString()).append("\n");
        }
        return builder.toString();
    }
}
