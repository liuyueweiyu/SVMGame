package scene.map;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import player.Player;
import props.Shop;
import scene.dialog.Arena;
import scene.dialog.Dialog;
import scene.dialog.DialogCallback;
import scene.dialog.GlobalArena;



public class CallbackHander {
	private Map<String, DialogCallback> callbackMap = new HashMap<String, DialogCallback>();
	
	private static CallbackHander instance;
	private  CallbackHander(){}
    public static  CallbackHander getInstance(){
        if(instance==null){
            instance=new  CallbackHander();
        }
        return instance;
    }
    
	public void registerCallBack() {
		callbackMap.put("SetPalyerProps", new DialogCallback() {
			@Override
			public void run(String jsonStr) {
				// TODO Auto-generated method stub
				System.out.println(jsonStr);
			}
		});
		callbackMap.put("BuyGoods", new DialogCallback() {
			@Override
			public void run(String jsonStr) {
				// TODO Auto-generated method stub
				JSONObject  object = JSON.parseObject(jsonStr);
				System.out.println(Player.getPlayer().getMoney());
				String res = Player.getPlayer().BuyGood(object.getIntValue("money"), Shop.getShop().getGoods(object.getString("name")));
				GlobalArena.getCurrentArena().addDialogsNow(new Dialog("商店老板","shopman",res));
				
			}
		});
		callbackMap.put("DoSomeTreatment", new DialogCallback() {
			@Override
			public void run(String jsonStr) {
				// TODO Auto-generated method stub
				System.out.println("do some treatment");
			}
		});
		
	}
	
	public void triggerCallBack(String id,String jsonStr) {
		System.out.println("调用callback`" + id + "`,参数为:" + jsonStr );
		if (callbackMap.get(id) !=  null) {
			callbackMap.get(id).run(jsonStr);
		}
	}
}
