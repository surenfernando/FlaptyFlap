import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Weapon Class, a template to construct a functional weapon.
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class Weapon {
    Random rand = new Random();
    private final int ROCK_RANGE = 25;
    private final int BOMB_RANGE = 50;
    private final int MAX_Y = 500;
    private final int MIN_Y = 100;
    private Image weapon;
    private double weaponY;
    private double weaponX = Window.getWidth();
    private boolean pickedUp = false;
    private boolean shot = false;
    private static int travelSpeed = 5;
    private int shootSpeed = 5;
    private int shootFrame = 0;
    private int shootRange;
    private boolean lost = false;


    /**
     * Update weapon left movement speed (timescale)
     * @param newSpeed New speed of weapon
     */
    public static void setWeaponSpeed(int newSpeed){
        travelSpeed = newSpeed;
    }


    /**
     * Get current left speed of the weapon
     * @return int, speed of weapon.
     */
    public static int getWeaponSpeed(){
        return travelSpeed;
    }


    /**
     * Update weapon left movement speed (timescale)
     * @param weapon filename string of the location of weapon image
     * @param range integer shoot range of weapon
     */
    public Weapon(String weapon, int range) {
        this.weapon = new Image(weapon);
        int customY = rand.nextInt(MAX_Y-MIN_Y)+MIN_Y;
        weaponY = (double)customY;
        shootRange = range;
    }


    /**
     * Render weapon on screen
     */
    public void render(){
        if (weaponX >= 0){
            weapon.draw(weaponX, weaponY);
        }
    }


    /**
     * Status update of weapon spawn, collection, shooting and pipe destruction
     * @param input input during game play
     * @param bird bird object in current game level
     * @param pipeList ArrayList of all the spawned pipes on screen
     */
    public void update(Input input, lvlOneBird bird, ArrayList<Pipes> pipeList){
        if(!lost) {
            //Render weapon and status update
            render();
            pickedStatus(input, bird);
            moveLeft();

            //Check if weapon is fired
            if (pickedUp && input.wasPressed(Keys.S)){
                shot = true;
            }

            //Fix weapon to the bird
            pegToBeak(bird);

            //Forward trajectory of fired weapon
            if (pickedUp && shot) {
                shootMovement(bird);
                for (Pipes pipe: pipeList){
                    if (((getBox().intersects(pipe.getBottomBox())) ||
                            (getBox().intersects(pipe.getTopBox()))) &&
                            (!pipe.damageStatus())){
                        if ((shootRange == ROCK_RANGE && pipe.getType().equals("plastic")) ||
                                (shootRange == BOMB_RANGE)){
                            demolishPipe(bird, pipe);
                        }
                    }
                }
            }
        }
    }


    /**
     * Update weapon shot and pipe demolish state
     * @param bird bird who fired the weapon
     * @param pipe pipe to be demolished
     */
    public void demolishPipe(lvlOneBird bird, Pipes pipe){
        pipe.damage();
        lost = true;
        bird.weaponShot();
        bird.addScore();
    }


    /**
     * Shoot movement to right
     * @param bird bird object that shot the weapon
     */
    public void shootMovement(lvlOneBird bird){
        weaponX += shootSpeed;
        shootFrame++;
        if (shootFrame == shootRange) {
            lost = true;
            bird.weaponShot();
        }
    }


    /**
     * @return Rectangle object at the current location of the Weapon
     */
    public Rectangle getBox(){
        return weapon.getBoundingBoxAt(new Point(weaponX, weaponY));
    }


    /**
     * Fix weapon to birds movement (fix to beak)
     * @param bird to match movement to the weapon
     */
    public void pegToBeak(lvlOneBird bird){
        if (pickedUp && !shot) {
            weaponX = bird.getBox().right();
            weaponY = bird.getY();
        }
    }


    /**
     * Update bird's and weapon picked status
     * @param input Input during game play
     * @param bird to update picked up status if intersected
     */
    public void pickedStatus(Input input, lvlOneBird bird){
        if (bird.getBox().intersects(getBox()) && !(bird.Weapon())) {
            pickedUp = true;
            bird.weaponCollected();
        }
    }


    /**
     * Move the weapon to the left
     */
    public void moveLeft(){
        if (!pickedUp && weaponX >= 0) {
            weaponX -= travelSpeed;
            if (weaponX < 0){
                lost = true;
            }
        }
    }
}
