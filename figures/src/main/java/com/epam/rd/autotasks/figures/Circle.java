package com.epam.rd.autotasks.figures;

class Circle  extends Figure{

    private Point c;
    private double r;

    public Circle(Point c, double r){
        this.c = c;
        this.r = r;
    }


    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public String pointsToString(){
        return c.toString();
    }
    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "[" + pointsToString() + r + "]";
    }
    @Override
    public Point leftmostPoint(){

        return new Point(c.getX() - r,c.getY());
    }
}
