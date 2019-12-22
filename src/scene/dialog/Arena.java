package scene.dialog;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import javax.swing.WindowConstants;

import com.alibaba.fastjson.JSON;

import common.Constant;
import common.RemoveType;
import common.Util;
import entity.Entity2D;
import event.KeyEvent;
import mode.GlobalMode;
import mode.ModeManger;
import module.ResourceLoader;
import player.Player;
import player.bag.Bag;
import props.goods.Goods;
import props.goods.UseGood;
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
	private Entity2D people;
	private TextComponent name,dialog;
	private ArrayList<TextComponent> optionsArrayList = new ArrayList<TextComponent>();
	private ArrayList<Dialog> dialogs;

	private int posIndex = 0;
	
	private int hoverTagert = 0;
	
	
	int padding = 20, nameHeight = 60,nameWidth = 200;
	int dialogHeight = (int) (Constant.HEIGHT / 3);
	int peopleHeight = (int) (Constant.HEIGHT / 2), peopleWight = 500;
	private TextComponent hover;
	
	private Entity2D informationBackground,me;
	private Entity2D title,nameTitle,countTitle;
	private ArrayList<Entity2D> list = new ArrayList<>();
	private boolean informationFlag = false;
	
	
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
		background = ResourceLoader.creatImage(scene, Constant.WIDTH, Constant.HEIGHT,0,Constant.HEIGHT, LayerConstant.LayerBackground);
		UIEventManger.getInstance().addEventListenner(background.getLabel(), UIEventType.UIOnHover, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				hoverTagert = background.getLabel();
				removeHover();
				return false;
			}
		});
		name = new TextComponent("商店老版", nameWidth, nameHeight, padding, dialogHeight + nameHeight + 2* padding, LayerConstant.LayerDialog,"background");
		people = ResourceLoader.creatImage("shopman", peopleWight, peopleHeight, (int) (Constant.WIDTH - peopleWight ) / 2, dialogHeight + peopleHeight, LayerConstant.LayerPeople);
		
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
		UIEventManger.getInstance().addEventListenner(GlobalMode.GlobalTextInteractionMode.hashCode(), UIEventType.UIOnKey, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				if (uiEventObj.getKeyValue() == KeyEvent.KEY_B) {
					openInformation();
				}
				return false;
			}
		});
		
	}
	
	private void changeDialog() {
		int size = dialogs.size();
		if (size> 0 && posIndex < dialogs.size() && posIndex != -1) {
			dialog.ChangeText(dialogs.get(posIndex).getContent());
			name.ChangeText(dialogs.get(posIndex).getSpeaker());
			changePeople(dialogs.get(posIndex).getSpeakerId());
			this.generateOptions();		// 生成选项
			if (dialogs.get(posIndex).getNextPos() == 0) {
				posIndex++;
			} else {
				posIndex = dialogs.get(posIndex).getNextPos();
			}	
		}
		if (posIndex == -1) {
			System.out.println("切换场景");
		}
	}
	
	
	
	private void openInformation() {
		informationFlag = false;
		int w = (int)(Constant.WIDTH * .8),
			h = (int)(Constant.HEIGHT * .8),
			x = (int) (Constant.WIDTH * .1),
			y = (int)(Constant.HEIGHT* .9);
		informationBackground = ResourceLoader.creatImage("information",w,h,x,y, LayerConstant.LayerInformation);
		System.out.println( "information:" + informationBackground.getLabel());
		informationBackground.setLabelable(true);
		UIEventManger.getInstance().addEventListenner(informationBackground.getLabel(), UIEventType.UIOnClick, new UIEventFunction() {
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				System.out.println(informationFlag);
				if (informationFlag) {
					openInformation();
				} else {
					closeInformation();
				}
				return false;
			}
		});
		int padding = 20;
		title = ResourceLoader.creatTextButton("背包                               当前金币:" + Player.getPlayer().getMoney(), x+padding, y-padding,LayerConstant.LayerInformationText , 24, Color.WHITE);

		Bag bag = Player.getPlayer().getBag();
		nameTitle = ResourceLoader.creatTextButton("物品名称", x+padding *4, y-padding*4,LayerConstant.LayerInformationText , 24, Color.WHITE);
		countTitle = ResourceLoader.creatTextButton("物品数量", x+padding *12, y-padding*4,LayerConstant.LayerInformationText , 24, Color.WHITE);
		for (int i = 0; i < bag.getGoods().size(); i++) {
			Goods goods = bag.getGoods().get(i);
			int space = padding*4+padding*(i+1)*3;
			Entity2D namet = ResourceLoader.creatTextButton(goods.getName(), x+padding *4, y-space,LayerConstant.LayerInformationText , 24, Color.WHITE);
			Entity2D countt = ResourceLoader.creatTextButton(goods.getCount()+"", x+padding *12, y-space,LayerConstant.LayerInformationText , 24, Color.WHITE);
			list.add(namet);list.add(countt);
		}
		
	}
	
	private void closeInformation() {
		informationFlag = true;
		Util.removeEntity2d(RemoveType.RemoveMemory, informationBackground);
		
		Util.removeEntity2d(RemoveType.RemoveMemory	, title);
		Util.removeEntity2d(RemoveType.RemoveMemory	, nameTitle);
		Util.removeEntity2d(RemoveType.RemoveMemory	, countTitle);
		for (int i = 0; i < list.size();) {
			Util.removeEntity2d(RemoveType.RemoveMemory	, list.get(i));
			list.remove(i);
		}
		System.out.println("list.size():"+list.size());
	}
	
	private void changePeople(String name) {
		ResourceLoader.removeEntity2D(people);
		if (name != "me") {			
			people = ResourceLoader.creatImage(name, peopleWight, peopleHeight, (int) (Constant.WIDTH - peopleWight ) / 2, dialogHeight + peopleHeight, LayerConstant.LayerPeople);
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
						if (op.getCallbackID() != "" && op.getCallbackID() != null) {				
							CallbackHander.getInstance().triggerCallBack(op.getCallbackID(), op.getCallbackParam());
						}
						if (op.getNextPos() != 0) {
							posIndex = op.getNextPos();
	
						} else if (dialogs.get(posIndex).getNextPos() != 0) {
							posIndex = dialogs.get(posIndex).getNextPos();
						} else {
							posIndex++;
						}
						
						removeOptions();
						changeDialog();
						return false;
					}
				});
				
				if (op.getHoverContent() != "" && op.getHoverContent() != null) {
					UIEventManger.getInstance().addEventListenner(textComponent.getLabel(),UIEventType.UIOnHover,new UIEventFunction() {
						@Override
						public boolean run(UIEventObj uiEventObj) {
							// TODO Auto-generated method stub
							System.out.println("hoverTaget:" + uiEventObj.getTarget());
							if (uiEventObj.getTarget() != hoverTagert) {
								hoverTagert = uiEventObj.getTarget();
								addHover(uiEventObj.getX(), Constant.HEIGHT - uiEventObj.getY(), op.getHoverContent());
							}
							
							return false;
						}
					});
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
			hover = null;
		}
	}
	
	private void printDialog() {
		for (int i = 0; i < dialogs.size(); i++) {
			System.out.println(i + ":" + dialogs.get(i).getContent()+",is next:" + dialogs.get(i).getNextPos());
		}
		System.out.println("posIndex:" + posIndex);
	}
	
	private void removeOptions() {
		for(TextComponent op: optionsArrayList) {
			op.clear();
		}
		optionsArrayList = new ArrayList<TextComponent>();
	}
	
	public void addDialogsNow(Dialog d) {
		int next = posIndex;
		d.setNextPos(next);
		dialogs.get(posIndex).setNextPos(dialogs.size());
		dialogs.add(d);
	}
	
	
	public ArrayList<Dialog> getDialogs() {
		return dialogs;
	}

	
	public void End() {
		Util.removeEntity2d(RemoveType.RemoveMemory	, background);
		Util.removeEntity2d(RemoveType.RemoveMemory, people);
		name.clear();
		dialog.clear();
		hover.clear();
		removeHover();
		removeOptions();
		closeInformation();
	}
}
