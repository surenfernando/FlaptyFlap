import bagel.Font;
import bagel.Input;
import bagel.Keys;
import bagel.Window;


/**
 * Class to handle instruction/message screen of the game.
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class Screens{
    private final int SCORE_MSG_OFFSET = 75;
    private final int LVL_MSG_OFFSET = 68;
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String LEVEL_UP_MSG = "LEVEL-UP!";
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";


    /**
     * Renders the level zero instruction screen
     * @param input Input during game run
     * @return boolean, flag if space was pressed/game start
     */
    public boolean InstructionZero(Input input) {
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        return input.wasPressed(Keys.SPACE);
    }


    /**
     * Renders the level one instruction screen
     * @param input Input during game run
     * @return boolean, flag if space was pressed/game start
     */
    public boolean InstructionOne(Input input) {
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        FONT.drawString(SHOOT_MSG, (Window.getWidth()/2.0-(FONT.getWidth(SHOOT_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+LVL_MSG_OFFSET);
        return input.wasPressed(Keys.SPACE);
    }


    /**
     * Renders the game over screen with current score
     * @param score score to be rendered
     */
    public void GameOverScreen(int score) {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }


    /**
     * Renders the Level Up Screen
     */
    public void LevelUp() {
        FONT.drawString(LEVEL_UP_MSG, (Window.getWidth() / 2.0 - (FONT.getWidth(LEVEL_UP_MSG) / 2.0)),
                (Window.getHeight() / 2.0 - (FONT_SIZE / 2.0)));
    }


    /**
     * Renders the live score (score board)
     * @param score Int value of current score to render
     */
    public void scoreBoard(int score){
        String temp = SCORE_MSG + score;
        FONT.drawString(temp, 100, 100);
    }


    /**
     * Renders the Win (Congratulations) Screen
     */
    public void WinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }


}
