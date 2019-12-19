package scene.map;

import java.util.ArrayList;

import scene.dialog.Dialog;

public class Map {
	
	private String name;
	private ArrayList<Dialog> dialog;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Dialog> getDialog() {
		return dialog;
	}
	public void setDialog(ArrayList<Dialog> dialog) {
		this.dialog = dialog;
	}

}
