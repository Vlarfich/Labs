package com.epam.rd.autotasks.figures;

class Triangle extends Figure {
    private Point a, b, c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double area() {
        double p = (a.getLength(b) + a.getLength(c) + b.getLength(c) ) / 2;
        return Math.sqrt(p * (p - a.getLength(b)) * (p - a.getLength(c)) * (p - b.getLength(c)));
    }

    @Override
    public String pointsToString() {
        return "" + a + b + c;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Point leftmostPoint() {
        Point left = a;
        left = left.getX() < b.getX() ? left : b;
        left = left.getX() < c.getX() ? left : c;

        return left;
    }
}
