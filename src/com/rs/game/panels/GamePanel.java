package com.rs.game.panels;

import java.util.ArrayList;

import com.rs.engine.display.Camera;
import com.rs.engine.display.Window;
import com.rs.engine.shaders.Shader;
import com.rs.engine.text.Font;
import com.rs.engine.texture.Texture;
import com.rs.engine.utils.Level;
import com.rs.engine.utils.LevelLoader;
import com.rs.game.map.Map;
import com.rs.game.objs.Button;
import com.rs.game.objs.rects.TexRect;
import com.rs.game.objs.tiles.Battery;
import com.rs.game.objs.tiles.Counter;
import com.rs.game.objs.tiles.Diode;
import com.rs.game.objs.tiles.Inverter;
import com.rs.game.objs.tiles.Light;
import com.rs.game.objs.tiles.Switch;
import com.rs.game.objs.tiles.Tile;
import com.rs.game.objs.tiles.Wire;
import com.rs.game.objs.ui.Mouse;

public class GamePanel {

	private Camera camera;
	private Window window;

	private Map map;
	private Map inventoryMap;

	private Counter[] invCounts = new Counter[8];

	private Mouse mouse;

	private TexRect selected, levelBoard, ruleBoard, title, subtitle;

	private Button start, stop, pencil, wrench, reset, magnet, plus, minus;

	private Font font;

	private long lastTime = System.nanoTime();

	private boolean playing = false;

	public GamePanel(Window window, Camera camera) {
		this.window = window;
		this.camera = camera;
		mouse = new Mouse(camera);
		init();
	}

	public void init() {

		font = new Font(camera, "res/tiles/default.png");

		inventoryMap = new Map((int) camera.getWidth()/2-8*32, (int) camera.getHeight()-64, 8, 1);
		inventoryMap.init(camera, 64, 64);

		loadLevel(0);

		selected = new TexRect(camera, "res/tiles/selected.png", 0, 0, 0, 64, 64);
		selected.reset(inventoryMap.getX()*64, inventoryMap.getY(), 64, 64);
		levelBoard = new TexRect(camera, "res/tiles/levelBoard.png", window.getWidth()-293, camera.getHeight()/4, 0, 293, 551);
		ruleBoard = new TexRect(camera, "res/tiles/ruleBoard.png", 0, camera.getHeight()/4, 0, 293, 551);
		title = new TexRect(camera, "res/tiles/title.png", camera.getWidth()/2-200, 100, 0, 400, 80);
		subtitle = new TexRect(camera, "res/tiles/subtitle.png", camera.getWidth()/2-150, 175, 0, 298, 51);


		inventoryMap.setTile(new Battery(camera, inventoryMap.getX()+0*64, inventoryMap.getY()+0*64, 0, 64, 64), 0, 0);
		inventoryMap.setTile(new Light(camera, inventoryMap.getX()+1*64, inventoryMap.getY()+0*64, 0, 64, 64), 1, 0);
		inventoryMap.setTile(new Inverter(camera, inventoryMap.getX()+2*64, inventoryMap.getY()+0*64, 0, 64, 64), 2, 0);
		inventoryMap.setTile(new Wire(camera, inventoryMap.getX()+3*64, inventoryMap.getY()+0*64, 0, 64, 64), 3, 0);
		inventoryMap.setTile(new Switch(camera, inventoryMap.getX()+4*64, inventoryMap.getY()+0*64, 0, 64, 64), 4, 0);
		inventoryMap.setTile(new Diode(camera, inventoryMap.getX()+5*64, inventoryMap.getY()+0*64, 0, 64, 64), 5, 0);
		inventoryMap.setTile(new Tile(camera, "res/tiles/blankTile.png", inventoryMap.getX()+6*64, inventoryMap.getY()+0*64, 0, 64, 64, "blank"), 6, 0);

		start = new Button(camera, "res/tiles/playButton.png", "res/tiles/playButtonDown.png", "res/tiles/playButtonHover.png", camera.getWidth()/2-72-45, camera.getHeight()-172, 0, 64, 64);
		stop = new Button(camera, "res/tiles/stopButton.png", "res/tiles/stopButtonDown.png", "res/tiles/stopButtonHover.png", camera.getWidth()/2+16-45, camera.getHeight()-172, 0, 64, 64);
		pencil = new Button(camera, "res/tiles/pencilButton.png", "res/tiles/pencilButtonPressed.png", "res/tiles/pencilButtonHover.png", camera.getWidth()/2-72-45, camera.getHeight()-272, 0, 64, 64);
		wrench = new Button(camera, "res/tiles/wrenchButton.png", "res/tiles/wrenchButtonPressed.png", "res/tiles/wrenchButtonHover.png", camera.getWidth()/2+16-45, camera.getHeight()-272, 0, 64, 64);
		magnet = new Button(camera, "res/tiles/magnetButton.png", "res/tiles/magnetButtonPressed.png", "res/tiles/magnetButtonHover.png", camera.getWidth()/2+105-45, camera.getHeight()-272, 0, 64, 64);
		reset = new Button(camera, "res/tiles/resetButton.png", "res/tiles/resetButtonPressed.png", "res/tiles/resetButtonHover.png", camera.getWidth()/2+16+86-45, camera.getHeight()-172, 0, 64, 64);
		plus = new Button(camera, "res/tiles/plusButton.png", "res/tiles/plusButtonPressed.png", "res/tiles/plusButtonHover.png", camera.getWidth()/2+16+86, camera.getHeight()-172, 0, 64, 64);
		minus = new Button(camera, "res/tiles/minusButton.png", "res/tiles/minusButtonPressed.png", "res/tiles/minusButtonHover.png", camera.getWidth()/2-16-86-45, camera.getHeight()-172, 0, 64, 64);
	}

