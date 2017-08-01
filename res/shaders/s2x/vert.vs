#version 330 core

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec2 character;

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec2 iTexCoords;

out vec2 texCoords;

void main() {
	texCoords = iTexCoords + character;
	gl_Position = projection * view * model * vec4(vertices, 1.0);
}