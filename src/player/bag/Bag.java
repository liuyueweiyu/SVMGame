package player.bag;


import java.util.ArrayList;

import props.goods.Goods;

public class Bag {
	private int maxSize;
	private ArrayList<Goods> goods = new ArrayList<Goods>();
	
	public Bag() {
		// TODO Auto-generated constructor stub
	}

	public Bag(int size) {
		maxSize = size;
	}
	
	public String AddGoods(Goods good) {
		if (GetUsedSize() >= maxSize) {
			return "±³°üÒÑÂú!";
		}
		for (int i = 0,len = goods.size(); i < len; i++) {
			if (goods.get(i).getName() == good.getName()) {
				goods.get(i).Add();
				return "";
			}
		}
		goods.add(good);
		return "";
	}
	
	
	
	public int GetUsedSize() {
		int sum = 0;
		for (int i = 0,len = goods.size(); i < len; i++) {
			sum += goods.get(i).getCount();
		}
		return sum;
	}
	
}
