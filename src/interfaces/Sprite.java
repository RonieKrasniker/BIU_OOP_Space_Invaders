package interfaces;

import biuoop.DrawSurface;

/**
 * An object that can be drawn on the screen and notified that time has passed.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     *
     * @param d the DrawSurface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}