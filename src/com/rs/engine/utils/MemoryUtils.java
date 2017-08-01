package com.rs.engine.utils;

import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;

import java.util.ArrayList;

public class MemoryUtils {

	private static final byte numTypes = 6;
	private static boolean init = false;
	
	public static final byte
		AL_BUFFER = 0,
		AL_SOURCE = 1,
		GL_BUFFER = 2,
		GL_PROGRAM = 3,
		GL_SHADER = 4,
		GL_TEXTURE = 5;
	
	private static ArrayList<Integer>[] data;
	
	private MemoryUtils() {}
	
	private static void init() {
		data = new ArrayList[numTypes];
		for (int i = 0; i < numTypes; i++)
			data[i] = new ArrayList<Integer>();
		init = true;
	}
	
	public static void add(byte type, int num) {
		if(!init)
			init();
		data[type].add(num);
	}
	
	public static void destroy() {
		if(init) {
			for (int i : data[AL_BUFFER])
				alDeleteBuffers(i);
			for (int i : data[AL_SOURCE])
				alDeleteSources(i);
			for (int i : data[GL_BUFFER])
				glDeleteBuffers(i);
			for (int i : data[GL_PROGRAM])
				glDeleteProgram(i);
			for (int i : data[GL_SHADER])
				glDeleteShader(i);
			for (int i : data[GL_TEXTURE])
				glDeleteTextures(i);
		}
	}
}
