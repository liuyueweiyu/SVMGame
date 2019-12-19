package uievent;

import event.Event;

public class UIEventObj extends Event {
	private int target;
	private int keyValue;

	
	public UIEventObj() { }
	public UIEventObj(int label) {
		this.target = label;
	}



	
	
	
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(int keyValue) {
		this.keyValue = keyValue;
	}
}
