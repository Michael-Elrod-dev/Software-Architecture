#pragma once
#include "../ECS/ECS.h"
#include "../Components/BoxColliderComponent.h"
#include "../Components/ProjectileComponent.h"
#include "../Components/HealthComponent.h"
#include "../EventBus/EventBus.h"
#include "../Events/CollisionEvent.h"

using namespace std;

class DamageSystem : public System {
public:
    DamageSystem() {
        requireComponent<BoxColliderComponent>();
    }

    void subscribeToEvents(unique_ptr<EventBus>& eventBus) {
        eventBus->subscribeToEvent<CollisionEvent>(this, &DamageSystem::onCollision);
    }

    void onCollision(CollisionEvent& event) {
        Entity a = event.a;
        Entity b = event.b;

        if (a.belongsToGroup("projectiles") && b.hasTag("player")) {
            onProjectileHitsPlayer(a, b); // "a" is the projectile, "b" is the player
        }

        if (b.belongsToGroup("projectiles") && a.hasTag("player")) {
            onProjectileHitsPlayer(b, a); // "b" is the projectile, "a" is the player
        }

        if (a.belongsToGroup("projectiles") && b.belongsToGroup("enemies")) {
            onProjectileHitsEnemy(a, b); // "a" is the projectile, "b" is the enemy
        }

        if (b.belongsToGroup("projectiles") && a.belongsToGroup("enemies")) {
            onProjectileHitsEnemy(b, a); // "b" is the projectile, "a" is the enemy
        }
    }

    void onProjectileHitsPlayer(Entity projectile, Entity player) {
        const auto projectileComponent = projectile.getComponent<ProjectileComponent>();

        if (!projectileComponent.isFriendly) {
            // Reduce the health of the player by the projectile hitPercentDamage
            auto& health = player.getComponent<HealthComponent>();

            // Subtract the health of the player
            health.healthPercent -= projectileComponent.damagePercent;
            Logger::Log("Collision: Projectile " + to_string(projectile.getId()) + " hit Player " + to_string(player.getId()));
            // Kills the player when health reaches zero
            if (health.healthPercent <= 0) {
                player.kill();
                Logger::Log("Player: " + to_string(player.getId()) + " was killed");
            }

            // Kill the projectile
            projectile.kill();
        }
    }

    void onProjectileHitsEnemy(Entity projectile, Entity enemy) {
        const auto projectileComponent = projectile.getComponent<ProjectileComponent>();

        if (projectileComponent.isFriendly) {
            // Reduce the health of the enemy by the projectile hitPercentDamage
            auto& health = enemy.getComponent<HealthComponent>();

            // Subtract the health of the enemy
            health.healthPercent -= projectileComponent.damagePercent;
            Logger::Log("Collision: Projectile " + to_string(projectile.getId()) + " hit Enemy " + to_string(enemy.getId()));
            // Kills the enemy when health reaches zero
            if (health.healthPercent <= 0) {
                enemy.kill();
                Logger::Log("Enemy: " + to_string(enemy.getId()) + " was killed");
            }

            // Kill the projectile
            projectile.kill();
        }
    }
};