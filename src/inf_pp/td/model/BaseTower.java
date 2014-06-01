package inf_pp.td.model;

import java.awt.Point;

public class BaseTower {
	private Point position;
	private int cooldown;
	private float range;
	private int fireRate;
	
	
	public boolean canFire(){
		return cooldown==0;
	}
	
	public void fire(){
		
	}
}
