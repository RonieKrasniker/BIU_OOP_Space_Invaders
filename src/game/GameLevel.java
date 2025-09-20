package game;
// File: /space-invaders/space-invaders/src/game/GameLevel.java

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import animation.Animation;
import interfaces.Collidable;
import interfaces.Sprite;
import sprites.SpaceShip;
import sprites.Alien;
import sprites.Block;
import sprites.Shot;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a single level and implements the Animation interface.
 * Responsibilities include initializing the level, running main game logic,
 * keeping track of score and lives, and determining when the level is over.
 */
public class GameLevel implements Animation {
    private List<Sprite> sprites;
    private GameEnvironment environment;
    private KeyboardSensor keyboard;
    private boolean running;
    private SpaceShip spaceShip;
    private List<Alien> aliens;
    private int score;
    private int lives;
    private List<Shot> shots = new ArrayList<>();
    private long lastShotTime = 0;
    private long fireCooldownMs = 200; // faster player cooldown
    private Image backgroundImage;
    // Alien fleet movement
    private double alienSpeed = 1.0;
    private int alienDirection = 1; // 1 -> right, -1 -> left
    private int leftMargin = 10;
    private int rightMargin = 790;
    private int dropStep = 8; // slower drop
    // HUD assets
    private Image lifeIcon;
    // Alien shooting
    private long lastAlienShotTime = 0;
    private long alienFireCooldownMs = 1500;
    private int maxAlienShots = 3;
    private final Random rng = new Random();

    /**
     * Construct a GameLevel driven by a KeyboardSensor.
     *
     * @param keyboard keyboard input sensor
     */
    public GameLevel(KeyboardSensor keyboard) {
        this.sprites = new ArrayList<>();
        this.environment = new GameEnvironment();
        this.keyboard = keyboard;
        this.running = false;
        this.aliens = new ArrayList<>();
        this.score = 0;
        this.lives = 3;
    }

    /**
     * Initialize the level: create the fleet of aliens, the shields, and the
     * spaceship.
     */
    public void initialize() {
        // Create spaceship (larger and closer to bottom)
    int shipW = 140;
    int shipH = 90;
        int startX = 400 - shipW / 2;
    int startY = 600 - shipH - 15;
    this.spaceShip = new SpaceShip(startX, startY, shipW, shipH, 8);
        addSprite(spaceShip);
    addCollidable(spaceShip);

        // Create aliens fleet
        createAlienFleet();

        // Create shields
        createShields();

        this.running = true;
        this.backgroundImage = ImageManager.getScaled("media/elements/backgroung.png", 800, 600);
        this.lifeIcon = ImageManager.getScaled("media/elements/high hit points - healthy rick.png", 30, 30);
        // Start background music at low volume so effects stand out
        SoundManager.loopMusic("media/elements/background theme.wav", 0.15);
    }

