import java.util.Scanner;

/**
 * LabProgram class draws a centered triangle using stars (*) and spaces.
 * 
 * @author stleroux.1
 */
public class LabProgram {
   
   /**
    * Returns a string of spaces.
    * 
    * @param numSpaces The number of spaces to generate.
    * @return A string consisting of 'numSpaces' spaces.
    */
   public static String getSpaces(int numSpaces) {
        String spacesToPrint = "";
        for (int i = 0; i < numSpaces; i++) {
            spacesToPrint += " ";
        }
        return spacesToPrint;
   }
   
   /**
    * Returns a string of stars.
    * 
    * @param numStars The number of stars to generate.
    * @return A string consisting of 'numStars' stars.
    */
   public static String getStars(int numStars) {
        String starsToPrint = "";
        for (int i = 0; i < numStars; i++) {
            starsToPrint += "*";
        }
        return starsToPrint;
   }
   
   /**
    * Recursively draws a centered triangle with the given base width.
    * 
    * @param starsToPrint The number of stars to print at the current level.
    *        Should start with the base width of the triangle.
    */
   public static void drawTriangle(int starsToPrint) {
        int triSpaces;
        // Bbse case: when there are no more stars to print, stop recursion
        if (starsToPrint <= 0) {
            System.out.print("");
        }
        else {            
            // Line length should be 19, and half of the spaces come before, so print those.
            triSpaces = (19 - starsToPrint) / 2;
            // Print the spaces followed by the stars
            System.out.println(getSpaces(triSpaces) + getStars(starsToPrint));
            starsToPrint -= 2;
            // recursive call
            drawTriangle(starsToPrint);
        }
   }
   
   /**
    * Main method to take user input and start drawing the triangle.
    * 
    * @param args Command-line argument
    */
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      int baseLength;
      baseLength = scnr.nextInt();
      drawTriangle(baseLength);
   }
}
