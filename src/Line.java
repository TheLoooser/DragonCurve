public class Line {
    private Point p1, p2;

    public Line(Point point1, Point point2) {
        this.p1 = point1;
        this.p2 = point2;
    }

    public Point[] getPoints() {
        return new Point[] { p1, p2 };
    }
}
