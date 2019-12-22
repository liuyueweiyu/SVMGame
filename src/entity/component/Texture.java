package entity.component;

import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.lwjgl.assimp.AITexture;
import org.lwjgl.system.MemoryUtil;

public class Texture {
	
	private int texId;
	
	public Texture(File file) {
		BufferedImage im = null;
		try {
			im = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		createTex(im);
	}
	
	public Texture(String path) {
		BufferedImage im = null;
		try {
			im = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		createTex(im);
	}
	
	public Texture(AITexture tex) {
		long ad = tex.pcData(0).address();
		byte[] b = new byte[tex.mWidth()];
		for(int i = 0; i < tex.mWidth(); i++) {
			b[i] = MemoryUtil.memGetByte(ad+i);
		}		
		BufferedImage im = null;
		try {
			im = ImageIO.read(new ByteArrayInputStream(b));
		} catch (IOException e) {
			e.printStackTrace();
		}
		createTex(im);
	}
	
	public Texture(BufferedImage tex) {
		createTex(tex);
	}
	
	
	private void createTex(BufferedImage tex) {
		int[] pixels = new int[tex.getWidth() * tex.getHeight()];
		
		tex.getRGB(0, 0, tex.getWidth(), tex.getHeight(), pixels, 0, tex.getWidth());
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i]) << 8 | (pixels[i] >>> 24);
		}
		texId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tex.getWidth(), tex.getHeight(), 0, GL_RGBA, GL_UNSIGNED_INT_8_8_8_8, pixels);
		glGenerateMipmap(GL_TEXTURE_2D);
	}
	
	public void bind(int gal) {
		glActiveTexture(GL_TEXTURE0+gal);
		glBindTexture(GL_TEXTURE_2D, texId);
	}
	public void clear() {
		glDeleteTextures(texId);
	}
	
}
