#version 330 core

uniform sampler2D sampler;

in vec2 texCoords;

out vec4 color;

void main() {
	if (texture(sampler, texCoords).a == 0)
		discard;
	color = mix(texture(sampler, texCoords), vec4(0, 0, 1, 1), 0.3);
}