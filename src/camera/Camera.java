package camera;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import static manager.WindowManager.*;

public class Camera {

	protected final Vector3f position = new Vector3f();
	protected final Quaternionf rotation = new Quaternionf();
	protected final Matrix4f projection = new Matrix4f().identity().perspective(1.1f, 1f * width() / height(), 0, 1000);
	protected final Matrix4f view = new Matrix4f();
	protected final Vector3f offset = new Vector3f();

	public Matrix4fc getProjectionMat() {
		return projection;
	}

	public Matrix4fc getViewMat() {
		return view.identity().rotate(rotation).translate(position);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(double x, double y, double z) {
		position.set(-(float) x, -(float) y, -(float) z);
	}

	public void movePosition(double x, double y, double z) {
		position.add(-(float) x, -(float) y, -(float) z);
	}

	public Quaternionfc getRotation() {
		return rotation;
	}

	public void setRotation(Quaternionfc q) {
		rotation.set(q);
	}

	public void forward(double dis) {
		offset.set(0, 0, (float) dis);
		offset.rotate(rotation.invert());
		position.add(offset);
		rotation.invert();
	}

	public void up(double dis) {
		offset.set(0, -(float) dis, 0);
		offset.rotate(rotation.invert());
		position.add(offset);
		rotation.invert();
	}

	public void right(double dis) {
		offset.set(-(float) dis, 0, 0);
		offset.rotate(rotation.invert());
		position.add(offset);
		rotation.invert();
	}
	
	public void pitch(double angle) {
		rotation.rotateLocalX((float) Math.toRadians(-angle));
	}
	
	public void yaw(double angle) {
		rotation.rotateLocalY((float) Math.toRadians(angle));
	}

	public void roll(double angle) {
		rotation.rotateLocalZ((float) Math.toRadians(angle));
	}

	public void rotate(double x, double y, double z) {
		rotation.rotateLocalX((float) Math.toRadians(x));
		rotation.rotateLocalY((float) Math.toRadians(y));
		rotation.rotateLocalZ((float) Math.toRadians(z));
	}
	
}
