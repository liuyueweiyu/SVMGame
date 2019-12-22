package entity;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL30.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.joml.Math;
import org.joml.Vector3f;

import camera.Camera;
import entity.component.Texture;
import event.Event;
import event.TickEvent;
import manager.GLManager;
import renderer.Renderable;
import renderer.Shader;
import src.Transformation;

public class Particular implements Renderable {

	List<Particle> ps = new LinkedList<>();
	private final OpEntity target;
	private int vao;
	private int vbo;
	private final Vector3f relpos = new Vector3f(0, 0, 5.5f);
	private final Vector3f pos = new Vector3f();
	private Vector3f vel = new Vector3f();
	Texture patex;

	public Particular(OpEntity target, Texture tex) {
		this.target = target;
		patex = tex;
		glEnable(GL_VERTEX_PROGRAM_POINT_SIZE);
		vao = glGenVertexArrays();
		assert vao != 0 : "Create vao error";
		bind();
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);

		Event.register(this);
	}

	private void bind() {
		glBindVertexArray(vao);
	}

	@Override
	public void render(Shader shader, Camera camera) {
		GLManager.DepthTest(false);
		shader.setUniform("V", Transformation.getViewMatrix(camera));
		shader.setUniform("P", Transformation.getProjectionMatrix());
		bind();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		float[] data = new float[ps.size() * 4];
		for (int i = 0; i < ps.size(); i++) {
			Particle p = ps.get(i);
			data[4 * i] = p.x;
			data[4 * i + 1] = p.y;
			data[4 * i + 2] = p.z;
			data[4 * i + 3] = p.t/k;
		}
		glBufferData(GL_ARRAY_BUFFER, data, GL_DYNAMIC_DRAW);
		shader.setUniform("tex", 0);
		patex.bind(0);
		glDrawArrays(GL_POINTS, 0, ps.size());
		GLManager.DepthTest(true);
	}

	public void onTick(TickEvent event) {
		pos.set(relpos).rotate(target.getOrientation()).add(target.getPosition());
		vel.set(0, 0, 20).rotate(target.getOrientation()).add(target.getVelocity());
		float time = (float) event.deltTime();
		ps.removeIf(p -> {
			return p.tick(time);
		});
		generate(100);
	}

	void generate(int n) {
		Random r = new Random();
		for (int i = 0; i < n; i++)
			ps.add(new Particle(r));
	}

	@Override
	public String getShader() {
		return "partcle";
	}

	public static float Gaussion(Random r) {
		while (true) {
			float g = (float) r.nextGaussian();
//			if (g < 2 && g > -2)
				return g/4;
		}
	}

	private static final float k =1f;

	public static Vector3f jun(Random r, Vector3f v) {
		while (true) {
			v.set(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1);
			if (v.length() < 1)
				return v;
		}
	}
	
	private static final Vector3f tmp = new Vector3f();
	private class Particle {
		
		public Particle(Random r) {
			Vector3f v = jun(r, tmp).add(pos);
			this.x = Gaussion(r)+pos.x;
			this.y = Gaussion(r)+pos.y;
			this.z = Gaussion(r)+pos.z;
//			t0 = k*Gaussion(r);
//			this.x = v.x;
//			this.y = v.y;
//			this.z = v.z;
			t0 = k / (2*(Math.abs(this.z - pos.z) + Math.abs(this.x - pos.x) + Math.abs(this.y - pos.y)));
		}

		public boolean tick(float delta) {

			t += delta;
			x += delta * vel.x;
			y += delta * vel.y;
			z += delta * vel.z;
			return t > t0;
		}

		public float t0, t = 0;
		public float x, y, z;
	}

}
