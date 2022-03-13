import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Steel Pipe inherits from Pipe
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class SteelPipes extends Pipes{

    private final Image FIRE = new Image("res/level-1/flame.png");
    private final double FIRE_CENTER = FIRE.getHeight()/2.0;
    private final double FIRE_GAP = 168.0-FIRE.getHeight();
    private int flameDuration = 30;
    private int flameRate = 20;
    private boolean flameOn = false;
    private int frameCount = 0;
    private int flameFrame = 0;
    private double upFlameY = 0;
    private double downFlameY = 0;


    /**
     * Create steel pipe
     * @param pipeImg filename string of the location of steel pipe image
     * @param pipeY Y coordinate the pipe should be spawned at
     */
    public SteelPipes(String pipeImg, double pipeY) {
        super(pipeImg, pipeY, "steel");
    }


    /**
     * Status update to render flames and pipes at logical frame rates
     */
    @Override
    public void renderPipeSet() {
        super.renderPipeSet();

        frameCount++;
        if (frameCount%flameRate == 0 || flameOn){
            flameOn = true;
            renderFlames();
            flameFrame++;
            if (flameFrame == flameDuration){
                flameOn = false;
                flameFrame = 0;
            }
            if (frameCount%flameRate==0){
                frameCount = 0;
            }
        }
    }


    /**
     * Render flames on both pipes (top and bottom)
     */
    public void renderFlames(){
        upFlameY = getTopY()+(Window.getHeight()/2.0)+FIRE_CENTER;
        downFlameY = upFlameY+FIRE_GAP;
        FIRE.draw(getX(), upFlameY);
        FIRE.draw(getX(), downFlameY);
    }


    /**
     * @return Rectangle object at the current location of the Top Flame
     */
    public Rectangle TopFlameBox(){
        return FIRE.getBoundingBoxAt(new Point(getX(), upFlameY));
    }


    /**
     * @return Rectangle object at the current location of the Bottom Flame
     */
    public Rectangle BottomFlameBox(){
        return FIRE.getBoundingBoxAt(new Point(getX(), downFlameY));
    }


    /**
     * Check if bird has collided with a flame
     * @param bird used to check for collisions
     */
    @Override
    public void flameCollision(Bird bird) {
        if (flameOn){
            if (bird.getBox().intersects(TopFlameBox()) ||
                    bird.getBox().intersects(BottomFlameBox())) {
                if (!getCollided()) {
                    setCollided();
                    bird.dead();
                }
            }
        }
    }
}