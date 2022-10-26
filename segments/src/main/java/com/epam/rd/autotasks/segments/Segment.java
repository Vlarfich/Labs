package com.epam.rd.autotasks.segments;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.pow;

class Segment {
    Point s;
    Point e;

    public Segment(Point start, Point end) {
        if (start.getX() == end.getX() && start.getY() == end.getY()) {
            throw new IllegalArgumentException();
        }
        s = start;
        e = end;
    }

    double length() {
        return Math.sqrt(Math.abs(s.getX() - e.getX()) * Math.abs(s.getX() - e.getX()) +
                Math.abs(s.getY() - e.getY()) * Math.abs(s.getY() - e.getY()));
    }

    Point middle() {
        double x = (e.getX() + s.getX()) / 2;
        double y = (e.getY() + s.getY()) / 2;
        return new Point(x, y);
    }

    Point intersection(Segment another) {
        double k, b;
        double c = (e.getX() - s.getX()) / (e.getY() - s.getY());
        k = 1 / c;
        b = s.getY() - s.getX() / c;

        double k2, b2;
        c =(another.e.getX() - another.s.getX()) / (another.e.getY() - another.s.getY());
        k2 = 1 / c;
        b2 = another.s.getY() - another.s.getX() / c;

        if(k == k2){
            return null;
        }
        double x = (b2 - b) / (k - k2);
        double y = k * x + b;

        if(x < Math.min(s.getX(), e.getX()) || x > Math.max(s.getX(), e.getX()) ||
                y < Math.min(s.getY(), e.getY()) || y > Math.max(s.getY(), e.getY())){
            return null;
        }
        if(x < Math.min(another.s.getX(), another.e.getX()) || x > Math.max(another.s.getX(), another.e.getX()) ||
                y < Math.min(another.s.getY(), another.e.getY()) || y > Math.max(another.s.getY(), another.e.getY())){
            return null;
        }
        return new Point( x, y);
    }

    public static void main(String[] args){
        Segment first = new Segment(new Point(0, 0), new Point(4, 4));
        Segment second = new Segment(new Point(2, 0), new Point(0, 2));
        Point intersection = first.intersection(second);

        System.out.println(intersection.getX());
        System.out.println(intersection.getY());
    }

}
