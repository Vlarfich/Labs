package com.epam.rd.autotasks.figures;

class Triangle extends Figure {
    private Point a, b, c;

    public Triangle(Point a, Point b, Point c) throws IllegalArgumentException {
        if(a == null || b == null || c == null)
            throw new IllegalArgumentException();
        this.a = a;
        this.b = b;
        this.c = c;
        System.out.println(area());
        if (!checkTriangle(a, b, c))
            throw new IllegalArgumentException();
        //if((new Quadrilateral.Segment(a, b)).intersection(new Quadrilateral.Segment(b, c)) == null)
            //throw new IllegalArgumentException();
    }

    @Override
    public Point centroid() {
        return new Point((a.getX() + b.getX() + c.getX()) / 3, (a.getY() + b.getY() + c.getY()) / 3);
    }

    @Override
    public boolean isTheSame(Figure f) {
        if (f.getClass() != Triangle.class)
            return false;
        if (!a.equals(((Triangle) f).a) && !a.equals(((Triangle) f).c) && !a.equals(((Triangle) f).b))
            return false;
        if (!b.equals(((Triangle) f).c) && !b.equals(((Triangle) f).b))
            return false;
        if (!c.equals(((Triangle) f).c))
            return false;
        return true;
    }

    @Override
    public double area() {
        double p = (a.getLength(b) + a.getLength(c) + b.getLength(c)) / 2;
        return Math.sqrt(p * (p - a.getLength(b)) * (p - a.getLength(c)) * (p - b.getLength(c)));
    }

    public static boolean checkTriangle(Point a, Point b, Point c) {
        double p = (a.getLength(b) + a.getLength(c) + b.getLength(c)) / 2;
        p = Math.sqrt(p * (p - a.getLength(b)) * (p - a.getLength(c)) * (p - b.getLength(c)));
        if (p < Math.pow(10, -3))
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

