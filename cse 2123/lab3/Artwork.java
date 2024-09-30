/**
 * The {@code Artwork} class represents a piece of artwork with a title, 
 * the year it was created, and the artist who created it.
 * It provides methods to retrieve this information and to print a formatted summary.
 */
public class Artwork {
   // Declare private fields - title, yearCreated, artist

   /**
    * Default constructor that initializes the title to "unknown",
    * the yearCreated to -1, and the artist field to a new {@code Artist} object.
    */
   public Artwork() {
      this.title = "unknown";
      this.yearCreated = -1;
      this.artist = new Artist();  // Initialize new Artist object
   }

   /**
    * Constructor to initialize private fields with the provided values.
    *
    * @param titleVar       The title of the artwork.
    * @param yearCreatedVar The year the artwork was created.
    * @param artistVar      The artist who created the artwork.
    */
   public Artwork(String titleVar, int yearCreatedVar, Artist artistVar) {
      this.title = titleVar;
      this.yearCreated = yearCreatedVar;
      this.artist = artistVar;
   }

   /**
    * Gets the title of the artwork.
    *
    * @return The title of the artwork.
    */
   public String getTitle() {
      return this.title;        
   }

   /**
    * Gets the year the artwork was created.
    *
    * @return The year the artwork was created.
    */
   public int getYearCreated() {
      return this.yearCreated;
   } 

   /**
    * Prints information about the artist and the artwork.
    * First, it calls the {@code printInfo()} method in the {@code Artist} class
    * to print the artist's information, and then it prints the artwork's title and year created.
    */
   public void printInfo() {
      artist.printInfo();  // Print artist information
      System.out.printf("Title: %s, Year: %d%n", title, yearCreated);
   }
}
