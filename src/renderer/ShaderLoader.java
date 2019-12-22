package renderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ShaderLoader {
	
	private static final String ShaderPath = "shaders/";
	private static final Map<String, Shader> shaders = new HashMap<>();;

	public static void loadShaders() {
//		String[] names = { "def", "box", "sun", "label", "partcle", "tex2d"};
//		for (String name : names) {
//			shaders.put(name, loadShader(name));
//		}
		File d = new File(ShaderPath);
		File[] fs = d.listFiles();
		for (File f : fs) {
			if (!f.isFile())
				continue;
			String[] name = f.getName().split("\\.", 2);
			if (name[1].equals("vs") || name[1].equals("gs") || name[1].equals("fs")) {
				shaders.computeIfAbsent(name[0], x -> {
					return new Shader();
				});
			} else
				continue;
			try (InputStream s = new FileInputStream(f)) {
				byte[] b = new byte[s.available()];
				s.read(b);
				String code = new String(b);
				if (name[1].equals("vs")) {
					shaders.get(name[0]).createVS(code);
				} else if (name[1].equals("gs")) {
					shaders.get(name[0]).createGS(code);
				} else if (name[1].equals("fs")) {
					shaders.get(name[0]).createFS(code);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		shaders.forEach((x,s)->{
			s.link();
		});
	}
	
	public static Shader getShader(String name) {
		if (name == null || !shaders.containsKey(name))
			name = "def";
		return shaders.get(name);
	}
	
}
