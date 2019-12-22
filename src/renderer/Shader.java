package renderer;

import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.lwjgl.system.MemoryStack;

public class Shader {

	public final int programId;

	private int vertexShaderId;
	
	private int geometryShaderId;
	
	private int fragmentShaderId;

	private final Map<String, Integer> uniforms = new HashMap<String, Integer>();
	public Shader() {
		programId = glCreateProgram();
		assert programId != 0 : "Create shader error";
	}

	public Shader(String vertexSource, String fragmentSource) {
		programId = glCreateProgram();
		assert programId != 0 : "Create shader error";
		createVS(vertexSource);
		createFS(fragmentSource);
		link();
	}
	
	public Shader(String vertexSource,String geometrySource, String fragmentSource) {
		programId = glCreateProgram();
		assert programId != 0 : "Create shader error";
		createVS(vertexSource);
		createGS(geometrySource);
		createFS(fragmentSource);
		link();
	}

	public boolean setUniform(String uniformName, Vector2fc value) {
		bind();
		if(!uniforms.containsKey(uniformName) && !createUniform(uniformName))
			return false;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			glUniform2f(uniforms.get(uniformName), value.x(),value.y());
		}
		return true;
	}

	public boolean setUniform(String uniformName, Matrix4fc value) {
		bind();
		if(!uniforms.containsKey(uniformName) && !createUniform(uniformName))
			return false;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
		}
		return true;
	}
	
	public boolean setUniform(String uniformName, Vector3fc value) {
		bind();
		if(!uniforms.containsKey(uniformName) && !createUniform(uniformName))
			return false;
		glUniform3f(uniforms.get(uniformName), value.x(), value.y(), value.z());
		return true;
	}
	
	public boolean setUniform(String uniformName, int value) {
		bind();
		if(!uniforms.containsKey(uniformName) && !createUniform(uniformName))
			return false;
		glUniform1i(uniforms.get(uniformName), value);
		return true;
	}

	private boolean createUniform(String uniformName) {
		int uniformLocation = glGetUniformLocation(programId, uniformName);
		if(uniformLocation >= 0) {
			uniforms.put(uniformName, uniformLocation);
			return true;
		}else {
			return false;
		}
	}

	public void createVS(String shaderCode) {
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}
	
	public void createGS(String shaderCode) {
		geometryShaderId = createShader(shaderCode, GL_GEOMETRY_SHADER);
	}

	public void createFS(String shaderCode) {
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}

	protected int createShader(String shaderCode, int shaderType) {
		int shaderId = glCreateShader(shaderType);
		assert shaderId != 0 : "Create shader fail" + Integer.toString(shaderType);

		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		assert glGetShaderi(shaderId, GL_COMPILE_STATUS) != 0 : "Compile shader fail:"
				+ glGetShaderInfoLog(shaderId, 1024);

		glAttachShader(programId, shaderId);

		return shaderId;
	}

	public void link() {
		glLinkProgram(programId);
		assert glGetProgrami(programId, GL_LINK_STATUS) != 0 : "link Shader fail:"
				+ glGetProgramInfoLog(programId, 1024);

		if (vertexShaderId != 0) {
			glDetachShader(programId, vertexShaderId);
			glDeleteShader(vertexShaderId);
		}
		if (fragmentShaderId != 0) {
			glDetachShader(programId, fragmentShaderId);
			glDeleteShader(fragmentShaderId);
		}
		
		if (geometryShaderId != 0) {
			glDetachShader(programId, geometryShaderId);
			glDeleteShader(geometryShaderId);
		}

		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
		}
	}

	public void bind() {
		glUseProgram(programId);
		
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void clear() {
		unbind();
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}


	
}
