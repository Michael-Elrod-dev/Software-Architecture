#pragma once
#pragma warning(disable : 4244)
#include "../ECS/ECS.h"
#include "../Game/Game.h"
#include "../EventBus/EventBus.h"
#include "../Events/CollisionEvent.h"
#include "../Components/SpriteComponent.h"
#include "../Components/TransformComponent.h"
#include "../Components/RigidBodyComponent.h"

using namespace std;

class MovementSystem : public System
{
public:
	MovementSystem(){
		requireComponent<TransformComponent>();
		requireComponent<RigidBodyComponent>();
	}

	void subscribeToEvents(unique_ptr<EventBus>& eventBus) {
		eventBus->subscribeToEvent<CollisionEvent>(this, &MovementSystem::onCollision);
	}

	void onCollision(CollisionEvent& event) {
		Entity a = event.a;
		Entity b = event.b;

		if (a.belongsToGroup("enemies") && b.belongsToGroup("obstacles")) {
			onEnemyHitsObstacle(a, b); // "a" is the projectile, "b" is the player
		}

		if (b.belongsToGroup("enemies") && a.belongsToGroup("obstacles")) {
			onEnemyHitsObstacle(b, a); // "b" is the projectile, "a" is the player
		}
	}

	void onEnemyHitsObstacle(Entity enemy, Entity obstacle) {
		if (enemy.hasComponent<RigidBodyComponent>() && enemy.hasComponent<SpriteComponent>()) {
			auto& rigidBody = enemy.getComponent<RigidBodyComponent>();
			auto& sprite = enemy.getComponent<SpriteComponent>();

			if (rigidBody.velocity.x != 0) {
				rigidBody.velocity.x *= -1;
				sprite.flip = (sprite.flip == SDL_FLIP_NONE) ? SDL_FLIP_HORIZONTAL : SDL_FLIP_NONE;
				//sprite.assetId = (sprite.assetId == "tank-image") ? "tank2-image" : "tank-image";
			}
			if (rigidBody.velocity.y != 0) {
				rigidBody.velocity.y *= -1;
				sprite.flip = (sprite.flip == SDL_FLIP_NONE) ? SDL_FLIP_VERTICAL : SDL_FLIP_NONE;
			}
		}
	}
	
	// Update entity position based on its velocity, every frame
	void update(double deltaTime){
		// Update all entities
		for (auto entity : getSystemEntities()){
			// Update entity position based on its velocity
			auto& transform = entity.getComponent<TransformComponent>();
			auto& rigidbody = entity.getComponent<RigidBodyComponent>();

			transform.position.x += rigidbody.velocity.x * deltaTime;
			transform.position.y += rigidbody.velocity.y * deltaTime;
			
			// Prevent player from moving outside map bounds
			if (entity.hasTag("player")) {
				int paddingLeft = 10;
				int paddingTop = 10;
				int paddingRight = 50;
				int paddingBottom = 50;
				transform.position.x = transform.position.x < paddingLeft ? paddingLeft : transform.position.x;
				transform.position.x = transform.position.x > Game::mapWidth - paddingRight ? Game::mapWidth - paddingRight : transform.position.x;
				transform.position.y = transform.position.y < paddingTop ? paddingTop : transform.position.y;
				transform.position.y = transform.position.y > Game::mapHeight - paddingBottom ? Game::mapHeight - paddingBottom : transform.position.y;
			}

			// Check for entities that go outside the map bounds
			int margin = 100;
			bool entityOutsideMap = (
				transform.position.x < -margin ||
				transform.position.y < -margin ||
				transform.position.x > Game::mapWidth + margin ||
				transform.position.y > Game::mapHeight + margin
				);

			if (entityOutsideMap && !entity.hasTag("player")) {
				entity.kill();
			}
		}
	}
};