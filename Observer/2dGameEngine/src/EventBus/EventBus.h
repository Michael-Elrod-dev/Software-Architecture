#pragma once
#include "../Logger/Logger.h"
#include "Event.h"
#include <map>
#include <typeindex>
#include <functional>
#include <list>

using namespace std;

class IEventCallback
{
private:
	virtual void Call(Event& e) = 0;

public:
	virtual ~IEventCallback() = default;

	void Execute(Event& e){
		Call(e);
	}
};


template <typename TOwner, typename TEvent>
class EventCallback : public IEventCallback
{
private:
	typedef void(TOwner::*CallbackFunction)(TEvent&);

	TOwner* ownerInstance;
	CallbackFunction callbackFunction;

	virtual void Call(Event& e) override{
		invoke(callbackFunction, ownerInstance, static_cast<TEvent&>(e));
	}

public:
	EventCallback(TOwner* ownerInstance, CallbackFunction callbackFunction){
		this->ownerInstance = ownerInstance;
		this->callbackFunction = callbackFunction;
	}

	virtual ~EventCallback() override = default;
};

typedef list<unique_ptr<IEventCallback>> HandlerList;

class EventBus
{
private:
	map<type_index, unique_ptr<HandlerList>> subscribers;

public:
	EventBus() = default;

	// Clears the subscriber list
	void reset(){
		subscribers.clear();
	}

	/////////////////////////////////////////////////////////////////////////
	// Subscribe to an event type ///////////////////////////////////////////
	// In this implementation, an observer subscribes to an event ///////////
	// Ex: eventBus->SubscribeToEvent<CollisionEvent>(&Game::onCollision); //
	/////////////////////////////////////////////////////////////////////////
	template <typename TEvent, typename TOwner>
	void subscribeToEvent(TOwner* ownerInstance, void (TOwner::* callbackFunction)(TEvent&)) {
		if (!subscribers[typeid(TEvent)].get()) {
			subscribers[typeid(TEvent)] = make_unique<HandlerList>();
		}
		auto subscriber = make_unique<EventCallback<TOwner, TEvent>>(ownerInstance, callbackFunction);
		subscribers[typeid(TEvent)]->push_back(move(subscriber));
	}

	/////////////////////////////////////////////////////////////////////////
	// Emit an event of type <T> ////////////////////////////////////////////
	// In this implementation, as soon as something emits an event //////////
	// we go ahead and execute all the observer callback functions //////////
	// Ex: eventBus->EmitEvent<CollisionEvent>(player, enemy); //////////////
	/////////////////////////////////////////////////////////////////////////
	template <typename TEvent, typename ...TArgs>
	void emitEvent(TArgs&& ...args) {
		auto handlers = subscribers[typeid(TEvent)].get();
		if (handlers) {
			for (auto it = handlers->begin(); it != handlers->end(); it++) {
				auto handler = it->get();
				TEvent event(forward<TArgs>(args)...);
				handler->Execute(event);
			}
		}
	}
};