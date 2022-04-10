

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.io.*;
import java.awt.event.*;
import java.util.Random;
public class ProjectPanel extends JPanel
{  
     //instance variables
     private Player p;
     private boolean Akey = false;
     private boolean Dkey = false;
     private boolean Wkey = false;
     private int N = 0;
     private double jump = 0.0;
     private Timer timer = new Timer(10, new actionListener());  
     private int timerCount =  0;
     private ArrayList<ArrayList<GameObject>> numArray = new ArrayList<ArrayList<GameObject>>();
      
   
   //panel constructor    
   public ProjectPanel()
   {
      super ();
      //set size and color
      setSize(800,600);
      setBackground(Color.GRAY);
     
      Scanner read;
     
      int x, y, rows, cols;
      // add key listnere to panel and make is focusable 
      addKeyListener(new KeyEventDemo());
      setFocusable(true);
         
         //read in file inside a try catch
          try
          {  
             
              read = new Scanner(new File("Project.txt"));
              
               //x and y are the position of the player object 
               x  = read.nextInt();
               y = read.nextInt();
               //rows and cols is how big the array of game objects will be
               rows = read.nextInt();
               cols = read.nextInt();
               
               // create player object and give it correct color and position
               p = new Player((x*25) + 12, (y*25) + 12, Color.RED);
               
               
              int numChoice;
              //loop throught the file and add the game objects to the numaArray
              for(int i = 0; i < rows; i++)
              {
                  ArrayList<GameObject> innerList  = new ArrayList<GameObject>();

                  for(int j = 0; j < cols; j++)
                  {
                     numChoice = read.nextInt();
                     //if the number in the file equals a 1 it will be a block 
                     if(numChoice == 1)
                     {
                        innerList.add(new Block((j * 25) + 12, (i * 25) + 12));
                     }
                     //if the number in the file equals a 2 it will be a Victory block
                     else if (numChoice == 2)
                     {
                        innerList.add(new VictoryBlock((j * 25) + 12, (i * 25) + 12));
                     }
                     //if not 1 or 2 it will add null to the array list
                     else
                     {
                        innerList.add(null);
                     }
                  }
                numArray.add(innerList);
              }
              //start the timer
             timer.start();
          }
          // If file not found will catch it and give the following exception
          catch(FileNotFoundException e)
          {
              System.out.println("Unable to load file");
              System.exit(0);
          }
          
       
   }
   
   public class actionListener implements ActionListener
   {
      public void actionPerformed(ActionEvent actionEvent)
      {
           
         //gravity code
         if(!(p.isOnGround(numArray)) && (int)jump == 0)
          {
               timerCount++;
             
               //Every 20 timer clicks N will increment by 1 
               if(timerCount % 20 == 0)
               {
                       if(N < 7) 
                       {
                            N++;
                       }
                }
                
                //for loop that will move and repaint the player object as if it is gravity          
                for(int i=0; i< N; i++)
                {
                     p.move(0, 1, numArray);
                     repaint();
                     
                     //if it on ground will check if it collides with a victory block if not will set N to 0 and timer count back to 0
                     if(p.isOnGround(numArray))
                     {
                        if( p.getVictory())
                        {
                             JOptionPane.showMessageDialog(null, "Victory!!!");
                             System.exit(0);
                         }
                         else
                         {
                              N = 0;
                              timerCount = 0;
                              break;
                         }
                     }
                
                  }
              }  
          


     
          //if the A key is pressed will move the player block to the left and then check if it collides with a victory block
          if(Akey)
          {
             p.move(-1,0,numArray);
             repaint();
             if(p.getVictory())
             {
                JOptionPane.showMessageDialog(null, "Victory!!!");
                System.exit(0);
             }
          }
          //if the D key is pressed will move the player block to the right and then check if it collides with a victory block
          if(Dkey)
          {
             p.move(1,0,numArray);
             repaint();
             if(p.getVictory())
             {
                JOptionPane.showMessageDialog(null, "Victory!!!");
                System.exit(0);
             }

          }
          //if the W key is pressed will make the player jump up 
          if (Wkey) 
          {
             
             if(p.isOnGround(numArray))
             {
                 jump = 7;
             }
          }
          
          if((int)jump > 0)
          {
             
             jump = jump - .1; 
             for(int i = 0; i < (int)jump; i++)
             {
                if(!p.move(0, -1, numArray))
                {
                   //if get victory returns true it will show message victory and exit progam
                   if(p.getVictory())
                   {
                         JOptionPane.showMessageDialog(null, "Victory!!!");
                         System.exit(0);
                    }
                    jump = 0.0;
                 }
                 repaint();
              }
         }
      }
   }
   
   public class KeyEventDemo implements KeyListener
   {
       public void keyTyped(KeyEvent e){ }
       
       public void keyReleased(KeyEvent e)
       { 
           //If Akey is released will set it to false
           if(e.getKeyCode() == KeyEvent.VK_A)
           {
               Akey = false;
           }
           //If Dkey is released will set it to false
           if(e.getKeyCode() == KeyEvent.VK_D)
           {
               Dkey = false;
           }
           //If Wkey is released will set it to false
           if(e.getKeyCode() == KeyEvent.VK_W)
           {
               Wkey = false;
           }
 
       }
       
