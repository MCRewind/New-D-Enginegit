package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;

public class Inverter extends Tile {
	
	public Inverter(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/inverter.png", x, y, depth, width, height, "inverter");
	}

	public static String getOrient(boolean top, boolean bottom, boolean left, boolean right) {
		if (left && top)
			return "res/tiles/inverterBottomRight.png";
		else if (left && bottom)
			return "res/tiles/inverterTopRight.png";
		else if (right && top)
			return "res/tiles/inverterBottomLeft.png";
		else if (right && bottom)
			return "res/tiles/inverterTopLeft.png";
		else if (right || left)
			return "res/tiles/inverterHoriz.png";
		else if (top || bottom)
			return "res/tiles/inverterVert.png";
		else 
			return "res/tiles/inverter.png";
	}
	
	public boolean invert(boolean power) {
		return !power;
	}
	
}
