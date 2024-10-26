import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main2 extends JPanel {

    private static final int w = 1200;
    private final int[][] pixels = new int[w][w];
    private void calculateBuddhabrot() {
        for (int y = 0; y < w; y++) {
            for (int x = 0; x < w; x++) {
                double x0 = 0;
                double y0 = 0;
                double ZOOM = 250;
                double cx = (x - (double) w / 2) / ZOOM;
                double cy = (y - (double) w / 2) / ZOOM;
                int maxIterations = 500;
                double[] X = new double[maxIterations];
                double[] Y = new double[maxIterations];
                int len = 0;
                boolean escaped = false;
                for (int iter = 0; iter < maxIterations; iter++) {
                    double abszx2 = x0 * x0;
                    double abszy2 = y0 * y0;
                    if (abszx2 + abszy2 > 4.0) {
                        escaped = true;
                        break;
                    }
                    X[len] = x0;
                    Y[len] = y0;
                    y0 = 2.0 * x0 * y0 + cy;
                    x0 = abszx2 - abszy2 + cx;
                    len++;
                }
                if (escaped) {
                    for (int i = 0; i < len; i++) {
                        int px = (int) ((X[i] * ZOOM) + w / 2);
                        int py = (int) ((Y[i] * ZOOM) + w / 2);
                        if (px >= 0 && px < w && py >= 0 && py < w) {
                            pixels[px][py]++;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateBuddhabrot();
        for (int y = 0; y < w; y++) {
            for (int x = 0; x < w; x++) {
                int visits = pixels[x][y];
                if (visits > 0) {
                    int colorValue = Math.min(255, visits * 5);
                    g.setColor(new Color(colorValue, colorValue, colorValue));
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Buddhabrot Set");
        Main2 buddhabrotPanel = new Main2();
        frame.setContentPane(buddhabrotPanel);
        frame.setSize(w, w);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