       public void keyPressed(KeyEvent e)
       { 
           // If Akey is pressed will set it to true
           if(e.getKeyCode() == KeyEvent.VK_A)
           {
               Akey = true;
           }
            // If Dkey is pressed will set it to true
           if(e.getKeyCode() == KeyEvent.VK_D)
           {
               Dkey = true;
           }
            // If Wkey is pressed will set it to true
           if(e.getKeyCode() == KeyEvent.VK_W)
           {
               Wkey = true;
           }
        }
     }
       
   
   public class GameObject
   {  
      protected int xpos;
      protected int ypos;
      protected Color col = Color.BLACK;
     
      //Pass in the x and y pos and color of the game object
      public GameObject(int x, int y, Color c)
      {
          xpos = x;
          ypos = y;
          col = c;
      }
      //method to get x
      public int getx()
      {
         return xpos;
      }
      // method to get y
      public int gety()
      {
         return ypos;
      }
      // method to set x
      public void setX(int x)
      {
          xpos = x;
      }  
      // method to set y
      public void setY(int y)
      {
          ypos = y;
      }  
     
      // method to get color
      public Color getColor()
      {
         return col;
      }
     
      // method collides
      public boolean collides(GameObject other)
      { 
        // Returns a boolean, takes in GameObject as a parameter
        // if this object is the same as the parameter return false

         if(this == other)
         {
             return false;
         }
         else
         {
             int topthis = this.ypos - 12;
             int bottomthis = this.ypos + 12;
             int leftthis = this.xpos - 12;
             int rightthis = this.xpos + 12;
             int topother = other.ypos - 12;
             int bottomother = other.ypos + 12;
             int leftother = other.xpos - 12;
             int rightother = other.xpos + 12;
             return !((topthis > bottomother) || (bottomthis < topother) || (leftthis > rightother) || (leftother > rightthis));
         }
      }
     
      public void draw(Graphics g)
      {
         g.setColor(getColor());
         g.fillRect(getx() - 12, gety() - 12, 25, 25);
     
      }
     
   }
   
   public class Player extends GameObject
   { 
      private boolean victory = false;
      //pass in x and y position and color for the player object
      public Player(int x, int y, Color c)
      {
         super(x, y, c);
      }
 
      public boolean isOnGround(ArrayList<ArrayList<GameObject>> numArray)
      {
        //increase y position by one
         ypos++;
         //if collides is true decrease y position by one and return true
         if(collides(numArray))
         {
             ypos--;
             return true;
         }
         ypos--;
         return false;
      }
     
      //method that returns if victory is true or not
      public boolean getVictory()
      {
         return victory;
      }
   
      public boolean move(int x, int y, ArrayList<ArrayList<GameObject>> numArray)
      {
            //will add x and y to xpos and ypos and 
            xpos = x + xpos;
            ypos = y + ypos;
            //if collides is true will minus x and y from position so it doesnt move and will return false
            if(collides(numArray))
            {
                xpos = xpos - x;
                ypos = ypos - y;
                return false;
            }
            return true;
      }
     
      public boolean collides(ArrayList<ArrayList<GameObject>> numArray)
      { 
          // get the index postion of where the block is the array list
          int fx = Math.floorDiv(xpos - 12, 25);
          int fy = Math.floorDiv(ypos - 12, 25);
         
          //create a temp game object    
          GameObject temp;
         
          
          temp = numArray.get(fy).get(fx);
          //check if temp is equal to null
          if(temp != null)
          {
              //check if temp collides with anything
              if(super.collides(temp))
              { 
                 //compare the colors and if it equals green set victory to true
                 if(temp.getColor().equals(Color.GREEN))
                 {
                     victory = true;
                     
                 }
                   return true;
              }
          }
         
          temp = numArray.get(fy + 1).get(fx);
           //check if temp is equal to null
          if (temp != null)
          {
              //check if temp collides with anything
              if(super.collides(temp))
              { 
                 //compare the colors and if it equals green set victory to true
                 if(temp.getColor().equals(Color.GREEN))
                 {
                     victory = true;
                     
                 }
                 return true;
              }
          }
         
          temp = numArray.get(fy).get(fx + 1);
          //check if temp is equal to null
          if (temp != null)
          { 
              //check if temp collides with anything
              if(super.collides(temp))
              {
                 //compare the colors and if it equals green set victory to true
                 if(temp.getColor().equals(Color.GREEN))
                 {
                     victory = true;
                    
                 }
                  return true;
              }
          }
         
          temp = numArray.get(fy + 1).get(fx + 1);
          //check if temp is equal to null
          if(temp != null)
          {
              //check if temp collides with anything
              if(super.collides(temp))
              {
                  //compare the colors and if it equals green set victory to true
                  if(temp.getColor().equals(Color.GREEN))
                  {
                     victory = true;
                  }
                  return true;
              }
          }  
         
           return false;
        }
       
     }
   
   
    public class Block extends GameObject
    {
        //pass in the x and y pos and set color to blue
        public Block(int x, int y)
        {
          super(x, y, Color.BLUE);
        }
       
    }
   
    public class VictoryBlock extends GameObject
    {
        //pass in the x and y pos and set color to green
        public VictoryBlock(int x, int y)
        {
          super(x, y, Color.GREEN);
        }
       
    }  
   
   
   
   public void paintComponent(Graphics g)
   {      
                   
       super.paintComponent(g);
       //loop through the array list and draw each object 
       for(int i = 0; i < numArray.size(); i++)
       {
           ArrayList<GameObject> innerList  = numArray.get(i);
           
           for(int j = 0; j < innerList.size(); j++)
           {
              if (innerList.get(j) != null)
              {
                  innerList.get(j).draw(g);
              }
           }
       
        }
       p.draw(g); 
                   
    }
   
  }
