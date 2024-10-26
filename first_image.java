import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
    public int maxIterations = 1000;
    public double mandelbrot(double x0, double y0) {
        double temp, iter = 0, x = 0, y = 0;
        while (x * x + y * y < 4 && iter < maxIterations) {
            temp = x * x - y * y + x0;
            y = 2.0 * x * y + y0;
            x = temp;
            iter++;
        }
        if (iter == maxIterations){
            double log_zn = Math.log(x * x + y * y)/2;
            double nu = Math.log(log_zn/Math.log(2))/Math.log(2);
            iter += 1 - nu;
        }
        return maxIterations - iter;
    }
    public Color pointColor(double iter){
        return Color.getHSBColor((float) (iter/maxIterations%360), 100, (float) (iter/maxIterations*100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < 800; y++) {
            for (int x = 0; x < 800; x++) {
                double iter =  mandelbrot((x - 400) / 170.0, (y - 400) / 170.0);
                g.setColor(pointColor(iter));
                g.fillRect(x, y, 1, 1);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame1 = new JFrame("Mandelbrot Set");
        Main mandelbrotPanel = new Main();
        frame1.setContentPane(mandelbrotPanel);
        frame1.setSize(800, 800);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }
}
