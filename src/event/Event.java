package event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base Event class that all other events are derived from
 */
public class Event {

	protected boolean isCanceled = false;
	private Result result = Result.DEFAULT;
	public static EventBus eventBus = new EventBus();
	public static ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = new ConcurrentHashMap<Object, ArrayList<IEventListener>>();


	public boolean post() {
		eventBus.post(this);
		return isCanceled();
	}
	
	public static void register(Object target) {
		register(target, Priority.NORMAL);
	}

	public static void register(Object target, Priority priority) {
		if (listeners.containsKey(target))
			return;
		
		if(target instanceof IEventListener) {
			eventBus.register(priority, (IEventListener) target);
			return;
		}
		
		boolean isStatic = target.getClass() == Class.class;
		
		for (Method method : (isStatic ? (Class<?>) target : target.getClass()).getMethods()) {

			if (isStatic != Modifier.isStatic(method.getModifiers()))
				continue;
			if (method.getParameterCount() != 1)
				continue;
			
			Class<?> eventType = method.getParameters()[0].getType();
			if (!Event.class.isAssignableFrom(eventType))
				continue;
			
			IEventListener listener = new IEventListener() {
				@Override
				public void invoke(Event event) {
					try {
						method.invoke((isStatic ? null : target), event);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public Class<?> getType() {
					return eventType;
				}
				
				public void print() {
					System.out.println(method.toString()+target.hashCode());
				}
			};
			eventBus.register(priority, listener);
			ArrayList<IEventListener> others = listeners.computeIfAbsent(target, x -> new ArrayList<>());
			others.add(listener);
		}
	}

	public static void unregister(Object object) {
		ArrayList<IEventListener> list = listeners.remove(object);
		if (list == null)
			return;
		for (IEventListener listener : list) {
			eventBus.unregister(listener);
		}
	}

	public enum Result {
		DENY, DEFAULT, ALLOW
	}

	public enum Priority {
		/*
		 * Priority of event listeners, listeners will be sorted with respect to this
		 * priority level.
		 *
		 * Note: Due to using a ArrayList in the ListenerList, these need to stay in a
		 * contiguous index starting at 0. {Default ordinal}
		 */
		HIGHEST, // First to execute
		HIGH, NORMAL, LOW, LOWEST // Last to execute
	}

	/**
	 * Determine if this function is cancelable at all.
	 * 
	 * @return If access to setCanceled should be allowed
	 *
	 *         Note: Events with the Cancelable annotation will have this method
	 *         automatically added to return true.
	 */
	public boolean isCancelable() {
		return false;
	}

	/**
	 * Determine if this event is canceled and should stop executing.
	 * 
	 * @return The current canceled state
	 */
	public boolean isCanceled() {
		return isCanceled;
	}

	/**
	 * Sets the cancel state of this event. Note, not all events are cancelable, and
	 * any attempt to invoke this method on an event that is not cancelable (as
	 * determined by {@link #isCancelable} will result in an
	 * {@link UnsupportedOperationException}.
	 *
	 * The functionality of setting the canceled state is defined on a per-event
	 * bases.
	 *
	 * @param cancel The new canceled value
	 */
	public void setCanceled(boolean cancel) {
		if (!isCancelable()) {
			throw new UnsupportedOperationException(
					"Attempted to call Event#setCanceled() on a non-cancelable event of type: "
							+ this.getClass().getCanonicalName());
		}
		isCanceled = cancel;
	}

	/**
	 * Determines if this event expects a significant result value.
	 *
	 * Note: Events with the HasResult annotation will have this method
	 * automatically added to return true.
	 */
	public boolean hasResult() {
		return false;
	}

	/**
	 * Returns the value set as the result of this event
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * Sets the result value for this event, not all events can have a result set,
	 * and any attempt to set a result for a event that isn't expecting it will
	 * result in a IllegalArgumentException.
	 *
	 * The functionality of setting the result is defined on a per-event bases.
	 *
	 * @param value The new result
	 */
	public void setResult(Result value) {
		result = value;
	}

}