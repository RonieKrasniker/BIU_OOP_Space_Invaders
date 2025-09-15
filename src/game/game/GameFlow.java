package game;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.util.List;

public class GameFlow {
    private GUI gui;
    private KeyboardSensor keyboard;
    private int score;

    public GameFlow(GUI gui) {
        this.gui = gui;
        this.keyboard = gui.getKeyboardSensor();
        this.score = 0;
    }

    public void runLevels(List<GameLevel> levels) {
        for (GameLevel level : levels) {
            level.initialize();
            while (level.hasMoreEnemies()) {
                DrawSurface surface = gui.getDrawSurface();
                level.draw(surface);
                gui.show(surface);
                level.playOneTurn();
            }
            score += level.getScore();
            if (!askToContinue()) {
                break;
            }
        }
        endGame();
    }

    private boolean askToContinue() {
        // Implementation for asking the player if they want to continue to the next level
        return true; // Placeholder for actual implementation
    }

    private void endGame() {
        DrawSurface surface = gui.getDrawSurface();
        surface.drawText(100, 100, "Game Over! Your score: " + score, 32);
        gui.show(surface);
        // Wait for user to close the game
        while (!keyboard.isPressed("space")) {
            // Wait for space key to be pressed to exit
        }
        gui.close();
    }
}