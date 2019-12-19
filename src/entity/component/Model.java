package entity.component;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMaterialProperty;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIPropertyStore;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AITexture;
import org.lwjgl.assimp.Assimp;

import renderer.Shader;

public class Model {
	String shader = null;
	List<Mesh> meshes = new ArrayList<>();
	List<Texture> textures = new ArrayList<>();
	List<AIMaterial> materials = new ArrayList<>();


	public String getShaderName() {
		return shader;
	}

	public Model(AIScene scene) {
		loadMeshes(scene);
		loadTexture(scene);
		for (int i = 0; i < scene.mNumMaterials(); i++) {
			materials.add(AIMaterial.createSafe(scene.mMaterials().get(i)));

		}
	}

	private void loadTexture(AIScene scene) {
		for (int i = 0; i < scene.mNumTextures(); i++) {
			textures.add(new Texture(AITexture.createSafe(scene.mTextures().get(i))));
		}
	}

	private void loadMeshes(AIScene scene) {
		for (int i = 0; i < scene.mNumMeshes(); i++) {
			meshes.add(new Mesh(AIMesh.createSafe(scene.mMeshes().get(i))));
		}
	}

	public void draw(Shader shader) {
		meshes.forEach(mesh -> {
			Texture tex;
			tex = getTexbyMat(materials.get(mesh.getMatIndex()), Assimp.aiTextureType_DIFFUSE);
			if (tex != null) {
				tex.bind(1);
				shader.setUniform("diffuse_tex", 1);
			}
			tex = getTexbyMat(materials.get(mesh.getMatIndex()), Assimp.aiTextureType_NORMALS);
			if (tex != null) {
				tex.bind(6);
				shader.setUniform("normals_tex", 6);
			}
			tex = getTexbyMat(materials.get(mesh.getMatIndex()), Assimp.aiTextureType_SPECULAR);
			if (tex != null) {
				tex.bind(2);
				shader.setUniform("specular_tex", 2);
			}
			mesh.draw();
		});
	}

	private Texture getTexbyMat(AIMaterial mat, int Textype) {
		AIString p = AIString.calloc();
		Texture tex = null;
		if (Assimp.aiGetMaterialTexture(mat, Textype, 0, p, (IntBuffer) null, null, null, null, null,
				null) == Assimp.aiReturn_SUCCESS) {
			tex = textures.get(Integer.valueOf(p.dataString().substring(1)));
		}
		p.free();
		return tex;
	}

	public void clear() {
		meshes.forEach(x -> {
			x.clear();
		});
		textures.forEach(x -> {
			x.clear();
		});
	}
}
