package scene;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import entity.Entity2D;
import entity.MoveText;
import mode.GlobalMode;
import mode.ModeManger;
import module.ModuleLoader;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;


public class Menu implements Action {


	private Map<Integer, Entity2D> textMap = new HashMap<Integer, Entity2D>();
	
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalStartMode);
		ModuleLoader.createBackground();
		Entity2D start = ModuleLoader.creatTextButton("开始游戏", 100,500,400,0);
		Entity2D history = ModuleLoader.creatTextButton("读取记录", 100,500,350,0);
		Entity2D end = ModuleLoader.creatTextButton("结束游戏", 100,500,300,0);
		textMap.put(start.getLabel(),start);
		textMap.put(history.getLabel(), history);
		textMap.put(end.getLabel(), end);
		
		UIEventManger.getInstance().addEventListenner(start.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				System.out.print("start!!");
				
				return false;
			}
		});
	}
	@Override
	public void Close() {
		// TODO Auto-generated method stub
		ModeManger.setCurrentMode(GlobalMode.GlobalNoneMode);
		
	}
}
