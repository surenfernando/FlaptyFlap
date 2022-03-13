/**
 * Level Zero Bird inherits from Bird
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class lvlZeroBird extends Bird{
    private final int LIVES = 3;

    /**
     * Create level zero bird
     * @param wngUp filename string of the location of wingUp image
     * @param wngDwn filename string of the locatin of wingDown image
     */
    public lvlZeroBird(String wngUp, String wngDwn) {
        super(wngUp, wngDwn);
        birdHealth = new LifeBar(LIVES);
    }
}
