package scene.dialog;

import java.util.ArrayList;

import common.Constant;
import module.ModuleLoader;
import scene.LayerConstant;

public class Dialog {
	private String speaker;
	private String content;
	private ArrayList<Options> options;
	private int nextPos;
	
	public Dialog() { }
	public Dialog(String speaker,String content) {
		// TODO Auto-generated constructor stub
		this.speaker = speaker;
		this.content = content;
	}
	
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<Options> getOptions() {
		return options;
	}
	public void setOptions(ArrayList<Options> options) {
		this.options = options;
	}
	public int getNextPos() {
		return nextPos;
	}
	public void setNextPos(int nextPos) {
		this.nextPos = nextPos;
	}
	
}
