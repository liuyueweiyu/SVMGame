package entity;

import org.joml.Vector3fc;

import entity.component.Model;

/*
 * 三维模型基类
 */

public class Aster extends MobileEntity implements Collider{
    
    public Aster(Model model, String shader) {
    	super(model,shader);
    }

    public Aster(Model model) {
    	super(model);
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