	public void loadLevel(int num) {
		Level level = LevelLoader.loadLevel(num);
		map = new Map((int) camera.getWidth()/2-(level.getWidth()/2)*32, (int) camera.getHeight()/2-(level.getHeight()/2)*32, level.getWidth(), level.getHeight());
		map.init(camera, 32, 32);

		for (int i = 0; i < invCounts.length; i++) {
			invCounts[i] = new Counter(camera, inventoryMap.getX()+i*64, inventoryMap.getY()-32, 0, 64, 32);
			invCounts[i].setCapacity(level.getAmounts()[i]);
			invCounts[i].update();
		}

		font.drawString("test", window.getWidth()-256, camera.getHeight()/4, 0f, 10f, 0f, 0f, 0f, 1f);
	}

	//MOUSE CODES
	//0 = unpressed
	//1 = just released
	//2 = just pressed
	//3 = held

	public void update() {
		if (playing)
			if(System.nanoTime() - lastTime >= 1000000000) {
				tick();
				lastTime = System.nanoTime();
			}
		map.update(window, camera);
		//Main Map
		for (int i = 0; i < map.getTiles().length; i++) {
			for (int j = 0; j < map.getTiles()[1].length; j++) {
				if (map.getTile(i, j) != null) {
					if (map.getTiles()[i][j].getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
						map.getTiles()[i][j].setShader(Shader.SHADER2S);
						if (!playing) {
							if (window.getLeftMouse() >= 2) {
								if(mouse.getPointer() == "res/tiles/handPencil.png") {
									if(mouse.getHeldObj() != null) {
										//wire checks
										if (mouse.getHeldObj().getName().equals("wire") && invCounts[3].getAvailable() > 0) {
											map.setTile(new Wire(camera, map.getX()+i*32, map.getY()+j*32, 0, 32, 32), i, j);

											boolean[] dirs = blankCheck(i, j);
											boolean[] dirs2 = neighborCheck(i, j, "wire");
											map.getTiles()[i][j].setTexture(new Texture(Wire.getOrient(dirs[0], dirs[1], dirs[2], dirs[3])));
											map.getTiles()[i][j].setName("wire");
											if (dirs2[0]) {
												boolean[] dirs0 = blankCheck(i, j-1);
												map.getTiles()[i][j-1].setTexture(new Texture(Wire.getOrient(dirs0[0], dirs0[1], dirs0[2], dirs0[3])));
												map.getTiles()[i][j-1].setName("wire");
											}
											if (dirs2[1]) {
												boolean[] dirs0 = blankCheck(i, j+1);
												map.getTiles()[i][j+1].setTexture(new Texture(Wire.getOrient(dirs0[0], dirs0[1], dirs0[2], dirs0[3])));
												map.getTiles()[i][j+1].setName("wire");
											}
											if (dirs2[2]) {
												boolean[] dirs0 = blankCheck(i-1, j);
												map.getTiles()[i-1][j].setTexture(new Texture(Wire.getOrient(dirs0[0], dirs0[1], dirs0[2], dirs0[3])));
												map.getTiles()[i-1][j].setName("wire");
											}
											if (dirs2[3]) {
												boolean[] dirs0 = blankCheck(i+1, j);
												map.getTiles()[i+1][j].setTexture(new Texture(Wire.getOrient(dirs0[0], dirs0[1], dirs0[2], dirs0[3])));
												map.getTiles()[i+1][j].setName("wire");
											}
										} else if (mouse.getHeldObj().getName().equals("light") && invCounts[1].getAvailable() > 0) {									
											map.setTile(new Light(camera, map.getX()+i*32, map.getY()+j*32, 0, 32, 32), i, j);
										} else if (mouse.getHeldObj().getName().equals("battery") && invCounts[0].getAvailable() > 0) {									
											map.setTile(new Battery(camera, map.getX()+i*32, map.getY()+j*32, 0, 32, 32), i, j); 
										} else if (mouse.getHeldObj().getName().equals("inverter") && invCounts[2].getAvailable() > 0) {							
											map.setTile(new Inverter(camera, map.getX()+i*32, map.getY()+j*32, 0, 32, 32), i, j);
										} else if (mouse.getHeldObj().getName().equals("switch") && invCounts[4].getAvailable() > 0) {			
											map.setTile(new Switch(camera, map.getX()+i*32, map.getY()+j*32, 0, 32, 32), i, j);
										} else if (mouse.getHeldObj().getName().equals("diode") && invCounts[5].getAvailable() > 0) {
											map.setTile(new Diode(camera, map.getX()+i*32, map.getY()+j*32, 0, 32, 32), i, j);
										} else {
											//map.getTiles()[i][j].setTexture(mouse.getHeldObj().getTexture());
											//map.getTiles()[i][j].setName(mouse.getHeldObj().getName());
										}
									}
								} else if (mouse.getPointer().equals("res/tiles/handMagnet.png")) {
									map.setTile(new Tile(camera, "res/tiles/blankTile.png", map.getX()+i*32, map.getY()+j*32, 0, 32, 32, "blank"), i, j);
								}
							}  
							//play mode wrench inputs
						}
						//(camera, map.getX()+i*map.getWidth(), map.getY()+j*map.getHeight(), 0, map.getWidth(), map.getHeight());
						if (window.getLeftMouse() == 1) {
							if (mouse.getPointer().equals("res/tiles/handWrench.png")) {
								if (map.getTile(i, j).getName().equals("switch")){
									map.getTile(i, j).toSwitch().flip();
									map.getTile(i, j).toSwitch().updateTex();
								}
								if (map.getTile(i, j).getName().equals("diode")){
									map.getTile(i, j).toDiode().rotate();
									map.getTile(i, j).toDiode().updateTex();
								}
								if (map.getTile(i, j).getName().equals("light")){
									map.getTile(i, j).toLight().nextColor();
								}
							}
						}
					} else {
						map.getTiles()[i][j].setShader(Shader.SHADER2T);
					}
				}
			}	
		}

		//inventory count checking
		int battery = 0, light = 0, inverter = 0, wire = 0, switchNum = 0, diode = 0;
		for (int j2 = 0; j2 < map.getColumns(); j2++) {
			for (int k = 0; k < map.getRows(); k++) {
				if(map.getTile(j2, k).getName().equals("battery")) {
					battery++;
				}
				if(map.getTile(j2, k).getName().equals("light")) {
					light++;
				}
				if(map.getTile(j2, k).getName().equals("inverter")) {
					inverter++;
				}
				if(map.getTile(j2, k).getName().equals("wire")) {
					wire++;
				}
				if(map.getTile(j2, k).getName().equals("switch")) {
					switchNum++;
				}
				if(map.getTile(j2, k).getName().equals("diode")) {
					diode++;
				}
			}
		}
		invCounts[0].setUsed(battery);
		invCounts[1].setUsed(light);
		invCounts[2].setUsed(inverter);
		invCounts[3].setUsed(wire);
		invCounts[4].setUsed(switchNum);
		invCounts[5].setUsed(diode);

		//Inventory updating
		for (int i = 0; i < inventoryMap.getColumns(); i++) {
			if (inventoryMap.getTiles()[i][0].getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
				inventoryMap.getTiles()[i][0].setShader(Shader.SHADER2S);
				if (window.getLeftMouse() == 1) {
					mouse.setHeldObj(inventoryMap.getCopy(i, 0));
					selected.reset(inventoryMap.getX()+i*64, inventoryMap.getY(), 64, 64);
				}
			} else {
				inventoryMap.getTiles()[i][0].setShader(Shader.SHADER2T);
			}
		}



		//button checks
		if(start.getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
			start.setState(1);
			start.setTex();
			if (window.getLeftMouse() >= 2) {
				start.setState(2);
				start.setTex();
			}
			else if (window.getLeftMouse() == 1) {
				playing = true;
				mouse.setPointer("res/tiles/handWrench.png");
				mouse.setyOffset(0);
				mouse.setxOffset(0);
				start.setState(2);
				start.setTex();
			}
		} else {
			start.setState(0);
			start.setTex();
		}
		if(stop.getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
			stop.setState(1);
			stop.setTex();
			if (window.getLeftMouse() >= 2) {
				stop.setState(2);
				stop.setTex();
			}
			else if (window.getLeftMouse() == 1) {
				playing = false;
				mouse.setPointer("res/tiles/handPencil.png");
				mouse.setyOffset(0);
				mouse.setxOffset(0);
				stop.setState(2);
				stop.setTex();
			}
		} else {
			stop.setState(0);
			stop.setTex();
		}
		if(pencil.getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
			pencil.setState(1);
			pencil.setTex();
			if (window.getLeftMouse() >= 2) {
				pencil.setState(2);
				pencil.setTex();
			}
			else if (window.getLeftMouse() == 1) {
				mouse.setPointer("res/tiles/handPencil.png");
				mouse.setyOffset(0);
				mouse.setxOffset(0);
				pencil.setState(2);
				pencil.setTex();
			}
		} else {
			pencil.setState(0);
			pencil.setTex();
		}
		if(wrench.getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
			wrench.setState(1);
			wrench.setTex();
			if (window.getLeftMouse() >= 2) {
				wrench.setState(2);
				wrench.setTex();
			}
			else if (window.getLeftMouse() == 1) {
				mouse.setPointer("res/tiles/handWrench.png");
				mouse.setyOffset(0);
				mouse.setxOffset(0);
				wrench.setState(2);
				wrench.setTex();
			}
		} else {
			wrench.setState(0);
			wrench.setTex();
		}
		if(magnet.getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
			magnet.setState(1);
			magnet.setTex();
			if (window.getLeftMouse() >= 2) {
				magnet.setState(2);
				magnet.setTex();
			}
			else if (window.getLeftMouse() == 1) {
				mouse.setPointer("res/tiles/handMagnet.png");
				mouse.setyOffset(32);
				mouse.setxOffset(16);
				magnet.setState(2);
				magnet.setTex();
			}
		} else {
			magnet.setState(0);
			magnet.setTex();
		}
		if(reset.getHitbox().checkCollision(window.getMouseCoords().x, window.getMouseCoords().y)) {
			reset.setState(1);
			reset.setTex();
			if (window.getLeftMouse() >= 2) {
				reset.setState(2);
				reset.setTex();
			}
			else if (window.getLeftMouse() == 1) {
				map.clear(camera, 32, 32);
				reset.setState(2);
				reset.setTex();
			}
		} else {
			reset.setState(0);
			reset.setTex();
		}
		
		mouse.update(window);
		inventoryMap.update(window, camera);
	}

