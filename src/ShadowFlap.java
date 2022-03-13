import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class ShadowFlap extends AbstractGame {
    Random rand = new Random();
    private final double[] OPTS = {100, 300, 500};
    private static final int OPT_RANGE = 3;
    private static final int PIPE_TYPES = 2;
    private static final int L_ZERO_WIN_SCORE = 10;
    private static final int L_ONE_WIN_SCORE = 30;
    private static final int MAX_Y = 500;
    private static final int MIN_Y = 100;
    private static final int WAIT_TIME = 150;
    private final int DEF_FRM_RATE = 100;
    private final int DEF_PIPE_SPEED = 5;
    private final Background BACKGROUND;
    private final Screens myScreen = new Screens();

    private static final String BACK_ZERO = "res/level-0/background.png";
    private static final String BACK_ONE = "res/level-1/background.png";

    private lvlZeroBird birdZero = new lvlZeroBird("res/level-0/birdWingUp.png",
            "res/level-0/birdWingDown.png");
    private lvlOneBird birdOne = new lvlOneBird("res/level-1/birdWingUp.png",
            "res/level-1/birdWingDown.png");

    private ArrayList<Pipes> lvlZeroPipes = new ArrayList<Pipes>();
    private ArrayList<Pipes> lvlOnePipes = new ArrayList<Pipes>();
    private ArrayList<Weapon> Weapons = new ArrayList<Weapon>();


    private int frameNumber = 100;
    private int frameRate = 100;
    private int currentLevel = 0;
    private int lvlUpScreen = 0;
    private int timescale = 1;
    private boolean gameOn;
    private boolean oneStart = false;
    private boolean zeroStart = false;
    private boolean levelUp = false;
    private boolean win = false;
    private boolean pipeMade = false;
    private boolean weaponMade = false;


    /**
     * Constructing the initial elements to facilitate game play
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        BACKGROUND = new Background(BACK_ZERO);
        gameOn = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        // Render background and check of esc key
        BACKGROUND.render();
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // Display level zero instruction screen until space is pressed
        if(!gameOn && currentLevel == 0 && birdZero.isAlive()){
            gameOn = myScreen.InstructionZero(input);
            zeroStart = true;
        }
        // Display level one instruction screen until space is pressed
        else if (currentLevel == 1 && !oneStart && birdZero.isAlive()){
            oneStart = myScreen.InstructionOne(input);
        }
        //Check if game is over
        else if (!birdZero.isAlive() || !birdOne.isAlive()){
            if (currentLevel == 0){
                myScreen.GameOverScreen(birdZero.score());
            }else{
                myScreen.GameOverScreen(birdOne.score());
            }
            gameOn = false;
        }
        // Level zero is complete, display level up screen
        else if (levelUp){
            lvlUpHandle();
        }

        //Game is on
        if (gameOn){
            updateTimeScale(input);
            if (zeroStart && currentLevel == 0){
                //LEVEL ZERO Activity
                GenerateZeroPipes();
                birdZero.update(input);
                birdZero.healthBar();
                flowUpdate(birdZero, lvlZeroPipes);
                if (birdZero.score() == L_ZERO_WIN_SCORE){
                    // END of level zero
                    levelUp = true;
                    zeroStart = false;
                    resetTimeScale();
                }
            }
            else if (oneStart && currentLevel == 1){
                //LEVEL ONE Activity
                GenerateOnePipes();
                GenerateWeapons();
                WeaponUpdate(input, birdOne);
                birdOne.update(input);
                birdOne.healthBar();
                flowUpdate(birdOne, lvlOnePipes);
                if (birdOne.score() == L_ONE_WIN_SCORE){
                    // END of level one
                    gameOn = false;
                    win = true;
                }
            }
        }else if (win){
            // Game is won
            myScreen.WinScreen();
        }

    }

    /**
     * Resets the timescale to default timescale of 1
     */
    public void resetTimeScale(){
        frameRate = DEF_FRM_RATE;
        Pipes.setPipeSpeed(DEF_PIPE_SPEED);
        Weapon.setWeaponSpeed(DEF_PIPE_SPEED);
        timescale = 1;
    }

    /**
     * State update on Timescale feature
     * @param input Parameter holding any input during game play
     */
    public void updateTimeScale(Input input){
        if (input.wasPressed(Keys.L) && timescale<5){
            frameRate = (int)(frameRate/1.5);
            Pipes.setPipeSpeed((int)(Pipes.getPipeSpeed()*1.5));
            Weapon.setWeaponSpeed((int)(Weapon.getWeaponSpeed()*1.5));
            timescale++;
        }
        else if (input.wasPressed(Keys.K) && timescale>1){
            frameRate = (int)(frameRate*1.5);
            Pipes.setPipeSpeed((int)(Pipes.getPipeSpeed()/1.5));
            Weapon.setWeaponSpeed((int)(Weapon.getWeaponSpeed()/1.5));
            timescale--;
        }
    }


    /**
     * Status update on Weapons and bird's pick/shoot option
     * @param input Input during game play
     * @param bird Level 1 bird to check for weapon status
     */
    public void WeaponUpdate(Input input, lvlOneBird bird){
        for (Weapon weapon: Weapons){
            weapon.update(input, bird, lvlOnePipes);
        }
    }


    /**
     * Status update to generate weapons that do not collide with pipes
     */
    public void GenerateWeapons(){
        int check = frameRate/2;
        if (!pipeMade && !weaponMade && frameNumber==check) {
            int temp = rand.nextInt(2);
            if (temp == 0) {
                Weapons.add(new Rock("res/level-1/rock.png"));
            } else {
                Weapons.add(new Bomb("res/level-1/bomb.png"));
            }
            weaponMade = true;
        }
    }

    /**
     * Status update on bird's fly and collision with pipes
     * @param bird bird that is alive in the level
     * @param pipeList ArrayList of pipes that are spawned
     */
    public void flowUpdate(Bird bird, ArrayList<Pipes> pipeList){
        bird.boundCheck();
        myScreen.scoreBoard(bird.score());
        for (Pipes pipe: pipeList){
            if (!pipe.damageStatus()){
                pipe.update();
                UpdateScore(bird, pipe);
                detectCollision(bird, pipe);
                pipe.flameCollision(bird);
            }
        }
    }


    /**
     * Displays level up screen until wait time has elapsed, then updates the background to next level.
     */
    public void lvlUpHandle(){
        lvlUpScreen++;
        myScreen.LevelUp();
        if (lvlUpScreen == WAIT_TIME){
            levelUp = false;
            BACKGROUND.updateTo(BACK_ONE);
            currentLevel = 1;
        }
    }


    /**
     * Generate level zero pipes (Plastic) that are to be spawned at the required frame rate
     */
    public void GenerateZeroPipes(){
        if (frameNumber % frameRate == 0){
            int index = rand.nextInt(OPT_RANGE);
            lvlZeroPipes.add(new PlasticPipes("res/level/plasticPipe.png", OPTS[index]));
            frameNumber = 0;
        }
        frameNumber++;
    }




    /**
     * Generate level one pipes (Plastic and Steel) that are to be spawned at the required frame rate
     */
    public void GenerateOnePipes(){
        frameNumber++;
        if (frameNumber % frameRate == 0){
            int customY = rand.nextInt(MAX_Y-MIN_Y)+MIN_Y;
            int pipeToss = rand.nextInt(PIPE_TYPES);
            if (pipeToss == 0){
                lvlOnePipes.add(new PlasticPipes("res/level/plasticPipe.png", (double)customY));
            }else{
                lvlOnePipes.add(new SteelPipes("res/level-1/steelPipe.png", (double)customY));
            }
            frameNumber = 0;
            pipeMade = true;
            weaponMade = false;
        }else{
            pipeMade = false;
        }
    }


    /**
     * Detect collision between a bird and given pipe
     * @param bird
     * @param pipe
     */
    public void detectCollision(Bird bird, Pipes pipe){
        Rectangle topPipeBox = pipe.getTopBox();
        Rectangle bottomPipeBox = pipe.getBottomBox();
        if (bird.getBox().intersects(topPipeBox) || bird.getBox().intersects(bottomPipeBox)){
            if (!pipe.getCollided()){
                pipe.setCollided();
                bird.dead();
            }
        }
    }


    /**
     * Update the score if bird has destroyed/passed a given pipe
     * @param bird
     * @param pipe
     */
    public void UpdateScore(Bird bird, Pipes pipe){
        if ((bird.getX() > pipe.getTopBox().right()) &&
                (!pipe.scoredAlready()) && (!pipe.getCollided())){
            bird.addScore();
            pipe.setScore();
        }
    }
}
