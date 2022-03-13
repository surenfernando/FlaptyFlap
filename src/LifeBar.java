import bagel.Image;

/**
 * Life Bar Class to record bird's active health status.
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class LifeBar {
    private final Image LIFE = new Image("res/level/fullLife.png");
    private final Image DEAD = new Image("res/level/noLife.png");
    private final int MAX_LIVES;
    private final double Y = 15;
    private final double INITIAL_X = 100;
    private int remainingLives;

    /**
     * Constructing Life Bar
     * @param lives total lives available at the time of birds instantiating.
     */
    public LifeBar(int lives) {
        this.MAX_LIVES = lives;
        this.remainingLives = MAX_LIVES;
    }

    /**
     * Draw life bar with total lives and remaining lives
     */
    public void render(){
        int deadCount = MAX_LIVES - remainingLives;
        double tempX = INITIAL_X;
        for (int x=0; x<remainingLives; x++){
            LIFE.drawFromTopLeft(tempX, Y);
            tempX += 50;
        }

        for (int x=0; x<deadCount; x++){
            DEAD.drawFromTopLeft(tempX, Y);
            tempX += 50;
        }
    }

    /**
     * Reduce remaining lives by one, kill the bird.
     */
    public void dead(){
        remainingLives--;
    }

    /**
     * @return Integer lives left
     */
    public int livesLeft(){
        return remainingLives;
    }
}
