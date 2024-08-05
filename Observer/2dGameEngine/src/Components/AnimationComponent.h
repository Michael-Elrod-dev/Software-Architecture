#pragma once
#include <SDL.h>        // SDL functions

using namespace std;

class AnimationComponent
{
public:
	bool isLoop;
	int numFrames;
	int startTime;
	int currentFrame;
	int frameRateSpeed;

	AnimationComponent(int numFrames = 1, int frameRateSpeed = 1, bool isLoop = true){
		this->numFrames = numFrames;
		this->currentFrame = 1;
		this->frameRateSpeed = frameRateSpeed;
		this->isLoop = isLoop;
		// Milliseconds since the SDL library initialized
		this->startTime = SDL_GetTicks();
	}
};