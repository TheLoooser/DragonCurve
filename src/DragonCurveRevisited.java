import java.util.ArrayList;
import java.util.Collections;
import diuf.iip_lib.Canvas;
import diuf.iip_lib.Painter;
import java.awt.Color;

public class DragonCurveRevisited {
    // canvas and painter
    static final int WIDTH = 1980;
    static final int HEIGHT = 1080;
    static Canvas canvas = new Canvas(WIDTH, HEIGHT);
    static Painter painter = new Painter(canvas);
    static Color[] colours = RainbowColours.getColours();

    public static void draw(ArrayList<Point> points, Color colour, boolean center, boolean mixed) {
        draw(points, colour, center, mixed, new ArrayList<Integer>());
    }

    public static void draw(ArrayList<Point> points, Color colour, boolean center, boolean mixed,
            ArrayList<Integer> map) {
        painter.setColor(colour);

        if (center) {

            double minX = WIDTH, minY = HEIGHT;
            double maxX = 0, maxY = 0;

            for (Point p : points) {
                if (p.getX() > maxX) {
                    maxX = (int) p.getX();
                }
                if (p.getX() < minX) {
                    minX = (int) p.getX();
                }
                if (p.getY() > maxY) {
                    maxY = (int) p.getY();
                }
                if (p.getY() < minY) {
                    minY = (int) p.getY();
                }
            }
            double scale = 1;
            if (maxX - minX > WIDTH) {
                scale = WIDTH / (maxX - minX);
            }
            if (maxY - minY > HEIGHT) {
                double s = HEIGHT / (maxY - minY);
                scale = s < scale ? s : scale;
            }
            double scale_value = scale; // Local variable scale defined in an enclosing scope must be final or
                                        // effectively final for lambda expression

            double tx = -minX, ty = -minY;
            points.forEach((pt) -> pt.translate(tx, ty));

            double transX = (WIDTH - (maxX - minX) * scale) / 2;
            double transY = (HEIGHT - (maxY - minY) * scale) / 2;
            points.forEach((pt) -> pt.resize(scale_value));
            points.forEach((pt) -> pt.translate(transX, transY));
        }

        int colour_index;
        // loop to paint each line
        for (int j = 0; j < (points.size() - 1); j++) {
            if (mixed) {
                colour_index = j / (points.size() / (colours.length - 2));
                double percentage = ((double) j % (points.size() / (colours.length - 2)))
                        / (points.size() / (colours.length - 2));
                painter.setColor(RainbowColours.mixture(colours[colour_index + 1], colours[colour_index], percentage));
            }
            if (!map.contains(j)) {
                painter.drawLine(points.get(j).getX(), points.get(j).getY(), points.get(j + 1).getX(),
                        points.get(j + 1).getY());
            }
        }

    }

    public static ArrayList<Point> dragon_curve(ArrayList<Point> points, int n) {
        return dragon_curve(points, n, false);
    }

    public static ArrayList<Point> dragon_curve(ArrayList<Point> points, int n, boolean coloured) {
        if (n == 0) {
            return points;
        }

        ArrayList<Point> new_points = new ArrayList<>();
        for (Point p : points) {
            try {
                new_points.add(p.clone());
            } catch (CloneNotSupportedException cnse) {
                cnse.printStackTrace();
            }
        }

        double angle = -Math.PI / 2; // 90°
        Point anchor = new_points.remove(new_points.size() - 1);
        new_points.parallelStream().forEach((pt) -> pt.rotate(anchor, angle));

        Collections.reverse(new_points);
        points.addAll(new_points);

        return dragon_curve(points, n - 1);
    }

    private static int drawNext(ArrayList<Point> points, int index, double angle, int colour_index) {
        Point anchor = points.get(index);
        points.parallelStream().forEach((pt) -> pt.rotate(anchor, angle));
        draw(points, colours[colour_index++], false, false);
        colour_index = colour_index % colours.length;
        return colour_index;
    }

