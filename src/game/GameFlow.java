package game;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import animation.AnimationRunner;
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

    public GameFlow(AnimationRunner animationRunner, KeyboardSensor keyboard) {
        this.animationRunner = animationRunner;
        this.keyboard = keyboard;
        this.totalScore = 0;
    }

    public void runLevels(List<GameLevel> levels) {
        for (GameLevel level : levels) {
            level.initialize();
            
            this.animationRunner.run(level);
            
            this.totalScore += level.getScore();
            
            if (!level.hasMoreEnemies()) {
                // Player completed the level
                continue;
            } else {
                // Player lost (no lives left or other game over condition)
                break;
            }
        }
        
        // Show final score or game over screen
        showEndGame();
    }
    
    private void showEndGame() {
        // Could implement an end game animation here
        System.out.println("Game Over! Final Score: " + totalScore);
    }
    
    public int getTotalScore() {
        return totalScore;
    }
}