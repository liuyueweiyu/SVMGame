package entity;

import org.joml.Vector3fc;

public interface Collider {
	public default boolean isCollide(Collider a) {
		return getColliderPos().distance(a.getColliderPos()) < getColliderSize()+a.getColliderSize();
	}
	public float getColliderSize();
	
	public Vector3fc getColliderPos();
	
	public default void onCollide(Collider b) {
		return;
	}
}
