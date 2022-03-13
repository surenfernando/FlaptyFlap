import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Pipe Class, a template to construct a functional pipe that needs to be spawned in game play.
 */
public class Pipes {
    private final Image PIPE_IMAGE;
    protected final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);

    private boolean collided = false;
    private boolean gotScore = false;
    private final int PIPE_GAP = 168;
    private static int pipeSpeed = 5;
    private double topPipeY = -Window.getHeight()/2.0;
    private double botPipeY = (Window.getHeight()/2.0)+PIPE_GAP;
    private double pipeX = Window.getWidth();
    private boolean damaged = false;
    private String type;


    /**
     * Create pipe
     * @param pipeImg filename string of the location of pipe image
     * @param pipeY Y coordinate the pipe should be spawned at
     * @param pipeType Type of pipe
     */
    public Pipes(String pipeImg, double pipeY, String pipeType){
        PIPE_IMAGE = new Image(pipeImg);
        topPipeY += pipeY;
        botPipeY += pipeY;
        type = pipeType;
    }


    /**
     * Render pipe set (both top and bottom)
     */
    public void renderPipeSet() {
        PIPE_IMAGE.draw(pipeX, topPipeY);
        PIPE_IMAGE.draw(pipeX, botPipeY, ROTATOR);
    }


    /**
     * Status update of pipe set (both top and bottom)
     */
    public void update() {
        if (pipeX>= -PIPE_IMAGE.getWidth()){
            renderPipeSet();
            pipeX -= pipeSpeed;
        }
    }


    /**
     * Update movement speed of pipe set (both top and bottom)
     * @param newSpeed Integer new speed value
     */
    public static void setPipeSpeed(int newSpeed){
        pipeSpeed = newSpeed;
    }


    /**
     * @return pipeSpeed, current speed of pipe set
     */
    public static int getPipeSpeed(){
        return pipeSpeed;
    }


    /**
     * @return Rectangle object at the current location of the Top pipe
     */
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, topPipeY));

    }


    /**
     * @return Rectangle object at the current location of the Bottom pipe
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, botPipeY));
    }


    /**
     * Set pipe as collided
     */
    public void setCollided(){
        collided = true;
    }


    /**
     * @return boolean, collided status of pipe set
     */
    public boolean getCollided(){
        return collided;
    }


    /**
     * Set pipe as scored
     */
    public void setScore(){
        gotScore = true;
    }


    /**
     * @return boolean, score status of pipe set
     */
    public boolean scoredAlready(){
        return gotScore;
    }


    /**
     * @return double, x coordinate of the pipeset
     */
    public double getX(){
        return pipeX;
    }


    /**
     * @return double, y coordinate of the top pipe
     */
    public double getTopY(){
        return topPipeY;
    }


    /**
     * @return double, y coordinate of the bottom pipe
     */
    public double getBotY(){
        return botPipeY;
    }


    /**
     * Check if bird has collided with a flame
     * @param bird used to check for collisions
     */
    public void flameCollision(Bird bird){
    }


    /**
     * Set pipe as damaged
     */
    public void damage(){
        damaged = true;
    }


    /**
     * @return boolean, damage status of pipe set
     */
    public boolean damageStatus(){
        return damaged;
    }


    /**
     * @return String, get pipe type
     */
    public String getType(){
        return type;
    }
}
