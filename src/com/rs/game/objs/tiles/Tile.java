package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;
import com.rs.engine.utils.Hitbox;
import com.rs.game.objs.rects.TexRect;

public class Tile extends TexRect {

	private String name, texPath;
	
	private float width, height;
	
	private Hitbox hitbox;
	
	public Tile(Camera camera, String texPath, float x, float y, float depth, float width, float height, String name) {
		super(camera, texPath, x, y, depth, width, height);
		this.name = name;
		this.texPath = texPath;
		this.width = width;
		this.height = height;
		hitbox = new Hitbox(x, y, width, height);
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Wire toWire() {
		return (Wire) this;
	}
	
	public Light toLight() {
		return (Light) this;
	}
	
	public Inverter toInverter() {
		return (Inverter) this;
	}
	
	public Switch toSwitch() {
		return (Switch) this;
	}
	
	public Diode toDiode() {
		return (Diode) this;
	}
	
	public void reset(float x, float y, float width, float height) {
		super.reset(x, y, width, height);
		hitbox.setX(x);
		hitbox.setY(y);
		hitbox.setWidth(width);
		hitbox.setHeight(height);
	}
	
	public Tile copy() {
		return new Tile(camera, texPath, position.x, position.y, position.z, width, height, name);
	}
	
}
