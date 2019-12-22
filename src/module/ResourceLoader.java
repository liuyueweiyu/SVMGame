package module;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.assimp.Assimp;

import entity.Aster;
import entity.Entity;
import entity.Entity2D;
import entity.MobileEntity;
import entity.MoveText;
import entity.OpEntity;
import entity.Particular;
import entity.component.Model;
import entity.component.Texture;
import renderer.RenderManager;
import renderer.Renderable.Priority;
import src.Main;
import src.MotionHandler;

public class ResourceLoader {

	static public Map<String, Model> models = new HashMap<>();
	static public Map<String, Texture> textures = new HashMap<>();
	
	public static Model getModel(String name) {
		return models.get(name);
	}
	
	public static Texture getTexture(String name) {
		return textures.get(name);
	}

	private static void loadModel() {// load your models with glb format
		File dir = new File("resource/model");
		File[] fs = dir.listFiles();
		for (File f : fs) {
			if (f.getName().endsWith(".glb")) {
				Model model = new Model(Assimp.aiImportFile(f.toString(), 0));
				models.put(f.getName().replace(".glb", ""), model);
			}
		}
	}

	private static void loadtextures() {// load your textures
		File dir = new File("resource/texture");
		File[] fs = dir.listFiles();
		for (File f : fs) {
			if (f.isFile()) {
				Texture tex = new Texture(f);
				textures.put(f.getName().substring(0, f.getName().indexOf('.')), tex);
			}
		}
	}

	public static void load() {
//		 loadModel();
		loadtextures();
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
		if (textures.get(name) == null) {
			System.out.println("load textture " + name + " error!");
		}
		Entity2D entity2d = new Entity2D(textures.get(name), w, h);
		entity2d.setPos(x, y);
		entity2d.setZ(z);
		RenderManager.add(entity2d);
		return entity2d;
	}
	
	public static void removeEntity2D(Entity2D entity2d) {
		entity2d.clear();
		RenderManager.remove(entity2d);
	}
}
