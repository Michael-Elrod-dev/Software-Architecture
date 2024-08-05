# 2D Game Engine written in C++, SDL, and Lua.

## In-Game Image
![Game](https://github.com/Michael-Elrod-dev/2DGameEngine/blob/master/2dGameEngine/assets/images/C%2B%2BEngine.png)

## Sprites
All sprites used in this project can be found in the /Assets/ folder. All sprites used in this project are under copyright of Pikuma.com.

## Software Versions
OS Version: Windows 10
C++ Version: C++ 17  
SDL2 Version: SDL2 2.0.16  
Lua Version: Lua 5.4.4  
Environment: Microsoft Visual Studio

## How To Play
You can download the test game I created for this engine [HERE](https://drive.google.com/file/d/1Zar7DEuZkpvbqdR2kgMW2XQU7Cn3uRYz/view?usp=sharing).

## Motivation
This game engine was my first major project, crafted with the purpose of honing my programming and software engineering skills. The creation of this game engine allowed me to understand the intricacies of developing large, user-focused systems. Building it from scratch, I grasped the challenges and solutions in making a complex system come together to provide a good experience for the end-user.

## Reasoning
The structure of the project, while complex, is designed to provide a powerful toolset for building games. The game engine is built around an Entity Component System (ECS), which provides flexibility and modularity. Scripts are written in Lua, allowing for faster iteration times and separation of game logic from engine code.

## Work Flow
### Main
Main.cpp - The entry point of the application. This file initializes SDL2, sets up the game window, and runs the main game loop.

### AssetStore
AssetStore directory contains AssetStore.cpp that loads and stores all the game assets such as sprites, fonts, and sound effects. It reduces memory usage by ensuring each asset is only loaded once.

### Components
This directory includes multiple components that can be attached to game entities to give them their behavior and properties.

- AnimationComponent: Controls sprite animations.
- BoxColliderComponent: Handles collision detection.
- CameraComponent: Handles camera movement and positioning.
- HealthComponent: Manages entity health.
- KeyboardControlledComponent: Enables an entity to be controlled by keyboard input.
- ProjectileComponent: Defines a projectile's properties.
- ProjectileEmitterComponent: Handles the emission of projectiles.
- RigidBodyComponent: Handles physics calculations.
- ScriptComponent: Assigns Lua scripts to entities.
- SpriteComponent: Controls sprite rendering.
- TextLabelComponent: Manages text rendering.
- TransformComponent: Handles entity positioning, scaling and rotation.

### Systems
This directory contains various systems that manipulate components to create game behavior. Each system interacts with its corresponding component, updating the state of the game entities. For instance, AnimationSystem interacts with AnimationComponent to control sprite animations.

### EventBus & Events
EventBus handles communication between different parts of the game engine. The Events directory holds different types of events that can be emitted and handled by different parts of the game engine.

### Game
Game.cpp manages the main game mechanics. It is responsible for initializing the game, loading the levels, and managing the game state.

### Logger
Logger.cpp is used for debug logging, which helps in tracking the flow and state of the game for debugging purposes.

## Future Work
While the game engine is quite versatile in its current state, it can be improved by adding more generic components, systems, and expanding the variety of games that can be created. It could also be optimized for better performance, and additional tools could be developed to aid in creating game levels and scripting game logic.

## Contributions
Along with a course from Pikuma, I referenced several libraries and online resources to learn about ECS, SDL2, Lua integration and other game development topics. Special thanks to the creators of SDL, glm, imgui, sol, and Lua.
