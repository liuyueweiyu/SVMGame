package src;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

import camera.Camera;
import entity.Aster;
import entity.Entity;
import manager.WindowManager;

public class Transformation {
	private static Matrix4f projectionMatrix;
	private static Matrix4f worldMatrix;
	private static Matrix4f viewMatrix;

	static {
		worldMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}

	public static final Matrix4fc getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		float aspectRatio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
		return projectionMatrix;
	}

	public static final Matrix4fc getProjectionMatrix() {
		return new Matrix4f(getProjectionMatrix(1.0f, WindowManager.width(), WindowManager.height(), 0, 1000));
	}

	public static Matrix4fc getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		worldMatrix.identity().translate(offset).rotateX((float) Math.toRadians(rotation.x))
				.rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
		return worldMatrix;
	}

	public static Matrix4fc getTranslateMatrix(float x, float y, float z) {
		return worldMatrix.identity().translate(x, y, z);
	}

	public static Matrix4fc getVPMatrix(Camera camera) {
		return new Matrix4f(camera.getProjectionMat()).mul(camera.getViewMat());
	}

	public static Matrix4fc getModelViewMatrix(Entity gameItem, Camera camera) {
		Matrix4f modelViewMatrix = new Matrix4f();
		Quaternionfc rotation = gameItem.getRotation();
		modelViewMatrix.set(getViewMatrix(camera)).translate(gameItem.getPosition()).
			rotate(gameItem.getRotation()).
//            rotateX((float)Math.toRadians(-rotation.x)).
//            rotateY((float)Math.toRadians(-rotation.y)).
//            rotateZ((float)Math.toRadians(-rotation.z)).
			scale(gameItem.getScale());

		return modelViewMatrix.mulLocal(camera.getProjectionMat());
	}

	public static Matrix4fc getViewMatrix(Camera camera) {
		return camera.getViewMat();
	}
}
