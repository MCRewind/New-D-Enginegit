#version 330 core

uniform sampler2D sampler;
uniform vec4 highlight;

in vec2 texCoords;

out vec4 color;

void main() {
	color = texture(sampler, texCoords);
	if (color.a == 0)
		discard;
}