	//renders everything
	public void render() {
		mouse.render();
		title.render();
		subtitle.render();
		levelBoard.render();
		ruleBoard.render();
		start.render();
		stop.render();
		pencil.render();
		reset.render();
		wrench.render();
		magnet.render();
		selected.render();
		map.render(32, 32);
		inventoryMap.render(64, 64);
		for (int i = 0; i < invCounts.length; i++) {
			invCounts[i].render();
		}	
	}

	//check for neighbors with type
	public boolean[] neighborCheck(int i, int j, String name) {
		boolean[] dirs = {false, false, false, false};
		if (j > 0 && map.getTile(i, j-1).getName().equals(name))
			dirs[0] = true;
		if (j < map.getRows()-1 && map.getTile(i, j+1).getName().equals(name))
			dirs[1] = true;
		if (i > 0 && map.getTile(i-1, j).getName().equals(name))
			dirs[2] = true;
		if (i < map.getColumns()-1 && map.getTile(i+1, j).getName().equals(name))
			dirs[3] = true;
		return dirs;
	}

	//check for powered wires
	public boolean[] poweredCheck(int i, int j) {
		boolean[] dirs = {false, false, false, false};
		if (j > 0 && map.getTile(i, j-1).getName().equals("wire"))
			if (j > 0 && map.getTile(i, j-1).toWire().isPowered())
				dirs[0] = true;
		if (j < map.getRows()-1 && map.getTile(i, j+1).getName().equals("wire"))
			if (j < map.getRows()-1 && map.getTile(i, j+1).toWire().isPowered()) {
				dirs[1] = true;
			}
		if (i > 0 && map.getTile(i-1, j).getName().equals("wire"))
			if (i > 0 && map.getTile(i-1, j).toWire().isPowered())
				dirs[2] = true;
		if (i < map.getColumns()-1 && map.getTile(i+1, j).getName().equals("wire"))
			if (i < map.getColumns()-1 && map.getTile(i+1, j).toWire().isPowered())
				dirs[3] = true;
		return dirs;
	}

