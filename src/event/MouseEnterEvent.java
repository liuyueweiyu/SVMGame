package event;

public class MouseEnterEvent extends Event {
	private boolean enter;

	public MouseEnterEvent(boolean enter) {
		this.enter = enter;
	}

	public boolean getEnter() {
		return enter;
	}
	
}
