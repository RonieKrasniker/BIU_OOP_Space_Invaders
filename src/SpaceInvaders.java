import biuoop.GUI;
import biuoop.KeyboardSensor;
import animation.AnimationRunner;
import game.GameFlow;
import game.GameLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * The main entry point of the application.
 * Creates the GUI, the AnimationRunner, and the GameFlow, and then starts the
 * game.
 */
public class SpaceInvaders {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;

    /**
     * Application entry point.
     *
     * @param args CLI args (unused)
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Space Invaders", SCREEN_WIDTH, SCREEN_HEIGHT);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner animationRunner = new AnimationRunner(gui, FRAMES_PER_SECOND);
        GameFlow gameFlow = new GameFlow(animationRunner, keyboard);

        // Create levels
        List<GameLevel> levels = createLevels(keyboard);

        // Run the game
        gameFlow.runLevels(levels);

        gui.close();
    }

    private static List<GameLevel> createLevels(KeyboardSensor keyboard) {
        List<GameLevel> levels = new ArrayList<>();

        // Create Level 1
        GameLevel level1 = new GameLevel(keyboard);
        levels.add(level1);

        // Could add more levels here

        return levels;
    }
}