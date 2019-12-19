package entity.component;

import static org.lwjgl.opengl.GL30.*;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

public class Mesh{

	public Mesh() {
		vaoId = glGenVertexArrays();
		assert vaoId != 0 : "Create vao error";
	}

	public Mesh(AIMesh aimesh) {
		vaoId = glGenVertexArrays();
		assert vaoId != 0 : "Create vao error";
		loadAIMesh(aimesh);
		matId = aimesh.mMaterialIndex();
	}

	public int getVaoId() {
		return vaoId;
	}

	public void draw() {

		bind();
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
		unbind();
	}
	
	public void loadByBuffer(int p, org.lwjgl.assimp.AIVector3D.Buffer buf, int n, int d) {
		float[] vec = new float[d*n];
		if(d == 3) {
			for(int i = 0; i < n; i++)
			{
				AIVector3D vec3 = buf.get(i);
				vec[d * i] = vec3.x();
				vec[d * i + 1] = vec3.y();
				vec[d * i + 2] = vec3.z();
			}
		}
		else if(d == 2){
			for(int i = 0; i < n; i++)
			{
				AIVector3D vec3 = buf.get(i);
				vec[d * i] = vec3.x();
				vec[d * i + 1] = -vec3.y();//tex的y坐标为负，可能要修改
			}
		}
		loadDataf(p, vec, d);
	}

	public void loadAIMesh(AIMesh mesh) {
		int n = mesh.mNumVertices();
		loadByBuffer(0,mesh.mVertices(),n,3);
		if(mesh.mTextureCoords(0)!=null)
			loadByBuffer(1,mesh.mTextureCoords(0),n,2);
		if(mesh.mNormals()!=null)
			loadByBuffer(2,mesh.mNormals(),n,3);
		if(mesh.mTangents()!=null)
			loadByBuffer(3,mesh.mTangents(),n,3);
		
		
		int[] ind = new int[mesh.mNumFaces() * 3];
		int faceCount = mesh.mNumFaces();

		for (int i = 0; i < faceCount; ++i) {
			IntBuffer face = mesh.mFaces().get(i).mIndices();
			ind[3 * i] = face.get(0);
			ind[3 * i + 1] = face.get(1);
			ind[3 * i + 2] = face.get(2);

		}
		loadIndex(ind, GL_STATIC_DRAW);
	}

	public void loadIndex(IntBuffer index) {
		bind();
		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, index, GL_STATIC_DRAW);
		count = index.limit();
		unbind();
	}

	public void loadIndex(int[] index, int usage) {
		bind();
		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, index, usage);
		count = index.length;
		unbind();
	}

	public void loadDatad(int location, double[] data, int size, int usage) {
		// 创建VBO并加载数据
		closeLocation(location);
		vbo[location] = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo[location]);
		glBufferData(GL_ARRAY_BUFFER, data, usage);
		// 将VBO数据关联到VAO
		bind();
		glEnableVertexAttribArray(location);
		glVertexAttribPointer(location, size, GL_DOUBLE, false, 0, 0);
		unbind();
	}

	public void loadDataf(int location, float[] data, int size) {
		// 创建VBO并加载数据
		closeLocation(location);
		vbo[location] = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo[location]);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		// 将VBO数据关联到VAO
		bind();
		glEnableVertexAttribArray(location);
		glVertexAttribPointer(location, size, GL_FLOAT, false, 0, 0);
		unbind();
	}

	public void loadDatai(int location, int[] data, int size, int usage) {
		// 创建VBO并加载数据
		closeLocation(location);
		vbo[location] = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo[location]);
		glBufferData(GL_ARRAY_BUFFER, data, usage);
		// 将VBO数据关联到VAO
		bind();
		glEnableVertexAttribArray(location);
		glVertexAttribPointer(location, size, GL_INT, false, 0, 0);
		unbind();
	}

	public void bind() {
		glBindVertexArray(vaoId);
	}

	public void unbind() {
		glBindVertexArray(0);
	}

	private void closeLocation(int location) {
		if (vbo[location] == 0)
			return;
		glDeleteBuffers(vbo[location]);
		vbo[location] = 0;
	}

	public void clear() {
		if (vaoId == 0)
			return;
		for (int i = 0; i < vbo.length; i++)
			closeLocation(i);
		glDeleteVertexArrays(vaoId);
		vaoId = 0;
	}
	
	public int getMatIndex() {
		return matId;
	}

	private int vaoId;
	private int count;
	private int ebo;
	private int[] vbo = new int[1 << 4];
	private int matId;
	
}
