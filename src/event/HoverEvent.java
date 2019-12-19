package event;

public class HoverEvent extends Event {
	private int x;
	private int y;
	private int label;
	public HoverEvent(int x, int y, int label) {
		super();
		this.x = x;
		this.y = y;
		this.label = label;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getLabel() {
		return label;
	}
	
}
