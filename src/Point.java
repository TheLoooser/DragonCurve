import java.lang.Math;

public class Point implements Cloneable {
    private double x, y;

    public Point(double x1, double y1) {
        super();
        x = x1;
        y = y1;
    }

    public double getX() {
        return x;
    };

    public double getY() {
        return y;
    };

    public void setX(double x) {
        this.x = x;
    };

    public void setY(double y) {
        this.y = y;
    };

    public Point add(Point other){
        return new Point(this.x + other.getX(),this.y + other.getY());
    }

    public void rotate(Point anchor, double angle) {
        // double angle = - Math.PI / 2; // 90°
        Point temp = new Point(
                anchor.x + (this.x - anchor.x) * Math.cos(angle) - (this.y - anchor.y) * Math.sin(angle),
                anchor.y + (this.x - anchor.x) * Math.sin(angle) + (this.y - anchor.y) * Math.cos(angle));
        this.x = temp.getX();
        this.y = temp.getY();
    }

    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void resize(double scale) {
        this.x *= scale;
        this.y *= scale;
    }

    @Override
    protected Point clone() throws CloneNotSupportedException {
        Point clone = null;
        try {
            clone = (Point) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", this.x, this.y);
    }
}
