#pragma once
#include "../ECS/ECS.h"
#include "../Components/TransformComponent.h"
#include "../Components/SpriteComponent.h"
#include "../AssetStore/AssetStore.h"
#include <SDL.h>
#include <algorithm>

using namespace std;

struct RenderableEntity{
	TransformComponent transformComponent;
	SpriteComponent spriteComponent;
};

class RenderSystem : public System
{
public:
	RenderSystem(){
		requireComponent<SpriteComponent>();
		requireComponent<TransformComponent>();
	}

	// Update entity position based on its velocity, every frame
	void update(SDL_Renderer* renderer, unique_ptr<AssetStore>& assetStore, SDL_Rect& camera){
		// Create a vector of all renderable entities
		vector<RenderableEntity> renderableEntities;
		for (auto entity : getSystemEntities()) {
			RenderableEntity renderableEntity;
			renderableEntity.spriteComponent = entity.getComponent<SpriteComponent>();
			renderableEntity.transformComponent = entity.getComponent<TransformComponent>();

			// Bypass rendering entities if they are out of camera view
			bool isOutsideCameraView = (
				renderableEntity.transformComponent.position.x + (renderableEntity.transformComponent.scale.x * renderableEntity.spriteComponent.width) < camera.x ||
				renderableEntity.transformComponent.position.x > camera.x + camera.w ||
				renderableEntity.transformComponent.position.y + (renderableEntity.transformComponent.scale.y * renderableEntity.spriteComponent.height) < camera.y ||
				renderableEntity.transformComponent.position.y > camera.y + camera.h
				);
			// Cull entities outside camera view
			if (isOutsideCameraView && !renderableEntity.spriteComponent.isFixed) { continue; }
			renderableEntities.emplace_back(renderableEntity);
		}

		// Sort vector of entityies by zIndex
		sort(renderableEntities.begin(), renderableEntities.end(), [](const RenderableEntity& a, const RenderableEntity& b) {
			return a.spriteComponent.zIndex < b.spriteComponent.zIndex;
			});

		// Update all entities
		for (auto entity : renderableEntities){
			// Update entity position based on its velocity
			const auto transform = entity.transformComponent;
			const auto sprite = entity.spriteComponent;

			// Set the source rectangle of our original sprite texture
			SDL_Rect srcRect = sprite.srcRect;
			// Set the destination rectangle with the x,y position to be rendered
			SDL_Rect dstRect = {
				// X, Y position to be drawn at
				static_cast<int>(transform.position.x - (sprite.isFixed ? 0 : camera.x)),
				static_cast<int>(transform.position.y - (sprite.isFixed ? 0 : camera.y)),
				// Size and scaling values
				static_cast<int>(sprite.width * transform.scale.x),
				static_cast<int>(sprite.height * transform.scale.y)
			};

			// Draw PNG texture
			SDL_RenderCopyEx( 
				renderer,                                 // SDL renderer
				assetStore->getTexture(sprite.assetId),   // Texture to render
				&srcRect,                                 // Area of texture that will be displayed (think portion of a map)
				&dstRect,                                 // Tranformation details (position & scale)
				transform.rotation,                       // Rotation details
				NULL,                                     // Center of rotation (NULL = center)
				sprite.flip                               // Flip the texture (SDL_FLIP_NONE = no flips)
				);
		}
	}
};