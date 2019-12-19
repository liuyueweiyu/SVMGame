package scene;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import entity.MoveText;
import mode.GlobalMode;
import mode.ModeManger;
import module.ModuleLoader;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;


public class Menu implements Action {


	private Map<Integer, MoveText> textMap = new HashMap<Integer, MoveText>();
	
	@Override
	public void Open() {
		// TODO Auto-generated method stub
		
		ModeManger.setCurrentMode(GlobalMode.GlobalStartMode);
		
		ModuleLoader.createBackground();
		MoveText start = ModuleLoader.creatTextButton("开始游戏", 100,500,400,0);
		MoveText history = ModuleLoader.creatTextButton("读取记录", 100,500,350,0);
		MoveText end = ModuleLoader.creatTextButton("结束游戏", 100,500,300,0);
		
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
