#pragma once
#include "../ECS/ECS.h"
#include "../Components/SpriteComponent.h"
#include "../Components/AnimationComponent.h"
#include <SDL.h>

using namespace std;

class AnimationSystem : public System
{
public:
	AnimationSystem(){
		requireComponent<AnimationComponent>();
		requireComponent<SpriteComponent>();
	}

	void update()
	{
		for (auto entity : getSystemEntities()){
			auto& animation = entity.getComponent<AnimationComponent>();
			auto& sprite = entity.getComponent<SpriteComponent>();

			// Time elapsed since start * FPS % frames of animation for PNG
			animation.currentFrame = ((SDL_GetTicks() - animation.startTime)
				* animation.frameRateSpeed/1000) % animation.numFrames;

			// Moves parameter of PNG being displayed to the right
			sprite.srcRect.x = animation.currentFrame * sprite.width;
		}
	}

private:
};