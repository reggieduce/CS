

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
           
           
         if(!(p.isOnGround(numArray)) && (int)jump == 0)
          {
               timerCount++;
             
               if(timerCount % 20 == 0)
               {
                       if(N < 7) 
                       {
                            N++;
                       }
                }
                           
                for(int i=0; i< N; i++)
                {
                     p.move(0, 1, numArray);
                     repaint();
                     
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
           if(e.getKeyCode() == KeyEvent.VK_A)
           {
               Akey = false;
           }
           if(e.getKeyCode() == KeyEvent.VK_D)
           {
               Dkey = false;
           }
           if(e.getKeyCode() == KeyEvent.VK_W)
           {
               Wkey = false;
           }
 
       }
       
       public void keyPressed(KeyEvent e)
       {
           if(e.getKeyCode() == KeyEvent.VK_A)
           {
               Akey = true;
           }
           if(e.getKeyCode() == KeyEvent.VK_D)
           {
               Dkey = true;
           }
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
     
     
      public GameObject(int x, int y, Color c)
      {
          xpos = x;
          ypos = y;
          col = c;
      }
     
      public int getx()
      {
         return xpos;
      }
     
      public int gety()
      {
         return ypos;
      }
     
      public void setX(int x)
      {
          xpos = x;
      }  
     
      public void setY(int y)
      {
          ypos = y;
      }  
     
     
      public Color getColor()
      {
         return col;
      }
     
     
      public boolean collides(GameObject other)
      {
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
      
      public Player(int x, int y, Color c)
      {
         super(x, y, c);
      }
 
      public boolean isOnGround(ArrayList<ArrayList<GameObject>> numArray)
      {
         ypos++;
         
         if(collides(numArray))
         {
             ypos--;
             return true;
         }
         ypos--;
         return false;
      }
     
      public boolean getVictory()
      {
         return victory;
      }
   
      public boolean move(int x, int y, ArrayList<ArrayList<GameObject>> numArray)
      {
            xpos = x + xpos;
            ypos = y + ypos;
           
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
          int fx = Math.floorDiv(xpos - 12, 25);
          int fy = Math.floorDiv(ypos - 12, 25);
         
                  
          GameObject temp;
         
          temp = numArray.get(fy).get(fx);
          
          if(temp != null)
          {
              if(super.collides(temp))
              { 
               
                 if(temp.getColor().equals(Color.GREEN))
                 {
                     victory = true;
                     
                 }
                   return true;
              }
          }
         
          temp = numArray.get(fy + 1).get(fx);
          if (temp != null)
          {
          
              if(super.collides(temp))
              { 
               
                 if(temp.getColor().equals(Color.GREEN))
                 {
                     victory = true;
                     
                 }
                 return true;
              }
          }
         
          temp = numArray.get(fy).get(fx + 1);
          if (temp != null)
          {
              if(super.collides(temp))
              {
                  if(temp.getColor().equals(Color.GREEN))
                 {
                     victory = true;
                    
                 }
                  return true;
              }
          }
         
          temp = numArray.get(fy + 1).get(fx + 1);
          if(temp != null)
          {
              if(super.collides(temp))
              {
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
        public Block(int x, int y)
        {
          super(x, y, Color.BLUE);
        }
       
    }
   
    public class VictoryBlock extends GameObject
    {
        public VictoryBlock(int x, int y)
        {
          super(x, y, Color.GREEN);
        }
       
    }  
   
   
   
   public void paintComponent(Graphics g)
   {      
                   
       super.paintComponent(g);
       
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
