package renderer;

import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.lwjgl.opengl.GL;

import camera.Camera;
import event.Event;
import manager.GLManager;
import manager.WindowManager;
import renderer.Renderable.Priority;

public class RenderManager {

	private static final List<List<Renderable>> RenderLists;
	private static boolean isLabelMode = false;
	private static int labelfbo;
	private static int labelrbo;
	private static int labelrbod;

	static {
		int cnt = Event.Priority.values().length;
		RenderLists = new ArrayList<>(cnt);
		for (int i = 0; i < cnt; i++) {
			RenderLists.add(new ArrayList<>());
		}
	}

	public static boolean isLabelMode() {
		return isLabelMode;
	}

	public static void LabelMode(boolean on_off) {
		if (isLabelMode == on_off)
			return;
		isLabelMode = on_off;
		if (isLabelMode) {
			labelfbo = glGenFramebuffers();
			glBindFramebuffer(GL_FRAMEBUFFER, labelfbo);
			labelrbo = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, labelrbo);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_R32I, WindowManager.width(), WindowManager.height());
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, labelrbo);
			labelrbod = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, labelrbod);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, WindowManager.width(), WindowManager.height());
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, labelrbod);

		} else {
			glDeleteRenderbuffers(labelrbo);
			glDeleteRenderbuffers(labelrbod);
			glDeleteFramebuffers(labelfbo);
		}

	}

	public static void add(Renderable entity, Priority priority) {
		RenderLists.get(priority.ordinal()).add(entity);
	}

	public static void add(Renderable entity) {
		RenderLists.get(entity.Prioritydefault().ordinal()).add(entity);
	}

	public static void remove(Renderable entity) {
		for (List<Renderable> list : RenderLists) {
			list.remove(entity);
		}
	}

	public static void render(Camera camera) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for (List<Renderable> list : RenderLists) {
			glClear(GL_DEPTH_BUFFER_BIT);
			for (Renderable x : list) {
				x.render(ShaderLoader.getShader(x.getShader()), camera);
			}
		}
	}

	public static int getLabel(int x, int y) {
		if(!isLabelMode())
			return 0;
		int[] label = new int[1];
		glReadPixels(x, y, 1, 1, GL_RED_INTEGER, GL_INT, label);
		return label[0];
	}

	public static void Label(Camera camera) {
		if(!isLabelMode())
			return;
		glBindFramebuffer(GL_FRAMEBUFFER, labelfbo);
		GLManager.ClearBuffers(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Shader shader = ShaderLoader.getShader("label");
		for (List<Renderable> list : RenderLists) {
			glClear(GL_DEPTH_BUFFER_BIT);
			for (Renderable x : list) {
				if (x.isLableabel()) {
					shader.setUniform("label", x.getLabel());
					x.render(shader, camera);
				}
			}
		}
	}

	public static void init() {
		ShaderLoader.loadShaders();
	}


}
