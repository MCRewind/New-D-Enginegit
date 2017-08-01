package com.rs.engine.utils;

public class Level {

	String name;
	String description;
	int[] amounts;
	int width, height;
	
	public Level(int width, int height, String name, String description, int[] amounts) {
		this.width = width;
		this.height = height;
		this.name = name;
		this.description = description;
		this.amounts = amounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int[] getAmounts() {
		return amounts;
	}

	public void setAmounts(int[] amounts) {
		this.amounts = amounts;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
