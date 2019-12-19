package event;

public interface IEventListener {

	public void invoke(Event event);
	
	public Class<?> getType();
	
	public default void print() {
		System.out.println(getType());
	}
}
