package at.ac.fhcampuswien.analogeuhr;

import java.util.Date;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnalogClock extends JPanel implements Runnable
{
    int xposition = 175;
    int yposition = 175;
    int lastxs = 0;
    int lastys = 0;
    int lastxm = 0;
    int lastym = 0;
    int lastxh = 0;
    int lastyh = 0;
    Thread thread;
    SimpleDateFormat formattedDate = new SimpleDateFormat("s", Locale.getDefault());
    Date date;

    /*
     * Die drawBackground Methode fügt einem Graphics objekt Attribute hinzu.
     */
    private void drawBackground(Graphics g)
    {
        //innere Farbe (Uhr)
        g.setColor(Color.orange);
        g.fillOval(xposition - 150, yposition - 150, 300, 300);

        //Dragon Ball Farbe
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 30));
        g.drawString("Dragon Ball", 95, 110);

        //Farbe Uhrzeit (12, 3, 6, 9)
        g.setColor(Color.black);
        g.drawString("XII", xposition - 20, yposition - 120);
        g.drawString("III", xposition + 115, yposition + 0);
        g.drawString("VI", xposition - 10, yposition + 145);
        g.drawString("IX", xposition - 145, yposition + 0);

        //Sternchen im Kreis
        g.drawString("*", xposition -50, yposition +40);
        g.drawString("*", xposition -10, yposition +100);
        g.drawString("*", xposition +50, yposition -40);
        g.drawString("*", xposition +110, yposition -20);

    }
    /*
     * Die paint Methode ist für die Darstellung der Zeiger verantwortlich.
     */
    public void paint(Graphics g)
    {
        int xhour;
        int yhour;
        int xminute;
        int yminute;
        int xsecond;
        int ysecond;
        int second;
        int minute;
        int hour;

        //Kreis (Uhr)
        drawBackground(g);

        //Datum
        date = new Date();

        formattedDate.applyPattern("s");
        second = Integer.parseInt(formattedDate.format(date));
        formattedDate.applyPattern("m");
        minute = Integer.parseInt(formattedDate.format(date));
        formattedDate.applyPattern("h");
        hour = Integer.parseInt(formattedDate.format(date));

        //x+y center = Beginn des Zeigers
        //120 = ZeigerLänge
        xsecond = (int)(Math.cos(second * Math.PI / 30 - Math.PI / 2) * 120 + xposition);
        ysecond = (int)(Math.sin(second * Math.PI / 30 - Math.PI / 2) * 120 + yposition);

        //x+y center = Beginn des Zeigers
        //100 = ZeigerLänge
        xminute = (int)(Math.cos(minute * Math.PI / 30 - Math.PI / 2) * 100 + xposition);
        yminute = (int)(Math.sin(minute * Math.PI / 30 - Math.PI / 2) * 100 + yposition);

        //x+y center = Beginn des Zeigers
        //80 = ZeigerLänge
        xhour = (int)(Math.cos((hour * 30 + minute / 2) * Math.PI / 180 - Math.PI / 2) * 80 + xposition);
        yhour = (int)(Math.sin((hour * 30 + minute / 2) * Math.PI / 180 - Math.PI / 2) * 80 + yposition);

        //aktuelle Uhrzeit darstellen
        if (xsecond != lastxs || ysecond != lastys)
        {
            g.drawLine(xposition, yposition, lastxs, lastys);
        }

        if (xminute != lastxm || yminute != lastym)
        {
            g.drawLine(xposition, yposition - 1, lastxm, lastym);
            g.drawLine(xposition - 1, yposition, lastxm, lastym);
        }

        if (xhour != lastxh || yhour != lastyh)
        {
            g.drawLine(xposition, yposition - 1, lastxh, lastyh);
            g.drawLine(xposition - 1, yposition, lastxh, lastyh);
        }

        //SekundenZeiger Farbe
        g.setColor(Color.black);
        g.drawLine(xposition, yposition, xsecond, ysecond);

        //MinutenZeiger Farbe
        g.setColor(Color.red);
        g.drawLine(xposition, yposition - 1, xminute, yminute);
        g.drawLine(xposition - 1, yposition, xminute, yminute);

        //StundenZeiger Farbe
        g.setColor(Color.blue);
        g.drawLine(xposition, yposition - 1, xhour, yhour);
        g.drawLine(xposition - 1, yposition, xhour, yhour);
        lastxs = xsecond;
        lastys = ysecond;
        lastxm = xminute;
        lastym = yminute;
        lastxh = xhour;
        lastyh = yhour;
    }

    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop()
    {
        thread = null;
    }


    public void run()
    {
        while (thread != null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {}
            repaint();
        }
        thread = null;
    }

    public void update(Graphics g)
    {
        paint(g);
    }


    public static void main(String args[])
    {
        //Fenster Pop-Up
        JFrame window = new JFrame();

        //Hintergrundfarbe
        Color c = new Color(255, 255, 255);

        //Hintergrundfarbe im Fenster implementieren
        window.setBackground(c);

        //Pop-up-Fenster schließen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Länge+Breite vom Fenster
        window.setBounds(0, 0, 400, 400);

        //Objekt
        AnalogClock clock = new AnalogClock();

        //Objektimport im Fenster
        window.getContentPane().add(clock);

        //Sichtbarkeit
        window.setVisible(true);

        //Start bzw. Anfang
        clock.start();
    }
}