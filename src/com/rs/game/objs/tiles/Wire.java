package com.rs.game.objs.tiles;

import com.rs.engine.display.Camera;

public class Wire extends Tile {

	private boolean powered;

	public Wire(Camera camera, float x, float y, float depth, float width, float height) {
		super(camera, "res/tiles/wire.png", x, y, depth, width, height, "wire");
	}

	public static String getOrient(boolean top, boolean bottom, boolean left, boolean right) {
		if (top && bottom && left && right)
			return "res/tiles/wireIntersection.png";
		else if (top && bottom && left)
			return "res/tiles/wireTLeft.png";
		else if (top && bottom && right)
			return "res/tiles/wireTRight.png";
		else if (left && right && top)
			return "res/tiles/wireTTop.png";
		else if (left && right && bottom)
			return "res/tiles/wireTBottom.png";
		else if (left && top)
			return "res/tiles/wireBottomRight.png";
		else if (left && bottom)
			return "res/tiles/wireTopRight.png";
		else if (right && top)
			return "res/tiles/wireBottomLeft.png";
		else if (right && bottom)
			return "res/tiles/wireTopLeft.png";
		else if (right || left)
			return "res/tiles/wireHoriz.png";
		else if (top || bottom)
			return "res/tiles/wireVert.png";
		else 
			return "res/tiles/wire.png";
	}

	public boolean isPowered() {
		return powered;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}

}
