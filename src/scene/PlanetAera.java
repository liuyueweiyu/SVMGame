package scene;

import common.Constant;
import mode.GlobalMode;
import mode.ModeManger;
import module.ModuleLoader;
import scene.dialog.Arena;
import scene.dialog.GlobalArena;
import scene.map.MapParse;

public class PlanetAera implements Action  {
	
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalTextInteractionMode);
		Arena shop = MapParse.Load("shop");
		GlobalArena.setCurrentArena(shop);
//		shop.Start();
	}
	
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalNoneMode);
	}
}
