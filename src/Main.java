import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        int width = 1080;
        int height = 720;

        List<Line> cat = pointsToLines(50,
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
        for (Line line : cat) {
            shift(line, width / 2, height / 2);
        }
        double compressionSpeed = 0.99;
        UnaryOperator<Double> compress = y -> y * compressionSpeed;
        UnaryOperator<Double> extend = x -> x / compressionSpeed;

        Visualiser v = new Visualiser(width, height, cat,
                shift(extend, (double) width / 2), shift(compress, (double) height / 2));
        new Thread(v).start();
    }

    private static UnaryOperator<Double> shift(UnaryOperator<Double> function, double shift) {
        return x -> function.apply(x - shift) + shift;
    }

    private static List<Line> pointsToLines(double scale, int... points) {
        assert points.length % 2 == 0;
        List<Line> result = new ArrayList<>(points.length / 2);
        int x1 = points[points.length - 2];
        int y1 = points[points.length - 1];
        for (int i = 0; i < points.length; i += 2) {
            int x2 = points[i];
            int y2 = points[i + 1];
            result.add(new Line(scale * x1, -scale * y1, scale * x2, -scale * y2));
            x1 = x2;
            y1 = y2;
        }
        return result;
    }

    private static void shift(Line line, double dx, double dy) {
        line.x1 += dx;
        line.y1 += dy;
        line.x2 += dx;
        line.y2 += dy;
    }
}