    private void createAlienFleet() {
        // Create a 5x11 grid of aliens
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 11; col++) {
                int x = 50 + col * 60;
                int y = 50 + row * 40;
                Alien alien = new Alien(x, y, 40, 30, Color.GREEN);
                alien.setImageIndex(Math.min(row, 4));
                aliens.add(alien);
                addSprite(alien);
                addCollidable(alien);
            }
        }
    }

    private void createShields() {
        // Create 2 shields of 3×6 tiles
    int shields = 2;
    int shieldTile = 20; // tile size
    int cols = 6; // width tiles
    int rows = 3;  // height tiles
    int spacing = 360; // space between shields
    int baseY = 440; // vertical position
    int startX = 200; // leftmost shield position

        for (int i = 0; i < shields; i++) {
            int x = startX + i * spacing;
            int y = baseY;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    // simple shield block
                    Block block = new Block(x + col * shieldTile, y + row * shieldTile,
                            shieldTile, shieldTile, Color.CYAN);
                    addSprite(block);
                    addCollidable(block);
                }
            }
        }
    }

    /**
     * Adds a sprite to the level.
     *
     * @param sprite the sprite to add
     */
    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    /**
     * Add a collidable to the environment.
     *
     * @param collidable collidable to add
     */
    public void addCollidable(Collidable collidable) {
        this.environment.addCollidable(collidable);
    }

    /**
     * Remove a sprite from the level.
     *
     * @param sprite sprite to remove
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.remove(sprite);
    }

    /**
     * Remove a collidable from the environment.
     *
     * @param collidable collidable to remove
     */
    public void removeCollidable(Collidable collidable) {
        this.environment.removeCollidable(collidable);
    }

    /** {@inheritDoc} */
    @Override
    public void doOneFrame(DrawSurface d) {
        // Draw background
        if (backgroundImage != null) {
            d.drawImage(0, 0, backgroundImage);
        } else {
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }

        // Move alien fleet
        moveAliens();

        // Draw all sprites
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }

        // Handle input
        handleInput();

        // Update all sprites
        for (Sprite sprite : this.sprites) {
            sprite.timePassed();
        }

        // Alien shooting logic
        maybeAlienShoot();

        // Cleanup inactive shots and dead aliens
        cleanupSprites();

        // Check win/lose conditions
        if (aliens.isEmpty()) {
            this.stop(); // Player wins
        } else {
            // Lose if aliens reach the ship line
            for (Alien a : aliens) {
                if (a.isAlive() && a.getY() + a.getHeight() >= spaceShip.getY()) {
                    this.stop();
                    break;
                }
            }
        }

        // Check if ship was hit
        if (spaceShip.consumeHit()) {
            // Explosion sound on player hit
            SoundManager.playEffect("media/elements/explosion-6801.wav", 1.0);
            lives--;
            // Remove old ship
            removeCollidable(spaceShip);
            removeSprite(spaceShip);
            // Reset ship position
            this.spaceShip = new SpaceShip(400 - (int) spaceShip.getWidth() / 2,
                    600 - (int)spaceShip.getHeight() - 15,
                    (int)spaceShip.getWidth(), (int)spaceShip.getHeight(), 8);
            addSprite(spaceShip);
            addCollidable(spaceShip);
            if (lives <= 0) {
                this.stop();
            }
        }

        // Draw UI elements
        d.setColor(Color.WHITE);
        d.drawText(10, 20, "Score: " + score, 16);
        // Draw life icons on top-right
        int iconCount = Math.max(0, lives);
        int iconSize = 20;
        int padding = 5;
        int x = d.getWidth() - (iconSize + padding) * iconCount - 10;
        int y = 10;
        for (int i = 0; i < iconCount; i++) {
            if (lifeIcon != null) {
                d.drawImage(x + i * (iconSize + padding), y, lifeIcon);
            } else {
                d.fillCircle(x + i * (iconSize + padding) + iconSize / 2, y + iconSize / 2, iconSize / 2);
            }
        }
        d.drawText(x - 70, 25, "Lives:", 16);
    }

    private void maybeAlienShoot() {
        long now = System.currentTimeMillis();
        // Throttle max simultaneous alien shots
        int activeAlienShots = 0;
        for (Shot s : shots) {
            if (!s.isFromPlayer() && s.isActive()) {
                activeAlienShots++;
            }
        }
        if (activeAlienShots >= maxAlienShots) {
            return;
        }
        if (now - lastAlienShotTime < alienFireCooldownMs) {
            return;
        }
        // collect bottom-most alive aliens by column
        Alien[] bottomByCol = new Alien[11];
        for (Alien a : aliens) {
            if (!a.isAlive()) {
                continue;
            }
            int colIdx = (int) Math.round((a.getX() - 50) / 60.0);
            if (colIdx < 0) {
                colIdx = 0;
            }
            if (colIdx > 10) {
                colIdx = 10;
            }
            if (bottomByCol[colIdx] == null || a.getY() > bottomByCol[colIdx].getY()) {
                bottomByCol[colIdx] = a;
            }
        }
        List<Alien> candidates = new ArrayList<>();
        for (Alien a : bottomByCol) {
            if (a != null) {
                candidates.add(a);
            }
        }
        if (candidates.isEmpty()) {
            return;
        }
        Alien shooter = candidates.get(rng.nextInt(candidates.size()));
        // Fire shot downward
        Shot shot = new Shot(shooter.getX() + shooter.getWidth() / 2 - 5,
                shooter.getY() + shooter.getHeight(), 10, 20, 5);
        shot.setImagePath("media/sprites/portal_Shot.png");
        shot.setFromPlayer(false);
        shot.setGameEnvironment(this.environment);
        shots.add(shot);
        addSprite(shot);
        lastAlienShotTime = now;
    }

    /**
     * Move the alien fleet horizontally and drop down when reaching screen edges.
     */
    private void moveAliens() {
        if (aliens.isEmpty()) {
            return;
        }
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (Alien a : aliens) {
            if (!a.isAlive()) {
                continue;
            }
            minX = Math.min(minX, a.getX());
            maxX = Math.max(maxX, a.getX() + a.getWidth());
        }
        boolean hitRight = alienDirection > 0 && maxX + alienSpeed >= rightMargin;
        boolean hitLeft = alienDirection < 0 && minX - alienSpeed <= leftMargin;

        double dx = alienDirection * alienSpeed;
        double dy = 0;
        if (hitRight || hitLeft) {
            alienDirection *= -1;
            dy = dropStep;
            dx = alienDirection * alienSpeed; // reverse direction after dropping
        }

        for (Alien a : aliens) {
            if (a.isAlive()) {
                a.move(dx, dy);
            }
        }
    }

    private void handleInput() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            spaceShip.moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            spaceShip.moveRight(800); // screen width
        }
        if (keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            long now = System.currentTimeMillis();
            if (now - lastShotTime >= fireCooldownMs) {
                Shot shot = spaceShip.shoot();
                shot.setGameEnvironment(this.environment);
                shots.add(shot);
                addSprite(shot);
                lastShotTime = now;
                // Laser sound when firing
                SoundManager.playEffect("media/elements/laser-45816.wav", 0.9);
            }
        }
    }

    private void cleanupSprites() {
    // Remove inactive shots
        List<Shot> toRemoveShots = new ArrayList<>();
        for (Shot s : shots) {
            if (!s.isActive()) {
                toRemoveShots.add(s);
            }
        }
        for (Shot s : toRemoveShots) {
            shots.remove(s);
            removeSprite(s);
        }

    // Remove dead aliens and destroyed blocks
        List<Alien> deadAliens = new ArrayList<>();
        for (Alien a : aliens) {
            if (!a.isAlive()) {
                deadAliens.add(a);
            }
        }
        for (Alien a : deadAliens) {
            aliens.remove(a);
            removeSprite(a);
            removeCollidable(a);
            score += 100; // award points
        }
        // Remove destroyed shields
        List<Block> destroyed = new ArrayList<>();
        for (Sprite sp : sprites) {
            if (sp instanceof Block) {
                Block b = (Block) sp;
                if (b.isDestroyed()) {
                    destroyed.add(b);
                }
            }
        }
        for (Block b : destroyed) {
            removeSprite(b);
            removeCollidable(b);
        }
        if (!deadAliens.isEmpty()) {
            // Speed up slightly as aliens are destroyed
            alienSpeed += 0.05 * deadAliens.size();
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @return current score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return true if aliens remain
     */
    public boolean hasMoreEnemies() {
        return !aliens.isEmpty();
    }

    /**
     * Stop the level loop.
     */
    public void stop() {
        this.running = false;
        // Stop background music when level ends
        SoundManager.stopMusic();
    }
}