#pragma once
#include <glm/glm.hpp>
#include <SDL.h>
#include <string>

using namespace std;

struct TextLabelComponent {
	glm::vec2 position;
	string text;
	string assetId;
	SDL_Color color;
	bool isFixed;

	TextLabelComponent(glm::vec2 position = glm::vec2(0), const string& text = "", const string& assetId = "", const SDL_Color& color = {0, 0, 0}, bool isFixed = true) {
		this->position = position;
		this->text = text;
		this->assetId = assetId;
		this->color = color;
		this->isFixed = isFixed;
	}
};