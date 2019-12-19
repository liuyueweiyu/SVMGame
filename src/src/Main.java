package src;


import camera.Camera;
import entity.MoveText;
import event.Event;
import event.TickEvent;
import manager.GLManager;
import manager.InputManager;
import manager.WindowManager;
import renderer.RenderManager;
import renderer.RenderManager.Priority;
import module.EventHandler;
import module.ModuleLoader;


import scene.Menu;
import scene.PlanetAera;

public class Main {

	public static Camera camera= new Camera();
	
	public void run() {

		preinit();
		init();
		loop();
		exit();
		
	}

	private void preinit() {//system init
		WindowManager.init();
		InputManager.init();
		RenderManager.init();
		Event.register(EventHandler.class);
		camera.setPosition(0, 100f, 0);
		ModuleLoader.load();

		GLManager.Blend(true);
		GLManager.DepthTest(true);
		GLManager.setClearColor(0, 0.5,0.5, 1.0);
		RenderManager.LabelMode(true);
	}
	
	private void init() { // your init codes
		PlanetAera planetAera = new PlanetAera();
		planetAera.Open();
//		Menu menu = new Menu();
//		menu.Open();
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

}