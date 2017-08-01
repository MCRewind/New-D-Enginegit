package com.rs.game.objs;

import com.rs.engine.display.Camera;
import com.rs.engine.texture.Texture;
import com.rs.engine.utils.Hitbox;
import com.rs.game.objs.rects.TexRect;

public class Button extends TexRect {

	private Hitbox hitbox;
	
	String upPath;
	String downPath;
	String hoverPath;
	
	private int state = 0;
	
	public Button(Camera camera, String upPath, String downPath, String hoverPath, float x, float y, float depth, float width, float height) {
		super(camera, upPath, x, y, depth, width, height);
		this.upPath = upPath;
		this.downPath = downPath;
		this.hoverPath = hoverPath;
		hitbox = new Hitbox(x, y, width, height);
	}
	
	public void setTex() {
			if (state == 0)
				setTexture(new Texture(upPath));
			else if (state == 1)
				setTexture(new Texture(hoverPath));
			else if (state == 2)
				setTexture(new Texture(downPath));
			else
				setTexture(new Texture(upPath));
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}

	public String getUpPath() {
		return upPath;
	}

	public void setUpPath(String upPath) {
		this.upPath = upPath;
	}

	public String getDownPath() {
		return downPath;
	}

	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}

	public String getHoverPath() {
		return hoverPath;
	}

	public void setHoverPath(String hoverPath) {
		this.hoverPath = hoverPath;
	}
	
}
