package camera;

import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import static manager.WindowManager.*;

public class Camera {
	
    private Vector3f position;
    private Vector3f rotation;
    private Quaternionf rot;
    private Matrix4f projection;
        
    public Camera() {
        position = new Vector3f();
        rotation = new Vector3f();
        rot = new Quaternionf();
        projection = new Matrix4f().identity().perspective(1.1f, 1f*width()/height(), 0, 1000);
    }
    
    public Matrix4f getProjectionMat() {
    	return new Matrix4f(projection);
    }
    
    public Matrix4f getViewMat() {

    	Matrix4f view = new Matrix4f();
    	rot.identity();
    	rot.rotateX((float)Math.toRadians(rotation.x))
        .rotateY((float)Math.toRadians(rotation.y))
        .rotateZ((float)Math.toRadians(rotation.z));
            // 首先进行旋转，使摄像机在其位置上旋转
    	view.rotate(rot);
    	view.translate(-position.x, -position.y, -position.z);
		return view;
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(double x, double y, float offsetZ) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += offsetZ;
    }

}
