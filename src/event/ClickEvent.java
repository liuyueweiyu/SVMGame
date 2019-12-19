package event;

public class ClickEvent extends Event {
	
	private int x;
	private int y;
	private int button;
	private int action;
	private int label;
	public ClickEvent(int x, int y, int button, int action, int label) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.action = action;
		this.label = label;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getButton() {
		return button;
	}
	public int getAction() {
		return action;
	}
	public int getLabel() {
		return label;
	}
	
	/** The key or button was released. */
    public static final int RELEASE = 0;

    /** The key or button was pressed. */
    public static final int PRESS = 1;

    /** The key was held down until it repeated. */
    public static final int REPEAT = 2;
	
	public static final int
	    MOUSE_BUTTON_1      = 0,
	    MOUSE_BUTTON_2      = 1,
	    MOUSE_BUTTON_3      = 2,
	    MOUSE_BUTTON_4      = 3,
	    MOUSE_BUTTON_5      = 4,
	    MOUSE_BUTTON_6      = 5,
	    MOUSE_BUTTON_7      = 6,
	    MOUSE_BUTTON_8      = 7,
	    MOUSE_BUTTON_LAST   = MOUSE_BUTTON_8,
	    MOUSE_BUTTON_LEFT   = MOUSE_BUTTON_1,
	    MOUSE_BUTTON_RIGHT  = MOUSE_BUTTON_2,
	    MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
}
