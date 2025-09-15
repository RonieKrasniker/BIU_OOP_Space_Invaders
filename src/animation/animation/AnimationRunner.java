package animation;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.util.List;

public class AnimationRunner {
    private GUI gui;
    private double framesPerSecond;

    public AnimationRunner(GUI gui, double framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
    }

    public void run(Animation animation) {
        long millisecondsPerFrame = (long) (1000 / framesPerSecond);
        while (true) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long sleepTime = millisecondsPerFrame - usedTime;
            if (sleepTime > 0) {
                Sleeper sleeper = new Sleeper();
                sleeper.sleepFor(sleepTime);
            }
        }
    }

    public KeyboardSensor getKeyboardSensor() {
        return gui.getKeyboardSensor();
    }

    public void close() {
        gui.close();
    }
}