import diuf.iip_lib.Chronometer;
import java.awt.Color;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Chronometer chrono = new Chronometer();
        chrono.start();

        double offset = 2;
        int n = 18;
        switch (args[0]) {
            case "contour":
                Color colour = Color.BLACK;
                offset = 1;
                DragonCurveRevisited.contour(n, offset, colour);
                break;
            case "tiling":
                DragonCurveRevisited.tiling(n, offset);
                break;
            case "coloured":
                ArrayList<Point> points = new ArrayList<>();
                // initial line
                points.add(new Point(0, -10));
                points.add(new Point(0, 0));

                // If n is too large, then "Exception in thread "main"
                // java.lang.OutOfMemoryError: Java heap space"
                points = DragonCurveRevisited.dragon_curve(points, n, true);
                DragonCurveRevisited.draw(points, null, true, true);
                break;
            default:
                ArrayList<Point> pts = new ArrayList<>();
                pts.add(new Point(0, -10));
                pts.add(new Point(0, 0));
                points = DragonCurveRevisited.dragon_curve(pts, n);
                DragonCurveRevisited.draw(points, Color.BLACK, true, false);
        }

        DragonCurveRevisited.saveImage();

        chrono.stop();
        System.out.println("Execution time: " + chrono.getDuration());

    }
}
