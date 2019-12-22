package scene;

import camera.Camera;

public interface Action {
	public void Open();
	public void Close();
	public default Camera camera() {
		return null;
	}
}
