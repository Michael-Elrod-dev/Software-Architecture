#pragma once
#include "../ECS/ECS.h"
#include "../AssetStore/AssetStore.h"
#include "../Components/TransformComponent.h"
#include "../Components/SpriteComponent.h"
#include "../Components/HealthComponent.h"
#include <memory>
#include <SDL.h>

using namespace std;

class RenderHealthBarSystem : public System
{
public:
	RenderHealthBarSystem() {
		requireComponent<TransformComponent>();
		requireComponent<SpriteComponent>();
		requireComponent<HealthComponent>();
	}

	void update(SDL_Renderer* renderer, unique_ptr<AssetStore>& assetStore, const SDL_Rect& camera) {
        for (auto entity : getSystemEntities()) {
            const auto transform = entity.getComponent<TransformComponent>();
            const auto sprite = entity.getComponent<SpriteComponent>();
            const auto health = entity.getComponent<HealthComponent>();

            // Draw a health bar with the correct color relevant to percentage
            SDL_Color healthBarColor = { 255, 255, 255 };

            if (health.healthPercent >= 0 && health.healthPercent < 40) {
                // 0-40 = red
                healthBarColor = { 255, 0, 0 };
            }
            if (health.healthPercent >= 40 && health.healthPercent < 80) {
                // 40-80 = yellow
                healthBarColor = { 255, 255, 0 };
            }
            if (health.healthPercent >= 80 && health.healthPercent <= 100) {
                // 80-100 = green
                healthBarColor = { 0, 255, 0 };
            }

            // Scale collision boxes by entity scale
            double spriteWidth = sprite.width * transform.scale.x;
            double spriteHeight = sprite.height * transform.scale.y;

            // Position the health bar indicator in the top-right part of the entity sprite
            int healthBarWidth = spriteWidth - (spriteWidth / 4);
            int healthBarHeight = spriteHeight / 10;
            double healthBarPosX = (transform.position.x + ((spriteWidth - healthBarWidth) / 2)) - camera.x;
            double healthBarPosY = (transform.position.y + (spriteHeight - (spriteHeight / 10))) - camera.y;

            SDL_Rect healthBarRectangle = {
                static_cast<int>(healthBarPosX),
                static_cast<int>(healthBarPosY),
                static_cast<int>(healthBarWidth * (health.healthPercent / 100.0)),
                static_cast<int>(healthBarHeight)
            };
            SDL_SetRenderDrawColor(renderer, healthBarColor.r, healthBarColor.g, healthBarColor.b, 255);
            SDL_RenderFillRect(renderer, &healthBarRectangle);

            /*
            // Render the health percentage text label indicator
            std::string healthText = std::to_string(health.healthPercentage);
            SDL_Surface* surface = TTF_RenderText_Blended(assetStore->GetFont("pico8-font-5"), healthText.c_str(), healthBarColor);
            SDL_Texture* texture = SDL_CreateTextureFromSurface(renderer, surface);
            SDL_FreeSurface(surface);

            int labelWidth = 0;
            int labelHeight = 0;
            SDL_QueryTexture(texture, NULL, NULL, &labelWidth, &labelHeight);
            SDL_Rect healthBarTextRectangle = {
                static_cast<int>(healthBarPosX),
                static_cast<int>(healthBarPosY) + 5,
                labelWidth,
                labelHeight
            };

            SDL_RenderCopy(renderer, texture, NULL, &healthBarTextRectangle);

            SDL_DestroyTexture(texture);
            */
        }
	}
};