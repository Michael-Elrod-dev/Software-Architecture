#pragma once
#include <memory>
#include "../AssetStore/AssetStore.h"
#include "../ECS/ECS.h"
#include "../Components/TextLabelComponent.h"
#include <SDL.h>

using namespace std;

class RenderTextSystem : public System {
public:
    RenderTextSystem() {
        requireComponent<TextLabelComponent>();
    }

    void update(SDL_Renderer* renderer, unique_ptr<AssetStore>& assetStore, const SDL_Rect& camera) {
        for (auto entity : getSystemEntities()) {
            const auto textlabel = entity.getComponent<TextLabelComponent>();

            SDL_Surface* surface = TTF_RenderText_Blended(
                assetStore->getFont(textlabel.assetId),
                textlabel.text.c_str(),
                textlabel.color
            );
            SDL_Texture* texture = SDL_CreateTextureFromSurface(renderer, surface);
            SDL_FreeSurface(surface);

            int labelWidth = 0;
            int labelHeight = 0;

            SDL_QueryTexture(texture, NULL, NULL, &labelWidth, &labelHeight);

            SDL_Rect dstRect = {
                static_cast<int>(textlabel.position.x - (textlabel.isFixed ? 0 : camera.x)),
                static_cast<int>(textlabel.position.y - (textlabel.isFixed ? 0 : camera.y)),
                labelWidth,
                labelHeight
            };

            SDL_RenderCopy(renderer, texture, NULL, &dstRect);

            SDL_DestroyTexture(texture);
        }
    }
};