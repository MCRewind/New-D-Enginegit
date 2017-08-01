package com.rs.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.rs.engine.display.Camera;
import com.rs.engine.display.Window;
import com.rs.game.objs.rects.TexRect;
import com.rs.game.panels.GamePanel;

public class Main implements Runnable {

	private final int FPS = 59;

	public static double time = 0;
	public static int fps = 0;

	private Window window;
	private Camera camera;
	private GamePanel gamePanel;

	public void start() {
		Thread gameLoop = new Thread(this, "OpenGL");
		gameLoop.start();
	}

	public void run() {
		long pastTime, pastSec, curTime, mspf = 1000000000 / FPS;
		int frames = 0;
		init();
		pastTime = System.nanoTime();
		pastSec = pastTime;
		while(!window.shouldClose()) {
			curTime = System.nanoTime();
			if(curTime - pastTime >= mspf) {
				update();
				render();
				time = ((double) (curTime - pastTime)) / 1000000000d;
				pastTime = curTime;
				++frames;
			}
			if(curTime - pastSec >= 1000000000) {
				fps = frames;
				frames = 0;
				pastSec += 1000000000;
			}
		}
	}

	private void init() {
		window = new Window(1920, 1080, true, false, "Nope.avi");
		camera = new Camera(1920, 1080);
		gamePanel = new GamePanel(window, camera);
	}

	private void update() {
		window.update();
		if(window.isKeyPressed(GLFW_KEY_ESCAPE))
			window.close();
		gamePanel.update();
	}

	private void render() {
		window.clear();
		gamePanel.render();
		window.swap();
	}

	public static void main(String[] args) {
		new Main().start();
	}
}