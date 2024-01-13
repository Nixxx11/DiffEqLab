import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.UnaryOperator;

public class Visualiser extends JFrame implements Runnable {
    private final List<Line> lines;
    private final UnaryOperator<Double> transformationX;
    private final UnaryOperator<Double> transformationY;

    public Visualiser(int width, int height, List<Line> lines,
                      UnaryOperator<Double> transformationX, UnaryOperator<Double> transformationY) {
        this.lines = lines;
        this.transformationX = transformationX;
        this.transformationY = transformationY;
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
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void transform() {
        for (Line line : lines) {
            line.x1 = transformationX.apply(line.x1);
            line.y1 = transformationY.apply(line.y1);
            line.x2 = transformationX.apply(line.x2);
            line.y2 = transformationY.apply(line.y2);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        for (Line line : lines) {
            g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
        }
    }
}
