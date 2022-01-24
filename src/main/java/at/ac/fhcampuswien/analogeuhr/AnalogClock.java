package at.ac.fhcampuswien.analogeuhr;

import java.awt.*;
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
    private final int XCLOCKPOSITION = 300;
    private final int YCLOCKPOSITION = 300;
    private final SimpleDateFormat formattedDate = new SimpleDateFormat();

    /*
     * The drawBackground method adds attributes to a Graphics g object.
     */
    private void drawBackground(Graphics g) {

        //Select input file for background.
        BufferedImage img = null;
        switch (design) {
            case 1:
                try {
                    img = ImageIO.read(new File("src/main/resources/image0.jpg"));
                } catch (IOException e) {
                } break;
            case 2: {
                try {
                    img = ImageIO.read(new File("src/main/resources/image1.jpg"));
                } catch (IOException e) {
                } break;
            }
            case 3: {
                try {
                    img = ImageIO.read(new File("src/main/resources/image2.jpg"));
                } catch (IOException e) {
                } break;
            }
                default:
                    try {
                    img = ImageIO.read(new File("src/main/resources/image3.jpg"));
                } catch (IOException e) {
                }
            }

        g.drawImage(img, 0, 0, null); //Uses img to draw background.

        //Adds Roman numerals to the background.
        g.setColor(Color.black);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2.4F);
        g.setFont(newFont);
        g.drawString("XII", XCLOCKPOSITION - 20, YCLOCKPOSITION - 220);
        g.drawString("III", XCLOCKPOSITION + 215, YCLOCKPOSITION);
        g.drawString("VI", XCLOCKPOSITION - 10, YCLOCKPOSITION + 245);
        g.drawString("IX", XCLOCKPOSITION - 245, YCLOCKPOSITION);
    }

    /*
     * The paint method draws the hands of the analogue clock.
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
        Date date = new Date();

        formattedDate.applyPattern("s");
        second = Integer.parseInt(formattedDate.format(date));
        formattedDate.applyPattern("m");
        minute = Integer.parseInt(formattedDate.format(date));
        formattedDate.applyPattern("h");
        hour = Integer.parseInt(formattedDate.format(date));

        /* x+y coordinates determine the end of the clock hands
        * when second = 30 then x should be 0 and y should be 1 (times a constant)
        * sin/cos function use degree values
        * pi is 180 degree
        * pi/2 is 90 degree
        * example: second = 30 then 30 * pi / 30 provides an angle (pi) that is shifted by 90 degree because clock starts at 90 degree therefore - pi/2
        */
        xsecond = (int)(Math.cos(second * Math.PI / 30 - Math.PI / 2) * 220 + XCLOCKPOSITION);
        ysecond = (int)(Math.sin(second * Math.PI / 30 - Math.PI / 2) * 220 + YCLOCKPOSITION);

        xminute = (int)(Math.cos(minute * Math.PI / 30 - Math.PI / 2) * 200 + XCLOCKPOSITION);
        yminute = (int)(Math.sin(minute * Math.PI / 30 - Math.PI / 2) * 200 + YCLOCKPOSITION);

        //example: hour = 6; minute = 0; then (180 + 0) * pi/180 = pi = 180 degree. Minus pi/90 to get to the right angle.
        xhour = (int)(Math.cos((hour * 30 + minute / 2.0) * Math.PI / 180 - Math.PI / 2) * 180 + XCLOCKPOSITION);
        yhour = (int)(Math.sin((hour * 30 + minute / 2.0) * Math.PI / 180 - Math.PI / 2) * 180 + YCLOCKPOSITION);

        //Second clock hand
        g.setStroke(new BasicStroke(6)); // clock hand width
        g.setColor(Color.black); //color
        g.drawLine(XCLOCKPOSITION, YCLOCKPOSITION, xsecond, ysecond); //drawing clock hands

        //Minute clock hand
        g.setStroke(new BasicStroke(8));
        g.setColor(Color.black);
        g.drawLine(XCLOCKPOSITION, YCLOCKPOSITION , xminute, yminute);
        g.drawLine(XCLOCKPOSITION , YCLOCKPOSITION, xminute, yminute);

        //Hour clock hand
        g.setStroke(new BasicStroke(10));
        g.setColor(Color.black);
        g.drawLine(XCLOCKPOSITION, YCLOCKPOSITION, xhour, yhour);
        g.drawLine(XCLOCKPOSITION, YCLOCKPOSITION, xhour, yhour);
    }

    /*
     * Thread enters runnable state. The run method is internally called by the start() method.
     */
    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    /*
     * Thread starts and calls paint via repaint() in a while loop.
     */
    public void run()
    {
        while (true) {
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Set background variable
     */
    public static void setBackground(int background) {
        AnalogClock.design = background;
    }

    /*
     * Create a window with buttons
     */
    public void createButtons(){
        //The first JFrame object is used for the button window.
        JFrame buttonWindow = new JFrame("Button window");
        final JTextField textfield = new JTextField();
        textfield.setBounds(50,25, 150,20);
        textfield.setEditable(false);

        //Set Button properties
        JButton button0=new JButton("No background");
        JButton button3=new JButton("Click here for design 3");
        JButton button2=new JButton("Click here for design 2");
        JButton button1=new JButton("Click here for design 1");
        JButton button4=new JButton("Stop");

        button0.setBounds(50,50,200,30);
        button1.setBounds(50,75,200,30);
        button2.setBounds(50,100,200,30);
        button3.setBounds(50,125,200,30);
        button4.setBounds(50,150,200,30);

        //Action listeners are used to get input from buttons
        //Lambda expressions are used for the sake of simplicity
        button0.addActionListener(e -> {
            textfield.setText("Design 0");
            setBackground(0);
        });
        button1.addActionListener(e -> {
            textfield.setText("Design 1");
            setBackground(1);
        });
        button2.addActionListener(e -> {
            textfield.setText("Design 2");
            setBackground(2);
        });
        button3.addActionListener(e -> {
            textfield.setText("Design 3");
            setBackground(3);
        });
        button4.addActionListener(e -> {
            textfield.setText("Exit");
            System.exit(0);
        });

        //Add buttons to window
        buttonWindow.add(button0);
        buttonWindow.add(button1);
        buttonWindow.add(button2);
        buttonWindow.add(button3);
        buttonWindow.add(button4);

        buttonWindow.add(textfield);
        buttonWindow.setBounds(605,100,300,300);
        buttonWindow.setLayout(null);
        buttonWindow.setVisible(true);
    }

    /*
     * Create a window with clock
     */
    public void createClockWindow(){
        //The second JFrame object is used to depict the clock.
        JFrame windowClock = new JFrame();

        //Window closes when program is stopped.
        windowClock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Window dimensions
        windowClock.setBounds(0, 0, 615, 635);

        //Depict clock on window
        windowClock.getContentPane().add(this);

        //Set visibility
        windowClock.setVisible(true);
    }


    public static void main(String[] args)
    {
        AnalogClock clock = new AnalogClock();    //Create instance of the class AnalogClock
        clock.createButtons();                    //Create button window
        clock.createClockWindow();                //Create clock window
        clock.start();                            //Start clock procedure
    }
}