package entity;

import org.joml.Vector3f;

import camera.Camera;
import entity.component.Model;
import renderer.Renderable;
import renderer.Shader;
import src.Transformation;

/*
 * 三维模型基类
 */

public class Entity implements Renderable{

    protected final Model model;

    protected final Vector3f position = new Vector3f();
    
    protected final Vector3f rotation = new Vector3f();
    
    protected String shader = null;
    

    protected float scale = 1;
    
    public Entity(Model model, String shader) {
        this.model = model;
        this.shader = shader;
    }

    public Entity(Model model) {
        this.model = model;

    }

    public Vector3f getPosition() {
        return position;
    }

    public Entity setPosition(double x, double y, double z) {
        this.position.x = (float) x;
        this.position.y = (float) y;
        this.position.z = (float) z;
        return this;
    }

    public float getScale() {
        return scale;
    }

    public Entity setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(double x, double y, double z) {
        this.rotation.x = (float) x;
        this.rotation.y = (float) y;
        this.rotation.z = (float) z;
    }


	
	public void clear() {
		model.clear();
	}
	
	protected boolean isLabelable = true;
	
	public Entity setLabelable(boolean Labelable) {
		isLabelable = Labelable;
		return this;
	}
	
	@Override
	public boolean isLableabel() {
		return isLabelable;
	}

	@Override
	public void render(Shader shader, Camera camera) {
		shader.setUniform("MVPmat", Transformation.getModelViewMatrix(this, camera));
		shader.setUniform("VPmat", Transformation.getVPMatrix(camera));
		shader.setUniform("sun", new Vector3f(100, 0, 0));
		model.draw(shader);
	}

	@Override
	public String getShader() {
		return shader;
	}

}