	//check for non-blanks
	public boolean[] blankCheck(int i, int j) {
		boolean[] dirs = {false, false, false, false};
		if (j > 0 && !map.getTile(i, j-1).getName().equals("blank"))
			dirs[0] = true;
		if (j < map.getRows()-1 && !map.getTile(i, j+1).getName().equals("blank"))
			dirs[1] = true;
		if (i > 0 && !map.getTile(i-1, j).getName().equals("blank"))
			dirs[2] = true;
		if (i < map.getColumns()-1 && !map.getTile(i+1, j).getName().equals("blank"))
			dirs[3] = true;
		return dirs;
	}

	//ticks once per second
	public void tick() {
		for (int i = 0; i < map.getColumns(); i++) {
			for (int j = 0; j < map.getRows(); j++) {
				if (map.getTile(i, j).getName().equals("wire")) {
					map.getTile(i, j).toWire().setPowered(false);
				}
			}
		}
		for (int i = 0; i <  map.getTiles().length; i++) {
			for (int j = 0; j <  map.getTiles()[1].length; j++) {
				if (map.getTile(i, j).getName().equals("battery")) {
					batteryCheck(i, j);
				} 
				else if (map.getTile(i, j).getName().equals("switch")) {
					if (map.getTile(i, j).toSwitch().getState()) {
						batteryCheck(i, j);
					}
				}
			}
		}
		for (int i = 0; i < map.getColumns(); i++) {
			for (int j = 0; j < map.getRows(); j++) {
				if (map.getTile(i, j).getName().equals("inverter")) {
					inverter(i, j);
				}
				else if (map.getTile(i, j).getName().equals("diode")) {
					diodeCheck(i, j);
				}
				else if (map.getTile(i, j).getName().equals("light")) {
					boolean[] dirs = poweredCheck(i, j);
					for (int k = 0; k < dirs.length; k++) {
					}
					if (dirs[0] || dirs[1] || dirs[2] || dirs[3]) {
						map.getTile(i, j).toLight().on();
					} else {
						map.getTile(i, j).toLight().off();
					}
				}
			}
		}
	}

