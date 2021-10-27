import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.awt.*;

public class Life
{
  public static final int PIXELSIZE = 3;
  
  public static void main(String[] args) throws FileNotFoundException
  {
    Scanner console = new Scanner(System.in);
    
    intro();
    
    Scanner scan = MyUtils.getFileScanner(console);
    PrintStream output = new PrintStream(MyUtils.getWriterScanner(console));
    
    int frames = getFrames(console);
    int steps = getSteps(console);
    
    int numRows = getRows(scan);
    int numColumns = getColumns(scan);
    
    DrawingPanel panel = new DrawingPanel(PIXELSIZE*numColumns, PIXELSIZE*numRows);
    panel.setBackground(Color.white);
    Graphics g = panel.getGraphics();
 
    char[][] charArray = createArray(scan, numRows, numColumns);
    char[][] charArrayNext = new char[numRows][numColumns];
 
    /*
    for(int i = 0; i < frames; i++)
    {
      charArrayNext = simulation(charArray, numRows, numColumns);
      
      for(int r = 0; r < numRows; r++)
      {
        for(int c = 0; c < numColumns; c++)
        {
          System.out.print(charArrayNext[r][c]);
        }
          System.out.println();  
      }
      System.out.println();
      
      char[][] temp = charArrayNext;
      charArrayNext = charArray;
      charArray = temp;
    }
    */  
      
    for(int i = 1; i < frames; i++) 
    {
      charArrayNext = simulation(charArray, numRows, numColumns);
      
      drawPixels(output, panel.getGraphics(), numRows, numColumns, charArrayNext);
      //output.print();
      
      panel.sleep(steps);
      
      char[][] temp = charArrayNext;
      charArrayNext = charArray;
      charArray = temp;
    }
    
  }
  
  public static void intro()
  {
    System.out.println("This program runs Conway's Game of Life");
  }
  
  public static int getFrames(Scanner console)
  {
    while (true)
    {
      
      System.out.print("Number of frames to run the simulation (0-5000): ");
      
      if (!console.hasNextInt())
      {
        console.nextLine();
        System.out.println("You must enter an *integer* between 0 and 5000.");
        console.nextLine();
      }
      
      else
      {
        int frames = console.nextInt();
        
        if (frames < 0 || frames > 5000)
        {
          System.out.println("Your number needs to be between 0 and 5000.");
        }
    
        else
        {
          return frames; 
        }
      }
    }
  }
  
  public static int getSteps(Scanner console)
  {
    while (true)
    {
      
      System.out.print("Time between steps in ms (1-5000): ");
      
      if (!console.hasNextInt())
      {
        console.nextLine();
        System.out.println("You must enter an *integer* between 1 and 5000.");
        console.nextLine();
      }
      
      else
      {
        int steps = console.nextInt();
        
        if (steps < 0 || steps > 5000)
        {
          System.out.println("Your number needs to be between 1 and 5000.");
        }
    
        else
        {
          return steps; 
        }
      }
    }
  }
  
  public static int getRows(Scanner scan)
  {
    String dimension = scan.next();
    Scanner numbers = new Scanner(dimension);
    
    int rows = numbers.nextInt();
    
    return rows;
  }
  
  public static int getColumns(Scanner scan)
  {
    String dimension = scan.nextLine();
    Scanner numbers = new Scanner(dimension);
    
    int columns = numbers.nextInt();
    
    return columns;
  }
  
  public static char[][] createArray(Scanner scan, int rows, int columns)
  {
    char[][] charArray = new char[rows][columns];
    
    try
    {
      while(scan.hasNextLine())
      {
      
        for(int r = 0; r < rows; r++)
        {
          String sentence = scan.nextLine();
          Scanner line = new Scanner(sentence);
        
            for(int c = 0; c < columns; c++)
            {
              charArray[r][c] = sentence.charAt(c);
              //System.out.print(charArray[r][c]);
            }
            //System.out.println();  
        } 
      }
      System.out.println("Simulation successful!");
    }
    
    catch(Exception e)
    {
      System.out.println("Error found in input file. Halting simulation.");
      System.exit(0);
    }
    
    return charArray;
  }
  
  public static char[][] simulation(char[][] charArray, int rows, int columns)
  {
    int liveNeighborCount;
    char[][] charArrayNext = new char[rows][columns];
    
    for(int r = 0; r < rows; r++)
    {     
        for(int c = 0; c < columns; c++)
        {
          liveNeighborCount = 0;
          
          if(r == 0 && c == 0)
          {
            if(charArray[r+1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c+1] == 'x')
            {
              liveNeighborCount++;
            }  
          }
          
          else if(r == rows-1 && c == columns-1)
          {
            if(charArray[r-1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c-1] == 'x')
            {
              liveNeighborCount++;
            } 
          
          }
          
          else if(r == 0 && c == columns-1)
          {
            if(charArray[r+1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
          }
          
          else if(r == rows-1 && c == 0)
          {
            if(charArray[r-1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c+1] == 'x')
            {
              liveNeighborCount++;
            } 
          }
          
          else if(c == 0)
          {
            if(charArray[r+1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c] == 'x')
            {
              liveNeighborCount++;
            } 
          }
          
          else if(c == columns-1)
          {
            if(charArray[r][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c] == 'x')
            {
              liveNeighborCount++;
            }
          }
          
          else if(r == 0)
          {
            if(charArray[r][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c+1] == 'x')
            {
              liveNeighborCount++;
            }
          }
          
          else if(r == rows-1)
          {
            if(charArray[r][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c+1] == 'x')
            {
              liveNeighborCount++;
            }
          }
          
          else
          {  
          
            if(charArray[r+1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c+1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r-1][c-1] == 'x')
            {
              liveNeighborCount++;
            }
            
            if(charArray[r+1][c+1] == 'x')
            {
              liveNeighborCount++;
            }
          }//else
            //System.out.print(liveNeighborCount);
            
            if(liveNeighborCount < 2 && charArray[r][c] == 'x')
            {
              charArrayNext[r][c] = '.';
            }
            
            else if(liveNeighborCount > 3 && charArray[r][c] == 'x')
            {
              charArrayNext[r][c] = '.';
            }
            
            else if((liveNeighborCount == 2 || liveNeighborCount == 3) && charArray[r][c] == 'x')
            {
              charArrayNext[r][c] = 'x';
            }
            
            else if(liveNeighborCount == 3 && charArray[r][c] == '.')
            {
              charArrayNext[r][c] = 'x';
            }
            
            else
            {
              charArrayNext[r][c] = charArray[r][c];
            }
            
        }//first for
        //System.out.println();  
    }//second for
    
    
    return charArrayNext;  
  }
  
  public static void drawPixels(PrintStream output, Graphics g, int rows, int columns, char[][] charArray)
  {
    for(int r = 0; r < rows; r++)
    {
        
      for(int c = 0; c < columns; c++)
      {
        if(charArray[r][c] == 'x')
        {
          g.setColor(Color.black);
          g.fillRect(PIXELSIZE*c, PIXELSIZE*r, PIXELSIZE, PIXELSIZE); 
        }
        else if(charArray[r][c] == '.')
        {
          g.setColor(Color.white);
          g.fillRect(PIXELSIZE*c, PIXELSIZE*r, PIXELSIZE, PIXELSIZE);
        }
      }
    } 
    
  }
}