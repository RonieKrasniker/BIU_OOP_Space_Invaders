# Space Invaders Game Documentation

## Overview
This project implements a Space Invaders game using the `biuoop-1.4.jar` package. The game features a player's spaceship that can shoot at alien enemies while avoiding their attacks. The game consists of multiple levels, each with increasing difficulty.

## Project Structure
The project is organized into several packages, each serving a specific purpose:

- **animation**: Contains classes related to animations in the game.
  - `Animation.java`: Defines the structure for animations.
  - `AnimationRunner.java`: Manages the execution of animations.

- **game**: Contains classes that manage the game flow and environment.
  - `GameEnvironment.java`: Manages collidable objects and collision detection.
  - `GameFlow.java`: Controls the overall flow of the game, including levels and scoring.
  - `GameLevel.java`: Represents a single level of the game.

- **interfaces**: Contains interfaces that define essential game functionalities.
  - `Collidable.java`: Defines methods for collidable objects.
  - `HitListener.java`: Defines methods for objects that need to be notified of hits.
  - `Sprite.java`: Defines methods for drawable objects.

- **sprites**: Contains classes that represent various game entities.
  - `Alien.java`: Represents alien enemies.
  - `Block.java`: Represents blocks that can be hit.
  - `Shot.java`: Represents shots fired by the spaceship.
  - `SpaceShip.java`: Represents the player's spaceship.

- **SpaceInvaders.java**: The main entry point of the game.

## Setup Instructions
1. Ensure you have Java installed on your machine.
2. Download the `biuoop-1.4.jar` package and add it to your project's classpath.
3. Compile the Java files in the `src` directory.
4. Run the `SpaceInvaders.java` file to start the game.

## Gameplay Mechanics
- The player controls a spaceship that can move left and right and shoot upwards.
- Aliens move downwards and can shoot at the player.
- The player must shoot the aliens while avoiding their shots.
- The game consists of multiple levels, with each level increasing in difficulty.

## Additional Notes
- The game utilizes the `biuoop` package for GUI management and keyboard input.
- Ensure to handle exceptions properly, especially for user interactions and game state changes.

This documentation serves as a guide for understanding the structure and functionality of the Space Invaders game project.