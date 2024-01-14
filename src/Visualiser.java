import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.BinaryOperator;

public class Visualiser extends JFrame implements Runnable {
    private static final int TIME_PER_FRAME = 100;
    private static final double DERIVATIVE_COEFFICIENT = 0.01;
    private static final int POINT_RADIUS = 4;

    private final int width;
    private final int height;
    private final List<Point> points;
    private final BinaryOperator<Double> derivativeX;
    private final BinaryOperator<Double> derivativeY;
    private final boolean trace;

    public Visualiser(int width, int height, List<Point> points,
                      BinaryOperator<Double> derivativeX, BinaryOperator<Double> derivativeY, boolean trace) {
        this.width = width;
        this.height = height;
        this.points = points;
        this.derivativeX = derivativeX;
        this.derivativeY = derivativeY;
        this.trace = trace;
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            transform();
            try {
                Thread.sleep(TIME_PER_FRAME);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void transform() {
        for (Point point : points) {
            double dx = derivativeX.apply(point.x, point.y) * DERIVATIVE_COEFFICIENT;
            double dy = derivativeY.apply(point.x, point.y) * DERIVATIVE_COEFFICIENT;
            point.x += dx;
            point.y += dy;
        }
    }

    @Override
    public void paint(Graphics g) {
        if (!trace) {
            super.paint(g);
        }
        g.setColor(Color.BLACK);
        for (Point point : points) {
            g.fillOval((int) point.x + width / 2, (int) -point.y + height / 2, POINT_RADIUS, POINT_RADIUS);
        }
    }
}
