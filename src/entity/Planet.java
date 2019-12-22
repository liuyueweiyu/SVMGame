package entity;

import entity.component.Model;

public class Planet extends Aster {

	public Planet(Model model) {
		super(model);
	}
	
	@Override
	public float getColliderSize() {
		return super.getColliderSize()*4;
	}

}
