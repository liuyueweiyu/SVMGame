package scene.map;

import java.util.HashMap;
import java.util.Map;

import scene.dialog.DialogCallback;

public class CallbackHander {
	private Map<String, DialogCallback> callbackMap = new HashMap<String, DialogCallback>();
	public void registerCallBack() {
		callbackMap.put("1", new DialogCallback() {
			@Override
			public void run(String jsonStr) {
				// TODO Auto-generated method stub
				System.out.println(jsonStr);
			}
		});
	}
}
