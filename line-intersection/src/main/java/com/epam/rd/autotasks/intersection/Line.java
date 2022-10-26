package com.epam.rd.autotasks.intersection;

public class Line {
    int k = 0;
    int b = 0;
    public Line(int c, int d) {
        k = c;
        b = d;
    }

    public Point intersection(Line other) {
        if(this.k == other.k){
            return null;
        }
        int x = (other.b - this.b) / (this.k - other.k);
        int y = this.k * x + this.b;
        Point res = new Point( x, y);
        return res;
    }

}
