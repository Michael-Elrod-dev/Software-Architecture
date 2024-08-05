#pragma once
#include "../ECS/ECS.h"
#include "../Components/CameraComponent.h"
#include "../Components/TransformComponent.h"
#include <SDL.h>

using namespace std;

class CameraMovementSystem : public System
{
public:
	CameraMovementSystem() {
		requireComponent<CameraComponent>();
		requireComponent<TransformComponent>();
	}

    void update(SDL_Rect& camera) {
        for (auto entity : getSystemEntities()) {
            auto transform = entity.getComponent<TransformComponent>();

            if (transform.position.x + (camera.w / 2) < Game::mapWidth)
                camera.x = transform.position.x - (Game::windowWidth / 2.0);

            if (transform.position.y + (camera.h / 2) < Game::mapHeight)
                camera.y = transform.position.y - (Game::windowHeight / 2.0);

            // Keep camera rectangle view inside the screen limits
            camera.x = camera.x < 0 ? 0 : camera.x;
            camera.y = camera.y < 0 ? 0 : camera.y;
            camera.x = (camera.x + camera.w > Game::mapWidth) ? Game::mapWidth - camera.w : camera.x;
            camera.y = (camera.y + camera.h > Game::mapHeight) ? Game::mapHeight - camera.h : camera.y;
        }
    }
};