# Java Analog Clock
## Overview
The following repo contains the program for an analog clock. The function of the program is very simple: Display an analog clock and allow the user to choose between three different backgrounds by clicking buttons. The exit button ends the program.

## Guideline and Function
Run the program by importing the project in IntelliJ and execute the main Class. By default the program starts with two windows. One window displays the clock. The second, minute, and hour hand of the clock will start to move at the start of the program. The second window contains buttons. At the beginning of the program the background for the clock is white. By pressing one of the three design buttons the user can switch between three background options. The exit button closes the program.

## Source Code Review
The code contains two main sections: Class methods and the main method. The class methods can be divided into three sections: 

1. A method responsible for the drawing of the background
2. A method responsible for the drawing of second, minute, and hour hands of the clock
3. And thread methods responsible for the execution of the methods within a thread

The main method can be divided into two section:
1. A section responsible for the creation of windows, buttons, and button input
2. And a section responsible for the creaton of a class object that is used to provide the clock functionality

The main classes use in drawing the background and clock hands are the standard Java graphics, and graphics2D classes.

At the start of the program we import the classes used in the application:
```java
import java.awt.*;                      //--> Graphics object
import java.awt.event.ActionEvent;      //--> Action event for button
import java.awt.event.ActionListener;   //--> Action listener for button
import java.awt.image.BufferedImage;    //--> Save image
import java.io.File;                    //--> Image import
import java.io.IOException;             //--> Exception handling
import java.util.Date;                  //--> Getting date and time
import java.text.SimpleDateFormat;      //--> Formatting date and time
import javax.imageio.ImageIO;           //--> Import images
import javax.swing.*;                   //--> Create container and windows
```

The drawBackground method is used to draw the background of the Clock. 

Different files are imported depending on the choice of the user:
```java
    private void drawBackground(Graphics g)
    {
        BufferedImage img = null;
        if (design==1) {
            try {
                img = ImageIO.read(new File("image0.jpg"));
            } catch (
                    IOException e) {
            }
```

Uses image to draw background:
```java
    g.drawImage(img, 0, 0, null);
```
Adds roman numerals to the background:
```java
    g.setColor(Color.black);
    g.drawString("XII", xposition - 20, yposition - 220);
```

Type casting is used to cread a 2D graphics object because we want to display 2D clock hands:
```java
   Graphics2D g = (Graphics2D) g1;
```

Formatted date function is used to acquire second, minutes, and hour values:
```java
    formattedDate.applyPattern("s");
    second = Integer.parseInt(formattedDate.format(date));
```
Those values are used as input for a function that calculates coordinates:
```java
    xsecond = (int)(Math.cos(second * Math.PI / 30 - Math.PI / 2) * 220 + xposition);
```
Additional attributes of the clock hands are set and the hands are drawn:
```java
        g.setStroke(new BasicStroke(6)); 
        g.setColor(Color.black);
        g.drawLine(xposition, yposition, xsecond, ysecond);
```

The thread methods are used to execute the code above within a thread. start() puts the thread in runnable state: 
```java
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }
```
run() starts the thread
```java

    public void run()  
    {
        while (thread!=null) {
            try {
                Thread.sleep(50); //pauses execution for 50 milliseconds
            } catch (InterruptedException e) {
            }  // try catch //used because other threads can pause thread execution
            repaint();      // paints und updates
        }
    }
```    
update() is called internally by repaint()
```java
    public void update(Graphics g) 
    {
        paint(g);
    }

```

In the main method JFrame objects are used as containers. Those containers serve as the main windows.
Buttons are created to determine user input:
```java
        JFrame buttonWindow = new JFrame("Button window");
        final JTextField textfield = new JTextField();
        textfield.setBounds(50,25, 150,20);

        JButton button3=new JButton("Click here for design 3");
        
                button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textfield.setText("Design 1");
                setBackground(1);
            }
        });
 ```   
A second JFrame object contains and displays the clock:
```java
    JFrame windowClock = new JFrame();
    Color c = new Color(255, 255, 255);
    windowClock.setBackground(c);
    windowClock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    windowClock.setBounds(0, 0, 600, 600);
    windowClock.getContentPane().add(clock);
    windowClock.setVisible(true);
```
clock.start() starts the clock procedure:
```java
    clock.start();
```

## Authors
Thomas Durlacher, Yassin Elwan, Enes Berk, Lisa Ebenbauer