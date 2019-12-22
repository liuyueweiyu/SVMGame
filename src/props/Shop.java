package props;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import player.Player;
import props.goods.Goods;
import props.goods.UseGood;

public class Shop {
	
	private static Shop instance;
    public static  Shop getShop(){
        if(instance==null){
            instance=new  Shop();
        }
        return instance;
    }
	
	private Map<String, Goods> commodity = new HashMap<String, Goods>();
	public Shop() {
		// TODO Auto-generated constructor stub
	}
	public void init() {
		commodity.put("good-1",new Goods("物品-1", -1, new UseGood() {
			@Override
			public String use() {
				// TODO Auto-generated method stub
				System.out.println("use:good-1");
				return null;
			}
		}));
		commodity.put("good-2",new Goods("物品-2", -1, new UseGood() {
			@Override
			public String use() {
				// TODO Auto-generated method stub
				System.out.println("use:good-2");
				return null;
			}
		}));
	}
	
	public Goods getGoods(String name) {
		return commodity.get(name);
	}
}
