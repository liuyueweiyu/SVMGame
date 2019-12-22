package props.goods;


public class Goods {
	private String name;
	private int id;	
	private int count = 0;
	private UseGood use;
	public Goods(String name,int count,UseGood use) {
		// TODO Auto-generated constructor stub
		id = this.hashCode();
		this.name = name;
		this.use = use;
		this.count = count;
	}
	
	public void Add() {
		count = count + 1;
	}
	public String Use() {
		count --;
		return this.use.use();
	
	}
	public String Buy() {
		if(this.count <= 0) {
			return "数量不足,购买失败!";
		}
		this.count--;
		return "购买成功!";
	}
	
	
	
	
	// getter and setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getID() {
		return id;
	}
	public void setID(int iD) {
		id = iD;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
