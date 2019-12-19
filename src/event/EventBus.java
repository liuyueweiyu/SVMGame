package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus {

	private final Class<?> root;
	private List<Map<Class<?>, SubList>> lists;

	public EventBus() {
		this.root = Event.class;

		int cnt = Event.Priority.values().length;
		lists = new ArrayList<Map<Class<?>, SubList>>(cnt);

		for (int i = 0; i < cnt; i++) {
			Map<Class<?>, SubList> list = new ConcurrentHashMap<Class<?>, SubList>();
			list.put(root, new SubList());
			lists.add(list);
		}

	}
	
	public void print() {
		for (Map<Class<?>, SubList> list : lists)
			list.get(root).print(0);
	}

	public void post(Event event) {
		for (Map<Class<?>, SubList> list : lists) {
			getList(list, event.getClass()).forEach(listener -> {
				listener.invoke(event);
			});
		}
	}

	public void register(Event.Priority priority, IEventListener listener) {
		assert root.isAssignableFrom(listener.getType()) : "传入类型非Event子类 " + listener.getType().toGenericString();
		getList(lists.get(priority.ordinal()), listener.getType()).add(listener);
	}

	public void unregister(IEventListener listener) {
		for (Map<Class<?>, SubList> list : lists)
			getList(list, listener.getType()).remove(listener);
	}

	private SubList getList(Map<Class<?>, SubList> list, Class<?> EventType) {
		if (!list.containsKey(EventType))
			list.put(EventType, new SubList(getList(list, EventType.getSuperclass())));
		return list.get(EventType);
	}

	private class SubList {
		private List<IEventListener> listeners;
		private SubList parent;
		private List<SubList> children;

		public void print(int dep) {
			for (int i = 0; i <= dep; i++)
				System.out.printf("——");
			System.out.println();
			for (IEventListener listener : listeners)
				listener.print();
			if (children != null)
				for (SubList ch : children)
					ch.print(dep + 1);
		}

		private SubList() {
			listeners = new ArrayList<IEventListener>();
		}

		private SubList(SubList parent) {
			this();
			this.parent = parent;
			this.parent.addChild(this);
		}

		public void forEach(Consumer<IEventListener> action) {
			SubList list = this;
			while (list != null) {
				list.listeners.forEach(action);
				list = list.parent;
			}
		}

		private void addChild(SubList child) {
			if (this.children == null)
				this.children = new ArrayList<>();
			this.children.add(child);
		}

		public void add(IEventListener listener) {
			listeners.add(listener);
		}

		public void remove(IEventListener listener) {
			listeners.remove(listener);
		}
	}
}
