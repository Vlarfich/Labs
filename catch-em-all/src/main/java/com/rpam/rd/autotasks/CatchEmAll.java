package com.rpam.rd.autotasks;

import java.io.FileNotFoundException;
import java.io.IOException;

interface it{
    default void doAction(){

    };
    static void haha(){};
    void hi();
}

public class CatchEmAll {

    //You may set another exception in this field;
    static Exception exception = new FileNotFoundException();

    public static void riskyMethod() throws Exception {
        throw exception;
    }
    abstract public void nono(){

    };

    public static void main(String[] args) throws Exception {

    }
}
