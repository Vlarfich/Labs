package com.epam.rd.autotasks.figures;

class Point {
    private double x;
    private double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength(Point b) {
        return Math.sqrt((x - b.x) * (x - b.x) + (y - b.y) * (y - b.y));
    }

    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Point d){
        if(d == null)
            return false;
        if(d == this)
            return true;

        return Math.abs(this.x - d.x) < Math.pow(10, -6) && Math.abs(this.y - d.y) < Math.pow(10, -6);
    }
}
