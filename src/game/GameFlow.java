package game;

import biuoop.KeyboardSensor;
import animation.AnimationRunner;
import animation.Animation;
import java.util.List;

/**
 * A high-level class to manage the sequence of animations.
 * For example, it will run the "Level 1" animation, and if the player wins,
 * it might run a "You Win" screen or move to "Level 2".
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboard;
    private int totalScore;

    /**
     * Construct a GameFlow manager.
     *
     * @param animationRunner drives animations
     * @param keyboard        keyboard for screens that need input
     */
    public GameFlow(AnimationRunner animationRunner, KeyboardSensor keyboard) {
        this.animationRunner = animationRunner;
        this.keyboard = keyboard;
        this.totalScore = 0;
    }

    /**
     * Run a sequence of levels until completion or failure.
     *
     * @param levels list of levels
     */
    public void runLevels(List<GameLevel> levels) {
    // Start screen
    this.animationRunner.run(new StartScreen(this.keyboard));

    boolean wonAll = true;
        for (GameLevel level : levels) {
            level.initialize();
            this.animationRunner.run(level);
            this.totalScore += level.getScore();
            if (level.hasMoreEnemies()) {
                // Player lost
                wonAll = false;
                break;
            }
        }
        // After levels: show win or game over
    // End screen: wait for SPACE press (after release) to exit
    final boolean victory = wonAll;
    Animation endScreen = new Animation() {
        private boolean stop = false;
        private boolean waitingForRelease = keyboard.isPressed(KeyboardSensor.SPACE_KEY);
        @Override
        public void doOneFrame(biuoop.DrawSurface d) {
            d.setColor(java.awt.Color.BLACK);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            d.setColor(java.awt.Color.WHITE);
            if (victory) {
                d.drawText(250, 280, "You Win!", 48);
            } else {
                d.drawText(250, 280, "Game Over", 48);
            }
            d.drawText(250, 330, "Score: " + totalScore, 32);
            d.drawText(200, 380, "Press SPACE to exit", 24);
            if (waitingForRelease) {
                if (!keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                    waitingForRelease = false;
                }
            } else if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                stop = true;
            }
        }
        @Override
        public boolean shouldStop() { return stop; }
    };
        this.animationRunner.run(endScreen);
    }


    /**
     * @return total accumulated score
     */
    public int getTotalScore() {
        return totalScore;
    }
}