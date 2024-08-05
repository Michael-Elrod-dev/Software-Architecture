#pragma once
#include "../Logger/Logger.h"
#include <vector>
#include <bitset>
#include <set>
#include <deque>
#include <unordered_map>
#include <typeindex>
#include <memory>

using namespace std;

const unsigned int MAX_COMPONENTS = 32;
// Bitset of 1's & 0's used as on and off switches for entities
typedef bitset<MAX_COMPONENTS> signature;


struct IComponent{
protected:
	static int nextId;
};


// Used to assign a unique ID to a component type
template <typename T>
class Component : public IComponent
{
public:
	// Returns the unique ID of component<T>
	static int getId(){
		static auto id = nextId++;// We make these variables static so that they are unique to each
		return id;                // generated version of this class since it is a template class
	}
};


// Assigns entity IDs and calls Registry to manage components
class Entity {
private:
	int id;

public:
	Entity(int id) : id(id) {};
	Entity(const Entity& entity) = default;
	void kill();
	int getId() const;

	// Manage entity tags and groups
	void tag(const string& tag);
	bool hasTag(const string& tag) const;
	void group(const string& group);
	bool belongsToGroup(const string& group) const;

	// Operator overloads for comparing entity objects
	Entity& operator =  (const Entity& e1) = default;
	bool operator == (const Entity& e1) const { return id == e1.id; }
	bool operator != (const Entity& e1) const { return id != e1.id; }
	bool operator >  (const Entity& e1) const { return id > e1.id; }
	bool operator <  (const Entity& e1) const { return id < e1.id; }

	template <typename TComponent, typename ...TArgs> void addComponent(TArgs&& ...args);
	template <typename TComponent> void removeComponent();
	template <typename TComponent> bool const hasComponent();
	template <typename TComponent> TComponent& getComponent();

	// Declare class to act as a prototype for the class definition below
	// Hold a pointer to the entity's owner registry
	class Registry* registry;
};


// Processes entities that contain a specific signature
class System
{
private:
	signature componentSignature;
	vector<Entity> entities;

public:
	System() = default;
	~System() = default;

	void addEntityToSystem(Entity entity);
	void removeEntityFromSystem(Entity entity);
	vector<Entity> getSystemEntities() const;
	const signature& getComponentSignature() const;

	// Defines the comonent type that entities must have to be considered by the system
	template <typename T> void requireComponent();
};


// A vector of objects of type T
// We use IPool as a way of getting around having to declare a type for our vector of Pools
class IPool
{
public:
	virtual ~IPool() {}
	virtual void removeEntityFromPool(int entityId) = 0;
};


template <typename T>
class Pool : public IPool
{
private:
	vector<T> data;
	int size;

	// Maps to keep track of entity ids per index so the vector is always "packed"
	unordered_map<int, int> entityIdToIndex;
	unordered_map<int, int> indexToEntityId;

public: 
	//  Manage Pool vector
	Pool(int capacity = 100) { 
		size = 0;
		resize(capacity); 
	}
	virtual ~Pool() 
		= default;

	bool const isEmpty() { 
		return size == 0;
	}

	size_t getSize() { 
		return size;
	}

	void resize(int n) { 
		data.resize(n);
	}

	void clear() { 
		data.clear();
		size = 0;
	}

	void add(T object) { 
		data.push_back(object);
	}

	void set(int entityId, T object) { 
		// If the entity element already exists, simply replace the component object
		if (entityIdToIndex.find(entityId) != entityIdToIndex.end()) {
			int index = entityIdToIndex[entityId];
			data[index] = object;
		} else {
			// When adding a new object, we keep track of the entity ids and their index
			int index = size;
			entityIdToIndex.emplace(entityId, index);
			indexToEntityId.emplace(index, entityId);

			// If nessesary, we resize by always doubling the current capacity
			if (index >= data.size())
				data.resize(size * 2);

			data[index] = object;
			size++;
		}
	}

