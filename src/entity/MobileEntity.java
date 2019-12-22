package entity;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import entity.component.Model;

public class MobileEntity extends Entity{
	 protected Vector3f velocity = new Vector3f();
	    
	    protected Quaternionf palstance = new Quaternionf();
	    
	    public MobileEntity(Model model, String shader) {
	    	super(model,shader);
	    }

	    public MobileEntity(Model model) {
	    	super(model);
	    }


	    
	    public void addVelocity(Vector3fc vec) {
			this.velocity.add(vec);
		}

		public void setVelocity(double x, double y, double z) {
			this.velocity.x = (float) x;
	        this.velocity.y = (float) y;
	        this.velocity.z = (float) z;
		}
		
		public void setVelocity(Vector3fc vel) {
			this.velocity.set(vel);
		}

		public void setPalstance(double x, double y, double z) {
			palstance.rotateLocalX((float) Math.toRadians(x));
			palstance.rotateLocalY((float) Math.toRadians(y));
			palstance.rotateLocalZ((float) Math.toRadians(z));
		}
		public void setPalstance(Quaternionfc q) {
			palstance.set(q);
		}

		public Vector3f getVelocity() {
			return velocity;
		}

		public Quaternionfc getPalstance() {
			return palstance;
		}
		
		public void move(float step) {
			position.add(new Vector3f(velocity).mul(step));
			rotation.mul(new Quaternionf().nlerp(palstance, step));
		}
}