	public void diodeCheck(int i, int j) {
		boolean[] dirs = neighborCheck(i, j, "wire");
		if (j > 0 && j < map.getRows()-1 && dirs[0] && map.getTile(i, j-1).toWire().isPowered() && map.getTile(i, j).toDiode().getDir() == 1) {
			treeCheck(i, j+1);
		}
		if (j > 0 && j < map.getRows()-1 && dirs[1] && map.getTile(i, j+1).toWire().isPowered() && map.getTile(i, j).toDiode().getDir() == 0) {
			treeCheck(i, j-1);
		}
		if (i > 0 && i < map.getColumns()-1 && dirs[2] && map.getTile(i-1, j).toWire().isPowered() && map.getTile(i, j).toDiode().getDir() == 3) {
			treeCheck(i+1, j);
		}
		if (i > 0 && i < map.getColumns()-1 && dirs[3] && map.getTile(i+1, j).toWire().isPowered() && map.getTile(i, j).toDiode().getDir() == 2) {
			treeCheck(i-1, j);
		}
	}

	public void inverter(int i, int j) {
		if (j < map.getRows()-1 && map.getTile(i, j+1).getName().equals("wire")) {
			boolean powered = map.getTile(i, j+1).toWire().isPowered();
			invertCheck(i, j, powered);
		}
	}

