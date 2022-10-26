package com.epam.rd.autotasks.figures;

class Circle extends Figure {

    private Point c;
    private double r;

    public Circle(Point c, double r) throws IllegalArgumentException {
        if (c == null)
            throw new IllegalArgumentException();
        if (r <= 0)
            throw new IllegalArgumentException();
        this.c = c;
        this.r = r;
    }

    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public Point centroid() {
        return c;
    }

    @Override
    public boolean isTheSame(Figure f) {
        if (f.getClass().getSimpleName() != Circle.class.getSimpleName())
            return false;
        return c.equals(((Circle) f).c) && Math.abs(r - ((Circle) f).r) < Math.pow(10, -6);
    }


}
