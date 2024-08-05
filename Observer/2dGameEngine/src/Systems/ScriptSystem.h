#pragma once
#include <tuple>
#include "../ECS/ECS.h"
#include "../Logger/Logger.h"
#include "../Components/ScriptComponent.h"
#include "../Components/RigidBodyComponent.h"
#include "../Components/AnimationComponent.h"
#include "../Components/TransformComponent.h"
#include "../Components/ProjectileEmitterComponent.h"

using namespace std;

///////////////////////////////////////////////////////////
// Declare native C++ functions to bind with Lua //////////
///////////////////////////////////////////////////////////
tuple<double, double> getEntityPosition(Entity entity) {
    if (entity.hasComponent<TransformComponent>()) {
        const auto transform = entity.getComponent<TransformComponent>();
        return make_tuple(transform.position.x, transform.position.y);
    }
    else {
        Logger::Err("Trying to get the position of an entity that has no transform component");
        return make_tuple(0.0, 0.0);
    }
}

void setEntityVelocity(Entity entity, double x, double y) {
    if (entity.hasComponent<RigidBodyComponent>()) {
        auto& rigidbody = entity.getComponent<RigidBodyComponent>();
        rigidbody.velocity.x = x;
        rigidbody.velocity.y = y;
    }
    else 
        Logger::Err("Trying to set the velocity of an entity that has no rigidbody component");
}

void setEntityRotation(Entity entity, double angle) {
    if (entity.hasComponent<TransformComponent>()) {
        auto& transform = entity.getComponent<TransformComponent>();
        transform.rotation = angle;
    }
    else 
        Logger::Err("Trying to set the rotation of an entity that has no transform component");
}

void setEntityAnimationFrame(Entity entity, int frame) {
    if (entity.hasComponent<AnimationComponent>()) {
        auto& animation = entity.getComponent<AnimationComponent>();
        animation.currentFrame = frame;
    }
    else 
        Logger::Err("Trying to set the animation frame of an entity that has no animation component");
}

void setProjectileVelocity(Entity entity, double x, double y) {
    if (entity.hasComponent<ProjectileEmitterComponent>()) {
        auto& projectileEmitter = entity.getComponent<ProjectileEmitterComponent>();
        projectileEmitter.projectileVelocity.x = x;
        projectileEmitter.projectileVelocity.y = y;
    }
    else 
        Logger::Err("Trying to set the projectile velocity of an entity that has no projectile emitter component");
}

tuple<double, double> getEntityVelocity(Entity entity) {
    if (entity.hasComponent<RigidBodyComponent>()) {
        const auto rigidbody = entity.getComponent<RigidBodyComponent>();
        return make_tuple(rigidbody.velocity.x, rigidbody.velocity.y);
    }
    else 
        Logger::Err("Trying to get the velocity of an entity that has no rigidbody component");
        return make_tuple(0.0, 0.0);
}

void setEntityPosition(Entity entity, double x, double y) {
    if (entity.hasComponent<TransformComponent>()) {
        auto& transform = entity.getComponent<TransformComponent>();
        transform.position.x = x;
        transform.position.y = y;
    }
    else 
        Logger::Err("Trying to set the position of an entity that has no transform component.");
}

class ScriptSystem : public System {
public:
    ScriptSystem() {
        requireComponent<ScriptComponent>();
    }

    void createLuaBindings(sol::state& lua) {
        // Create the "entity" usertype so Lua knows what an entity is
        lua.new_usertype<Entity>(
            "entity",
            "get_id", &Entity::getId,
            "destroy", &Entity::kill,
            "has_tag", &Entity::hasTag,
            "belongs_to_group", &Entity::belongsToGroup
            );

        // Create all the bindings between C++ and Lua functions
        lua.set_function("get_position", getEntityPosition);
        lua.set_function("get_velocity", getEntityVelocity);
        lua.set_function("set_position", setEntityPosition);
        lua.set_function("set_velocity", setEntityVelocity);
        lua.set_function("set_rotation", setEntityRotation);
        lua.set_function("set_projectile_velocity", setProjectileVelocity);
        lua.set_function("set_animation_frame", setEntityAnimationFrame);
    }

    void update(double deltaTime, int ellapsedTime) {
        // Loop all entities that have a script component and invoke their Lua function
        for (auto entity : getSystemEntities()) {
            const auto script = entity.getComponent<ScriptComponent>();
            script.func(entity, deltaTime, ellapsedTime); // here is where we invoke a sol::function
        }
    }
};