	private void invertCheck(int i, int j, boolean powered) {
		ArrayList<Integer> check = new ArrayList<Integer>();
		check.add(i*map.getColumns()+j);
		while(check.size() > 0) {
			i = (int) check.get(0)/map.getColumns();
			j = (int) check.get(0)%map.getColumns();
			if (map.getTile(i, j).getName().equals("wire")) {
				map.getTile(i, j).toWire().setPowered(!powered);
			}
			check.remove(0);
			boolean[] dirs = neighborCheck(i, j, "wire");
			if (j > 0 && dirs[0] && map.getTile(i, j-1).toWire().isPowered() == powered) {
				check.add(i*map.getColumns() + j-1);
			}
			if (j < map.getRows()-1 && dirs[1] && !map.getTile(i, j).getName().equals("inverter") && map.getTile(i, j+1).toWire().isPowered() == powered) {
				check.add(i*map.getColumns() + j+1);
			}
			if (i > 0 && dirs[2] && map.getTile(i-1, j).toWire().isPowered() == powered) {
				check.add((i-1)*map.getColumns() + j);
			}

			if (i < map.getColumns()-1 && dirs[3] && map.getTile(i+1, j).toWire().isPowered() == powered) {
				check.add((i+1)*map.getColumns() + j);
			}
		}
	}

	private void batteryCheck(int i, int j) {
		int loop = 0;
		ArrayList<Integer> check = new ArrayList<Integer>();
		check.add(i*map.getColumns()+j);
		while(check.size() > 0) {
			loop++;
			i = (int) check.get(0)/map.getColumns();
			j = (int) check.get(0)%map.getColumns();
			if (map.getTile(i, j).getName().equals("wire")) {
				map.getTile(i, j).toWire().setPowered(true);
			}
			check.remove(0);
			boolean[] dirs = neighborCheck(i, j, "wire");
			if (j > 0 && dirs[0] && !map.getTile(i, j-1).toWire().isPowered()) {
				check.add(i*map.getColumns() + j-1);
			}
			if (j < map.getRows()-1 && dirs[1] && !map.getTile(i, j+1).toWire().isPowered()) {
				check.add(i*map.getColumns() + j+1);
			}
			if (loop > 1) {
				if (i > 0 && dirs[2] && !map.getTile(i-1, j).toWire().isPowered()) {
					check.add((i-1)*map.getColumns() + j);
				}

				if (i < map.getColumns()-1 && dirs[3] && !map.getTile(i+1, j).toWire().isPowered()) {
					check.add((i+1)*map.getColumns() + j);
				}
			}
		}
	}

	private void treeCheck(int i, int j) {
		ArrayList<Integer> check = new ArrayList<Integer>();
		check.add(i*map.getColumns()+j);
		while(check.size() > 0) {
			i = (int) check.get(0)/map.getColumns();
			j = (int) check.get(0)%map.getColumns();
			if (map.getTile(i, j).getName().equals("wire")) {
				map.getTile(i, j).toWire().setPowered(true);
			}
			check.remove(0);
			boolean[] dirs = neighborCheck(i, j, "wire");
			if (j > 0 && dirs[0] && !map.getTile(i, j-1).toWire().isPowered()) {
				check.add(i*map.getColumns() + j-1);
			}
			if (j < map.getRows()-1 && dirs[1] && !map.getTile(i, j+1).toWire().isPowered()) {
				check.add(i*map.getColumns() + j+1);
			}
			if (i > 0 && dirs[2] && !map.getTile(i-1, j).toWire().isPowered()) {
				check.add((i-1)*map.getColumns() + j);
			}

			if (i < map.getColumns()-1 && dirs[3] && !map.getTile(i+1, j).toWire().isPowered()) {
				check.add((i+1)*map.getColumns() + j);
			}
		}
	}

	public String toTile(int id) {
		switch(id) {
		case 0:
			return "res/tiles/battery.png";
		case 1:
			return "res/tiles/wire.png";
		case 2:
			return "res/tiles/lightOff.png";
		default:
			return "res/tiles/blankTile.png";
		}
	}

}
