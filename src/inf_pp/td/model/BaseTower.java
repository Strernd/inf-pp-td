package inf_pp.td.model;

import java.awt.Point;

public class BaseTower {
	private Point position;
	private int cooldown;
	private float range;
	private int fireRate;
	
	
	/**
	 * check if this tower is able to fire at the moment
	 * @return true if tower can fire
	 * @return false if tower can not fire
	 */
	public boolean canFire(){
		return cooldown==0;
	}
	
	/**
	 * fires (a projectile), does damage
	 */
	public void fire(){
		
	}
	
	/**
	 * @return this towers position
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * sets the position of this tower
	 * @param position the new position
	 */
	public void setPosition(Point position) {
		this.position=position;
	}
}
