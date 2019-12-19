package scene.dialog;

import java.util.ArrayList;

import common.Constant;
import entity.Entity2D;
import event.Event;
import event.IEventListener;
import module.ModuleLoader;
import scene.LayerConstant;
import scene.map.Map;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;

public class Arena {
	private String scene;
	private Entity2D background;
	private TextComponent name,dialog;
	private ArrayList<Dialog> dialogs;
	private int dialogPos = -1;
	
	
	public Arena(String scene) {
		this.scene = scene;
	}
	
	public Arena(Map map) {
		// TODO Auto-generated constructor stub
		this.scene = map.getName();
		this.dialogs = map.getDialog();
	}
	
	
	
	public void Start() {
		// TODO Auto-generated constructor stub
		int padding = 20, nameHeight = 60,nameWidth = 200;
		int dialogHeight = (int) (Constant.HEIGHT / 3);
		background = ModuleLoader.creatImage(scene, Constant.WIDTH, Constant.HEIGHT,0,Constant.HEIGHT, LayerConstant.LayerBackground);
		name = new TextComponent("商店老版", nameWidth, nameHeight, padding, dialogHeight + nameHeight + 2* padding, LayerConstant.LayerDialog,"background");
		UIEventManger.getInstance().addEventListenner(name.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				System.out.println("ssssssssssss");
				
				return false;
			}
		});
		dialog = new TextComponent("", Constant.WIDTH - padding *2, dialogHeight ,padding,  dialogHeight + padding,LayerConstant.LayerDialog,"dialog");		
		System.out.println(dialog.getLabel());
		System.out.println(dialog.getText().getLabel());
		System.out.println(dialog.getBg().getLabel());
		UIEventManger.getInstance().addEventListenner(dialog.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				System.out.println("ssssssssssss");
				changeDialog();
				return false;
			}
		});
		this.changeDialog();
	}
	
	private void changeDialog() {
		dialogPos++;
		if (dialogPos < dialogs.size() ) {
			dialog.ChangeText(dialogs.get(dialogPos).getContent());
		}
	}
	
	public void ChangeName() {
		name.ChangeText("黑点老板");
	}
	
	public void StartWithMap() {
		
	}
	
	
	
}
