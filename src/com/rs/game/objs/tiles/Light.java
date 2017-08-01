package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;
import com.rs.engine.texture.Texture;

public class Light extends Tile {

	private boolean state = false;
	
	String color = "yellow";
	
	public Light(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/lightOff.png", x, y, depth, width, height, "light");
	}

	public void off() {
		state = false;
		this.setTexture(new Texture("res/tiles/" + color + "LightOff.png"));
	}
	
	public void on() {
		state = true;
		this.setTexture(new Texture("res/tiles/" + color + "LightOn.png"));
	}
	
	public void updateTex() {
		if (state)
			this.setTexture(new Texture("res/tiles/" + color + "LightOn.png"));
		else
			this.setTexture(new Texture("res/tiles/" + color + "LightOff.png"));
	}
	
	public void nextColor() {
		if (color.equals("yellow")) {
			color = "green";
		} else if (color.equals("green")) {
			color = "blue";
		} else if (color.equals("blue")) {
			color = "red";
		} else if (color.equals("red")) {
			color = "yellow";
		}
		updateTex();
	}
	
	public boolean getState() {
		return state;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
