package entity;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import entity.component.Model;
import event.Event;

public class OpEntity extends MobileEntity implements Collider{
	
	private final Vector3f offset = new Vector3f();
	private final Quaternionf orientation = new Quaternionf();
	
	public OpEntity(Model model, String shader) {
		super(model, shader);
		Event.register(this);
	}

	public OpEntity(Model model) {
		super(model);
		Event.register(this);
	}

	public void forward(double dis) {
		offset.set(0, 0, -(float) dis);
		offset.rotate(orientation);
		position.add(offset);
	}

	public void up(double dis) {
		offset.set(0, (float) dis, 0);
		offset.rotate(orientation);
		position.add(offset);
	}

	public void right(double dis) {
		offset.set((float) dis, 0, 0);
		offset.rotate(orientation);
		position.add(offset);
	}
	
	public void pitch(double angle) {
		orientation.rotateX((float) Math.toRadians(angle));
	}
	
	public void yaw(double angle) {
		orientation.rotateY((float) Math.toRadians(-angle));
	}

	public void roll(double angle) {
		orientation.rotateZ((float) Math.toRadians(-angle));
	}
	@Override
	public Quaternionfc getRotation() {
		return new Quaternionf(orientation).mul(rotation);
	}
	
	public Quaternionfc getOrientation() {
		return orientation;
	}
	
	public void addspeed(double speed) {
		offset.set(0,0,-(float)speed).rotate(orientation);
		velocity.add(offset);
	}

	public void move(float step) {
		super.move(step);
		offset.set(0,0,-1).rotate(orientation);
		offset.mul(velocity.length());
		velocity.lerp(offset, 0.5f*step);
		
	}

	@Override
	public float getColliderSize() {
		return scale;
	}

	@Override
	public Vector3fc getColliderPos() {
		return position;
	}
	
}
