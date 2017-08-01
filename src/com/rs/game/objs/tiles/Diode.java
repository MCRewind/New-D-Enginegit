package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;
import com.rs.engine.texture.Texture;

public class Diode extends Tile {

	public int dir = 0;

	public Diode(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/diodeUp.png", x, y, depth, width, height, "diode");
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getDir() {
		return dir;
	}

	public void rotate() {
		switch (dir) {
		case 0:
			dir = 3;
			break;
		case 1:
			dir = 2;
			break;
		case 2:
			dir = 0;
			break;
		case 3:
			dir = 1;
			break;
		default:
			dir = 0;
		}
	}

	public void updateTex() {
		switch (dir) {
		case 0:
			setTexture(new Texture("res/tiles/diodeUp.png"));
			break;
		case 1:
			setTexture(new Texture("res/tiles/diodeDown.png"));
			break;
		case 2:
			setTexture(new Texture("res/tiles/diodeLeft.png"));
			break;
		case 3:
			setTexture(new Texture("res/tiles/diodeRight.png"));
			break;
		default:
			setTexture(new Texture("res/tiles/diodeUp.png"));
		}
	}

}
