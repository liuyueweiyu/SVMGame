package mode;

public class ModeManger {
	static private GlobalMode currentMode = GlobalMode.GlobalStartMode;
	public static GlobalMode getCurrentMode() {
		return currentMode;
	}
	
	public static int getCurrentModeInt() {
		return currentMode.hashCode();
	}
	
	public static void setCurrentMode(GlobalMode currentMode) {
		ModeManger.currentMode = currentMode;
	}
	
}
