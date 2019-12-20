package scene.dialog;

import java.util.ArrayList;

public class Options {

	private String text;
	private String callbackID;
	private String callbackParam;
	private String hoverContent;
	private int nextPos;
	
	public String getHoverContent() {
		return hoverContent;
	}
	public String getText() {
		return text;
	}

	public String getCallbackID() {
		return callbackID;
	}

	public int getNextPos() {
		return nextPos;
	}
	public void setNextPos(int nextPos) {
		this.nextPos = nextPos;
	}
	public String getCallbackParam() {
		return callbackParam;
	}
	

	public void setText(String text) {
		this.text = text;
	}

	public void setCallbackID(String callbackID) {
		this.callbackID = callbackID;
	}
	public void setCallbackParam(String callbackParam) {
		this.callbackParam = callbackParam;
	}
	public void setHoverContent(String hoverContent) {
		this.hoverContent = hoverContent;
	}
}
