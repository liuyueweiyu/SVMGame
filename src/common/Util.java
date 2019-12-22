package common;

import entity.Entity2D;
import module.ResourceLoader;
import renderer.RenderManager;
import uievent.UIEventFunction;
import uievent.UIEventManger;

public class Util {
	public static void removeEntity2d(RemoveType type,Entity2D entity2d) {
		if (entity2d != null) {
			switch (type) {
			case RemoveMemory:
				entity2d.clear();
			case RemoveDisplay:
				RenderManager.remove(entity2d);
			case RemoveEvent:
				UIEventManger.getInstance().removeEventLister(entity2d.getLabel());
			default:
				
			}
		}
		
	}
	
	
}
