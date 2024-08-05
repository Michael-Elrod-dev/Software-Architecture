#pragma once
#include "../ECS/ECS.h"
#include "../EventBus/Event.h"
#include <SDL.h>

class KeyPressedEvent : public Event
{
public:
	SDL_Keycode symbol;

	// this->symbol = symbol
	KeyPressedEvent(SDL_Keycode symbol) : symbol(symbol) {}
};