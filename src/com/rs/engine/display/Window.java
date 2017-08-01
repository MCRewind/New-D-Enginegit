package com.rs.engine.display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class Window {
	
	private long window;
	
	private int gWidth, gHeight, mouseState;
	
	private double scrollX, scrollY;
	
	private boolean resized;
	
	public Window(int width, int height, boolean vSync, boolean resizable, String title) {
		init(width, height, vSync, resizable, title);
	}
	
	public Window(boolean vSync) {
		init(0, 0, vSync, false, null);
	}
	
	private void init(int width, int height, boolean vSync, boolean resizable, String title) {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		if(!glfwInit()) {
			System.err.println("GLFW failed to initialize!");
			System.exit(-1);
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);

		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if(width == 0 || height == 0 || width >= vidMode.width() || height >= vidMode.height()) {
			width = vidMode.width();
			height = vidMode.height();
			window = glfwCreateWindow(width, height, "", glfwGetPrimaryMonitor(), NULL);
		}
		else {
			window = glfwCreateWindow(width, height, title, NULL, NULL);
			glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		}
		gWidth = width;
		gHeight = height;
		
		glfwMakeContextCurrent(window);
		createCapabilities();

		glfwSwapInterval(vSync ? 1 : 0);
		
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
			public void invoke(long window, int width, int height) {
				gWidth = width;
				gHeight = height;
				resized = true;
				glViewport(0, 0, width, height);
			}
		});
		
		glfwSetScrollCallback(window, new GLFWScrollCallback() {
			public void invoke(long window, double x, double y) {
				scrollX = x;
				scrollY = y;
			}
		});
		
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		
		glClearColor(0.8f, 0.8f, 0.8f, 1);
		
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void update() {
		scrollX = 0;
		scrollY = 0;
		resized = false;
		glfwPollEvents();
		 if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
			 if(mouseState < 2)
				 mouseState = 2;
			 else
				 mouseState = 3;
		 }
		 else {
			 if(mouseState > 1)
				 mouseState = 1;
			 else
				 mouseState = 0;
		 }
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void swap() {
		glfwSwapBuffers(window);
	}
	
	public void setVSync(boolean vSync) {
		glfwSwapInterval(vSync ? 1 : 0);
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, true);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	public Vector3f getMouseCoords() {
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(window, x, y);
		return new Vector3f((float) x[0], (float) y[0], 0);
	}
	
	public int getLeftMouse() {
		 return mouseState;
	}
	
	public boolean mouseReleased(int button) {
		return glfwGetMouseButton(window, button) == GLFW_RELEASE;
	}
	
	public int getWidth() {
		return gWidth;
	}
	
	public int getHeight() {
		return gHeight;
	}
	
	public boolean wasResized() {
		return resized;
	}
	
	public float getScrollX() {
		return (float) scrollX;
	}
	
	public float getScrollY() {
		return (float) scrollY;
	}
}