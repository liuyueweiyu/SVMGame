package scene.dialog;

import java.awt.Color;
import java.time.format.TextStyle;

import javax.security.auth.Destroyable;

import entity.Entity2D;
import entity.MoveText;
import module.ModuleLoader;
import renderer.RenderManager;
import scene.LayerConstant;

public class TextComponent {
	public Entity2D getBg() {
		return bg;
	}

	public Entity2D getText() {
		return text;
	}


	private Entity2D bg;
	private Entity2D text;
	private int padding = 20;
	private int w,h,x,y,z,fontSize = 24;
	public TextComponent(String texrString, int w,int h,int x,int y,int z,String background) {
		// TODO Auto-generated constructor stub
		bg = ModuleLoader.creatImage(background,w,h,x,y,z);	
		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
		this.z = z;
		text = createText(texrString);		
	}

	public TextComponent(String texrString,int x,int y,int z,String background,int fontSize) {
		// TODO Auto-generated constructor stub
		
		this.x = x;
		this.h = fontSize + padding ;
		this.y = y;
		this.z = z;
		padding = fontSize / 2;
		this.fontSize = fontSize;
		text = 	createText(texrString);	
		bg = ModuleLoader.creatImage(background,text.getW() + padding * 2,h,x,y,z);	
	}

	
	private Entity2D createText(String texrString) {
		return w != 0 
				? ModuleLoader.creatTextButton(texrString, w-padding, x + padding , y,z,fontSize ,Color.WHITE) 
				: ModuleLoader.creatTextButton(texrString, x + padding , y,z,fontSize ,Color.WHITE);
	}
	

	
	public void ChangeText(String texrString) {
		if (text != null) {
			text.clear();
			RenderManager.remove(text);
		}
		text = createText(texrString);
		text.setLabelable(false);
	}
	
	
	public int getLabel() {
		return bg.getLabel();
	}
	
	public void clear() {
		RenderManager.remove(bg);
		RenderManager.remove(text);
		text.clear();
	}
	
	
}
