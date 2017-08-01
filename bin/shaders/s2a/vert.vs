#version 330 core

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec2 animProps; // Loc 0 - Frame Width, Loc 1 - Offset

layout (location = 0) in vec3 vertices;
layout (location = 1) in vec2 iTexCoords;

out vec2 texCoords;

void main() {
	texCoords = vec2(((iTexCoords.x * animProps.x) + animProps.y), iTexCoords.y);
	gl_Position = projection * view * model * vec4(vertices, 1.0);
}