#pragma once
#include "../ECS/ECS.h"
#include "../Components/ProjectileComponent.h"

using namespace std;

class ProjectileLifecycleSystem : public System
{
public:
	ProjectileLifecycleSystem() {
		requireComponent<ProjectileComponent>();
	}

	void update() {
		for (auto entity : getSystemEntities()) {
			auto projectile = entity.getComponent<ProjectileComponent>();

			// Kill projectiles after they reach the limit of their duration
			if (SDL_GetTicks() - projectile.startTime > projectile.duration)
				entity.kill();
		}
	}
};