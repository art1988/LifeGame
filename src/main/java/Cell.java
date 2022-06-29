
public class Cell {
	private boolean state;
	private int neibCount;
	
	Cell() {
		this(false);
	}
	
	Cell(boolean st) {
		this.state = st;
	}
	
	public void turnOn() {
		state = true;
	}
	
	public void turnOff() {
		state = false;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public void setNeibCount(int num) {
		neibCount = num;
	}
	
	public void checkDeathN1() {
		if(neibCount > 3) turnOff();
	}
	
	public void checkDeathN2() {
		if(neibCount < 2) turnOff();
	}
	
	public void checkAlive() {
		if(neibCount == 3) turnOn();
	}
	
}
