package scene.map;



import java.awt.Dialog;
import java.io.File;
import java.io.IOException;

import com.alibaba.fastjson.JSON;

import common.FileUtils;
import scene.dialog.Arena;

public class MapParse {
	
	static public Arena Load(String mapName) {
		
		String mapString = ReadJsonFile(mapName);
		
		Map map = JSON.parseObject(mapString,Map.class);
		

		Arena arena = new Arena(map);
		arena.Start();
		return arena;
	}
	
	static public String ReadJsonFile(String mapName) {
		String filePathString = "maps\\"+mapName + ".json";
		String jsonString = "";
		try {
			jsonString = FileUtils.readFile(filePathString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
	
	
	
}
