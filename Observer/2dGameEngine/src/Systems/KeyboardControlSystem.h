#pragma once
#include "../ECS/ECS.h"
#include "../EventBus/EventBus.h"
#include "../Events/KeyPressedEvent.h"
#include "../Components/KeyboardControlledComponent.h"
#include "../Components/RigidBodyComponent.h"
#include "../Components/SpriteComponent.h"

using namespace std;

class KeyboardControlSystem : public System
{
public:
    // Components of entities we are interested in
	KeyboardControlSystem() {
        requireComponent<SpriteComponent>();
        requireComponent<RigidBodyComponent>();
        requireComponent<KeyboardControlledComponent>();
    }

    void subscribeToEvents(unique_ptr<EventBus>& eventBus) {
        eventBus->subscribeToEvent<KeyPressedEvent>(this, &KeyboardControlSystem::onKeyPressed);
    }

    void onKeyPressed(KeyPressedEvent& event) {
        string keyCode = to_string(event.symbol);
        string keySymbol(1, event.symbol);
        Logger::Log("Key pressed: [" + keyCode + "] " + keySymbol);

        // Find entites with required components
        for (auto entity : getSystemEntities()) {
            auto& keyboardcontrol = entity.getComponent<KeyboardControlledComponent>();
            auto& sprite = entity.getComponent<SpriteComponent>();
            auto& rigidbody = entity.getComponent<RigidBodyComponent>();

            // Modify entities based on key pressed
            switch (event.symbol) {
            case SDLK_w:
                rigidbody.velocity = keyboardcontrol.upVelocity;
                // Poriton of PNG to display
                sprite.srcRect.y = sprite.height * 0;
                break;
            case SDLK_d:
                rigidbody.velocity = keyboardcontrol.rightVelocity;
                sprite.srcRect.y = sprite.height * 1;
                break;
            case SDLK_s:
                rigidbody.velocity = keyboardcontrol.downVelocity;
                sprite.srcRect.y = sprite.height * 2;
                break;
            case SDLK_a:
                rigidbody.velocity = keyboardcontrol.leftVelocity;
                sprite.srcRect.y = sprite.height * 3;
                break;
            }
        }
    }

    void update() {

    }
};