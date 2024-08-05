#pragma once
#include "../ECS/ECS.h"
#include "../AssetStore/AssetStore.h"
#include "../EventBus/EventBus.h"
#include <sol/sol.hpp>
#include <SDL.h>

using namespace std;
const int FPS = 60;
const int MILLISECONDS_PER_FRAME = 1000 / FPS;

class Game
{
private:
	double deltaTime;
	int prevFrameTime;
	int timeToWait;
	bool isDebug;
	bool playing;
	bool musicPlaying;
	
	//Declare Lua state with Sol
	sol::state lua;

	SDL_Window* window;
	SDL_Renderer* renderer;
	SDL_Rect camera;
	unique_ptr<Registry> registry;
	unique_ptr<AssetStore> assetStore;
	unique_ptr<EventBus> eventBus;

	SDL_AudioSpec wavSpec;
	Uint32 wavLength;
	Uint8* wavBuffer;
	SDL_AudioDeviceID deviceId;

public:
	Game();
	~Game();
	void run();
	void processInput();
	void setup();
	void update();
	void music();
	void render();

	static int windowWidth;
	static int windowHeight;
	static int mapWidth;
	static int mapHeight;
};