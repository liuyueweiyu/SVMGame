package manager;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static renderer.RenderManager.*;
import java.nio.IntBuffer;

import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import common.Constant;
import event.*;
import renderer.RenderManager;

public class WindowManager {
	private static long window;
	private static int WIDTH = Constant.WIDTH;
	private static int HEIGHT = Constant.HEIGHT;
	private static boolean isInit = false;
	private static int x, y;
	private static boolean entered;

	public static void init(int w, int h) {
		if (isInit)
			return;
		WIDTH = w;
		HEIGHT = h;
		init();
	}

	public static void init() {
		if (isInit)
			return;
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		window = glfwCreateWindow(WIDTH, HEIGHT, "My GAME!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		setCallBacks();

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		isInit = true;
	}

	private static void setCallBacks() {
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			new KeyEvent(key, action).post();
		});

		glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
			x = (int) xpos;
			y = (int) ypos;
			new HoverEvent(x, y, getLabel(x, HEIGHT-y-1)).post();

		});
		glfwSetCursorEnterCallback(window, (windowHandle, entered) -> {
			WindowManager.entered = entered;
			new MouseEnterEvent(WindowManager.entered).post();
		});
		glfwSetMouseButtonCallback(window, (windowHandle, button, action, mode) -> {
			new ClickEvent(x, y, button, action,getLabel(x, HEIGHT-y-1)).post();
		});
	}

	public static float glw(double w) {
		return (float) (2 * w / WIDTH);
	}

	public static float glh(double h) {
		return (float) (2 * h / HEIGHT);
	}

	public static float glx(double x) {
		return (float) (2 * x / WIDTH - 1);
	}

	public static float gly(double y) {
		return (float) (2 * y / HEIGHT - 1);
	}

	public static int width() {
		return WIDTH;
	}

	public static int height() {
		return HEIGHT;
	}

	public static void SwapBuffers() {
		glfwSwapBuffers(window);
	}

	public static boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	public static void setShouldClose(boolean value) {
		glfwSetWindowShouldClose(window, value);
	}

	public static void pollEvents() {
		glfwPollEvents();
	}

	public static void close() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
