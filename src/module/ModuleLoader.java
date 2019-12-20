package module;



import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.assimp.Assimp;

import entity.MobileEntity;
import entity.Entity;
import entity.Entity2D;
import entity.MoveText;
import entity.component.Model;
import entity.component.Texture;
import event.Event;
import event.HoverEvent;
import event.IEventListener;
import renderer.RenderManager;
import renderer.Renderable.Priority;

public class ModuleLoader {


	static public Map<String, Model> models = new HashMap<>();
	static public Map<String, Texture> textures = new HashMap<>();
	
	private static void loadModel() {// load your models with glb format
//		File dir = new File("resource");
//		File[] fs = dir.listFiles();
//		for(File f :fs) {
//			if(f.getName().endsWith(".glb")) {
//				Model model = new Model(Assimp.aiImportFile(f.toString(), 0));
//				models.put(f.getName().replace(".glb", ""),model);
//			}
//		}
	}
	
	private static void loadtextures() {//load your textures
		File dir = new File("images");
		File[] fs = dir.listFiles();
		for(File f :fs) {
			if(f.getName().endsWith(".png")) {
				Texture texture = new Texture(f.getAbsolutePath());
				textures.put(f.getName().replace(".png", ""),texture);
			}
		}
	}
	
	public static void load() {
		loadModel();
		loadtextures();
	}
	
	
	public static void createEntity() {//test codes
		
		MobileEntity box = new MobileEntity(models.get("blackbox"),"box");
		MobileEntity earth = new MobileEntity(models.get("earth"));
		MobileEntity sun = new MobileEntity(models.get("sun"),"sun");
		MobileEntity moon = new MobileEntity(models.get("moon"));
		
		Entity craft = new Entity(models.get("spacecraft"));
		MoveText t = new MoveText("込込込込",100,3,3);
		t.setPos(300, 200);
		t.setZ(0);
		RenderManager.add(t,Priority.LOW);
		
		RenderManager.add(craft);

		
		moon.setPosition(10, 0, 0);
		moon.setScale(0.5f);
		moon.setPalstance(0, 100, 0);
		moon.setVelocity(0, 0, -2.5);
		RenderManager.add(moon);
		EventHandler.EntityRegist(moon);
		EventHandler.InteractRegist(moon, earth, 1);
		EventHandler.InteractRegist(moon, sun, 5);
		RenderManager.add(craft);
		
		earth.setVelocity(0, 0, -1.5);
		earth.setPalstance(0, -200, 0);
		RenderManager.add(earth);
		
		EventHandler.EntityRegist(earth);
		EventHandler.InteractRegist(earth, sun, 5);
		
		sun.setPosition(100, 0, 0);
		sun.setPalstance(0, 2, 0);
		sun.setScale(0.001f);
		RenderManager.add(sun);
		EventHandler.EntityRegist(sun);
		
		earth.setScale(1f);
		
		RenderManager.add(box,Priority.HIGHEST);
		box.setScale(10000).setPosition(0, 0, -30000).setLabelable(false);
	}
	
	public static void createBackground() {
		MobileEntity box = new MobileEntity(models.get("blackbox"),"box");
		RenderManager.add(box,Priority.HIGHEST);
		box.setScale(10000).setPosition(0, 0, -30000).setLabelable(false);
	}
	
	public static Entity2D creatTextButton(String text,int width,int posX,int posY,int z) {
		MoveText t = new MoveText(text,width,0,0);
		t.setPos(posX, posY);
		t.setZ(z);
		RenderManager.add(t,Priority.LOW);	
		return t;
	}
	
	public static Entity2D creatTextButton(String text,int width,int posX,int posY,int z,int fontSize,Color color) {
		Entity2D t = new Entity2D(text,width,fontSize,color);
		t.setPos(posX, posY);
		t.setZ(z);
		RenderManager.add(t,Priority.LOW);	
		return t;
	}
	
	public static Entity2D creatTextButton(String text,int posX,int posY,int z,int fontSize,Color color) {
		Entity2D t = new Entity2D(text,fontSize,color);
		t.setPos(posX, posY);
		t.setZ(z);
		RenderManager.add(t,Priority.LOW);	
		return t;
	}
	
	public static Entity2D creatImage(String name,int w, int h,int x, int y,int z) {
		Entity2D entity2d = new Entity2D(textures.get(name), w, h);
		entity2d.setPos(x, y);
		entity2d.setZ(z);
		RenderManager.add(entity2d);
		return entity2d;
	}
	
}