	void remove(int entityId) {
		// Copy the last element to the deleted position to keep array packed
		int indexOfRemoved = entityIdToIndex[entityId];
		int indexOfLast = size - 1;
		data[indexOfRemoved] = data[indexOfLast];

		// Update the entity-index maps to point to the correct elements
		int entityIdOfLastElement = indexToEntityId[indexOfLast];
		entityIdToIndex[entityIdOfLastElement] = indexOfRemoved;
		indexToEntityId[indexOfRemoved] = entityIdOfLastElement;

		entityIdToIndex.erase(entityId);
		indexToEntityId.erase(indexOfLast);

		size--;
	}

	void removeEntityFromPool(int entityId) override {
		if (entityIdToIndex.find(entityId) != entityIdToIndex.end())
			remove(entityId);
	}

	T& get(int entityId) { 
		int index = entityIdToIndex[entityId];
		return static_cast<T&>(data[index]);
	}

	// Overload[] allows access to statements like: var = pool[index]
	T& operator[](unsigned int index) { 
		return data[index]; 
	}
};


// Manages the creation/deletion of entities, as well as
// adding systems and components to entities
class Registry
{
private:
	int numEntities = 0;

	// Vector of component pools
	// [vector index = component[id}], [pool index = entity[id]]
	vector<shared_ptr<IPool>> componentPools;
	
	// List of free entity Ids that were removed
	deque<int> freeIds;

	// Vector component sigatures per entity, returns whether component is turned on or off (01)
	// [vector index = entity id]
	vector<signature> entityComponentSignatures;
	unordered_map<type_index, shared_ptr<System>> systems;

	// Set of entities that are to be added/killed in the next Regirsty update
	set<Entity> entitiesToBeAdded;
	set<Entity> entitiesToBeKilled;

	// Entity tags (one tag name per entity)
	unordered_map<string, Entity> entityPerTag;
	unordered_map<int, string> tagPerEntity;

	// Entity groups (a set of entities per group name)
	unordered_map<string, set<Entity>> entitiesPerGroup;
	unordered_map<int, string> groupPerEntity;

public:
	Registry() = default;

	// Management of entities, components, and systems
	Entity createEntity();
	void killEntity(Entity entity);
	void update();

	// Component managment
	template <typename TComponent, typename ...TArgs> void addComponent(Entity entity, TArgs&& ...args);
	template <typename TComponent> void removeComponent(Entity entity);
	template <typename TComponent> bool const hasComponent(Entity entity);
	template <typename TComponent> TComponent& getComponent(Entity entity);

	// System managment
	template <typename TSystem, typename ...TArgs> void addSystem(TArgs&& ...args);
	template <typename TSystem> void removeSystem();
	template <typename TSystem> bool const hasSystem();
	template <typename TSystem> TSystem& getSystem();

	// Checks the component signature of an entity and adds/removes the entity from the systems
	void addEntityToSystems(Entity entity);
	void removeEntityFromSystems(Entity entity);

	// Tag management
	void tagEntity(Entity entity, const string& tag);
	bool entityHasTag(Entity entity, const string& tag) const;
	Entity getEntityByTag(const string& tag) const;
	void removeEntityTag(Entity entity);

	// Group management
	void groupEntity(Entity entity, const string& group);
	bool entityBelongsToGroup(Entity entity, const string& group) const;
	vector<Entity> getEntitiesByGroup(const string& group) const;
	void removeEntityGroup(Entity entity);
};

//TEMPLATE FUNCTION DEFINITIONS////////////////////////////////////////////////////////////////

//SYSTEM///////////////////////////////////////////////////////////////////////////////////////
template <typename TComponent> 
void System::requireComponent(){
	const auto componentId = Component<TComponent>::getId();
	componentSignature.set(componentId);
}


