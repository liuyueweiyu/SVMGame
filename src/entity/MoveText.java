package entity;

import java.awt.Color;

import event.ClickEvent;
import event.TickEvent;

/*
 * 缁ф壙浜岀淮鍩虹被瀹炵幇閫愬抚鍖�閫熻繍鍔�
 */


public class MoveText extends Entity2D{
	double u,v;
	public MoveText(String s,int width,double u, double v){
		super(s, width, 24, Color.red);
		this.u = u;
		this.v = v;
		TickEvent.register(this);
	}
	
	public MoveText(String s,int width,double u, double v,int fontSize,Color color){
		super(s, width, fontSize, color);
		this.u = u;
		this.v = v;
		TickEvent.register(this);
	}
	
	
	
	public void onTick(TickEvent event) {
		x+=u*event.deltTime();
		y+=v*event.deltTime();
	}
	
	protected void finalize(){
	}
}
