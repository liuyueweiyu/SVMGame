package camera;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import entity.OpEntity;
import event.Event;
import event.TickEvent;

public class TrackCamera extends Camera{
	
	private OpEntity target;
	private Quaternionf tarrot = new Quaternionf();
	private Vector3f tarpos = new Vector3f();
	
	public TrackCamera(OpEntity target){
		this.target = target;
		Event.register(this,Event.Priority.LOW);
	}
	
	public void setTarget(OpEntity target) {
		this.target = target;
	}
	
	public void onTick(TickEvent event) {
		if(target==null)
			return;
		float delta = (float) event.deltTime();
		float k = 5;
		tarrot.set(rotation);
		tarpos.set(position);
		rotation.set(target.getOrientation()).invert();
		position.set(target.getPosition()).mul(-1);
//		up(2);
		pitch(-30);
		forward(-20);
		
		tarrot.nlerp(rotation, k*delta, rotation);
		tarpos.lerp(position, k*delta, position);
	}
}
