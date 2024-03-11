import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.Timer;

/**
 * The GamePanel class represents a specialized panel for a game.
 * It extends the @code JPanel class to inherit its properties and behaviors
 * while adding its own unique functionalities.
 * This class also implements the ActionListener interface to handle action events.
 * Note the grid for the game is on a 2-D axis;
 * the leftmost vertical axis -> x = 0, rightmost -> x = width of screen
 * the uppermost vertical axis -> y = 0, lowermost vertical axis -> y = height of screen
 */
public class GamePanel extends JPanel implements ActionListener {

    // initialize timer and random objects for later
    Timer timer;
    Random random;

    // create immutable height, width, unit ("block"), composite units ("how many blocks on screen"), in-game i/o delay 
    // for inlining with bytecode, improving runtime
    // also for readability -- lets user know they are constants
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 75;

    // create 2D arrays to hold game dimensions unique to class instance
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    // beginning size for snake
    int bodyLength = 5;
    
    // indicates if game has started or not
    boolean gameOn = false;

    // initialize variables for apples consumed, and their x,y locations
    int applesEaten, appleX, appleY;

    // starting direction the snake moves: R - right, L - left, D - down, U - up
    char direction = 'R';

    /**
     * This method loads features in the panel & starts game by calling startGame
     * MyKeyAdapter receives keyboard events for the game
     */
    GamePanel() {
        random = new Random(); // initialize random instance
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // dimension for the panel
        this.setBackground(new Color(111, 189, 85)); // background color
        this.setFocusable(true); 
        this.addKeyListener(new MyKeyAdapter()); // get key input 
        startGame(); // start the game
    }
    
    /**
     * This method initializes apples, timers, & movement logic
     * We add an apple to the screen, start moving the snake, and add a timer
     */
    public void startGame() {
        addApple();
        gameOn = true;
        timer = new Timer(DELAY, this); // implemented ActionListener... can use current instance of GamePanel
        timer.start();
    }

    /**
     * This method first calls the parent class's paintComponent method to ensure proper painting of the JPanel. 
     * Then, it calls draw to paint the game components onto the panel.
     * Overrides paintComponent in JPanel to paint component.
     * 
     * @param g Graphics object used for painting
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // call on the parent class, JPanel w/ super
        draw(g); // call upon draw after game is painted
    }

    /**
     * The draw method creates the in-game and post-game graphics
     * 
     * @param g Graphics object used for painting
     */
    public void draw(Graphics g) {

        // in-game graphics
        if(gameOn) {
            // create a checkboard pattern on the board
            int rows = SCREEN_HEIGHT / UNIT_SIZE;
            int cols = SCREEN_WIDTH / UNIT_SIZE;

            // this loop checks if the row we're on is even or odd.
            // if even, draw a dark green at every other square starting at the 0th
            // if odd, draw at every other square starting at the 1st
            for (int y = 0; y < rows; ++y) {
                for (int x = (y % 2 == 0) ? 0 : 1; x < cols; x += 2) {
                    g.setColor(new Color(102, 171, 79));
                    g.fillRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
            }

            // draw apple
            g.setColor(Color.RED); // initialize color first
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // fill in unit with said color

            // draw snake
            for(int i = 0; i < bodyLength; ++i) {
                // head of snake
                if (i==0) {
                    g.setColor(new Color(6, 37, 122));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                // body of snake (alternating colors)
                else {
                    switch(i%2) {
                        case (1):
                            g.setColor(new Color(20, 117, 208));
                            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                            break;
                        case (0):
                            g.setColor(new Color(9, 78, 196));
                            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                            break;
                    }
                }
            }
        
            // score, or apples eaten, display
            String scoreString = "Score: " + applesEaten;
            g.setColor(Color.red);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont()); // used to center-align text
            g.drawString(scoreString, (SCREEN_WIDTH - metrics.stringWidth(scoreString))/2, g.getFont().getSize()); // center-top align
        }
        
        // post-game graphics
        else {
            gameOver(g);
        }
    }

    /**
     * This method sets randomized x, y coordinates for an apple in the game.
     */
    public void addApple() {
        appleX = (int)random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE; // random x location in the game frame
        appleY = (int)random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE; // random yy location in the game frame
    }

    /**
     * This method moves the position of the snake in a for loop,
     * and sets direction for it to move with a switch statement over the direction char
     */
    public void move() {
        // start from the head of the snake {i} moving to its tail {1}
        for(int i = bodyLength; i > 0; --i) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        // note: subtract/add bc Jpanel (0,0) is in top-left
        switch(direction) {
            case 'L': // left
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': // right
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'U': // up
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D': // down
                y[0] = y[0] + UNIT_SIZE;
                break;
        }
    } 

    /**
     * The checkApple method checks coordinate of head & apple are the same.
     * If they are, an apple is consumed, and we add to the snake body and the score
     */
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyLength++;
            applesEaten++;
            addApple();
        }
    } 

    /**
     * The checkCollisions method checks for collisions for the borders and the snake itself
     */
    public void checkCollisions() {
        // snake (head) collides with itself
        for (int i = bodyLength; i > 0; --i) {
            if ((x[0] == x[i]) && y[0] == y[i]) {
                gameOn = false;
            }
        }

        //snake (head) collides with borders
        if (x[0] < 0) { // left
            gameOn = false;
        }
        if (x[0] > SCREEN_WIDTH) { // right
            gameOn = false;
        }
        if (y[0] < 0) {
            gameOn = false; // top
        }
        if (y[0] > SCREEN_HEIGHT) {
            gameOn = false; // bottom
        }

        if (!gameOn) {
            timer.stop(); 
        }
    } 

    /**
     * The gameOver method displays a screen for the post-game message
     * Utilizse FontMetrics to get dimensions of text string, centering it on the screen
     * 
     * @param g Graphics object used for painting
     */
    public void gameOver(Graphics g) {
        // game over text display
        String gameOverText = "Game Over";
        g.setColor(Color.red);
        g.setFont(new Font("Courier New", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont()); // used to center-align text
        g.drawString(gameOverText, (SCREEN_WIDTH - metrics1.stringWidth(gameOverText))/2, SCREEN_HEIGHT/2); // center align

        // final score display
        String scoreString = "Score: " + applesEaten;
        g.setColor(Color.red);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont()); // used to center-align text
        g.drawString(scoreString, (SCREEN_WIDTH - metrics2.stringWidth(scoreString))/2, g.getFont().getSize()); // center-top align
    } 

    /**
     * MyKeyAdapter is an inner class that extends KeyAdapter.
     * Handles key events for the game, allowing player to control snake's movement
     */
    public class MyKeyAdapter extends KeyAdapter {
        
        /**
         * This method sets the direction of the snake based on the key pressed by the player.
         * @param e KeyEvent object representing key press event.
         */        
        @Override
        public void keyPressed(KeyEvent e) {

            // limit user to 90-degree turns, not 180
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOn) {
            move(); // start moving the snake
            checkCollisions(); // check for collisions
            checkApple(); // check for apples eaten
        }
        repaint(); // re-rendering of components
    }
}