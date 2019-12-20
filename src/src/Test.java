package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector4d;

import manager.WindowManager;
import renderer.Shader;

public class Test {
	private static final Map<String, Shader> shaders = new HashMap<>();

	public static void main(String[] args) {
//		Quaterniond q = new Quaterniond().rotateXYZ(0,1,1.57);
//		Matrix3d m = new Matrix3d();
//		m.rotate(q);
//		System.out.println(m);
//		String ShaderPath = "shaders";
//		File d = new File(ShaderPath);
//		File[] fs = d.listFiles();
//		for (File f : fs) {
//			if (!f.isFile())
//				continue;
//			String[] name = f.getName().split("\\.", 2);
//			System.out.println(name[1]);
//			if (name[1].equals("vs") || name[1].equals("gs") || name[1].equals("fs")) {
//				if(!shaders.containsKey(name[0]))
//					shaders.put(name[0], new Shader());
//			} else
//				continue;
//			try (InputStream s = new FileInputStream(f)) {
//				byte[] b = new byte[s.available()];
//				s.read(b);
//				String code = new String(b);
//				if (name[1].equals("vs")) {
//					shaders.get(name[0]).createVS(code);
//				} else if (name[1].equals("gs")) {
//					shaders.get(name[0]).createGS(code);
//				} else if (name[1].equals("fs")) {
//					shaders.get(name[0]).createFS(code);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		shaders.forEach((x,s)->{
//			s.link();
//		});
	}
}
