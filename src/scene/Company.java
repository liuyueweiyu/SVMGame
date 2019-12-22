package scene;

import mode.GlobalMode;
import mode.ModeManger;
import scene.dialog.Arena;
import scene.dialog.GlobalArena;
import scene.map.MapParse;

public class Company implements Action {
	Arena arena;
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalTextInteractionMode);
		arena = MapParse.Load("company");
		GlobalArena.setCurrentArena(arena);
	}
	
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		arena.End();
	}
}