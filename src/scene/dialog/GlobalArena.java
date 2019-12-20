package scene.dialog;

public class GlobalArena {
	private static Arena currentArena;

	public static Arena getCurrentArena() {
		return currentArena;
	}

	public static void setCurrentArena(Arena currentArena) {
		GlobalArena.currentArena = currentArena;
	}
	
}
