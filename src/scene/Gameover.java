package scene;

import common.Constant;
import common.RemoveType;
import common.Util;
import entity.Entity2D;
import module.ResourceLoader;
import src.Main;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;

public class Gameover implements Action {
	
	Entity2D bg;
	
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		bg = ResourceLoader.creatImage("gameover", Constant.WIDTH, Constant.HEIGHT, 0, Constant.HEIGHT, LayerConstant.LayerBackground);
		UIEventFunction iEventFunction = new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				Close();
				Main.setCurrentSence(new Menu());
				Main.getCurrentSence().Open();
				return false;
			}
		};
		UIEventManger.getInstance().addEventListenner(bg.getLabel(), UIEventType.UIOnClick,iEventFunction );
	}
	
	
	
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		Util.removeEntity2d(RemoveType.RemoveMemory, bg);
	}
}
