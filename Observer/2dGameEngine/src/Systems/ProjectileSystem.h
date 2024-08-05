#pragma once
#include <SDL.h>
#include <memory>
#include "../ECS/ECS.h"
#include "../Events/KeyPressedEvent.h"
#include "../EventBus/EventBus.h"
#include "../Components/ProjectileEmitterComponent.h"
#include "../Components/ProjectileComponent.h"
#include "../Components/TransformComponent.h"
#include "../Components/RigidBodyComponent.h"
#include "../Components/SpriteComponent.h"
#include "../Components/BoxColliderComponent.h"
#include "../Components/CameraComponent.h"

using namespace std;

class ProjectileSystem : public System {
public:
    ProjectileSystem() {
        requireComponent<ProjectileEmitterComponent>();
        requireComponent<TransformComponent>();
    }

    void subscribeToEvents(unique_ptr<EventBus>& eventBus) {
        eventBus->subscribeToEvent<KeyPressedEvent>(this, &ProjectileSystem::onKeyPressed);
    }

    void onKeyPressed(KeyPressedEvent& event) {
        if (event.symbol == SDLK_SPACE) {
            for (auto entity : getSystemEntities()) {
                if (entity.hasTag("player")) {
                    const auto projectileEmitter = entity.getComponent<ProjectileEmitterComponent>();
                    const auto transform = entity.getComponent<TransformComponent>();
                    const auto rigidbody = entity.getComponent<RigidBodyComponent>();

                    // If parent entity has sprite, start the projectile position in the middle of the entity
                    glm::vec2 projectilePosition = transform.position;
                    if (entity.hasComponent<SpriteComponent>()) {
                        auto sprite = entity.getComponent<SpriteComponent>();
                        projectilePosition.x += (transform.scale.x * sprite.width / 2);
                        projectilePosition.y += (transform.scale.y * sprite.height / 2);
                    }

                    // If parent entity direction is controlled by the keyboard keys, modify the direction of the projectile accordingly
                    glm::vec2 projectileVelocity = projectileEmitter.projectileVelocity;
                    int directionX = 0;
                    int directionY = 0;
                    if (rigidbody.velocity.x > 0) directionX = +1;
                    if (rigidbody.velocity.x < 0) directionX = -1;
                    if (rigidbody.velocity.y > 0) directionY = +1;
                    if (rigidbody.velocity.y < 0) directionY = -1;
                    projectileVelocity.x = projectileEmitter.projectileVelocity.x * directionX;
                    projectileVelocity.y = projectileEmitter.projectileVelocity.y * directionY;

                    // Create new projectile entity and add it to the world
                    Entity projectile = entity.registry->createEntity();
                    projectile.group("projectiles");
                    projectile.addComponent<TransformComponent>(projectilePosition, glm::vec2(1.0, 1.0), 0.0);
                    projectile.addComponent<RigidBodyComponent>(projectileVelocity);
                    projectile.addComponent<SpriteComponent>("bullet-texture", 4, 4, 4);
                    projectile.addComponent<BoxColliderComponent>(4, 4);
                    projectile.addComponent<ProjectileComponent>(projectileEmitter.isFriendly, projectileEmitter.damagePercent, projectileEmitter.duration);
                }
            }
        }
    }

    void update(unique_ptr<Registry>& registry) {
        for (auto entity : getSystemEntities()) {
            auto& projectileEmitter = entity.getComponent<ProjectileEmitterComponent>();
            const auto transform = entity.getComponent<TransformComponent>();

            // If emission frequency is zero, bypass re-emission logic
            if (projectileEmitter.frequency == 0) {
                continue;
            }

            // Check if its time to re-emit a new projectile
            if (SDL_GetTicks() - projectileEmitter.lastEmissionTime > projectileEmitter.frequency) {
                glm::vec2 projectilePosition = transform.position;
                if (entity.hasComponent<SpriteComponent>()) {
                    const auto sprite = entity.getComponent<SpriteComponent>();
                    projectilePosition.x += (transform.scale.x * sprite.width / 2);
                    projectilePosition.y += (transform.scale.y * sprite.height / 2);
                }

                // Add a new projectile entity to the registry
                Entity projectile = registry->createEntity();
                projectile.group("projectiles");                              // v PROJECTILE SCALE HERE v
                projectile.addComponent<TransformComponent>(projectilePosition, glm::vec2(1.0, 1.0), 0.0);
                projectile.addComponent<RigidBodyComponent>(projectileEmitter.projectileVelocity);
                projectile.addComponent<SpriteComponent>("bullet-texture", 4, 4, 4);
                projectile.addComponent<BoxColliderComponent>(4, 4);
                projectile.addComponent<ProjectileComponent>(projectileEmitter.isFriendly, projectileEmitter.damagePercent, projectileEmitter.duration);

                // Update the projectile emitter component last emission to the current milliseconds
                projectileEmitter.lastEmissionTime = SDL_GetTicks();
            }
        }
    }
};