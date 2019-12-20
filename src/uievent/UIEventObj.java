package uievent;

import event.Event;

public class UIEventObj extends Event {
	private int target;
	private int keyValue;
	private int x,y;
	
	
	public UIEventObj() { }

	public UIEventObj(int label) {
		this.target = label;
	}

	

	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
