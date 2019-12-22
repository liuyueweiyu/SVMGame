package event;

import org.joml.Vector3fc;

import entity.Collider;

public class CraftBumpEvent extends Event{
	Collider other;
	
	Vector3fc pos;

	public Collider getOther() {
		return other;
	}

	public Vector3fc getPos() {
		return pos;
	}

	public CraftBumpEvent(Vector3fc pos, Collider other) {
		this.pos = pos;
		this.other = other;
	}
}
