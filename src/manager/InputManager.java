package manager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector2d;
import org.joml.Vector2i;

import camera.Camera;
import event.*;
import renderer.RenderManager;
import src.Main;
import uievent.UIEventManger;

// ≤‚ ‘input?
public class InputManager {
	private static Vector2i mousePos = new Vector2i();
	private static Vector2d rotVec = new Vector2d();
	private static boolean inWindow;
	private static boolean rightButtonPressed;
	static Camera camera;
	
	public static void init() {
		Event.register(InputManager.class);
		camera = Main.camera;
	}
	
	public static void onKey(KeyEvent event) {
		int key = event.getKey(), action = event.getAction();
		float d = 1f;
		if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			WindowManager.setShouldClose(true);
		if (key == GLFW_KEY_D && action != GLFW_RELEASE)
			camera.movePosition(d, 0, 0);
		if (key == GLFW_KEY_A && action != GLFW_RELEASE)
			camera.movePosition(-d, 0, 0);
		if (key == GLFW_KEY_S && action != GLFW_RELEASE)
			camera.movePosition(0, 0, d);
		if (key == GLFW_KEY_W && action != GLFW_RELEASE)
			camera.movePosition(0, 0, -d);
		if (key == GLFW_KEY_R && action != GLFW_RELEASE)
			camera.movePosition(0, d, 0);
		if (key == GLFW_KEY_F && action != GLFW_RELEASE)
			camera.movePosition(0, -d, 0);
		if (key == GLFW_KEY_DOWN && action != GLFW_RELEASE)
			camera.moveRotation(0.1, 0, 0);
		if (key == GLFW_KEY_UP && action != GLFW_RELEASE)
			camera.moveRotation(-0.1, 0, 0);
		if (key == GLFW_KEY_LEFT && action != GLFW_RELEASE)
			camera.moveRotation(0, -0.1, 0);
		if (key == GLFW_KEY_RIGHT && action != GLFW_RELEASE)
			camera.moveRotation(0, 0.1, 0);
		
	}
	
	public static void onMouseMove(HoverEvent event) {
		if (inWindow && rightButtonPressed) {

			rotVec.x = event.getX() - mousePos.x;
			rotVec.y = event.getY() - mousePos.y;
			camera.moveRotation(rotVec.y * 0.1, rotVec.x * 0.1, 0);
		}
		mousePos.x = event.getX();
		mousePos.y = event.getY();
	}
	
	public static void onMouseButton(ClickEvent event) {
		rightButtonPressed = event.getButton() == GLFW_MOUSE_BUTTON_2 && event.getAction() == GLFW_PRESS;
		if(RenderManager.isLabelMode() && event.getButton() == GLFW_MOUSE_BUTTON_1 && event.getAction() == GLFW_PRESS)
		{
			System.out.println(event.getLabel());
		}
	}
	public static void onCursorEnter(MouseEnterEvent event) {
		inWindow = event.getEnter();
	}
}
