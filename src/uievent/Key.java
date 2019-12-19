package uievent;

public class Key {
	private final int id;
    private final UIEventType type;

    public Key(int id,  UIEventType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return type == key.type && id == key.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        return result;
    }
}
