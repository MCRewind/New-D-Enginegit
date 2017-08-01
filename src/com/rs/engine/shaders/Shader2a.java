package com.rs.engine.shaders;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform2f;

import com.rs.engine.texture.Anim;

public class Shader2a extends Shader {
	
	private int animLoc;
	
	protected Shader2a() {
		super("/shaders/s2a/vert.vs", "/shaders/s2a/frag.fs");
	}

	protected void getUniformLocs() {
		super.getUniformLocs();
		animLoc = glGetUniformLocation(program, "animProps");
	}
	
	public void setAnim(Anim anim) {
		glUniform2f(animLoc, anim.getFrameWidth(), anim.getOffset());
	}
}