//REGISTRY/////////////////////////////////////////////////////////////////////////////////////
// Add components to entities
template <typename TComponent, typename ...TArgs>
void Registry::addComponent(Entity entity, TArgs&& ...args){
	const auto componentId = Component<TComponent>::getId();
	const auto entityId = entity.getId();

	// Component Id = index itll go into in the pools vector
	// If vector is full, resize
	if (componentId >= componentPools.size())
		componentPools.resize(componentId + 1, nullptr);

	// If a pool for that component type doesnt exist yet we create it
	if (!componentPools[componentId]){
		shared_ptr<Pool<TComponent>> newComponentPool = make_shared<Pool<TComponent>>();
		componentPools[componentId] = newComponentPool;
	}

	// Get the pool of component values for that component type
	shared_ptr<Pool<TComponent>> componentPool = static_pointer_cast<Pool<TComponent>>(componentPools[componentId]);

	// Create a new component object of type T, and forward the various parameters to the constructor
	TComponent newComponent(forward<TArgs>(args)...);

	// Add the new component to the pool using the entity ID
	componentPool->set(entityId, newComponent);

	// Lastly, change the entities's component signature and set its bitset to 1 (on)
	entityComponentSignatures[entityId].set(componentId);

	Logger::Log("Component id: " + to_string(componentId) + " was added to entity id " + to_string(entityId));
}


// Remove components form entities
template <typename TComponent>
void Registry::removeComponent(Entity entity){
	const auto componentId = Component<TComponent>::getId();
	const auto entityId = entity.getId();

	// Get the component and remove it from entity of interest
	shared_ptr<Pool<TComponent>> componentPool = static_pointer_cast<Pool<TComponent>>(componentPools[componentId]);
	componentPool->remove(entityId);

	// Set the bitset to 0 for the component (off)
	entityComponentSignatures[entityId].set(componentId, false);

	Logger::Log("Component id: " + to_string(componentId) + " was removed from entity id " + to_string(entityId));
}


// Checks if entity has component
template <typename TComponent> 
bool const Registry::hasComponent(Entity entity){
	const auto componentId = Component<TComponent>::getId();
	const auto entityId = entity.getId();

	// .test is a function of bitset and checks if component is on or off
	return entityComponentSignatures[entityId].test(componentId);
}


// Returns specified component
template <typename TComponent> 
TComponent& Registry::getComponent(Entity entity){
	const auto componentId = Component<TComponent>::getId();
	const auto entityId = entity.getId();

	auto componentPool = static_pointer_cast<Pool<TComponent>>(componentPools[componentId]);
	
	return componentPool->get(entityId);
}


// Adds system to entity
template <typename TSystem, typename ...TArgs> 
void Registry::addSystem(TArgs&& ...args){
	// Create a new system and add it to the unordered_map systems
	shared_ptr<TSystem> newSystem = make_shared<TSystem>(forward<TArgs>(args)...);
	systems.insert(make_pair(type_index(typeid(TSystem)), newSystem));
}


// Removes system from an entity
template <typename TSystem>
void Registry::removeSystem(){
	// Get typeid from systems and then remove it
	auto system = systems.find(type_index(typeid(TSystem)));
	systems.erase(system);
}


// Checks if a system has been added
template <typename TSystem>
bool const Registry::hasSystem(){
	return systems.find(type_index(typeid(TSystem))) != systems.end();
}


// Finds and returns specified system
template <typename TSystem>
TSystem& Registry::getSystem(){
	auto system = systems.find(type_index(typeid(TSystem)));
	// first = key, second = value, in maps
	return *(static_pointer_cast<TSystem>(system->second));
}


//ENTITY///////////////////////////////////////////////////////////////////////////////////////
template <typename TComponent, typename ...TArgs> 
void Entity::addComponent(TArgs&& ...args){
	registry->addComponent<TComponent>(*this, forward<TArgs>(args)...);
}


template <typename TComponent> 
void Entity::removeComponent(){
	registry->removeComponent<TComponent>(*this);
}


template <typename TComponent> 
bool const Entity::hasComponent(){
	return registry->hasComponent<TComponent>(*this);
}


template <typename TComponent> 
TComponent& Entity::getComponent(){
	return registry->getComponent<TComponent>(*this);
}