package com.rs.game.objs.ui;

import com.rs.engine.display.Camera;
import com.rs.engine.display.Window;
import com.rs.engine.texture.Texture;
import com.rs.game.objs.rects.TexRect;
import com.rs.game.objs.tiles.Tile;

public class Mouse {

	private boolean holding = false;
	float x, y, xOffset = 0, yOffset = 0;;

	private Tile heldObj;
	private TexRect hand;

	private String pointer = "res/tiles/handPencil.png";
	
	public Mouse(Camera camera) {
		hand = new TexRect(camera, pointer, x, y, 0, 32, 32);
	}

	public void update(Window window) {

		x = window.getMouseCoords().x;
		y = window.getMouseCoords().y;
		hand.reset(x-xOffset, y-yOffset, 32, 32);
		if (holding && heldObj != null)
			heldObj.reset(x, y, 32, 32);
	}

	public void setPointer(String path) {
		pointer = path;
		this.hand.setTexture(new Texture(pointer));
	}
	
	public void render() {
		if (holding && heldObj != null)
			heldObj.render();
		hand.render();
	}

	public boolean isHolding() {
		return holding;
	}

	public void setHolding(boolean holding) {
		this.holding = holding;
	}

	public Tile getHeldObj() {
		return heldObj;
	}

	public void setHeldObj(Tile holdObj) {
		this.heldObj = holdObj;
	}

	public String getPointer() {
		return pointer;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}
