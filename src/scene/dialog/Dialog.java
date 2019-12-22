package scene.dialog;

import java.util.ArrayList;

import common.Constant;
import module.ResourceLoader;
import scene.LayerConstant;

public class Dialog {
	private String speakerId;
	private String speaker;
	private String content;
	private ArrayList<Options> options;
	private int nextPos;
	
	public Dialog() { }
	public Dialog(String speaker,String speakerId,String content) {
		// TODO Auto-generated constructor stub
		this.speaker = speaker;
		this.content = content;
		this.speakerId = speakerId;
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
	public String getSpeakerId() {
		return speakerId;
	}
	public void setSpeakerId(String speakerId) {
		this.speakerId = speakerId;
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
