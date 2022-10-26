package com.epam.rd.autotasks.figures;

class Quadrilateral extends Figure {

    private Point a, b, c, d;

    public Quadrilateral(Point a, Point b, Point c, Point d) throws IllegalArgumentException {
        if (a == null || b == null || c == null || d == null)
            throw new IllegalArgumentException();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        //Point mid = centroid();
        Point mid = (new Segment(a, c)).intersection(new Segment(b, d));
        if (mid == null)
            throw new IllegalArgumentException();
        if (!Triangle.checkTriangle(a, b, mid) ||
                !Triangle.checkTriangle(b, c, mid) ||
                !Triangle.checkTriangle(c, d, mid) ||
                !Triangle.checkTriangle(a, d, mid))
            throw new IllegalArgumentException();
        if (area() == 0)
            throw new IllegalArgumentException();
        if(centroid() == null)
            throw new IllegalArgumentException();

    }

    @Override
    public Point centroid() {
        Point first = (new Triangle(a, b, c)).centroid();
        Point second = (new Triangle(a, d, c)).centroid();
        Segment s1 = new Segment(first, second);

        Point third = (new Triangle(b, c, d)).centroid();
        Point forth = (new Triangle(a, b, d)).centroid();
        Segment s2 = new Segment(third, forth);

        return s1.intersection(s2);
    }

    @Override
    public double area() {
        Triangle f = new Triangle(a, b, c);
        Triangle t = new Triangle(a, d, c);
        return f.area() + t.area();
    }

    @Override
    public boolean isTheSame(Figure f) {
        if (f.getClass() != Quadrilateral.class)
            return false;
        if (!a.equals(((Quadrilateral) f).a) && !a.equals(((Quadrilateral) f).b) &&
                !a.equals(((Quadrilateral) f).c) && !a.equals(((Quadrilateral) f).d))
            return false;
        if (!b.equals(((Quadrilateral) f).b) && !b.equals(((Quadrilateral) f).c)
                && !b.equals(((Quadrilateral) f).d) && !b.equals(((Quadrilateral) f).a))
            return false;
        if (!c.equals(((Quadrilateral) f).c) && !c.equals(((Quadrilateral) f).d)
                && !c.equals(((Quadrilateral) f).a) && !c.equals(((Quadrilateral) f).b))
            return false;
        if (!d.equals(((Quadrilateral) f).d) && !d.equals(((Quadrilateral) f).c)
                && !d.equals(((Quadrilateral) f).b) && !d.equals(((Quadrilateral) f).a))
            return false;
        return true;
    }

    private class Segment {
        Point s;
        Point e;

        public Segment(Point start, Point end) {
            if (start.getX() == end.getX() && start.getY() == end.getY()) {
                throw new IllegalArgumentException();
            }
            s = start;
            e = end;
        }

        Point intersection(Segment another) {
            double x, y;
            double k1, b1;
            double c1 = (e.getX() - s.getX()) / (e.getY() - s.getY());
            double k2, b2;
            double c2 = (another.e.getX() - another.s.getX()) / (another.e.getY() - another.s.getY());
            if (c1 == c2)
                return null;
            if (c1 == 0) {
                x = this.s.getX();
                k2 = 1 / c2;
                b2 = another.s.getY() - another.s.getX() / c2;
                y = k2 * x + b2;
            } else if (c2 == 0) {
                x = another.s.getX();
                k1 = 1 / c1;
                b1 = this.s.getY() - this.s.getX() / c1;
                y = k1 * x + b1;
            } else {
                k1 = 1 / c1;
                b1 = s.getY() - s.getX() / c1;

                k2 = 1 / c2;
                b2 = another.s.getY() - another.s.getX() / c2;


                x = (b2 - b1) / (k1 - k2);
                y = k1 * x + b1;
            }

            if ((x < Math.min(s.getX(), e.getX()) || x > Math.max(s.getX(), e.getX()) ||
                    y < Math.min(s.getY(), e.getY()) || y > Math.max(s.getY(), e.getY()))) {
                return null;
            }
            if ((x < Math.min(another.s.getX(), another.e.getX()) || x > Math.max(another.s.getX(), another.e.getX()) ||
                    y < Math.min(another.s.getY(), another.e.getY()) || y > Math.max(another.s.getY(), another.e.getY()))) {
                return null;
            }

            return new Point(x, y);
        }
    }

}

