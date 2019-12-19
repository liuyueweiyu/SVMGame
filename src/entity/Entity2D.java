package entity;

import static manager.WindowManager.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import camera.Camera;
import entity.component.Texture;
import renderer.Renderable;
import renderer.Shader;
import src.Transformation;
/*
 * 平面模型基类，可通过文本和贴图创建，非负整数z表示上下覆盖关系0为最上层；
 * 仅包含最基本位置控制，长宽在初始化后无法修改，位置以屏幕左下角为原点。
 * 如需要复杂操作可继承该类实现额外控制方法，如MoveText子类
*/
public class Entity2D implements Renderable {
	private final boolean isText;
	private final int vao, vbo;
	private final Texture tex;
	protected int w, h;
	protected double x = 0, y = 0;
	protected int z = 0;
	

	public Entity2D(String str, int width, int size, Color c) {
		isText = true;
		Font f = new Font(null, Font.PLAIN, size);
		FontMetrics fm = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics().getFontMetrics(f);
		String[] strs = strSplit(str, fm, width);

		int fh = fm.getHeight();
		int height = fh * strs.length + fh / 2;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics g = image.getGraphics();
		g.setColor(c);
		g.setFont(f);

		for (int i = 0; i < strs.length; i++) {
			g.drawString(strs[i], 0, fh * i + fh - 1);
		}
		w = width;
		h = height;
		tex = new Texture(image);
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, getMeshData(), GL_STATIC_DRAW);
		bind();
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 72);
	}

	public Entity2D(Texture tex, int w, int h) {
		isText = false;
		this.w = w;
		this.h = h;
		this.tex = tex;
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, getMeshData(), GL_STATIC_DRAW);
		bind();
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 72);

	}

	public Entity2D setPos(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Entity2D setZ(int zdex) {
		this.z = zdex;
		return this;
	}

	@Override
	public void render(Shader shader, Camera camera) {
		bind();
		tex.bind(0);
		shader.bind();
		shader.setUniform("MVPmat", Transformation.getTranslateMatrix(glx(x), gly(y),z));
		shader.setUniform("tex", 0);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	@Override
	public String getShader() {
		return "tex2d";
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getZ() {
		return z;
	}

	protected void bind() {
		glBindVertexArray(vao);
	}

	public void clear() {
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vao);
		if(isText)
			tex.clear();
	}

	protected boolean isLabelable = true;

	public Entity2D setLabelable(boolean Labelable) {
		isLabelable = Labelable;
		return this;
	}
	
	@Override
	public Priority defaultPriority() {
		return Priority.LOW;
	}

	@Override
	public boolean isLableabel() {
		return isLabelable;
	}

	private static String[] strSplit(String str, FontMetrics fm, int width) {
		assert width > 0 : "error value of width";
		ArrayList<String> a = new ArrayList<>();
		int len = str.length();
		int l = 0, r = 0;
		for (int cnt = 0; r < len; r++) {
			if (str.charAt(r) == '\n') {
				a.add(str.substring(l, r));
				l = r + 1;
				cnt = 0;
				continue;
			}
			if (cnt + fm.charWidth(str.charAt(r)) <= width) {
				cnt += fm.charWidth(str.charAt(r));
			} else {
				if (l == r)
					r++;
				a.add(str.substring(l, r));
				l = r--;
				cnt = 0;
			}
		}
		if (l != r)
			a.add(str.substring(l));
		return (String[]) a.toArray(new String[a.size()]);
	}

	private float[] getMeshData() {
		float[] data = { 0, 0, 0, 0, glh(-h), 0, glw(w), glh(-h), 0, 0, 0, 0, glw(w), glh(-h), 0, glw(w), 0, 0, 0, 0,
				0, 1, 1, 1, 0, 0, 1, 1, 1, 0};
		return data;
	}


}
