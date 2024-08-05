#pragma once

#include "../AssetStore/AssetStore.h"
#include "../ECS/ECS.h"
#include "../Game/Game.h"
#include <SDL.h>
#include <SDL_mixer.h>
#include <memory>

using namespace std;

class MusicPlayerSystem : public System
{
public:

	MusicPlayerSystem(){

		Logger::Log("Music Player Constructor Called");
	}

	~MusicPlayerSystem(){
		Logger::Log("Music Destructor called");
	}

	void playMusic(unique_ptr<AssetStore>& assetStore, const string& assetID, int loops){
		if (!Mix_PlayMusic(assetStore->GetMusic(assetID), loops))
			Logger::Err("Music file was found but could not be played");
		Logger::Log("Playing Music: " + assetID);
	}

	void pauseMusic(){
		Mix_PauseMusic();
	}

	void resumeMusic(){
		// Resume music playback
		Mix_ResumeMusic();
	}

	void stopMusic(){
		// Halt the music playback
		Mix_HaltMusic();
	}
};