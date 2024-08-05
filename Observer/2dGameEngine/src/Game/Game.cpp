#include "Game.h"
#include <SDL.h>
#include <SDL_image.h>
#include <SDL_mixer.h>
#include <glm/glm.hpp>
#include <imgui/imgui.h>
#include <imgui/imgui_sdl.h>
#include <imgui/imgui_impl_sdl.h>
#include "../ECS/ECS.h"
#include "LevelLoader.h"
#include "../Logger/Logger.h"
#include "../Systems/ScriptSystem.h"
#include "../Systems/RenderSystem.h"
#include "../Systems/DamageSystem.h"
#include "../Systems/MovementSystem.h"
#include "../Systems/SoundFXSystem.h"
#include "../Systems/AnimationSystem.h"
#include "../Systems/CollisionSystem.h"
#include "../Systems/RenderGUISystem.h"
#include "../Systems/RenderTextSystem.h"
#include "../Systems/ProjectileSystem.h"
#include "../Systems/MusicPlayerSystem.h"
#include "../Systems/BoxCollisionSystem.h"
#include "../Systems/CameraMovementSystem.h"
#include "../Systems/RenderHealthBarSystem.h"
#include "../Systems/KeyboardControlSystem.h" 
#include "../Systems/ProjectileLifecyleSystem.h"

int Game::windowWidth;
int Game::windowHeight;
int Game::mapWidth;
int Game::mapHeight;

Game::Game() {
	isDebug = false;
	playing = false;
	musicPlaying = true;
	
	registry   = make_unique<Registry>();
	eventBus   = make_unique<EventBus>();
	assetStore = make_unique<AssetStore>();

	// Initialize everything from SDL, returns 0 is successful
	if (SDL_Init(SDL_INIT_EVERYTHING) != 0) {
		Logger::Err("Error Initializing SDL");
		return;
	}
	if (TTF_Init() != 0) {
		Logger::Err("Error Initializing SDL TTF");
		return;
	}
	else {
		// Get display resolution
		SDL_DisplayMode displayMode;
		// (Display#, DisplayMode object)
		SDL_GetCurrentDisplayMode(0, &displayMode); 
		windowWidth = 1280;  //displayMode.w;
		windowHeight = 720;  //displayMode.h;

		// Creates an OS window
		window = SDL_CreateWindow(
			NULL,                   // Title of window
			SDL_WINDOWPOS_CENTERED, // Windows x position on screen
			SDL_WINDOWPOS_CENTERED, // Windows y position on screen
			windowWidth,            // Size of window, could be in pixels
			windowHeight,           // Size of window, could be in pixels
			SDL_WINDOW_BORDERLESS   // Decoration of window, leave borderless for games
		);

		// If window doesnt open
		if (!window) {
			Logger::Err("Error ceating SDL window!");
			return;
		}

		// Creates a renderer
		renderer = SDL_CreateRenderer(
			window,					// Window to render in
			-1,						// Display to render on, -1 -> default
			SDL_RENDERER_ACCELERATED// Param for flags (ACCELERATED uses GPU rather than CPU for rendering)
		);
		if (!renderer) {
			Logger::Err("Error creating SDL renderer!");
			return;
		}

		// Initialize the imgui context
		ImGui::CreateContext();
		ImGuiSDL::Initialize(renderer, windowWidth, windowHeight);

		// Initalize the camera view with the entire screen view
		camera.x = 0;
		camera.y = 0;
		camera.w = windowWidth;
		camera.h = windowHeight;
		
		// Queue background music
		SDL_LoadWAV("./assets/sounds/Level1.wav", &wavSpec, &wavBuffer, &wavLength);
		deviceId = SDL_OpenAudioDevice(NULL, 0, &wavSpec, NULL, 0);
		if (!SDL_QueueAudio(deviceId, wavBuffer, wavLength))
			Logger::Err("Audio file could not be queued");
		music();

		// Change video to true fullscreen with selected resolution above
		//SDL_SetWindowFullscreen(window, SDL_WINDOW_FULLSCREEN);
		playing = true;
	}
}

void Game::music() {
	// Unmute music
	if (musicPlaying) 
		SDL_PauseAudioDevice(deviceId, 0);
	// Mute music
	else 
		SDL_PauseAudioDevice(deviceId, 1);
}


