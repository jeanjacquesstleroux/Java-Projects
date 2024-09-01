import java.util.Scanner;

/**
 * Program takes a user-defined size for an array, prompting
 * for Strings which populate an array of that length,
 * and outputs how many times each word occurs in the array, ignoring case.
 *
 * @author Jean-Jacques St. Leroux
 * @version 1.0
 */

public class LabProgram {

   /** 
   * Iterates over an array of strings and returns the amount of times
   * a particular word occurs in said array, neglectating case.
   *  
   * @param wordsList - the list of user words
   * @param listSize - the defined size of our array
   * @param currWord - the word to evaluate for its frequency
   * @return an integer of how many times currWord occurs in wordsList
   */
   public static int getWordFrequency(String[] wordsList, int listSize, String currWord) {
        
        int count = 0;

        for (int i = 0; i < listSize; i++) {
            if (currWord.equalsIgnoreCase(wordsList[i])) {
                count += 1;
            }
        }

        return count;
   }
    /**
    * Asks for an integer for a String array size, and then the strings.
    * Afterwards, loops/prints a line for each word and its count in the array.
    *
    * @param args command-line arguments
    */
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      int userCount = scnr.nextInt();
      String[] userList = new String[userCount];
      String wordEval; // the string to iterate over
      
      // deterministic loop to get each string from user
      for (int i = 0; i < userCount; i++) {
        userList[i] = scnr.next(); // read each next input 
      }

      // for each word in the list, print word and its count via getWordFrequency
      for (int i = 0; i < userCount; i++) {
        wordEval = userList[i];
        System.out.println(wordEval + " " + getWordFrequency(userList, userCount, wordEval));
      } 
   }
}
