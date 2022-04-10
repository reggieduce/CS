

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.io.*;
import java.awt.event.*;
import java.util.Random;
public class ProjectFrame extends JFrame
{  
   private Container contents = getContentPane();
   
   public ProjectFrame()
   {
      super("Project");
      
      //add panel to frame
      contents.add(new ProjectPanel());  
        
      //black border that goes around the frame
      getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK));
      //set size of frame
      setSize(834,657); 
      
      //set visible
      setVisible(true);      
   }
   
   public static void main(String[] args)
   {
      ProjectFrame theFrame = new ProjectFrame();
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}
