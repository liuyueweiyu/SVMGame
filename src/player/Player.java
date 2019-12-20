package player;

import player.bag.Bag;
import props.goods.Goods;

public class Player {
	
	private String Name;
	private String ID;

	private Bag bag = new Bag(20);
	private int money = 100;
	private static Player instance;
    private  Player(){}
    public static  Player getPlayer(){
        if(instance==null){
            instance=new  Player();
        }
        return instance;
    }
    
    public String BuyGood(int money,Goods good) {
		if (money > this.money) {
			return "���㣬����ʧ��";
		}
		String str = bag.AddGoods(good);
		if (str != "") {
			return str;
		}
    	return "����ɹ�";
	}
    
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Bag getBag() {
		return bag;
	}
	public void setBag(Bag bag) {
		this.bag = bag;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
}
