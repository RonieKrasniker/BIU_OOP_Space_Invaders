package animation;
import biuoop.GUI;
import biuoop.DrawSurface;

public abstract class Animation {
    private boolean running;

    public Animation() {
        this.running = false;
    }

    public abstract void doOneFrame(DrawSurface d, double dt);

    public boolean shouldStop() {
        return !this.running;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }
}