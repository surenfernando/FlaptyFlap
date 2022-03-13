/**
 * Level One Bird inherits from Bird
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class lvlOneBird extends Bird{
    private final int LIVES = 6;
    private boolean hasWeapon = false;

    /**
     * Create level one bird
     * @param wngUp filename string of the location of wingUp image
     * @param wngDwn filename string of the locatin of wingDown image
     */
    public lvlOneBird(String wngUp, String wngDwn) {
        super(wngUp, wngDwn);
        birdHealth = new LifeBar(LIVES);
    }

    /**
     * @return boolean status of weapon presence
     */
    public boolean Weapon(){
        return hasWeapon;
    }

    /**
     * Record weapon collection
     */
    public void weaponCollected(){
        hasWeapon = true;
    }

    /**
     * Record weapon dispatch
     */
    public void weaponShot(){
        hasWeapon = false;
    }
}
