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
    int xclockposition = 300;
    int yclockposition = 300;
    int lastxsecond = 0;
    int lastysecond = 0;
    int lastxminute = 0;
    int lastyminute = 0;
    int lastxhour = 0;
    int lastyhour = 0;
    Thread thread;
    SimpleDateFormat formattedDate = new SimpleDateFormat();
    Date date;

    /*
     * The drawBackground method adds attributes to a Graphics g object.
     */
    private void drawBackground(Graphics g) {

        //Select input file for background.
        BufferedImage img = null;
        switch (design) {
            case 1:
                try {
                    img = ImageIO.read(new File("image0.jpg"));
                } catch (IOException e) {
                } break;
            case 2: {
                try {
                    img = ImageIO.read(new File("image1.jpg"));
                } catch (IOException e) {
                } break;
            }
            case 3: {
                try {
                    img = ImageIO.read(new File("image2.jpg"));
                } catch (IOException e) {
                } break;
            }
                default:
                    try {
                    img = ImageIO.read(new File("image3.jpg"));
                } catch (IOException e) {
                }
            }

        g.drawImage(img, 0, 0, null); //Uses img to draw background.

        //Adds Roman numerals to the background.
        g.setColor(Color.black);
        g.drawString("XII", xclockposition - 20, yclockposition - 220);
        g.drawString("III", xclockposition + 215, yclockposition);
        g.drawString("VI", xclockposition - 10, yclockposition + 245);
        g.drawString("IX", xclockposition - 245, yclockposition);
    }

    /*
     * The paint method draws the hand of the analogue clock.
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
        Graphics2D g = (Graphics2D) g1; // Casting 1D to 2D objects because we want to represent 2D clock hands.

        drawBackground(g);

        //A date object is used to extract values for the current time.
        date = new Date();

        formattedDate.applyPattern("s");
        second = Integer.parseInt(formattedDate.format(date));
        formattedDate.applyPattern("m");
        minute = Integer.parseInt(formattedDate.format(date));
        formattedDate.applyPattern("h");
        hour = Integer.parseInt(formattedDate.format(date));

        //x+y coordinates determine the end of the clock hands
        xsecond = (int)(Math.cos(second * Math.PI / 30 - Math.PI / 2) * 220 + xclockposition);
        ysecond = (int)(Math.sin(second * Math.PI / 30 - Math.PI / 2) * 220 + yclockposition);

        xminute = (int)(Math.cos(minute * Math.PI / 30 - Math.PI / 2) * 200 + xclockposition);
        yminute = (int)(Math.sin(minute * Math.PI / 30 - Math.PI / 2) * 200 + yclockposition);

        xhour = (int)(Math.cos((hour * 30 + minute / 2.0) * Math.PI / 180 - Math.PI / 2) * 180 + xclockposition);
        yhour = (int)(Math.sin((hour * 30 + minute / 2.0) * Math.PI / 180 - Math.PI / 2) * 180 + yclockposition);

        //Second clock hand
        g.setStroke(new BasicStroke(6)); // clock hand width
        g.setColor(Color.black); //color
        g.drawLine(xclockposition, yclockposition, xsecond, ysecond); //drawing clock hands

        //Minute clock hand
        g.setStroke(new BasicStroke(8));
        g.setColor(Color.black);
        g.drawLine(xclockposition, yclockposition - 1, xminute, yminute);
        g.drawLine(xclockposition - 1, yclockposition, xminute, yminute);

        //Hour clock hand
        g.setStroke(new BasicStroke(10));
        g.setColor(Color.black);
        g.drawLine(xclockposition, yclockposition - 1, xhour, yhour);
        g.drawLine(xclockposition - 1, yclockposition, xhour, yhour);
    }

    /*
     * Thread enters runnable state. The start method is internally called by the run() method.
     */
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    /*
     * Thread starts and calls paint via repaint().
     */
    public void run()
    {
        while (thread!=null) {
            repaint();
        }
    }

    /*
     * Set background variable
     */
    public static void setBackground(int background) {
        AnalogClock.design = background;
    }


    public static void main(String[] args)
    {
        //Create object of the class AnalogClock
        AnalogClock clock = new AnalogClock();

        //The first JFrame object is used for the button window.
        JFrame buttonWindow = new JFrame("Button window");
        final JTextField textfield = new JTextField();
        textfield.setBounds(50,25, 150,20);

        //Set Button properties
        JButton button3=new JButton("Click here for design 3");
        JButton button2=new JButton("Click here for design 2");
        JButton button1=new JButton("Click here for design 1");
        JButton button4=new JButton("Stop");

        button1.setBounds(50,50,200,30);
        button2.setBounds(50,75,200,30);
        button3.setBounds(50,100,200,30);
        button4.setBounds(50,125,200,30);

        //Action listeners are used to get input from buttons
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

        //Add buttons to window
        buttonWindow.add(button1);
        buttonWindow.add(button2);
        buttonWindow.add(button3);
        buttonWindow.add(button4);

        buttonWindow.add(textfield);
        buttonWindow.setBounds(600,100,300,300);
        buttonWindow.setLayout(null);
        buttonWindow.setVisible(true);

        //The second JFrame object is used to depict the clock.
        JFrame windowClock = new JFrame();

        //Window closes when program is stopped.
        windowClock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Window dimensions
        windowClock.setBounds(0, 0, 600, 600);

        //Depict clock on window
        windowClock.getContentPane().add(clock);

        //Set visibility
        windowClock.setVisible(true);

        //Start clock procedure
        clock.start();
    }
}