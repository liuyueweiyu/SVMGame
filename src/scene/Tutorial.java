package scene;

import java.awt.Color;

import common.Constant;
import common.RemoveType;
import common.Util;
import entity.Entity2D;
import event.KeyEvent;
import mode.GlobalMode;
import mode.ModeManger;
import module.ResourceLoader;
import scene.dialog.TextComponent;
import src.Main;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;

public class Tutorial implements Action {
	private Entity2D bg,text,title;
	@Override
	public void Open() {
		ModeManger.setCurrentMode(GlobalMode.GlobalTextInteractionMode);
		// TODO Auto-generated method stub
		int w = (int) (Constant.WIDTH) / 10 * 8,
			h = (int) (Constant.HEIGHT) / 10 * 8,
			x = (int) (Constant.WIDTH) / 10,
			y = (int) (Constant.HEIGHT) / 10 * 9,
			padding = 20;
		bg = ResourceLoader.creatImage("background", w, h, x, y, LayerConstant.LayerInformation);
		String texrString = "    在太空中畅游，并躲避陨石，通过'w','a','s','d','r','f'操控飞行器，并向太阳飞行，在飞向太阳的过程中，可以选择的行星降落,可在行星降落后，浏览相关剧情或购买所需物品。按'enter'键，开始您的旅程吧！";
		title = ResourceLoader.creatTextButton("游戏教程", x+padding, y-padding, LayerConstant.LayerInformationText, 24, Color.WHITE);
		text = ResourceLoader.creatTextButton(texrString,w -2*padding , x+padding, y-3*padding, LayerConstant.LayerInformationText, 20,Color.WHITE);
		UIEventManger.getInstance().addEventListenner(GlobalMode.GlobalTextInteractionMode.hashCode(), UIEventType.UIOnKey, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				if (uiEventObj.getKeyValue() == KeyEvent.KEY_ENTER) {
					Close();
					// 打开游戏
					Main.setCurrentSence(Main.space);
					Main.getCurrentSence().Open();
				}
				return false;
			}
		});
	}
	
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		Util.removeEntity2d(RemoveType.RemoveDisplay, bg);
		Util.removeEntity2d(RemoveType.RemoveMemory, text);
		Util.removeEntity2d(RemoveType.RemoveMemory, title);
		bg = null;
		text = null;
		title = null;
	}
}
