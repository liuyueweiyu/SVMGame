package renderer;

import camera.Camera;

public interface Renderable {
	
	public void render(Shader shader, Camera camera);
	
	
	public String getShader();
	
	default public boolean isLableabel() {
		return false;
	}

	default public int getLabel() {
		return this.hashCode();
	}

	default public Priority defaultPriority() {
		return Priority.NORMAL;
	}
	public enum Priority {
		HIGHEST, // First to Render
		HIGH,
		NORMAL,
		LOW, // Last to Render
		LOWEST
	}
}
