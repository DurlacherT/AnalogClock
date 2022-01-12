# Java Analog Clock
## Overview
The following repo contains the program for an analog clock. The function of the program is very simple: Display an analog clock and allow the user to choose between three different backgrounds by clicking buttons. The exit button ends the program.

## Description of the program function
Run the program by importing the project in IntelliJ and executing the main Class. By default the program starts with two windows. One window displays the clock. The second, minute, and hour hand of the clock will start to move at the start of the program. The second window contains buttons. At the beginning of the program the background for the clock is white. By pressing one of the three design buttons the user can switch between three background options. The exit button closes the program.

## Source Code Review
The code contains two main sections: Class methods and the main method. The class methods can be divided into three sections: 

a method responsible for the drawing of the background
a method responsible for the drawing of second, minute, and hour hands of the clock
and thread methods responsible for the execution of the methods within a thread

The main method can be divided into two section:
a section responsible for the creation of windows, buttons, and button input
and a section responsible for the creaton of a class object that is used to provide the clock functionality

The main classes use in drawing the background and clock hands are the standard Java graphics, and graphics2D classes.

The drawBackground method is used to draw the background of the Clock. Different files are imported depending on the choice of the user.
```
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

            
