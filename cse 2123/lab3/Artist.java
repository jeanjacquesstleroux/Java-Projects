/**
 * The {@code Artist} class represents an artist with a name, birth year, and death year.
 * It provides methods to retrieve this information and to print a formatted summary.
 */
public class Artist {
   // Declare private fields - artistName, birthYear, deathYear
   
   private String artistName;
   private int birthYear;
   private int deathYear;

   /**
    * Default constructor. Initializes artistName to "unknown", 
    * birthYear and deathYear to -1.
    */
   public Artist() {
      this.artistName = "unknown";
      this.birthYear = -1;
      this.deathYear = -1;
   }

   /**
    * Constructor to initialize private fields with the provided values.
    *
    * @param artistName The artist's name.
    * @param birthYear  The year the artist was born.
    * @param deathYear  The year the artist died, or -1 if still alive or unknown.
    */
   public Artist(String artistName, int birthYear, int deathYear) {
      this.artistName = artistName;
      this.birthYear = birthYear;
      this.deathYear = deathYear;
   }

   /**
    * Gets the artist's name.
    *
    * @return The name of the artist.
    */
   public String getName() {
      return this.artistName;
   } 

   /**
    * Gets the artist's birth year.
    *
    * @return The year the artist was born.
    */
   public int getBirthYear() {
      return this.birthYear;
   }

   /**
    * Gets the artist's death year.
    *
    * @return The year the artist died, or -1 if still alive or unknown.
    */
   public int getDeathYear() {
      return this.deathYear;
   }

   /**
    * Prints information about the artist. 
    * If the death year is -1, it prints "present" for the death year.
    * If both birth and death years are -1, it prints "unknown" for the artist's years.
    */
   public void printInfo() {
      if (deathYear == -1 && birthYear != -1) {
         System.out.printf("Artist: %s (%d to present)%n", artistName, birthYear);
      } else if (birthYear == -1) {
         System.out.printf("Artist: %s (unknown)%n", artistName);
      } else {
         System.out.printf("Artist: %s (%d to %d)%n", artistName, birthYear, deathYear);
      }
   }
}
