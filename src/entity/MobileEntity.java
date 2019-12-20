package entity;


import org.joml.Vector3f;
import org.joml.Vector3fc;

import entity.component.Model;

/*
 * 三维模型基类
 */

public class MobileEntity extends Entity{
    
    protected Vector3f velocity = new Vector3f();
    
    protected Vector3f palstance = new Vector3f();

    protected float scale;
    
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

	public void setPalstance(double x, double y, double z) {
		this.palstance.x = (float) x;
        this.palstance.y = (float) y;
        this.palstance.z = (float) z;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public Vector3f getPalstance() {
		return palstance;
	}
	public void move(double step) {
		position.add(new Vector3f(velocity).mul((float)step));
		rotation.add(new Vector3f(palstance).mul((float)step));
	}
	



}
