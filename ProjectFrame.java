

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
      
      contents.add(new ProjectPanel());  
        
      getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK));
      setSize(834,657); 
      

      setVisible(true);      
   }
   
   public static void main(String[] args)
   {
      ProjectFrame theFrame = new ProjectFrame();
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}
