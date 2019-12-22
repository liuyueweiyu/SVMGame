package scene;


import mode.GlobalMode;
import mode.ModeManger;

import scene.dialog.Arena;
import scene.dialog.GlobalArena;
import scene.map.MapParse;

public class PlanetAera implements Action  {
	Arena shop;
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalTextInteractionMode);
		shop = MapParse.Load("shop");
		GlobalArena.setCurrentArena(shop);
//		shop.Start();
	}
	
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		shop.End();
		ModeManger.setCurrentMode(GlobalMode.GlobalNoneMode);
	}
}
