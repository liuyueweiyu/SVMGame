package src;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import camera.Camera;
import entity.MoveEntity;
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

    public static final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;        
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
//        projectionMatrix._m22(-projectionMatrix.m22());
        return projectionMatrix;
    }
    public static final Matrix4f getProjectionMatrix() {
        return new Matrix4f(getProjectionMatrix(1.0f, WindowManager.width(), WindowManager.height(), 0, 1000));
    }

    public static Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
        worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
    
    public static Matrix4f getTranslateMatrix(float x, float y, float z) {
        return worldMatrix.identity().translate(x, y, z);
    }
    
    public static Matrix4f getVPMatrix(Camera camera) {
        return camera.getProjectionMat().mul(getViewMatrix(camera));
    }
    
    public static Matrix4f getModelViewMatrix(Entity gameItem, Camera camera) {
    	Matrix4f modelViewMatrix = new Matrix4f();
        Vector3f rotation = gameItem.getRotation();
        modelViewMatrix.set(getViewMatrix(camera)).translate(gameItem.getPosition()).
            rotateX((float)Math.toRadians(-rotation.x)).
            rotateY((float)Math.toRadians(-rotation.y)).
            rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        return camera.getProjectionMat().mul(modelViewMatrix);
    }
    
    public static Matrix4f getViewMatrix(Camera camera) {
        return camera.getViewMat();
    }
}
