package manager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.Color;

import org.joml.Vector2d;
import org.joml.Vector2i;

import camera.Camera;
import entity.Entity2D;
import entity.OpEntity;
import event.*;
import renderer.RenderManager;
import src.Main;

public class InputManager {
	private static Vector2i mousePos = new Vector2i();
	private static Vector2d rotVec = new Vector2d();
	private static boolean W = false;
	private static boolean A = false;
	private static boolean S = false;
	private static boolean D = false;
	private static boolean R = false;
	private static boolean F = false;
	private static boolean Q = false;
	private static boolean E = false;
	private static boolean up = false;
	private static boolean down = false;
	private static boolean left = false;
	private static boolean right = false;
	private static boolean inWindow;
	private static boolean rightButtonPressed;
//	static Camera camera;
	private static OpEntity craft;
	
	public static void init() {
		Event.register(InputManager.class);
	}
	
	public static void setCraft(OpEntity target) {
		craft = target;
	}

	public static void onKey(KeyEvent event) {
		int key = event.getKey(), action = event.getAction();
		switch (key) {
		case GLFW_KEY_ESCAPE:
			if (action == GLFW_RELEASE)
				WindowManager.setShouldClose(true);
			break;
		case GLFW_KEY_W:
			W = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_A:
			A = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_S:
			S = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_D:
			D = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_R:
			R = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_F:
			F = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_Q:
			Q = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_E:
			E = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_UP:
			up = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_DOWN:
			down = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_LEFT:
			left = action != GLFW_RELEASE;
			break;
		case GLFW_KEY_RIGHT:
			right = action != GLFW_RELEASE;
			break;
		}

	}

	public static void onTick(TickEvent event) {
		float d = 0.2f;
		float r = 0.5f;
		if (D)
			craft.right(d);
		if (A)
			craft.right(-d);
		if (S)
			craft.addspeed(-d);
		if (W)
			craft.addspeed(d);
		if (R)
			craft.up(d);
		if (F)
			craft.up(-d);
		if (E)
			craft.roll(r);
		if (Q)
			craft.roll(-r);
		if (down)
			craft.pitch(-r);
		if (up)
			craft.pitch(r);
		if (left)
			craft.yaw(-r);
		if (right)
			craft.yaw(r);
	}

	public static void onMouseMove(HoverEvent event) {
		if (inWindow && rightButtonPressed) {

			rotVec.x = event.getX() - mousePos.x;
			rotVec.y = event.getY() - mousePos.y;
			craft.pitch(rotVec.y * 0.1);
			craft.yaw(rotVec.x * 0.1);
		}
		mousePos.x = event.getX();
		mousePos.y = event.getY();
	}
	static Entity2D text;
	public static void onMouseButton(ClickEvent event) {
		rightButtonPressed = event.getButton() == GLFW_MOUSE_BUTTON_2 && event.getAction() == GLFW_PRESS;
		if (RenderManager.isLabelMode() && event.getButton() == GLFW_MOUSE_BUTTON_1
				&& event.getAction() == GLFW_PRESS) {
			System.out.println(event.getLabel());
		}
	}

	public static void onCursorEnter(MouseEnterEvent event) {
		inWindow = event.getEnter();
	}
}
