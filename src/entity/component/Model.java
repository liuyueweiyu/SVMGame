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
	List<Material> materials = new ArrayList<>();


	public String getShaderName() {
		return shader;
	}

	public Model(AIScene scene) {
		loadMeshes(scene);
		loadTexture(scene);
		loadMaterial(scene);
		scene.close();
	}
	
	private void loadMaterial(AIScene scene) {
		for (int i = 0; i < scene.mNumMaterials(); i++) {
			materials.add(new Material(AIMaterial.createSafe(scene.mMaterials().get(i))));
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

	private Texture getTexbyMat(Material mat, int Textype) {
		int texid = mat.getTexId(Textype);
		if(texid == -1)
			return null;
		return textures.get(texid);
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
