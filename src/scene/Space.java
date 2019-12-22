package scene;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.system.CallbackI.P;

import common.Constant;
import common.RemoveType;
import common.Util;
import entity.Aster;
import entity.Entity2D;
import entity.OpEntity;
import entity.Particular;
import event.KeyEvent;
import mode.GlobalMode;
import mode.ModeManger;
import module.ResourceLoader;
import player.Player;
import player.bag.Bag;
import renderer.RenderManager;
import renderer.Renderable.Priority;
import scene.dialog.TextComponent;
import src.Main;
import src.MotionHandler;
import uievent.UIEventFunction;
import uievent.UIEventManger;
import uievent.UIEventObj;
import uievent.UIEventType;

public class Space implements Action {

	
	public OpEntity craft;
	
	private TextComponent bag,count;
	private int bagPos = 0;
	
	
	
	@Override
	public void Open() {
		Aster box = new Aster(ResourceLoader.getModel("blackbox"), "box");
		Aster earth = new Aster(ResourceLoader.getModel("ball1"));
		Aster sun = new Aster(ResourceLoader.getModel("sun"), "sun");
		Aster moon = new Aster(ResourceLoader.getModel("moon"));
		
		craft = new OpEntity(ResourceLoader.getModel("spacecraft"));
		craft.setRotation(90, 180, 0);
		MotionHandler.mobileRegist(craft);
		RenderManager.add(craft);
		Particular par = new Particular(craft,ResourceLoader.getTexture("partcle"));
		RenderManager.add(par,Priority.LOW);
		

		moon.setPosition(10, 0, 0);
		moon.setScale(0.5f);
		moon.setPalstance(0, 10, 0);
		moon.setVelocity(0, 0, -2.5);
		RenderManager.add(moon);
		MotionHandler.mobileRegist(moon);
		MotionHandler.InteractRegist(moon, earth, 1);
		MotionHandler.InteractRegist(moon, sun, 5);

		earth.setVelocity(0, 0, -1.5);
		earth.setRotation(0, 0, -23.5);
		earth.setPalstance(0, 200, 0);
		RenderManager.add(earth);
		MotionHandler.mobileRegist(earth);
		MotionHandler.InteractRegist(earth, sun, 5);

		sun.setPosition(100, 0, 0);
		sun.setPalstance(0, 2, 0);
		sun.setScale(0.001f);
		RenderManager.add(sun);
		MotionHandler.mobileRegist(sun);

		earth.setScale(1f);

		RenderManager.add(box, Priority.HIGHEST);
		box.setScale(10000).setPosition(0, 0, -30000).setLabelable(false);
		
		
		setBag();
		ModeManger.setCurrentMode(GlobalMode.GlobalCollisionMode);
		UIEventManger.getInstance().addEventListenner(GlobalMode.GlobalCollisionMode.hashCode(), UIEventType.UIOnKey, new UIEventFunction() {
			
			@Override
			public boolean run(UIEventObj uiEventObj) {
				// TODO Auto-generated method stub
				int size = Player.getPlayer().getBag().getGoods().size();
				if (uiEventObj.getKeyValue() == KeyEvent.KEY_O) {
					bagPos = (size + bagPos - 1) % size;
					System.out.println("bagPos:" + bagPos);
					setBag();
				}
				if (uiEventObj.getKeyValue() == KeyEvent.KEY_P) {
					bagPos = (bagPos + 1) % size;
					setBag();
					System.out.println("bagPos:" + bagPos);
				}
				if (uiEventObj.getKeyValue() == KeyEvent.KEY_U) {
					bagPos = Player.getPlayer().getBag().useGood(bagPos);
					setBag();
				}
				return false;
			}
		});
		
	}
	
	
	private void setBag() {
		Bag playBagbag = Player.getPlayer().getBag();
		if (bag != null) {
			bag.clear();
		}
		
		if (playBagbag.getGoods().size() == 0) {			
			bag = new TextComponent("‘›ŒﬁŒÔ∆∑", Constant.WIDTH - 200, 80, LayerConstant.LayerInformation, "bag", 20);
		} else {
			int x = Constant.WIDTH - 200, y = 80 , p = 40;
			bag = new TextComponent(playBagbag.getGoods().get(bagPos).getName() , x, y, LayerConstant.LayerInformation, "bag", 20);
			count = new TextComponent(playBagbag.getGoods().get(bagPos).getCount()+"",x + bag.getBg().getW() - p, y - p, LayerConstant.LayerInformationText, "bag",10);
		}
	}
	
	@Override
	public void Close() {
		bag.clear();
		if (count != null) {			
			count.clear();
		}
		ModeManger.setCurrentMode(GlobalMode.GlobalNoneMode);
		// TODO Auto-generated method stub
		
	}
}
