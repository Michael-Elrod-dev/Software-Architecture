#include "./AssetStore.h"
#include "../Logger/Logger.h"
#include <SDL_image.h>

AssetStore::AssetStore(){
	Logger::Log("AssetManager constructor called");
}


AssetStore::~AssetStore(){
	clearAssets();
	Logger::Log("AssetManager destructor called");
}


void AssetStore::clearAssets(){
	// Because the map holds SDL members, we must first use the
	// SDL destroy function to deallocate them first before clear()
	for (auto texture : textures)
		SDL_DestroyTexture(texture.second);
	textures.clear();

	for (auto font : fonts)
		TTF_CloseFont(font.second);
	fonts.clear();

	for (auto fx : soundFX)
		Mix_FreeChunk(fx.second);
	soundFX.clear();

	for (auto m_music : music)
		Mix_FreeMusic(m_music.second);
	music.clear();
}		


void AssetStore::addTexture(SDL_Renderer* renderer, const string& assetId, const string& filePath){
	// Load PNG from file path, convert it to a c style string
	SDL_Surface* surface = IMG_Load(filePath.c_str());
	SDL_Texture* texture = SDL_CreateTextureFromSurface(renderer, surface);
	SDL_FreeSurface(surface);

	// Add the texture to the map of textures
	textures.emplace(assetId, texture);

	Logger::Log("New texture added to asset store with id: " + assetId);
}


SDL_Texture* AssetStore::getTexture(const string& assetId){
	return textures[assetId];
}


void AssetStore::addFont(const string& assetId, const string& filePath, int fontSize) {
	fonts.emplace(assetId, TTF_OpenFont(filePath.c_str(), fontSize));
}


TTF_Font* AssetStore::getFont(const string& assetId) {
	return fonts[assetId];
}

void AssetStore::addMusic(const string& assetID, const char* filePath) {
	Mix_Music* addedMusic = Mix_LoadMUS(filePath);
	if (!addedMusic)
		Logger::Err("Music file could not be found");
	music.emplace(assetID, addedMusic);
}

Mix_Music* AssetStore::GetMusic(const string& assetID) {
	return music[assetID];
}

void AssetStore::addSoundFX(const string& assetID, const string& filePath) {
	soundFX.emplace(assetID, Mix_LoadWAV(filePath.c_str()));
}

Mix_Chunk* AssetStore::getSoundFX(const string& assetID) {
	return soundFX[assetID];
}