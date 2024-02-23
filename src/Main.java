import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> cat = verticesToLines(50, 10,
                1, 2,
                3, 4,
                4, 2,
                4, -2,
                2, -4,
                -2, -4,
                -4, -2,
                -4, 2,
                -3, 4,
                -1, 2
        );
        Visualiser v = new Visualiser(1080, 720, cat, (x, y) -> x, (x, y) -> -y, true);
        new Thread(v).start();
    }

    private static List<Point> verticesToLines(double scale, int pointsPerEdge, double... vertices) {
        if (vertices.length % 2 != 0) {
            throw new AssertionError("Invalid list of vertices");
        }

        List<Point> result = new ArrayList<>(vertices.length / 2 * pointsPerEdge);
        double x = vertices[vertices.length - 2];
        double y = vertices[vertices.length - 1];
        for (int i = 0; i < vertices.length; i += 2) {
            double dx = (vertices[i] - x) / pointsPerEdge;
            double dy = (vertices[i + 1] - y) / pointsPerEdge;
            for (int j = 0; j < pointsPerEdge; j++) {
                result.add(new Point(x * scale, y * scale));
                x += dx;
                y += dy;
            }
        }
        return result;
    }
}
