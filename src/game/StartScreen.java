package game;

import animation.Animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.awt.Image;

/**
 * Simple start screen that waits for the player to press SPACE.
 */
public class StartScreen implements Animation {
    private final KeyboardSensor keyboard;
    private boolean stop = false;
    private final Image image;

    /**
     * Construct start screen.
     *
     * @param keyboard keyboard sensor
     */
    public StartScreen(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.image = ImageManager.getScaled("media/elements/start game.png", 800, 600);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (image != null) {
            d.drawImage(0, 0, image);
        } else {
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            d.setColor(Color.WHITE);
            d.drawText(200, 300, "Press SPACE to start", 32);
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
