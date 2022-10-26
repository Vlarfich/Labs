package com.epam.rd.autotasks.figures;

class Quadrilateral extends Figure {

    private Point a, b, c, d;

    public Quadrilateral(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double area() {
        Triangle f = new Triangle(a, b, c);
        Triangle t = new Triangle(a, d, c);
        return f.area() + t.area();
    }

    @Override
    public String pointsToString() {
        return "" + a + b + c + d;
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
        left = left.getX() < d.getX() ? left : d;

        return left;
    }
}
