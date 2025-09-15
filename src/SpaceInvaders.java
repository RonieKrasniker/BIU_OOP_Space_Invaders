import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameFlow;

public class SpaceInvaders {
    private GUI gui;
    private KeyboardSensor keyboard;
    private GameFlow gameFlow;

    public SpaceInvaders() {
        this.gui = new GUI("Space Invaders", 800, 600);
        this.keyboard = gui.getKeyboardSensor();
        this.gameFlow = new GameFlow();
    }

    public void run() {
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            // Clear the screen
            d.setColor(java.awt.Color.BLACK);
            d.fillRectangle(0, 0, 800, 600);
            
            // Update and draw the game
            gameFlow.run(d, keyboard);
            
            gui.show(d);
            // Add a delay for frame rate control
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor(20);
        }
    }

    public static void main(String[] args) {
        SpaceInvaders game = new SpaceInvaders();
        game.run();
    }
}