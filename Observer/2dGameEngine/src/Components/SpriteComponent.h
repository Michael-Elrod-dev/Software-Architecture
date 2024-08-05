#pragma once
#include <SDL.h>
#include <string>

struct SpriteComponent
{
	int width;
	int height;
	int zIndex;
	SDL_RendererFlip flip;
	bool isFixed;
	string assetId;
	// x, y
	SDL_Rect srcRect;

	// We initialize to defualt values in the constructor,
	// This is a rule we must follow when using template functions
	SpriteComponent(string assetId = "", int width = 0, int height = 0, int zIndex = 0, bool isFixed = false, int srcRectX = 0, int srcRectY = 0){
		this->assetId = assetId;
		this->width = width;
		this->height = height;
		this->zIndex = zIndex;
		this->flip = SDL_FLIP_NONE;
		this->isFixed = isFixed;
		this->srcRect = { srcRectX, srcRectY, width, height };
	}// SDL_RendererFlip* flip = 
};