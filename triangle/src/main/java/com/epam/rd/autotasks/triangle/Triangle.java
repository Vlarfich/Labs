package com.epam.rd.autotasks.triangle;

class Triangle {
    Point a, b, c;

    private static double getK(Segment t, Segment m) {
        double k;
        double c = (t.e.getX() - t.s.getX()) / (t.e.getY() - t.s.getY());
        k = 1 / c;

        double k2;
        c = (m.e.getX() - m.s.getX()) / (m.e.getY() - m.s.getY());
        k2 = 1 / c;

        if (k == k2) {
            return -1;
        }
        return 0;
    }

    public Triangle(Point a_, Point b_, Point c_) {
        if ((a_.getX() == b_.getX() && a_.getY() == b_.getY()) || (a_.getX() == c_.getX() && a_.getY() == c_.getY()) ||
                (c_.getX() == b_.getX() && c_.getY() == b_.getY())) {
            throw new IllegalArgumentException();
        }
        if ((getK(new Segment(a_, b_), new Segment(a_, c_)) == -1) ||
                (getK(new Segment(a_, b_), new Segment(b_, c_)) == -1) ||
                (getK(new Segment(a_, c_), new Segment(b_, c_)) == -1)) {
            throw new IllegalArgumentException();
        }
        a = a_;
        b = b_;
        c = c_;
    }

    public double area() {
        double p;
        double A = (new Segment(a, b)).length();
        double B = (new Segment(a, c)).length();
        double C = (new Segment(c, b)).length();
        p = (A + B + C) / 2;
        return Math.sqrt(p * (p - A) * (p - B) * (p - C));
    }

    public Point centroid() {
        Point A = (new Segment(a, b)).middle();
        Point B = (new Segment(a, c)).middle();
        System.out.println(A);
        System.out.println(B);
        System.out.println();
        Segment k = new Segment(A, c);
        Segment m = new Segment(B, b);
        System.out.println(k.s + "  " + k.e);
        System.out.println(m.s + "  " + m.e);
        return m.intersection(k);
    }

    public static class Segment {
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
                    y < Math.min(another.s.getY(), another.e.getY()) || y > Math.max(another.s.getY(), another.e.getY())) ) {
                return null;
            }

            return new Point(x, y);
        }


    }

}
