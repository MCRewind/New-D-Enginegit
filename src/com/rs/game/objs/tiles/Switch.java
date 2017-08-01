package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;
import com.rs.engine.texture.Texture;
import com.rs.engine.utils.Hitbox;

public class Switch extends Tile {

	private boolean state = false;
	
	public Switch(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/switchOff.png", x, y, depth, width, height, "switch");
	}
	
	public void setState(boolean state) {
		this.state = state;
	}

	public boolean getState() {
		return state;
	}
	
	public void flip() {
		state = !state;
	}
	
	public void updateTex() {
		if (!state)
			this.setTexture(new Texture("res/tiles/switchOff.png"));
		else
			this.setTexture(new Texture("res/tiles/switchOn.png"));
	}
	
}
