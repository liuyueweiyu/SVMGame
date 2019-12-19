package module;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import entity.MoveEntity;
import event.TickEvent;

public class EventHandler {

	private static List<MoveEntity> actEntity = new ArrayList<>();
	private static List<Interaction> Interactions = new ArrayList<>();


	public static void EntityRegist(MoveEntity entity) {
		if (!actEntity.contains(entity))
			actEntity.add(entity);
	}

	public static void InteractRegist(MoveEntity a, MoveEntity b, double k) {
		Interactions.add(new Interaction(a, b, k));
	}

	private static double lastTime = 0;
	
	public static void postRender(TickEvent event) {
		double delta = event.getTime()-lastTime;
		lastTime = event.getTime();
		for (MoveEntity e : actEntity)
			e.move(delta);
		for (Interaction i : Interactions)
			i.interact(delta);
	}

	private static class Interaction {
		private MoveEntity A;
		private MoveEntity B;
		private double k;

		public Interaction(MoveEntity a, MoveEntity b, double k) {
			A = a;
			B = b;
			this.k = k;
		}

		public void interact(double step) {
			Vector3f vec = new Vector3f(B.getPosition()).sub(A.getPosition());
			if (vec.length() < 4) {
				Vector3f nor = new Vector3f(vec).div(vec.length());
				A.getVelocity().sub(nor.mul(2 * nor.dot(A.getVelocity())));
			}
			vec.div((float) Math.pow(vec.length(), 3)).mul((float) k);
			A.addVelocity((Vector3fc) vec);
		}
	}
}
