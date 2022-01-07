import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Clock extends JPanel {

    ImageIcon img;

    private GregorianCalendar cal;
    private int[] x = new int[2];
    private int[] y = new int[2];
    private Timer clocktimer = new Timer();

    private TimeZone clockTimeZone = TimeZone.getDefault();

    /*
     * constructor initializes window size and clock timer
     */
    public Clock() {
        this.setPreferredSize(new Dimension(210, 210));
        this.setMinimumSize(new Dimension(210, 210));
        clocktimer.schedule(new TickTimerTask(), 0, 1000);
    }



    public void paint(Graphics g) {

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawCardinals((Graphics2D) g);
        drawHands((Graphics2D) g);

    }

    void clockMinutes(int startRadius, int endRadius, double theta) {
        theta -= Math.PI / 2;
        x[0] = (int) (getWidth() / 2 + startRadius * Math.cos(theta));
        y[0] = (int) (getHeight() / 2 + startRadius * Math.sin(theta));
        x[1] = (int) (getWidth() / 2 + endRadius * Math.cos(theta));
        y[1] = (int) (getHeight() / 2 + endRadius * Math.sin(theta));
    }

    void drawCardinals(Graphics2D g) {
        g.setStroke(new BasicStroke(12));
        g.setColor(Color.black);

        for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 6) {
            clockMinutes(100, 100, theta);
            g.drawPolyline(x, y, 2);
        }
    }

    public void drawHands(Graphics2D g) {
        double h = 2 * Math.PI * (cal.get(Calendar.HOUR));
        double m = 2 * Math.PI * (cal.get(Calendar.MINUTE));
        double s = 2 * Math.PI * (cal.get(Calendar.SECOND));

        g.setStroke(new BasicStroke(9));

        clockMinutes(0, 55, h / 12 + m / (60 * 12));
        g.setColor(Color.BLACK);
        g.drawPolyline(x, y, 2);

        clockMinutes(0, 70, m / 60 + s / (60 * 60));
        g.setColor(Color.black);
        g.drawPolyline(x, y, 2);

        clockMinutes(0, 70, s / 60);
        g.setColor(Color.red);
        g.drawPolyline(x, y, 2);

        g.fillOval(getWidth() / 2 - 8, getHeight() / 2 - 8, 16, 16);
    }

    class TickTimerTask extends TimerTask {

        public void run() {
            cal = (GregorianCalendar) GregorianCalendar.getInstance(clockTimeZone);
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setTitle("Analog Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Clock m_AnalogClock = new Clock();
        frame.add(m_AnalogClock);
        frame.setVisible(true);

    }



}