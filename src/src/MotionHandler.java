package src;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import entity.Aster;
import entity.Collider;
import entity.MobileEntity;
import event.Event;
import event.TickEvent;

public class MotionHandler {

	private static List<MobileEntity> mobile = new ArrayList<>();
	private static List<Interaction> Interactions = new ArrayList<>();
	private static List<Collider> collider  = new ArrayList<>();
	
	public static void init() {
		Event.register(MotionHandler.class);
	}

	public static void mobileRegist(MobileEntity entity) {
		if (!mobile.contains(entity))
			mobile.add(entity);
	}
	
	public static void colliderRegist(Collider entity) {
		if (!collider.contains(entity))
			collider.add(entity);
	}

	public static void InteractRegist(Aster a, Aster b, double k) {
		Interactions.add(new Interaction(a, b, k));
	}

	private static double lastTime = 0;
	
	public static void onTick(TickEvent event) {
		double delta = event.getTime()-lastTime;
		lastTime = event.getTime();
		CollisionDetect();
		for (MobileEntity e : mobile)
			e.move((float) delta);
		for (Interaction i : Interactions)
			i.interact(delta);
	}
	
	public static void CollisionDetect() {
		int n = collider.size();
		for(int i = 0; i < n; i++) {
			Collider a = collider.get(i);
			for(int j = i+1; j < n; j++) {
				Collider b = collider.get(j);
				if(a.isCollide(b)||b.isCollide(a)) {
					a.onCollide(b);
					b.onCollide(a);
				}
			}
		}
	}

	private static class Interaction {
		private Aster A;
		private Aster B;
		private double k;

		public Interaction(Aster a, Aster b, double k) {
			A = a;
			B = b;
			this.k = k;
		}

		public void interact(double step) {
			Vector3f vec = new Vector3f(B.getPosition()).sub(A.getPosition());
			vec.div((float) Math.pow(vec.length(), 3)).mul((float) k);
			A.addVelocity(vec);
			vec.set(B.getPosition()).sub(A.getPosition());
			if (A.isCollide(B)) {
				Vector3f nor = new Vector3f(vec).div(vec.length());
				A.getVelocity().sub(nor.mul(2 * nor.dot(A.getVelocity())));
			}
		}
	}
}
