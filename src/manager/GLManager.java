package manager;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

public class GLManager {
	
	
	
	public static void Blend(boolean on_off) {
		if (on_off) {
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		} else {
			glDisable(GL_BLEND);
		}
	}
	
	public static void DepthMask(boolean on_off) {
		glDepthMask(on_off);
	}

	public static void DepthTest(boolean on_off) {
		if (on_off) {
			glEnable(GL_DEPTH_TEST);
			glDepthFunc(GL_LESS);
		} else {
			glDisable(GL_DEPTH_TEST);
		}
	}
	
	
	
	public static void setClearColor(double r, double g, double b, double a) {
		glClearColor((float)r, (float)g, (float)b, (float)a);
	}
	
	public static void ClearBuffers(int mask) {
		glClear(mask);
	}
	
	public static double getTime() {
		return glfwGetTime();
	}

}
