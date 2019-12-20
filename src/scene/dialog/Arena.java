package scene.dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import javax.swing.WindowConstants;

import com.alibaba.fastjson.JSON;

import common.Constant;
import entity.Entity2D;

import module.ModuleLoader;
import scene.LayerConstant;
import scene.map.CallbackHander;
import scene.map.Map;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;

public class Arena {
	private String scene;
	private Entity2D background;
	private TextComponent name,dialog;
	private ArrayList<TextComponent> optionsArrayList = new ArrayList<TextComponent>();
	private ArrayList<Dialog> dialogs;

	private int posIndex = 0;
	
	private int hoverTagert = 0;
	private TextComponent hover;
	
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
		UIEventManger.getInstance().addEventListenner(background.getLabel(), UIEventType.UIOnHover, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				hoverTagert = uiEventObj.getTarget();
				removeHover();
				return false;
			}
		});
		
		name = new TextComponent("商店老版", nameWidth, nameHeight, padding, dialogHeight + nameHeight + 2* padding, LayerConstant.LayerDialog,"background");
		dialog = new TextComponent("", Constant.WIDTH - padding *2, dialogHeight ,padding,  dialogHeight + padding,LayerConstant.LayerDialog,"dialog");		
		UIEventManger.getInstance().addEventListenner(dialog.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				// 对话框点击事件
				if (optionsArrayList.size() == 0) {
					changeDialog();
				}
				return false;
			}
		});
		this.changeDialog();
	}
	
	private void changeDialog() {
		int size = dialogs.size();
		if (size> 0 && posIndex < dialogs.size()) {
			dialog.ChangeText(dialogs.get(posIndex).getContent());
			name.ChangeText(dialogs.get(posIndex).getSpeaker());
			this.generateOptions();		// 生成选项
			if (dialogs.get(posIndex).getNextPos() == 0) {
				posIndex++;
			} else {
				posIndex = dialogs.get(posIndex).getNextPos();
			}	
		}
	}
	
	private void generateOptions() {
		Dialog d = dialogs.get(posIndex);
		if (d.getOptions() != null) {
			int size = d.getOptions().size(),
				optionWith = 400, 
				optionHeight = 60, 
				optionPaddingTop = 80,
				optionPaddingLeft = (Constant.WIDTH - optionWith) / 2,
				optionMargin = ((int) ( Constant.HEIGHT / 3 * 2) - optionPaddingTop * 2 - size * optionHeight) / 2;
			for (int i = 0; i < size; i++) {
				Options op =  d.getOptions().get(i);
				TextComponent textComponent = new TextComponent(op.getText(), optionWith, optionHeight, optionPaddingLeft, Constant.HEIGHT - optionPaddingTop - i * (optionHeight + optionMargin), LayerConstant.LayerDialog, "background");
				UIEventManger.getInstance().addEventListenner(textComponent.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
					@Override
					public boolean run(UIEventObj uiEventObj) {
						// TODO Auto-generated method stub
						// 选项点击事件
						System.out.println(op.getText() + ": " + textComponent.getLabel());
						if (op.getCallbackID() != "" && op.getCallbackID() != null) {				
							CallbackHander.getInstance().triggerCallBack(op.getCallbackID(), op.getCallbackParam());
						}
						if (op.getNextPos() != 0) {
							posIndex = op.getNextPos();
						} else {
							posIndex++;
						}
						printDialog();
						removeOptions();
						changeDialog();
						return false;
					}
				});
				
				if (op.getHoverContent() != "" && op.getHoverContent() != null) {
//					UIEventManger.getInstance().addEventListenner(textComponent.getLabel(),UIEventType.UIOnHover,new UIEventFunction() {
//						@Override
//						public boolean run(UIEventObj uiEventObj) {
//							// TODO Auto-generated method stub
//							System.out.println(op.getText() + ": " + textComponent.getLabel());
//							if (uiEventObj.getTarget() != hoverTagert) {
//								hoverTagert = uiEventObj.getTarget();
//								addHover(uiEventObj.getX(), Constant.HEIGHT - uiEventObj.getY(), op.getHoverContent());
//							}
//							
//							return false;
//						}
//					});
				}
				optionsArrayList.add(textComponent);
			}
			
		}
	}
	
	
	private void addHover(int x,int y,String texrString) {
		removeHover();
		hover = new TextComponent(texrString, x, y, LayerConstant.LayerHover, "hover",20);
	}
	private void removeHover() {
		if (hover != null) {
			hover.clear();
		}
	}
	
	private void printDialog() {
		for (int i = 0; i < dialogs.size(); i++) {
			System.out.println(i + ":" + dialogs.get(i).getContent());
		}
		System.out.println("posIndex:" + posIndex);
	}
	
	private void removeOptions() {
		for(TextComponent op: optionsArrayList) {
			op.clear();
			UIEventManger.getInstance().removeEventLister(op.getLabel());
		}
		optionsArrayList = new ArrayList<TextComponent>();
	}
	
	public void addDialogsNow(Dialog d) {
//		System.out.println("add:" + d.get);
		d.setNextPos(posIndex);
		posIndex = dialogs.size();
		dialogs.add(d);
		changeDialog();
	}
	
	
	public ArrayList<Dialog> getDialogs() {
		return dialogs;
	}

	
}
