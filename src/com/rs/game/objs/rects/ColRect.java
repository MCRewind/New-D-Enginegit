package com.rs.game.objs.rects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.rs.engine.display.Camera;
import com.rs.engine.shaders.Shader;
import com.rs.engine.shaders.Shader2c;

public class ColRect extends Rect {
	
	private Shader2c shader;
	
	private float r, g, b, a;
	
	public ColRect(Camera camera, float x, float y, float depth, float width, float height, float r, float g, float b, float a) {
		this.camera = camera;
		if(vao == null)
			initVao();
		position = new Vector3f(x, y, depth);
		rotation = 0;
		sx = width / dims.x;
		sy = height / dims.y;
		shader = Shader.SHADER2C;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void render() {
		shader.enable();
		shader.setProjection(camera.getProjection());
		shader.setView(camera.getView());
		shader.setModel(new Matrix4f().translate(position).rotateZ(rotation).scale(sx, sy, 1));
		shader.setColor(r, g, b, a);
		vao.render();
		shader.disable();
	}
}