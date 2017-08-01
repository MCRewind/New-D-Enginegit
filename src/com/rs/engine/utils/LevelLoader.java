package com.rs.engine.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.rs.engine.utils.Level;

public class LevelLoader {

	public static Level loadLevel(int level) {
		ArrayList<String> info = new ArrayList<String>();
		try{
			FileInputStream fis = new FileInputStream("res/levels/" + level);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				info.add(strLine);
			}
			in.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
		int[] amounts = {Integer.parseInt(info.get(4)), Integer.parseInt(info.get(5)), 
						Integer.parseInt(info.get(6)), Integer.parseInt(info.get(7)),
						Integer.parseInt(info.get(8)), Integer.parseInt(info.get(9)),
						Integer.parseInt(info.get(10)), Integer.parseInt(info.get(11))};
		return new Level(Integer.parseInt(info.get(0)), Integer.parseInt(info.get(1)), info.get(2), info.get(3), amounts);
	}
	
}


