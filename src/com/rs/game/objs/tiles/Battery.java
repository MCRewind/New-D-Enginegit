package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;

public class Battery extends Tile {

	private float energy = 10;
	
	public Battery(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/battery.png", x, y, depth, width, height, "battery");
	}

	public void fill() {
		energy++;
	}
	
	public void drain() {
		energy--;
	}
	
	public float getEnergy() {
		return energy;
	}
	
	public void setEnergy(float energy) {
		this.energy = energy;
	}
	
}
