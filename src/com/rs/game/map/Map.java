package com.rs.game.map;

import com.rs.engine.display.Camera;
import com.rs.engine.display.Window;
import com.rs.engine.shaders.Shader;
import com.rs.engine.texture.Texture;
import com.rs.game.objs.tiles.Battery;
import com.rs.game.objs.tiles.Tile;
import com.rs.game.objs.tiles.Wire;

public class Map {

	int x, y, rows, columns;

	Tile[][] tiles;

	Tile emptyTile;

	public Map(int x, int y, int columns, int rows) {
		this.x = x;
		this.y = y;
		this.rows = rows;
		this.columns = columns;

		tiles = new Tile[columns][rows]; 
	}


	public void init(Camera camera, int width, int height) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile(camera, "res/tiles/blankTile.png", x+i*width, y+j*height, 0, width, height, "blank");
			}
		}
		emptyTile = new Tile(camera, "res/tiles/blankTile.png", 0, 0, 0, width, height, "empty");
	}

	public void update(Window window, Camera camera) {

	}

	public void render(int width, int height) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (tiles[i][j] != null) {
					if (tiles[i][j] instanceof Wire) {
						if (tiles[i][j].toWire().isPowered()) { 
							tiles[i][j].setShader(Shader.SHADER2L);
							tiles[i][j].render();
						} else {
							tiles[i][j].render();
						}
					} else {
						tiles[i][j].render();
					}
				}
				emptyTile.reset(x+i*width, y+j*width, height, height);
				emptyTile.render();
			}
		}
	}

	public void clear(Camera camera, int width, int height) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile(camera, "res/tiles/blankTile.png", x+i*width, y+j*height, 0, width, height, "blank");
			}
		}
	}
	
	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public int getColumns() {
		return columns;
	}


	public void setColumns(int columns) {
		this.columns = columns;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Tile getTile(int i, int j) {
		return tiles[i][j];
	}

	public Tile getCopy(int i, int j) {
		return tiles[i][j].copy();
	}

	public void setTile(Tile tile, int i, int j) {
		tiles[i][j] = tile;
	}

}
