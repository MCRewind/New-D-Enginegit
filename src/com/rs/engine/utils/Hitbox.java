package com.rs.engine.utils;

public class Hitbox {

	float x, y, width, height;

	public Hitbox(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean checkCollision(float x1, float y1) {
		if (x1 >= x && x1 < x + width && y1 >= y && y1 < y + height)
			return true;
		return false;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String toString() {
		return "x: " + x + ", y: " + y + ", width: " + width + ", height: " + height;
	}
	
}
