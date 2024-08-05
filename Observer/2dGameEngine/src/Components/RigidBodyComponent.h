#pragma once
#include <glm/glm.hpp>

struct RigidBodyComponent
{
	glm::vec2 velocity;

	// We initialize to defualt values in the constructor,
	// This is a rule we must follow when using template functions
	RigidBodyComponent(glm::vec2 velocity = glm::vec2(0.0, 0.0)){
		this->velocity = velocity;
	}
};