void Game::processInput() {
	SDL_Event sdlEvent;
	// Check for input for every frame of gameplay
	while (SDL_PollEvent(&sdlEvent)){
		// Handle ImGui SDL input
		ImGui_ImplSDL2_ProcessEvent(&sdlEvent);
		ImGuiIO& io = ImGui::GetIO();
		int mouseX, mouseY;
		const int buttons = SDL_GetMouseState(&mouseX, &mouseY);
		io.MousePos = ImVec2(mouseX, mouseY);
		io.MouseDown[0] = buttons & SDL_BUTTON(SDL_BUTTON_LEFT);
		io.MouseDown[1] = buttons & SDL_BUTTON(SDL_BUTTON_RIGHT);

		// Handle core SDL events
		// .type = type of user input
		switch (sdlEvent.type){
		// User closes the window
		case SDL_QUIT:
			playing = false;
			break;
		// Key Press
		case SDL_KEYDOWN:
			// ESC key
			if (sdlEvent.key.keysym.sym == SDLK_ESCAPE)
				playing = false;
			// d key
			if (sdlEvent.key.keysym.sym == SDLK_9)
				isDebug = !isDebug;
			// m key
			if (sdlEvent.key.keysym.sym == SDLK_m) {
				musicPlaying = !musicPlaying;
				music();
			}
			// Emit an event for keys pressed
			eventBus->emitEvent<KeyPressedEvent>(sdlEvent.key.keysym.sym);
			break;
		}
	}
}


void Game::setup() {
	// Add the sytems that need to be processed in the game
	registry->addSystem<MovementSystem>();
	registry->addSystem<RenderSystem>();
	registry->addSystem<DamageSystem>();
	registry->addSystem<AnimationSystem>();
	registry->addSystem<CollisionSystem>();
	registry->addSystem<BoxCollisionSystem>();
	registry->addSystem<KeyboardControlSystem>();
	registry->addSystem<CameraMovementSystem>();
	registry->addSystem<ProjectileSystem>();
	registry->addSystem<ProjectileLifecycleSystem>();
	registry->addSystem<RenderTextSystem>();
	registry->addSystem<RenderHealthBarSystem>();
	registry->addSystem<RenderGUISystem>();
	registry->addSystem<ScriptSystem>();
	registry->addSystem<MusicPlayerSystem>();
	registry->addSystem<SoundFXSystem>();

	// Create bindings between C++ and Lua
	registry->getSystem<ScriptSystem>().createLuaBindings(lua);

	// Load/Play background music
	//assetStore->addMusic(to_string(0), "./assets/sounds/stranger-things.mp3");
	//registry->getSystem<MusicPlayerSystem>().playMusic(assetStore, to_string(0), 0);

	// Load the first level
	LevelLoader loader;
	lua.open_libraries(sol::lib::base, sol::lib::math, sol::lib::os);
	loader.loadLevel(lua, registry, assetStore, renderer, 1);
}


void Game::update() {
	// Prevent funtion from running until desired framerate is met
	timeToWait = MILLISECONDS_PER_FRAME - (SDL_GetTicks() - prevFrameTime);
	if (timeToWait > 0 && timeToWait <= MILLISECONDS_PER_FRAME)
		SDL_Delay(timeToWait);

	// SDL_GetTicks counts the time since last call
	deltaTime = (SDL_GetTicks() - prevFrameTime)/1000.00;
	prevFrameTime = SDL_GetTicks();      

	// Reset all event handlers for the current frame
	eventBus->reset();

	// Subscribe systems to appropriate events
	registry->getSystem<MovementSystem>().subscribeToEvents(eventBus);
	registry->getSystem<DamageSystem>().subscribeToEvents(eventBus);
	registry->getSystem<KeyboardControlSystem>().subscribeToEvents(eventBus);
	registry->getSystem<ProjectileSystem>().subscribeToEvents(eventBus);

	// Update entities waiting in registry 
	registry->update();

	// Ask all the systems to update
	registry->getSystem<MovementSystem>().update(deltaTime);
	registry->getSystem<AnimationSystem>().update(); 
	registry->getSystem<CollisionSystem>().update(eventBus);
	registry->getSystem<ProjectileSystem>().update(registry);
	registry->getSystem<CameraMovementSystem>().update(camera);
	registry->getSystem<ProjectileLifecycleSystem>().update();
	registry->getSystem<ScriptSystem>().update(deltaTime, SDL_GetTicks());
}


void Game::render() {
	// (type, red, green, blue, transparency) scale 0-255
	SDL_SetRenderDrawColor(renderer, 21, 21, 21, 255);
	// Clears renderer
	SDL_RenderClear(renderer);

	// Update rendering systems
	registry->getSystem<RenderSystem>().update(renderer, assetStore, camera);
	registry->getSystem<RenderTextSystem>().update(renderer, assetStore, camera);
	registry->getSystem<RenderHealthBarSystem>().update(renderer, assetStore, camera);

	if (isDebug) {
		registry->getSystem<BoxCollisionSystem>().update(renderer, camera);
		registry->getSystem<RenderGUISystem>().update(registry, camera);
	}

	// Swap back buffer with front buffer, presenting render to the display
	SDL_RenderPresent(renderer);
}


void Game::run() {
	setup();

	while (playing) {
		processInput();
		update();
		render();
	}
}


Game::~Game(){
	ImGuiSDL::Deinitialize();
	ImGui::DestroyContext();
	SDL_DestroyRenderer(renderer);
	SDL_DestroyWindow(window);
	SDL_CloseAudioDevice(deviceId);
	SDL_FreeWAV(wavBuffer);
	SDL_Quit();
}