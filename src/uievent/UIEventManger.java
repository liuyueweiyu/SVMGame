package uievent;


import java.util.HashMap;
import java.util.Map;

import event.ClickEvent;
import event.Event;
import event.HoverEvent;
import event.KeyEvent;
import event.MouseEnterEvent;
import manager.InputManager;
import mode.GlobalMode;
import mode.ModeManger;


public class UIEventManger {
	private static UIEventManger instance;
    private  UIEventManger(){}
    public static  UIEventManger getInstance(){
        if(instance==null){
            instance=new  UIEventManger();
            Event.register(instance);
        }
        return instance;
    }
    
 
    // all events
    private static Map<Key,UIEventFunction> events = new HashMap<Key,UIEventFunction>();
    

    public void OnClick(ClickEvent event) {
    	if (event.getAction() == KeyEvent.RELEASE) {
    		UIEventObj obj = new UIEventObj(event.getLabel());
        	dispatch(UIEventType.UIOnClick, obj);
		}
	}
        
    
    public void OnKey(KeyEvent	event) {
    	UIEventObj obj = new UIEventObj(ModeManger.getCurrentModeInt());
    	obj.setKeyValue(event.getKey());
    	dispatch(UIEventType.UIOnKey, obj);
	}
    
    public void OnHover(HoverEvent event) {
//    	UIEventObj obj = new UIEventObj(event.getLabel());
//    	dispatch(UIEventType.UIOnHover, obj);
    }
    
    public void OnMouseEnter(MouseEnterEvent event) {
    	
	}
    
    public void addEventListenner(int id, UIEventType type,UIEventFunction iEventFunction) {
		// TODO Auto-generated method stub
		events.put(new Key(id, type), iEventFunction);
	}
    
    public void triggerEvent(int id, UIEventType type,UIEventObj uiEventObj) {
		UIEventFunction uiEventFunction = events.get(new Key(id, type));
		if (uiEventFunction != null) {
			uiEventFunction.run(uiEventObj);
		}
	}
    
    
    private void dispatch(UIEventType type, UIEventObj obj) {
    	UIEventFunction function = events.get(new Key(obj.getTarget(),type));
    	if(function != null) {
    		function.run(obj);   	
    	}
	}
}
