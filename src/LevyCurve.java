/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class LevyCurve {

    // canvas and painter
    static final int WIDTH = 1640;
    static final int HEIGHT = 920;
    static final Canvas canvas = new Canvas(WIDTH, HEIGHT);
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
        Point start = new Point(500, 200);
        Point end = new Point(1140, 200);
        // call to the recursive method
        levycurve(level, direction, start, end);

        chrono.stop();
        double duration = chrono.getDuration();
        System.out.println("Execution time: " + duration);
    }

    /**
     *
     * @param N  level
     * @param d  direction of the edge
     * @param p1 first point
     * @param p2 second point
     */
    private static void levycurve(int N, int d, Point p1, Point p2) {
        if (N == 0) {
            painter.setColor(Color.BLACK);
            painter.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        } else {
            double x = 0;
            double y = 0;
            double length = sqrt(Math.pow(p1.getX() - p2.getX(), 2)
                    + Math.pow(p1.getY() - p2.getY(), 2)) / 2;

            // if statement to determine x and y
            // ________________________________________________________________
            if (p1.getY() == p2.getY()) {
                if (p1.getX() < p2.getX()) {
                    if (d == 1) {
                        x = p1.getX() + length;
                        y = p1.getY() + length;
                    } else {
                        x = p1.getX() + length;
                        y = p1.getY() - length;
                    }
                } else {
                    if (d == 1) {
                        x = p2.getX() + length;
                        y = p2.getY() - length;
                    } else {
                        x = p2.getX() + length;
                        y = p2.getY() + length;
                    }
                }
            } else if (p1.getX() == p2.getX()) {
                if (p1.getY() < p2.getY()) {
                    if (d == 1) {
                        x = p1.getX() - length;
                        y = p1.getY() + length;
                    } else {
                        x = p1.getX() + length;
                        y = p1.getY() + length;
                    }
                } else {
                    if (d == 1) {
                        x = p2.getX() + length;
                        y = p2.getY() + length;
                    } else {
                        x = p2.getX() - length;
                        y = p2.getY() + length;
                    }
                }
            // diagonal points (1)(first point is left of second)
            } else if (p1.getX() < p2.getX()) {
                if (p1.getY() > p2.getY()) {
                    if (d == 1) {
                        x = p2.getX();
                        y = p1.getY();
                    } else {
                        x = p1.getX();
                        y = p2.getY();
                    }
                } else {
                    if (d == 1) {
                        x = p1.getX();
                        y = p2.getY();
                    } else {
                        x = p2.getX();
                        y = p1.getY();
                    }
                }
            // diagonal points (2)
            } else if (p1.getX() > p2.getX()) {
                if (p1.getY() > p2.getY()) {
                    if (d == 1) {
                        x = p1.getX();
                        y = p2.getY();
                    } else {
                        x = p2.getX();
                        y = p1.getY();
                    }
                } else {
                    if (d == 1) {
                        x = p2.getX();
                        y = p1.getY();
                    } else {
                        x = p1.getX();
                        y = p2.getY();
                    }
                }
            }
            // _____________________________________________________________

            Point p3 = new Point(x, y);
            d = 1;
            levycurve(N - 1, d, p1, p3);
            levycurve(N - 1, d, p3, p2);
        }
    }
}
