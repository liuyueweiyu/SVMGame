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
		good.setCount(1);
		goods.add(good);
		return "";
	}
	
	
	public ArrayList<Goods> getGoods() {
		return goods;
	}

	public void setGoods(ArrayList<Goods> goods) {
		this.goods = goods;
	}


	public int GetUsedSize() {
		int sum = 0;
		for (int i = 0,len = goods.size(); i < len; i++) {
			sum += goods.get(i).getCount();
		}
		return sum;
	}
	
	public int useGood(int bagPos) {
		goods.get(bagPos).Use();
		if (goods.get(bagPos).getCount() == 0) {
			goods.remove(bagPos);
			if (goods.size() == 0) {
				bagPos = 0;
			} else {				
				bagPos = ( bagPos - 1) % goods.size();
			}
		}
		
		return bagPos;
	}
	
}
