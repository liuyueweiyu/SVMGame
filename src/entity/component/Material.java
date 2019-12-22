package entity.component;

import java.nio.IntBuffer;

import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.Assimp;

public class Material {
	private int[] texId = new int[13];

	public Material(AIMaterial mat) {
		AIString p = AIString.calloc();
		for (int i = 0; i < 13; i++) {
			if (Assimp.aiGetMaterialTexture(mat, i, 0, p, (IntBuffer) null, null, null, null, null,
					null) == Assimp.aiReturn_SUCCESS) {
				texId[i] = Integer.valueOf(p.dataString().substring(1));
			}else {
				texId[i] = -1;
			}
		}
		p.free();
	}
	public int getTexId(int TexType) {
		return texId[TexType];
	}
}
