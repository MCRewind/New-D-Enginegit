package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;
import com.rs.engine.texture.Texture;

public class Counter extends Tile{

	int capacity, used;

	public Counter(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/1.png", x, y, depth, width, height, "counter");
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getAvailable() {
		return capacity - used;
	}
	
	public void update() {
		switch(capacity - used) {
		case 0:
			setTexture(new Texture("res/tiles/0.png"));
			break;
		case 1:
			setTexture(new Texture("res/tiles/1.png"));
			break;
		case 2:
			setTexture(new Texture("res/tiles/2.png"));
			break;
		case 3:
			setTexture(new Texture("res/tiles/3.png"));
			break;
		case 4:
			setTexture(new Texture("res/tiles/4.png"));
			break;
		case 5:
			setTexture(new Texture("res/tiles/5.png"));
			break;
		case 6:
			setTexture(new Texture("res/tiles/6.png"));
			break;
		case 7:
			setTexture(new Texture("res/tiles/7.png"));
			break;
		case 8:
			setTexture(new Texture("res/tiles/8.png"));
			break;
		case 9:
			setTexture(new Texture("res/tiles/9.png"));
			break;
		default: 
			setTexture(new Texture("res/tiles/i.png"));
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
		update();
	}

}
