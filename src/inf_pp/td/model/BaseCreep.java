package inf_pp.td.model;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.CreepInterface;
import inf_pp.td.model.Buff.Type;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents any creep
 */
public class BaseCreep implements java.io.Serializable, CreepInterface {
	private static final long serialVersionUID = -672727195568844754L;
	
	/**
	 * This creep's position
	 */
	private Point2D.Float position;
	/**
	 * The waypoints that this creep follows
	 */
	private ArrayList<Point> waypoints;
	/**
	 * the index of the waypoint that this creep is heading
	 */
	private int nextWp;
	/**
	 * This creep's current health
	 */
	private int health;
	/**
	 * This creep's maximum health
	 */
	private int maxHealth;
	/**
	 * this creep's unmodified move speed
	 */
	private float baseMoveSpeed;
	/**
	 * the amount of gold the player is given if he/she kills this creep
	 */
	private int gold;
	/**
	 * this creep's type; used to select a tile to draw this creep
	 */
	private final String type;
	/**
	 * all (de-)buffs that are applied to this creep
	 */
	private HashMap<String,Buff> buffs=new HashMap<String,Buff>(); 
	
	/**
	 * Construct a creep
	 * @param waypoints the waypoints that this creep will follow
	 * @param maxHealth the maximum/starting health this creep will have
	 * @param baseMoveSpeed the speed that this creep moves at if is not (de-)buffed, in fields/ms
	 * @param goldWorth the amount of gold this creep gives the player when he/she kills it
	 * @param type the type of the creep, ie. the appearance
	 */
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
	 * Gets the current move speed, all de/buffs are applied
	 * @param time the current time
	 * @return the move speed in fields/ms
	 */
	protected float getMoveSpeed(TimeSource time) {
		return (float) Util.getBuffedValue(baseMoveSpeed,Type.MOVE_SPEED,buffs,time);
	}
	

	/**
	 * moves the creep by its speed.
	 * if the creeps reaches the last waypoint, it will despawn and take one of the player's lives
	 * @param time
	 * @param game Instance of the Game
	 */
	void move(TimeSource time, Game game) {
		Util.getBuffedValue(this,Buff.Type.DOT,buffs,time);
		nextWp+=Util.moveI(getMoveSpeed(time)*time.getMillisSinceLastTick(),position,waypoints.subList(nextWp, waypoints.size()));
		if(nextWp>=waypoints.size()){
			game.takeLife();
			kill();
		}
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.CreepInterface#getPosition()
	 */
	@Override
	public Point2D.Float getPosition(){
		return position;
	}

	/**
	 * Substract damage from creeps health
	 * Gives money to the player if dead
	 * 
	 * @param dmg Amount of damage dealt
	 */
	void doDamage(int dmg, Game game) {
		this.health-=dmg;
		if(isDead()){
			game.addGold(gold);
		}
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.CreepInterface#isDead()
	 */
	@Override
	public boolean isDead(){
		return this.health<=0;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.CreepInterface#getHealthPercentage()
	 */
	@Override
	public float getHealthPercentage(){
		return  Math.max((float)(health)/(float)maxHealth,0);
	}
	
	/**
	 * Sets the creeps health to zero, does not give player any money
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
	void addBuff(String id,Buff b){
		buffs.put(id,b);
	}
	
	/**
	 * Returns whether the creep has a certain buff or not
	 * 
	 * @param id
	 * @return
	 */
	boolean hasBuff(String id){
		return buffs.containsKey(id);
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.CreepInterface#getType()
	 */
	@Override
	public String getType() {
		return type;
	}
}
