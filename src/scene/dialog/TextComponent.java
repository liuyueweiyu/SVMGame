package scene.dialog;

import java.awt.Color;

import javax.security.auth.Destroyable;

import entity.Entity2D;
import entity.MoveText;
import module.ModuleLoader;
import scene.LayerConstant;

public class TextComponent {
	public Entity2D getBg() {
		return bg;
	}

	public MoveText getText() {
		return text;
	}


	private Entity2D bg;
	private MoveText text;
	private int w,h,x,y,z;
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

	
	private MoveText createText(String texrString) {
		return ModuleLoader.creatTextButton(texrString, w-20, x + 20 , y,z,24,Color.WHITE);
	}
	
	public void ChangeText(String texrString) {
		if (text != null) {
			text.clear();
		}
		text = createText(texrString);
	}
	
	
	public int getLabel() {
		return text.getLabel();
	}
	
	public void Destroy() {
		bg.clear();
		text.clear();
	}
}
