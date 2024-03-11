import javax.swing.JFrame;

/**
 * Builds the top-level window which serves as the GUI container.
 * GameFrame extends JFrame, Swing's class which extends {@link java.awt.Frame}
 */

public class GameFrame extends JFrame {
    
    GameFrame(){

        // We add "this" for readability and for instance-specific operations.

        this.add(new GamePanel()); // initialize the GamePanel
        this.setTitle("Snake Game"); // set the title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the app upon closing the JFrame
        this.setResizable(false); // fixed size
        this.pack(); // fits JFrame around components
        this.setVisible(true); // show the frame
        this.setLocationRelativeTo(null); // aligns the game frame to middle of screen

    }

    }
