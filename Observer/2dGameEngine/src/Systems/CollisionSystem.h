#pragma once
#include "../ECS/ECS.h"
#include "../EventBus/EventBus.h"
#include "../Events/CollisionEvent.h"
#include "../Components/HealthComponent.h"
#include "../Components/ProjectileComponent.h"
#include "../Components/BoxColliderComponent.h"
#include "../Components/TransformComponent.h"

using namespace std;

class CollisionSystem : public System {
public:
    CollisionSystem() {
        requireComponent<TransformComponent>();
        requireComponent<BoxColliderComponent>();
    }

    void update(unique_ptr<EventBus>& eventBus) {
        auto entities = getSystemEntities();

        // Loop all the entities that the system is interested in
        for (auto i = entities.begin(); i != entities.end(); i++) {
            Entity a = *i;
            auto aTransform = a.getComponent<TransformComponent>();
            auto aCollider = a.getComponent<BoxColliderComponent>();

            // Loop all the entities that still need to be checked (to the right of i)
            for (auto j = i; j != entities.end(); j++) {
                Entity b = *j;

                // Bypass if we are trying to test the same entity
                if (a == b) {
                    continue;
                }

                auto bTransform = b.getComponent<TransformComponent>();
                auto bCollider = b.getComponent<BoxColliderComponent>();

                // Perform the AABB collision check between entities a and b
                bool collisionHappened = checkAABBCollision(
                    aTransform.position.x + aCollider.offset.x,
                    aTransform.position.y + aCollider.offset.y,
                    aCollider.width * aTransform.scale.x,
                    aCollider.height * aTransform.scale.y,
                    bTransform.position.x + bCollider.offset.x,
                    bTransform.position.y + bCollider.offset.y,
                    bCollider.width * bTransform.scale.x,
                    bCollider.height * bTransform.scale.y
                );

                if (collisionHappened) {
                    eventBus->emitEvent<CollisionEvent>(a, b);
                }
            }
        }
    }

    bool checkAABBCollision(double aX, double aY, double aW, double aH, double bX, double bY, double bW, double bH) {
        return (
            aX < bX + bW &&
            aX + aW > bX &&
            aY < bY + bH &&
            aY + aH > bY
            );
    }
};