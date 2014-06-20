package inf_pp.td.model;

import inf_pp.td.Util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BaseCreep {
	private Point2D.Float position;
	private ArrayList<Point> waypoints;
	private int nextWp;
	private int health=100;
	private int maxHealth=100;
	private float moveSpeed;
	
	BaseCreep(ArrayList<Point> waypoints, int maxHealth, float moveSpeed){
		if(waypoints==null)
			throw new NullPointerException();
		else if(waypoints.size()<2)
			throw new ArrayIndexOutOfBoundsException();
		this.maxHealth=maxHealth;
		this.moveSpeed=moveSpeed;
		this.waypoints=waypoints;
		this.nextWp=1;
		Point tP=waypoints.get(0);
		this.position=new Point2D.Float(tP.x,tP.y);
	}
	
	/**
	 * moves the creep by its speed
	 */
	public void move(long deltaT, Game game) {
		nextWp+=Util.moveI(moveSpeed*deltaT,position,waypoints.subList(nextWp, waypoints.size()));
		if(nextWp>=waypoints.size()){
			game.takeLife();
			kill();
		}
		//return nextWp>=waypoints.size();
	}
	
	public Point2D.Float getPosition(){
		return position;
	}

	public void doDamage(int dmg) {
		this.health-=dmg;		
	}
	
	public boolean isDead(){
		return this.health<=0;
	}
	public float getHealthPercentage(){
		return (float)(health)/(float)maxHealth;
	}
	protected void kill(){
		health=0;
	}
}
