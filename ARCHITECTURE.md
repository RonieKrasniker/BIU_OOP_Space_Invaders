# Space Invaders Game Architecture

This implementation follows the exact 4-stage plan outlined in the problem statement.

## Directory Structure (Final)

```
space-invaders/
├── src/
│   ├── interfaces/          # Stage 1: Foundational Interfaces
│   │   ├── Sprite.java      # Object that can be drawn and updated
│   │   ├── Collidable.java  # Object that can be collided with
│   │   └── HitListener.java # Event listener for hits
│   ├── animation/           # Stage 1: Animation Framework  
│   │   ├── Animation.java   # Interface for screen/state
│   │   └── AnimationRunner.java # Main game loop driver
│   ├── geometry/            # Supporting geometry classes
│   │   └── Rectangle.java   # Collision rectangles
│   ├── sprites/             # Stage 2: Basic Game Objects
│   │   ├── Block.java       # Shield building blocks
│   │   ├── SpaceShip.java   # Player's ship
│   │   ├── Shot.java        # Projectile 
│   │   └── Alien.java       # Single alien enemy
│   ├── game/                # Stage 3: Game Structure & Logic
│   │   ├── GameEnvironment.java # Manages collidables
│   │   ├── GameLevel.java   # Single level (implements Animation)
│   │   └── GameFlow.java    # High-level game sequence
│   ├── SpaceInvaders.java   # Stage 4: Main entry point
│   └── TestSpaceInvaders.java # Validation test
├── biuoop-1.4.jar          # GUI library
├── checkstyle-8.44-all.jar # Code style checker
└── README.md
```

## Implementation Details per Stage

### Stage 1: Foundational Interfaces and Core Mechanics ✓

**Interfaces:**
- `Sprite`: Objects with `drawOn(DrawSurface d)` and `timePassed()` methods
- `Collidable`: Objects with `getCollisionRectangle()` and `hit()` methods

**Animation Framework:**
- `Animation`: Interface with `doOneFrame(DrawSurface)` and `shouldStop()` methods
- `AnimationRunner`: Contains the main game loop using `biuoop.GUI` and `biuoop.Sleeper`

### Stage 2: Basic Game Objects ✓

All implement `Sprite`, collidable ones also implement `Collidable`:

- **`Block`**: Simple rectangular building block for shields (Sprite + Collidable)
- **`SpaceShip`**: Player's ship with movement and shooting (Sprite)
- **`Shot`**: Projectile with collision detection (Sprite)
- **`Alien`**: Enemy alien (Sprite + Collidable)

### Stage 3: Game Structure and Logic ✓

- **`GameEnvironment`**: Manages all `Collidable` objects, used by `Shot` for collision checks
- **`GameLevel`**: Implements `Animation` interface, handles:
  - Level initialization (aliens, shields, spaceship)
  - Main game logic in `doOneFrame()` 
  - Score and lives tracking
  - Win/lose condition detection

### Stage 4: Assembling the Game ✓

- **`GameFlow`**: Manages animation sequences, runs levels, handles transitions
- **`SpaceInvaders`**: Main class that creates GUI, AnimationRunner, GameFlow and starts the game

## Key Features Implemented

1. **Proper Animation Pattern**: Uses the specified Animation interface pattern
2. **Collision System**: Rectangle-based collision detection
3. **Game Objects**: All basic sprites with proper inheritance
4. **Input Handling**: Keyboard input for spaceship movement and shooting
5. **Game Logic**: Level progression, score tracking, win/lose conditions
6. **Modular Design**: Clear separation of concerns across packages

## Usage

```bash
# Compile
javac -cp ".:biuoop-1.4.jar" src/geometry/*.java src/interfaces/*.java src/animation/*.java src/sprites/*.java src/game/*.java src/*.java

# Run
java -cp ".:biuoop-1.4.jar:src" SpaceInvaders

# Test core functionality
java -cp ".:biuoop-1.4.jar:src" TestSpaceInvaders
```

## Architecture Benefits

- **Extensible**: Easy to add new sprite types or levels
- **Maintainable**: Clear separation of animation, game logic, and sprites  
- **Testable**: Core functionality can be tested without GUI
- **Follows OOP Principles**: Proper use of interfaces and inheritance

This implementation precisely matches the 4-stage plan from the problem statement and provides a solid foundation for a complete Space Invaders game.