package inf_pp.td.model;

import java.awt.Point;

public class BaseTower {
	protected Point position;
	protected long cooldown;
	protected float range;
	protected int fireRate;
	
	
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
	public int doFire(Game game){
		//System.out.println(range);
		return 0;
	}
	
	public void fire(Game game,long time){
		if(time>cooldown){
			cooldown=time+doFire(game);
		}
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
