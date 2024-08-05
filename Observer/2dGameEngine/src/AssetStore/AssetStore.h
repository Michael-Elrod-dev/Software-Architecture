#pragma once
#include <map>
#include <string>
#include <SDL.h>
#include <SDL_ttf.h>
#include <SDL_mixer.h>

using namespace std;

class AssetStore
{
private:
	map<string, SDL_Texture*> textures;
	map<string, TTF_Font*> fonts;
	map<string, Mix_Music*> music;
	map<string, Mix_Chunk*> soundFX;

public:
	AssetStore();
	~AssetStore();

	void clearAssets();
	SDL_Texture* getTexture(const string& assetId);
	void addTexture(SDL_Renderer* renderer, const string& assetId, const string& filePath);

	void addFont(const string& assetId, const string& filePath, int fontSize);
	TTF_Font* getFont(const string& assetId);

	// Music
	void addMusic(const string& assetID, const char* filePath);
	Mix_Music* GetMusic(const string& assetID);

	// SoundFX
	void addSoundFX(const string& assetID, const string& filePath);
	Mix_Chunk* getSoundFX(const string& assetID);
};