//Noah H
//Program creates robust file reader and writer
//MyUtils
//11-17-20

import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;


public class MyUtils
{
  
   /**
   *   Description: Main method where all other methods are coordinated from
   *
   */
  
  public static void main(String[] args) throws FileNotFoundException
  {
    Scanner console = new Scanner(System.in);
    
    getFileScanner(console);
    getWriterScanner(console);
       
  }
    
   /**
   *   Description: Continues to ask the user for a file until the user enters one that exists and can be read
   *
   *   @param Scanner console represents the input from the user
   *   
   *   @return scan returns the scanner for the file if it exists
   */
  
  public static Scanner getFileScanner(Scanner console) throws FileNotFoundException
  {
    System.out.print("Input file name: ");
    String fileName = console.next();
    File name = new File(fileName);
    
    if(name.canRead())
    {
      Scanner scan = new Scanner(name);
      return scan;
    }
    
    else
    {
      while(!name.canRead())
      {
        System.out.print("File not found. Try again: ");
        fileName = console.next();
        name = new File(fileName);
      }
      Scanner scan = new Scanner(name);
      return scan;
    }
  }
  
   /**
   *   Description: Asks the user for a file output name 
   *
   *   @param Scanner console
   *   
   *   @return output returns the Print Stream
   */
  
  public static PrintStream getWriterScanner(Scanner console) throws FileNotFoundException
  {
    System.out.print("Output file name: ");
    String outFileName = console.next();
    File name = new File(outFileName);
    PrintStream output = new PrintStream(name);
    
    return output;
  }
}