    public static void tiling(int n, double offset) {
        int col_index = 0;
        double angle = Math.PI / 2;
        double x = 0, y = 0;
        Line initial_line = new Line(new Point(x, y), new Point(x, y - offset));

        // calculate dragon curve points
        ArrayList<Point> points = new ArrayList<>();
        points.add(initial_line.getPoints()[0]);
        points.add(initial_line.getPoints()[1]);
        points = dragon_curve(points, n);

        // turn to make start and end points on same horizontal line
        double initialAngle = (2 * Math.PI - ((n - 2) % 8) * Math.PI / 4) % (2 * Math.PI);
        Point anchor = points.get(0);
        points.parallelStream().forEach((pt) -> pt.rotate(anchor, initialAngle));
        double len = points.get(points.size() - 1).getX() - points.get(0).getX();
        points.parallelStream().forEach((pt) -> pt.translate(-len, -len));
        x = y = -len;

        // calculate top left tile corners
        ArrayList<Point> gridCorners = new ArrayList<>();
        while (y < HEIGHT + 2 * len) {
            while (x < WIDTH + 2 * len) {
                gridCorners.add(new Point(x, y));
                x += len;
            }
            x = points.get(0).getX();
            y += 2 * len;
        }
        gridCorners.remove(0);

        for (Point corner : gridCorners) {
            draw(points, colours[col_index++], false, false); // top
            col_index = col_index % colours.length;

            col_index = drawNext(points, 0, angle, col_index); // left
            col_index = drawNext(points, points.size() - 1, angle, col_index); // bottom
            col_index = drawNext(points, 0, angle, col_index); // right

            // rotate and shift to next tile point
            Point anchor5 = points.get(points.size() - 1);
            points.parallelStream().forEach((pt) -> pt.rotate(anchor5, angle));

            double transX = corner.getX() - points.get(0).getX();
            double transY = corner.getY() - points.get(0).getY();
            points.parallelStream().forEach((pt) -> pt.translate(transX, transY));
        }
    }

    private static boolean checkNeighbours(int[][] matrix, int x, int y, int n) {
        if (n == 0) {
            return true;
        }

        if (x == 0 || y == 0 || x == matrix[0].length - 1 || y == matrix.length - 1) {
            return false;
        }

        if (matrix[y][x + 1] == 1 && matrix[y][x - 1] == 1 && matrix[y + 1][x] == 1 && matrix[y - 1][x] == 1) {
            return checkNeighbours(matrix, x + 1, y, n - 1) &&
                    checkNeighbours(matrix, x - 1, y, n - 1) &&
                    checkNeighbours(matrix, x, y + 1, n - 1) &&
                    checkNeighbours(matrix, x, y - 1, n - 1);
        }

        return false;
    }

    public static void contour(int n, double offset, Color col) {        
        // create points
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, offset));
        points.add(new Point(0, 0));
        points = dragon_curve(points, n);

        // create matrix of points
        int minX = WIDTH, minY = HEIGHT;
        int maxX = 0, maxY = 0;

        for (Point p : points) {
            if (p.getX() > maxX) {
                maxX = (int) Math.round(p.getX());
            }
            if (p.getX() < minX) {
                minX = (int) Math.round(p.getX());
            }
            if (p.getY() > maxY) {
                maxY = (int) Math.round(p.getY());
            }
            if (p.getY() < minY) {
                minY = (int) Math.round(p.getY());
            }
        }
        n = (int) (maxY - minY) + 1;
        int m = (int) (maxX - minX) + 1;
        int[][] matrix = new int[n][m];

        // initialise matrix
        for (Point p : points) {
            matrix[(int) Math.round(p.getY() - minY)][(int) Math.round(p.getX() - minX)] = 1;
        }

        ArrayList<Integer> inside = new ArrayList<>();
        // loop over points
        for (int i = 0; i < (points.size() - 1); i++) {
            Point startPt = points.get(i);
             // call recursive function to check for neighbours on all four sides
            boolean start = checkNeighbours(
                    matrix,
                    (int) Math.round(startPt.getX() - minX),
                    (int) Math.round(startPt.getY() - minY),
                    1); // contour thickness

            Point endPt = points.get(i + 1);
            boolean end = checkNeighbours(
                    matrix,
                    (int) Math.round(endPt.getX() - minX),
                    (int) Math.round(endPt.getY() - minY),
                    1);
            // if start and end of line have 4 neighbours
            if (start && end) {
                // store point index, i.e. do not draw the outgoing line
                inside.add(i);
            }
        }

        // ASCII Dragon Curve
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < m; j++) {
        //         System.out.print(matrix[i][j]);
        //     }
        //     System.out.println("");
        // }

        draw(points, col, true, false, inside);
    }

    public static void saveImage() {
        canvas.saveAsPNG("D:\\Dizzy\\Documents\\NetBeansProjects\\DragonCurve\\resources", "out.png");
    }
}
