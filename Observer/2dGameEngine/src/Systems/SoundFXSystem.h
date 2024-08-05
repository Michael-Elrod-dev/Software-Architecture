#pragma once
#include "../ECS/ECS.h"
#include "../AssetStore/AssetStore.h"
#include "../Logger/Logger.h"
#include <memory>

using namespace std;

class SoundFXSystem : public System
{
public:
	SoundFXSystem(){}
	~SoundFXSystem(){}

	void PlaySoundFX(unique_ptr<AssetStore>& assetStore, const string& assetID, int loops = 1, int channel = 1){
		
		Mix_PlayChannel(channel, assetStore->getSoundFX(assetID), loops);
		Logger::Log("Playing " + assetID + " sound");
	}
};