package event;

public class TickEvent extends Event{
	
	static private double lastTime = 0;
	private double time;
	private double deltTime;
	
	
	
	public TickEvent(double time){
		super();
		this.time = time;
		if(lastTime == 0)
			deltTime = 0;
		else
			deltTime = time - lastTime;
		lastTime = time;
	}

	public double deltTime() {
		return deltTime;
	}
	
	public double getTime() {
		return time;
	}
}
