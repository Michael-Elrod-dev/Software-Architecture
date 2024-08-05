#pragma once
#include <SDL.h>
#include <memory>
#include <sol/sol.hpp>
#include "../ECS/ECS.h"
#include "../AssetStore/AssetStore.h"


using namespace std;

class LevelLoader
{
public:
	LevelLoader();
	~LevelLoader();
	void loadLevel(sol::state& lua, const unique_ptr<Registry>& registry, const unique_ptr<AssetStore>& assetStore, SDL_Renderer* renderer, int level);
};