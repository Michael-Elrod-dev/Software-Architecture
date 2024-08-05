#pragma once
#include <SDL.h>
#include "../ECS/ECS.h"
#include "../Components/TransformComponent.h"
#include "../Components/BoxColliderComponent.h"

using namespace std;

class BoxCollisionSystem : public System
{
public:
	BoxCollisionSystem(){
		requireComponent<TransformComponent>();
		requireComponent<BoxColliderComponent>();
	}

	void update(SDL_Renderer* renderer, SDL_Rect& camera){
		for (auto& entity : getSystemEntities()){
			auto& transform = entity.getComponent<TransformComponent>();
			auto& collision = entity.getComponent<BoxColliderComponent>();

			// Draw collision box around entity
			SDL_Rect colliderRect = {
				static_cast<int>(transform.position.x + collision.offset.x - camera.x),
				static_cast<int>(transform.position.y + collision.offset.y - camera.y),
				static_cast<int>(collision.width * transform.scale.x),
				static_cast<int>(collision.height * transform.scale.y)
			};
			if (transform.scale.x > 1 && transform.scale.y > 1) {
				colliderRect = {
				static_cast<int>(transform.position.x + (collision.offset.x + 2) - camera.x),
				static_cast<int>(transform.position.y + (collision.offset.y + 6) - camera.y),
				static_cast<int>(collision.width * transform.scale.x),
				static_cast<int>(collision.height * transform.scale.y)
				};
			}
			
			// Color box and set to border only
			SDL_SetRenderDrawColor(renderer, 255, 0, 0, 255);
			SDL_RenderDrawRect(renderer, &colliderRect);
		}
	}
};