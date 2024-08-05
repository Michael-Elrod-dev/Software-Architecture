#pragma once
#include "SDL.h"

// Component for the actual projectiles being emitted
struct ProjectileComponent {
	bool isFriendly;
	int damagePercent;
	int duration;
	int startTime;

	ProjectileComponent(bool isFriendly = false, int damagePercent = 0, int duration = 0) {
		this->isFriendly = isFriendly;
		this->damagePercent = damagePercent;
		this->duration = duration;
		this->startTime = SDL_GetTicks();
	}
};