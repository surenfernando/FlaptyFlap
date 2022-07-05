import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;


/**
 * Bird Class
 * @author: Suren Fernando
 */
public class Bird {
    private final Image WNG_UP;
    private final Image WNG_DWN;
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;

    private int score = 0;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    protected LifeBar birdHealth;



    /**
     * Constructor to set bird wing up/down images and speed settings
     * @param wngUp filename string of the location of wingUp image
     * @param wngDwn filename string of the locatin of wingDown image
     */
    public Bird(String wngUp, String wngDwn) {
        this.WNG_UP = new Image(wngUp);
        this.WNG_DWN = new Image(wngDwn);
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = WNG_DWN.getBoundingBoxAt(new Point(X, y));
    }


    /**
     * State update to implement bird flying and falling
     * @param input any input scanned during game play
     * @return Rectangle object of surrounding the birds updated location
     */
    public Rectangle update(Input input) {
        frameCount += 1;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
            renderWngDn();
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                renderWngUp();
                boundingBox = WNG_UP.getBoundingBoxAt(new Point(X, y));
                frameCount = 0;
            }
            else {
                renderWngDn();
                boundingBox = WNG_DWN.getBoundingBoxAt(new Point(X, y));
            }
        }

        y += yVelocity;
        return boundingBox;
    }


    /**
     * Check if the birds y coordinate is out of window border
     */
    public void boundCheck(){
        if ((y > Window.getHeight()) || (y < 0)){
            dead();
            y = INITIAL_Y;
        }
    }


    /**
     * Render the Wing Up image of the bird
     */
    protected void renderWngUp(){
        WNG_UP.draw(X,y);
    }


    /**
     * Render the Wing Down image of the bird
     */
    protected void renderWngDn(){
        WNG_DWN.draw(X,y);
    }


    /**
     * Returns the location of the bird encapsulated in a Rectangle
     * @return Rectangle object of bird's current location
     */
    public Rectangle getBox() {
        return boundingBox;
    }


    /**
     * Display the LifeBar object associated to the bird instance
     */
    public void healthBar(){
        birdHealth.render();
    }


    /**
     * Returns the current score of the bird
     * @return Integer score
     */
    public int score(){
        return score;
    }


    /**
     * Increment bird's score
     */
    public void addScore(){
        score++;
    }


    /**
     * Reduce bird's life by one and update Life Bar object
     */
    public void dead(){
        birdHealth.dead();
    }


    /**
     * Retrieve bird's life state
     * @return boolean value of birds health
     */
    public boolean isAlive(){
        return birdHealth.livesLeft()>0;
    }


    /**
     * Retrieve bird's X coordinate
     * @return Double value of X coordinate of bird
     */
    public double getX(){
        return X;
    }


    /**
     * Retrieve bird's Y coordinate
     * @return Double value of Y coordinate of bird
     */
    public double getY(){
        return y;
    }
}
