package at.ac.fhcampuswien.analogeuhr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AnalogClock extends JPanel implements Runnable
{
    static private int design;
    int xposition = 300;
    int yposition = 300;
    int lastxs = 0;
    int lastys = 0;
    int lastxm = 0;
    int lastym = 0;
    int lastxh = 0;
    int lastyh = 0;
    Thread thread;
    SimpleDateFormat formattedDate = new SimpleDateFormat();
    Date date;

    public static void setBackground(int background) {
        AnalogClock.design = background;
    }

    /*
     * Die drawBackground Methode fügt einem Graphics Objekt Attribute hinzu.
     */
    private void drawBackground(Graphics g)
    {
        BufferedImage img = null;
        if (design==1) {
            try {
                img = ImageIO.read(new File("image0.jpg"));
            } catch (
                    IOException e) {
            }
        } else if (design==2) {
            try {
                img = ImageIO.read(new File("image1.jpg"));
            } catch (
                    IOException e) {
            }
        } else if (design==3) {
            try {
                img = ImageIO.read(new File("image2.jpg"));
            } catch (
                    IOException e) {
            }
        } else {
            try {
                img = ImageIO.read(new File("image4.jpg"));
            } catch (
                    IOException ignored) {
            }
        }

        g.drawImage(img, 0, 0, null); //Eingabe die Hintergrund auswählt

        //Fügt römische Ziffern hinzu
        g.setColor(Color.black);
        g.drawString("XII", xposition - 20, yposition - 220);
        g.drawString("III", xposition + 215, yposition);
        g.drawString("VI", xposition - 10, yposition + 245);
        g.drawString("IX", xposition - 245, yposition);
    }

    /*
     * Die paint Methode ist für die Darstellung der Zeiger verantwortlich.
     */
    public void paint(Graphics g1)
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
        Graphics2D g = (Graphics2D) g1; // 2D Grphics Objekt wird für 2D Zeiger benötigt

        //Methode drawBackground zeichnet Hintergrund
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
        xsecond = (int)(Math.cos(second * Math.PI / 30 - Math.PI / 2) * 220 + xposition);
        ysecond = (int)(Math.sin(second * Math.PI / 30 - Math.PI / 2) * 220 + yposition);

        //x+y center = Beginn des Zeigers
        xminute = (int)(Math.cos(minute * Math.PI / 30 - Math.PI / 2) * 200 + xposition);
        yminute = (int)(Math.sin(minute * Math.PI / 30 - Math.PI / 2) * 200 + yposition);

        //x+y center = Beginn des Zeigers
        xhour = (int)(Math.cos((hour * 30 + minute / 2.0) * Math.PI / 180 - Math.PI / 2) * 180 + xposition);
        yhour = (int)(Math.sin((hour * 30 + minute / 2.0) * Math.PI / 180 - Math.PI / 2) * 180 + yposition);

        g.setStroke(new BasicStroke(6)); // Breite Zeiger
        //SekundenZeiger Farbe
        g.setColor(Color.black);
        g.drawLine(xposition, yposition, xsecond, ysecond);

        //MinutenZeiger Farbe
        g.setStroke(new BasicStroke(8));
        g.setColor(Color.black);
        g.drawLine(xposition, yposition - 1, xminute, yminute);
        g.drawLine(xposition - 1, yposition, xminute, yminute);

        //StundenZeiger Farbe
        g.setStroke(new BasicStroke(10));
        g.setColor(Color.black);
        g.drawLine(xposition, yposition - 1, xhour, yhour);
        g.drawLine(xposition - 1, yposition, xhour, yhour);
        lastxs = xsecond;
        lastys = ysecond;
        lastxm = xminute;
        lastym = yminute;
        lastxh = xhour;
        lastyh = yhour;
    }

    /*
     * Thread geht in runnable Status, die start Methode ruft intern die run Methode auf.
     */
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run()  // thread startet
    {
        while (thread!=null) {
            try {
                Thread.sleep(50); //Pausiert execution für 50 milliseconds
            } catch (InterruptedException e) {
            }  // try catch wirde benötigt weil anderen threads den thread unterbrechen können
            repaint();      // paints und updates
        }
    }

    public void update(Graphics g) // wird intern von repaint() aufgerufen
    {
        paint(g);
    }


    public static void main(String[] args)
    {
        //Erzeugt Objekt der Klasse AnalogClock
        AnalogClock clock = new AnalogClock();

        JFrame buttonWindow = new JFrame("Button window");
        final JTextField textfield = new JTextField();
        textfield.setBounds(50,25, 150,20);

        JButton button3=new JButton("Click here for design 3");
        JButton button2=new JButton("Click here for design 2");
        JButton button1=new JButton("Click here for design 1");
        JButton button4=new JButton("Stop");

        button1.setBounds(50,50,200,30);
        button2.setBounds(50,75,200,30);
        button3.setBounds(50,100,200,30);
        button4.setBounds(50,125,200,30);

        button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textfield.setText("Design 1");
                setBackground(1);
            }
        });
        button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textfield.setText("Design 2");
                setBackground(2);
            }
        });
        button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textfield.setText("Design 3");
                setBackground(3);
            }
        });
        button4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textfield.setText("Exit");
                System.exit(0);
            }
        });

        buttonWindow.add(button1);
        buttonWindow.add(button2);
        buttonWindow.add(button3);
        buttonWindow.add(button4);

        buttonWindow.add(textfield);
        buttonWindow.setBounds(600,100,300,300);
        buttonWindow.setLayout(null);
        buttonWindow.setVisible(true);

        //Fenster Pop-Up
        JFrame windowClock = new JFrame();

        //Hintergrundfarbe
        Color c = new Color(255, 255, 255);

        //Hintergrundfarbe im Fenster implementieren
        windowClock.setBackground(c);

        //Pop-up-Fenster schließen
        windowClock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Länge+Breite vom Fenster
        windowClock.setBounds(0, 0, 600, 600);

        //Objektimport im Fenster
        windowClock.getContentPane().add(clock);

        //Sichtbarkeit
        windowClock.setVisible(true);

        //Start bzw. Anfang
        clock.start();
    }
}