#include "ECS.h"
#include "../Logger/Logger.h"
#include <algorithm>

int IComponent::nextId = 0;

int Entity::getId() const {
    return id;
}

void Entity::kill() {
    registry->killEntity(*this);
}

void Entity::tag(const string& tag) {
    registry->tagEntity(*this, tag);
}

bool Entity::hasTag(const string& tag) const {
    return registry->entityHasTag(*this, tag);
}

void Entity::group(const string& group) {
    registry->groupEntity(*this, group);
}

bool Entity::belongsToGroup(const string& group) const {
    return registry->entityBelongsToGroup(*this, group);
}

void System::addEntityToSystem(Entity entity) {
    entities.push_back(entity);
}

void System::removeEntityFromSystem(Entity entity) {
    entities.erase(remove_if(entities.begin(), entities.end(), [&entity](Entity other) {
        return entity == other;
        }), entities.end());
}

vector<Entity> System::getSystemEntities() const {
    return entities;
}

const signature& System::getComponentSignature() const {
    return componentSignature;
}

Entity Registry::createEntity() {
    int entityId;

    if (freeIds.empty()) {
        // If there are no free ids waiting to be reused
        entityId = numEntities++;
        if (entityId >= entityComponentSignatures.size()) {
            entityComponentSignatures.resize(entityId + 1);
        }
    }
    else {
        // Reuse an id from the list of previously removed entities
        entityId = freeIds.front();
        freeIds.pop_front();
    }

    Entity entity(entityId);
    entity.registry = this;
    entitiesToBeAdded.insert(entity);
    Logger::Log("Entity created with id " + to_string(entityId));

    return entity;
}

void Registry::killEntity(Entity entity) {
    entitiesToBeKilled.insert(entity);
    if (entity.belongsToGroup("projectiles"))
        Logger::Log("Projectile " + to_string(entity.getId()) + " destroyed");
    else 
        Logger::Log("Entity " + to_string(entity.getId()) + " destroyed");
}

void Registry::addEntityToSystems(Entity entity) {
    const auto entityId = entity.getId();

    const auto& entityComponentSignature = entityComponentSignatures[entityId];

    for (auto& system : systems) {
        const auto& systemComponentSignature = system.second->getComponentSignature();

        bool isInterested = (entityComponentSignature & systemComponentSignature) == systemComponentSignature;

        if (isInterested) {
            system.second->addEntityToSystem(entity);
        }
    }
}

void Registry::removeEntityFromSystems(Entity entity) {
    for (auto system : systems) {
        system.second->removeEntityFromSystem(entity);
    }
}

void Registry::tagEntity(Entity entity, const string& tag) {
    entityPerTag.emplace(tag, entity);
    tagPerEntity.emplace(entity.getId(), tag);
}

bool Registry::entityHasTag(Entity entity, const string& tag) const {
    if (tagPerEntity.find(entity.getId()) == tagPerEntity.end()) {
        return false;
    }
    return entityPerTag.find(tag)->second == entity;
}

Entity Registry::getEntityByTag(const string& tag) const {
    return entityPerTag.at(tag);
}

void Registry::removeEntityTag(Entity entity) {
    auto taggedEntity = tagPerEntity.find(entity.getId());
    if (taggedEntity != tagPerEntity.end()) {
        auto tag = taggedEntity->second;
        entityPerTag.erase(tag);
        tagPerEntity.erase(taggedEntity);
    }
}

void Registry::groupEntity(Entity entity, const string& group) {
    entitiesPerGroup.emplace(group, std::set<Entity>());
    entitiesPerGroup[group].emplace(entity);
    groupPerEntity.emplace(entity.getId(), group);
}

bool Registry::entityBelongsToGroup(Entity entity, const string& group) const {
    if (entitiesPerGroup.find(group) == entitiesPerGroup.end()) {
        return false;
    }
    auto groupEntities = entitiesPerGroup.at(group);
    return groupEntities.find(entity.getId()) != groupEntities.end();
}

std::vector<Entity> Registry::getEntitiesByGroup(const string& group) const {
    auto& setOfEntities = entitiesPerGroup.at(group);
    return vector<Entity>(setOfEntities.begin(), setOfEntities.end());
}

void Registry::removeEntityGroup(Entity entity) {
    // if in group, remove entity from group management
    auto groupedEntity = groupPerEntity.find(entity.getId());

    if (groupedEntity != groupPerEntity.end()) {
        auto group = entitiesPerGroup.find(groupedEntity->second);

        if (group != entitiesPerGroup.end()) {
            auto entityInGroup = group->second.find(entity);

            if (entityInGroup != group->second.end()) {
                group->second.erase(entityInGroup);
            }
        }
        groupPerEntity.erase(groupedEntity);
    }
}

void Registry::update() {
    // Processing the entities that are waiting to be created to the active Systems
    for (auto entity : entitiesToBeAdded) {
        addEntityToSystems(entity);
    }
    entitiesToBeAdded.clear();

    // Process the entities that are waiting to be killed from the active Systems
    for (auto entity : entitiesToBeKilled) {
        removeEntityFromSystems(entity);
        entityComponentSignatures[entity.getId()].reset();

        // Remove the entity from the component pools
        for (auto pool : componentPools) {
            if (pool)
                pool->removeEntityFromPool(entity.getId());
        }

        // Make the entity id available to be reused
        freeIds.push_back(entity.getId());

        // Remove any traces of that entity from the tag/group maps
        removeEntityTag(entity);
        removeEntityGroup(entity);
    }
    entitiesToBeKilled.clear();
}