import diuf.iip_lib.Canvas;
import diuf.iip_lib.Chronometer;
import diuf.iip_lib.Painter;
import diuf.iip_lib.Point;
import java.awt.Color;
import static java.lang.Math.sqrt;

/**
 *
 * @author Dizzy
 */
public class DragonCurve {

    // canvas and painter
    static final int WIDTH = 900;
    static final int HEIGHT = 700;
    static Canvas canvas = new Canvas(WIDTH, HEIGHT);
    static Painter painter = new Painter(canvas);

    // main method
    public static void main(String[] args) {
        draw();
    }

    // draws the object, paints lines from point to point, points are stored inside an array
    private static void draw() {
        Chronometer chrono = new Chronometer();
        chrono.start();
        painter.setColor(Color.BLACK);
        // amount of recursive iterations
        int level = 20;
        // direction of the next "triangle"??
        int direction = 1;
        double arraysize = Math.pow(2, level);
        Point points[] = new Point[(int) arraysize + 1];
        // call to the recursive methode
        dragoncurve(level, direction, points, arraysize, 0);

        // loop to paint each line
        for (int j = 0; j < (points.length - 1); j++) {
            painter.drawLine(points[j], points[j + 1]);
            painter.pause(00);
        }
        chrono.stop();
        double duration = chrono.getDuration();
        System.out.println("Execution time: " + duration);
    }

    /**
     *
     * @param N      amount of iterations
     * @param x      direction of next fold
     * @param points array to store all points
     * @param size   index used to store points in the array
     */
    private static void dragoncurve(int N, int d, Point points[], double size, int angle) {
        // initial starting and ending point
        Point start = new Point(300, 250);
        Point end = new Point(600, 250);

        points[0] = start;
        points[(points.length - 1)] = end;

        if (N == 0) {
            System.out.println("end reached");

        } else {
            // size is the index of the new point to be calculated
            size = (int) size - (d * (int) Math.pow(2, N - 1));
            double x = 0;
            double y = 0;

            // calculates the length between the points
            double length = sqrt(Math.pow(points[(int) size - (int) Math.pow(2, N - 1)].getX()
                    - points[(int) size + (int) Math.pow(2, N - 1)].getX(), 2)
                    + Math.pow(points[(int) size - (int) Math.pow(2, N - 1)].getY()
                            - points[(int) size + (int) Math.pow(2, N - 1)].getY(), 2))
                    / 2;

            // create cases to calculate the new points
            // identicall y-axis
            if (points[(int) size - (int) Math.pow(2, N - 1)].getY() == points[(int) size + (int) Math.pow(2, N - 1)]
                    .getY()) {
                if (points[(int) size - (int) Math.pow(2, N - 1)].getX() < points[(int) size + (int) Math.pow(2, N - 1)]
                        .getX()) {
                    if (d == 1) {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX() + length;
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY() + length;
                    } else {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX() + length;
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY() - length;
                    }
                } else {
                    if (d == 1) {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX() + length;
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY() - length;
                    } else {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX() + length;
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY() + length;
                    }
                }
            // identical x-axis
            } else if (points[(int) size - (int) Math.pow(2, N - 1)]
                    .getX() == points[(int) size + (int) Math.pow(2, N - 1)].getX()) {
                if (points[(int) size - (int) Math.pow(2, N - 1)].getY() < points[(int) size + (int) Math.pow(2, N - 1)]
                        .getY()) {
                    if (d == 1) {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX() - length;
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY() + length;
                    } else {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX() + length;
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY() + length;
                    }
                } else {
                    if (d == 1) {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX() + length;
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY() + length;
                    } else {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX() - length;
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY() + length;
                    }
                }
            // diagonal points (1)(first point is left of second)
            } else if (points[(int) size - (int) Math.pow(2, N - 1)]
                    .getX() < points[(int) size + (int) Math.pow(2, N - 1)].getX()) {
                if (points[(int) size - (int) Math.pow(2, N - 1)].getY() > points[(int) size + (int) Math.pow(2, N - 1)]
                        .getY()) {
                    if (d == 1) {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY();
                    } else {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY();
                    }
                } else {
                    if (d == 1) {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY();
                    } else {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY();
                    }
                }
            // diagonal points (2)
            } else if (points[(int) size - (int) Math.pow(2, N - 1)]
                    .getX() > points[(int) size + (int) Math.pow(2, N - 1)].getX()) {
                if (points[(int) size - (int) Math.pow(2, N - 1)].getY() > points[(int) size + (int) Math.pow(2, N - 1)]
                        .getY()) {
                    if (d == 1) {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY();
                    } else {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY();
                    }
                } else {
                    if (d == 1) {
                        x = points[(int) size + (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size - (int) Math.pow(2, N - 1)].getY();
                    } else {
                        x = points[(int) size - (int) Math.pow(2, N - 1)].getX();
                        y = points[(int) size + (int) Math.pow(2, N - 1)].getY();
                    }
                }
            }

            // stores the new point in the array
            points[(int) size] = new Point(x, y);
            System.out.println(points[(int) size].getX() + "---" + points[(int) size].getY());

            // recursive calls
            dragoncurve(N - 1, d, points, size, angle);
            dragoncurve(N - 1, -d, points, size, angle);
        }

    }

}
