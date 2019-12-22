package scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

import camera.Camera;
import camera.TrackCamera;
import common.Constant;
import entity.Aster;
import entity.Entity;
import entity.Entity2D;
import entity.OpEntity;
import entity.Particular;
import entity.Planet;
import event.Event;
import event.KeyEvent;
import event.TickEvent;
import manager.InputManager;
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
	private Vector4f tp4 = new Vector4f();
	private Vector3f tp = new Vector3f();
	private Quaternionf tq = new Quaternionf();
	private TrackCamera camera;
	private OpEntity craft;
	private Map<Integer, Aster> stones = new HashMap<Integer, Aster>();
	private Aster sun;
	private Planet earth;
	private Entity background;


	private TextComponent bag,count;
	private int bagPos = 0;
	
	public Vector3fc getSunPos() {
		return sun.getPosition();
	}
	
	@Override
	public void Open() {
		Event.register(this);
		background = new Entity(ResourceLoader.getModel("blackbox"), "box");
		earth = new Planet(ResourceLoader.getModel("earth"));
		sun = new Aster(ResourceLoader.getModel("sun"), "sun");
		Aster moon = new Aster(ResourceLoader.getModel("moon"));
		
		craft = new OpEntity(ResourceLoader.getModel("spacecraft"));
		craft.setRotation(90, 180, 0);
		
		RenderManager.add(craft);
		MotionHandler.addMobile(craft);
		MotionHandler.addCollider(craft);
		InputManager.setCraft(craft);
		camera = new TrackCamera(craft);
		
		Particular par = new Particular(craft,ResourceLoader.getTexture("partcle"));
		RenderManager.add(par,Priority.LOW);

		
		moon.setPosition(10, 0, 0);
		moon.setScale(0.0005f);
		moon.setPalstance(0, 10, 0);
		moon.setVelocity(0, 0, -2.5);
		RenderManager.add(moon);
		MotionHandler.addMobile(moon);

		earth.setPosition(0,0,1000);
		earth.setVelocity(-1.5, 0, 0);
		earth.setRotation(0, 0, -23.5);
		earth.setPalstance(0, 2, 0);
		earth.setScale(100);
		RenderManager.add(earth);
		MotionHandler.addMobile(earth);
		MotionHandler.addCollider(earth);

		sun.setPosition(10000, 0, 0);
		sun.setPalstance(0, 0.2f, 0);
		sun.setScale(0.1f);
		RenderManager.add(sun);
		MotionHandler.addMobile(sun);

		
		RenderManager.add(background, Priority.HIGHEST);
		background.setScale(1000000).setPosition(0, 0, -3000000).setLabelable(false);
		
		
		setBag();
		
		UIEventManger.getInstance().addEventListenner(ModeManger.getCurrentModeInt(), UIEventType.UIOnKey, new UIEventFunction() {
			
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
			bag = new TextComponent("������Ʒ", Constant.WIDTH - 200, 80, LayerConstant.LayerInformation, "bag", 20);
		} else {
			bag = new TextComponent(playBagbag.getGoods().get(bagPos).getName() + ":" +playBagbag.getGoods().get(bagPos).getCount() , Constant.WIDTH - 200, 80, LayerConstant.LayerInformation, "bag", 20);
		}
	}
	
	double t = 0;
	
	public void onTick(TickEvent event) {
		t+=event.deltTime();
		if(stones.size() < 200 && t>0.1) {
			t=0;
			generate(1);
		}
		Iterator<Entry<Integer, Aster>> it = stones.entrySet().iterator();
		while(it.hasNext()) {
			Aster now = it.next().getValue();
			if(!inView(now.getPosition()))
			{
				it.remove();
				del(now);
			}
		}
	}
	static Random r = new Random();
	private float rand(float x) {
		return 2*x*r.nextFloat()-x;
	}
	
	private float g2(double x) {
		float v = (float) r.nextGaussian();
		return  v*v*(float)x;
	}

	private boolean inView(Vector3fc pos) {
		tp4.set(pos,1).mul(camera.getViewMat());
		return tp4.z<0;
	}

	private void generate(int n) {
		Aster a = new Aster(ResourceLoader.getModel("6"));
		stones.put(a.hashCode(), a);

		tp.set(rand(200),rand(500),rand(100)-300).rotate(craft.getOrientation()).add(craft.getPosition());
		a.setPosition(tp).setScale(g2(1)+1);
		a.setPalstance(tq.rotateXYZ(rand(1), rand(1), rand(1)));
		a.setVelocity(rand(10), rand(10), rand(10));
		RenderManager.add(a);
		MotionHandler.addMobile(a);
		MotionHandler.addCollider(a);
	}
	private void del(Aster a) {
		RenderManager.remove(a);
		MotionHandler.removeMobile(a);
		MotionHandler.removeCollider(a);
		a.clear();
	}
	
	@Override
	public Camera camera() {
		return camera;
	}
	
	@Override
	public void Close() {
		Event.unregister(this);
		bag.clear();
		if (count != null) {			
			count.clear();
		}
		ModeManger.setCurrentMode(GlobalMode.GlobalNoneMode);
		// TODO Auto-generated method stub
		
	}
}
