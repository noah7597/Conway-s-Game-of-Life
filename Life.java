//Noah H
//Program simulates Conway's Game of Life
//Life
//12-19-20

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.awt.*;

public class Life
{
  public static final int PIXELSIZE = 5; //sets the pixel size for each cell when the images are drawn

  /**
   *   Description: Main method where all other methods are coordinated from
   *
   */

  public static void main(String[] args) throws FileNotFoundException
  {
    Scanner console = new Scanner(System.in);

    intro();

    Scanner scan = MyUtils.getFileScanner(console);
    PrintStream output = new PrintStream(MyUtils.getWriterScanner(console));

    int frames = getFrames(console); //number of frames the world will rotate through
    int steps = getSteps(console); //time each frame will be shown in ms

    int numRows = getRows(scan); //number of rows in the world
    int numColumns = getColumns(scan); //number of columns in the world

    DrawingPanel panel = new DrawingPanel(PIXELSIZE*numColumns, PIXELSIZE*numRows);
    panel.setBackground(Color.white);
    Graphics g = panel.getGraphics();

    char[][] charArray = createArray(scan, numRows, numColumns); //Current array
    char[][] charArrayNext = new char[numRows][numColumns]; //next array

    for(int j = 1; j < frames; j++)
    {
      charArrayNext = simulation(charArray, numRows, numColumns);

      for(int r = 0; r < numRows; r++)
      {
        for(int c = 0; c < numColumns; c++)
        {
          if(j == frames - 1)
          {
            output.print(charArrayNext[r][c]);
          }
        }
        if(j == frames - 1)
        {
          output.println();
        }
      }

      char[][] temp = charArrayNext;
      charArrayNext = charArray;
      charArray = temp;
    }

    for(int i = 0; i < frames; i++)
    {
      charArrayNext = simulation(charArray, numRows, numColumns);

      drawPixels(panel.getGraphics(), numRows, numColumns, charArrayNext);

      panel.sleep(steps);

      char[][] temp = charArrayNext;
      charArrayNext = charArray;
      charArray = temp;
    }

  }

  /**
   *   Description: Introduction
   *
   */

  public static void intro()
  {
    System.out.println("This program runs Conway's Game of Life");
  }

  /**
   *   Description: The user enters the number of frames to be drawn
   *
   *   @param Scanner console is the paramater that allows the user to enter input to the computer
   *
   *   @return int frames is the number of frames to be shown
   */

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
        int frames = console.nextInt(); //number of frames to be shown

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

  /**
   *   Description: The user enters the amount of time for each frame to be shown
   *
   *   @param Scanner console is the paramater that allows the user to enter input to the computer
   *
   *   @return int steps is the amount of time each frame will be shown
   */


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
        int steps = console.nextInt(); //time each frame will be shown

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

  /**
   *   Description: Finds the number of rows of cells in the double array
   *
   *   @param Scanner scan is the parameter that allows the user to enter input to the computer
   *
   *   @return int rows is the number of rows in the world
   */

  public static int getRows(Scanner scan)
  {
    String dimension = scan.next();
    Scanner numbers = new Scanner(dimension);

    int rows = numbers.nextInt(); //number of rows in the world

    return rows;
  }

  /**
   *   Description: Finds the number of columns of cells in the double array
   *
   *   @param Scanner scan is the parameter that allows the user to enter input to the computer
   *
   *   @return int columns is the number of columns in the world
   */

  public static int getColumns(Scanner scan)
  {
    String dimension = scan.nextLine();
    Scanner numbers = new Scanner(dimension);

    int columns = numbers.nextInt(); //number of columns in the world

    return columns;
  }

  /**
   *   Description: Creates an initial array of the world
   *
   *   @param Scanner scan is the parameter that allows the user to enter input to the computer
   *   @param int rows is the number of rows in the world
   *   @param int columns is the number of columns in the world
   *
   *   @return char[][] charArray is the array generated of the initial world
   */

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
          }
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

  /**
   *   Description: Finds the neighbors of each cell and performs changes on the cell based on the rules of Conway's Game of life
   *
   *   @param char[][] charArray is the double character array that the method is performed on
   *   @param int rows is the number of rows in the world
   *   @param int columns is the number of columns in the world
   *
   *   @return char[][] charArrayNext is the array generated that represents the next generation of cells
   */

  public static char[][] simulation(char[][] charArray, int rows, int columns)
  {
    int liveNeighborCount; //counter for the number of neighbors of each cell
    char[][] charArrayNext = new char[rows][columns]; //array of next generation

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
        }

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

      }
    }


    return charArrayNext;
  }

  /**
   *   Description: This method draws a frame of the world
   *
   *   @param Graphics g represents the information in DrawingPanel.java
   *   @param int rows is the number of rows in the world
   *   @param int columns is the number of columns in the world
   *   @param char[][] charArray is the array this method will draw
   *
   */

  public static void drawPixels(Graphics g, int rows, int columns, char[][] charArray)
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
