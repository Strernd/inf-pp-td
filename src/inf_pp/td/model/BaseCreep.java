package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.Util;
import inf_pp.td.model.Buff.Type;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseCreep implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -672727195568844754L;
	private Point2D.Float position;
	private ArrayList<Point> waypoints;
	private int nextWp;
	private int health=100;
	private int maxHealth=100;
	private float baseMoveSpeed;
	private int gold=1;
	private final String type;
	private HashMap<String,Buff> buffs=new HashMap<String,Buff>(); 
	
	BaseCreep(ArrayList<Point> waypoints, int maxHealth, float baseMoveSpeed, int goldWorth, String type){
		if(waypoints==null)
			throw new NullPointerException();
		else if(waypoints.size()<2)
			throw new ArrayIndexOutOfBoundsException();
		this.maxHealth=maxHealth;
		this.health=maxHealth;
		this.baseMoveSpeed=baseMoveSpeed;
		this.waypoints=waypoints;
		this.nextWp=1;
		Point tP=waypoints.get(0);
		this.position=new Point2D.Float(tP.x,tP.y);
		this.gold=goldWorth;
		this.type=type;
	}
	
	/**
	 * Retrieves the moving speed of a creep
	 * 
	 * @return Moving Speed
	 */
	public float getMoveSpeed() {
		//TODO: set parameter time to some valid variable
		return (float) Util.getBuffedValue(baseMoveSpeed,Type.MOVE_SPEED,buffs,null);
	}
	

	/**
	 * moves the creep by its speed
	 * 
	 * @param time
	 * @param game Instance of the Game
	 */
	public void move(TimeSource time, Game game) {
		//this.health=(int)Util.getBuffedValue(this.health, Buff.Type.DOT, buffs, time);
		Util.getBuffedValue(this,Buff.Type.DOT,buffs,time);
		nextWp+=Util.moveI(getMoveSpeed()*time.getMillisSinceLastTick(),position,waypoints.subList(nextWp, waypoints.size()));
		if(nextWp>=waypoints.size()){
			game.takeLife();
			kill();
		}
		//return nextWp>=waypoints.size();
	}
	
	/**
	 * Returns the current position of a creep
	 * 
	 * @return position
	 */
	public Point2D.Float getPosition(){
		return position;
	}

	/**
	 * Substract damage from creeps health
	 * 
	 * @param dmg Amount Damage dealt
	 */
	public void doDamage(int dmg, Game game) {
		this.health-=dmg;
		if(isDead()){ //Did we just kill it?
			game.addGold(gold);
		}
	}
	
	/**
	 * Returns if the creep is dead or not
	 * 
	 * @return is the creep dead?
	 */
	public boolean isDead(){
		return this.health<=0;
	}
	
	/**
	 * Returns the health in absolute value
	 * 
	 * @return Health in Factor of maximum health
	 */
	public float getHealthPercentage(){
		return  Math.max((float)(health)/(float)maxHealth,0);
	}
	
	/**
	 * Sets the creeps health to zero
	 */
	protected void kill(){
		health=0;
	}
	
	/**
	 * Adds buff to the creep 
	 * 
	 * @param id
	 * @param b
	 */
	public void addBuff(String id,Buff b){
		buffs.put(id,b);
	}
	
	/**
	 * Returns whether the creep has a certain buff or not
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasBuff(String id){
		return buffs.containsKey(id);
	}
	
	public String getType() {
		return type;
	}
}
