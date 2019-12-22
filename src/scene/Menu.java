package scene;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import common.Constant;
import common.RemoveType;
import common.Util;
import entity.Entity2D;
import entity.MoveText;
import mode.GlobalMode;
import mode.ModeManger;
import module.ResourceLoader;
import player.Player;
import src.Main;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;


public class Menu implements Action {


	Entity2D start;
	
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalStartMode);
//		ResourceLoader.createBackground();
		start = ResourceLoader.creatTextButton("¿ªÊ¼ÓÎÏ·",500,400,LayerConstant.LayerInformationText,24,Color.WHITE);
		start.setLabelable(true);
		UIEventManger.getInstance().addEventListenner(start.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				Close();
				Main.setCurrentSence(new Tutorial());
				Main.getCurrentSence().Open();
				return false;
			}
		});
	}
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalNoneMode);
		Util.removeEntity2d(RemoveType.RemoveMemory, start);
	}
}
