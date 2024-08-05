#pragma once
#include <SDL.h>
#include <glm/glm.hpp>

struct ProjectileEmitterComponent{
	glm::vec2 projectileVelocity;
	int frequency;
	int duration;
	int damagePercent;
	int lastEmissionTime;
	bool isFriendly;

	ProjectileEmitterComponent(glm::vec2 projectileVelocity = glm::vec2(0), int frequency = 0, int duration = 10000, int damagePercent = 10, bool isFriendly = false) {
		this->projectileVelocity = projectileVelocity;
		this->frequency = frequency;
		this->duration = duration;
		this->damagePercent = damagePercent;
		this->isFriendly = isFriendly;
		this->lastEmissionTime = SDL_GetTicks();
	}
};