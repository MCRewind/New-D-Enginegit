package com.rs.game.objs.rects;


import org.joml.Vector3f;

import com.rs.engine.display.Camera;
import com.rs.engine.model.Vao;

public abstract class Rect {

	protected static final Vector3f dims = new Vector3f(10, 10, 0);
	protected static Vao vao = null;

	protected Camera camera;
	
	protected Vector3f position;
	protected float rotation;
	protected float sx, sy;
	protected float scale;
	
	protected void initVao() {
		float[] vertices = new float[] {
			0,      0,      0.0f,
			0,      dims.y, 0.0f,
			dims.x, dims.y, 0.0f,
			dims.x, 0,      0.0f
		};
		int[] indices = new int[] {
			0, 1, 3,
			3, 1, 2
		};
		float[] texCoords = new float[] {
			0, 0,
			0, 1,
			1, 1,
			1, 0
		};
		vao = new Vao(vertices, indices);
		vao.addAttribute(texCoords, 2);
	}
	
	public abstract void render();
	
	public float getWidth() {
		return sx * dims.x;
	}
	
	public float getHeight() {
		return sy * dims.y;
	}
	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void reset(float x, float y, float width, float height) {
		this.position.x = x;
		this.position.y = y;
		this.sx = width / dims.x;
		this.sy = height / dims.y;
	}
}