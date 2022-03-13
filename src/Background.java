import bagel.Image;
import bagel.Window;

/**
 * Class to hold the background of the game.
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class Background {
    private Image backImage;

    /**
     * Constructor to set an image for the background instance
     * @param filename string of the location of background image
     */
    public Background(String filename) {
        backImage = new Image(filename);
    }

    /**
     *  Add a new image for the background instance
     * @param filename string of the location of background image
     */
    public void updateTo(String filename){
        backImage = new Image(filename);
    }

    /**
     * Draw the background on screen
     */
    public void render(){
        backImage.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

}
