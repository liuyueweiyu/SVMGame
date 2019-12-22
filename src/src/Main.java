package src;

import camera.Camera;
import camera.TrackCamera;
import entity.OpEntity;
import event.Event;
import event.TickEvent;
import manager.GLManager;
import manager.InputManager;
import manager.WindowManager;
import module.ResourceLoader;
import props.Shop;
import renderer.RenderManager;
import scene.Action;
import scene.Menu;
import scene.PlanetAera;
import scene.Space;
import scene.map.CallbackHander;

public class Main {

	public static Camera camera;
	public static OpEntity craft;
	public static Action currentSence;
	
	public void run() {

		preinit();
		init();
		loop();
		exit();

	}

	private void preinit() {// system init
		WindowManager.init();
		RenderManager.init();
		ResourceLoader.load();

	}

	private void init() { // your init codes
		MotionHandler.init();
		InputManager.init();
		GLManager.Blend(true);
		GLManager.DepthTest(true);
		GLManager.setClearColor(0, 0.5, 0.5, 1.0);
		RenderManager.LabelMode(true);
		
		CallbackHander.getInstance().registerCallBack();
		Shop.getShop().init();

//		Space space = new Space();
//		space.Open();
//		camera = new TrackCamera(space.craft);
//		InputManager.setTarget(space.craft);


		currentSence = new Menu();
		currentSence.Open();
	}

	private void loop() {

		while (!WindowManager.shouldClose()) {

			RenderManager.render(camera);
			WindowManager.SwapBuffers(); // swap the color buffers
			RenderManager.Label(camera);
			WindowManager.pollEvents();
			new TickEvent(GLManager.getTime()).post();
		}
	}

	private void exit() {
		WindowManager.close();
	}

	public static void main(String[] args) {
		new Main().run();
	}

	public static Action getCurrentSence() {
		return currentSence;
	}

	public static void setCurrentSence(Action currentSence) {
		Main.currentSence = currentSence;
	}
	
	
	

}