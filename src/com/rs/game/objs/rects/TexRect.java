package com.rs.game.objs.rects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.rs.engine.display.Camera;
import com.rs.engine.shaders.Shader;
import com.rs.engine.shaders.Shader2a;
import com.rs.engine.texture.Anim;
import com.rs.engine.texture.Texture;
import com.rs.game.objs.tiles.Wire;

public class TexRect extends Rect {

	private Texture texture;
	private Shader shader;

	public TexRect(Camera camera, String texPath, float x, float y, float depth, float width, float height) {
		this.camera = camera;
		if(vao == null)
			initVao();
		position = new Vector3f(x, y, depth);
		rotation = 0;
		scale = 1;
		sx = width / dims.x;
		sy = height / dims.y;
		texture = new Texture(texPath);
		shader = Shader.SHADER2T;
	}

	public TexRect(Camera camera, String texPath, int frames, int fps, float x, float y, float depth, float width, float height) {
		this.camera = camera;
		if(vao == null)
			initVao();
		position = new Vector3f(x, y, depth);
		rotation = 0;
		scale = 1;
		sx = width / dims.x;
		sy = height / dims.y;
		texture = new Anim(texPath, frames, fps);
		shader = Shader.SHADER2A;
	}

	public void render() {
		texture.bind();
		shader.enable();
		shader.setProjection(camera.getProjection());
		shader.setView(camera.getView());
		shader.setModel(new Matrix4f().translate(position).rotateZ(rotation).scale(sx*scale, sy*scale, 1));
		if(shader instanceof Shader2a)
			((Shader2a) shader).setAnim((Anim) texture);
		vao.render();
		shader.disable();
		texture.unbind();
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public Shader getShader() {
		return shader;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}

}