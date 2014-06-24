package inf_pp.td.model;

import inf_pp.td.Util;
import inf_pp.td.model.Buff.Type;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BaseCreep {
	private Point2D.Float position;
	private ArrayList<Point> waypoints;
	private int nextWp;
	private int health=100;
	private int maxHealth=100;
	private float baseMoveSpeed;
	private HashMap<String,Buff> buffs=new HashMap<String,Buff>(); 
	
	BaseCreep(ArrayList<Point> waypoints, int maxHealth, float baseMoveSpeed){
		if(waypoints==null)
			throw new NullPointerException();
		else if(waypoints.size()<2)
			throw new ArrayIndexOutOfBoundsException();
		this.maxHealth=maxHealth;
		this.baseMoveSpeed=baseMoveSpeed;
		this.waypoints=waypoints;
		this.nextWp=1;
		Point tP=waypoints.get(0);
		this.position=new Point2D.Float(tP.x,tP.y);
	}
	
	public float getMoveSpeed() {
		//TODO: set parameter time to some valid variable
		return (float) Util.getBuffedValue(baseMoveSpeed,Type.MOVE_SPEED,buffs,0);
	}
	
	
	//TODO: improve this
	private long lastTime=0;
	/**
	 * moves the creep by its speed
	 */
	public void move(long time, Game game) {
		this.health=(int)Util.getBuffedValue(this.health, Buff.Type.DOT, buffs,time);
		long deltaT;
		if(lastTime!=0)
			deltaT=time-lastTime;
		else deltaT=0;
		lastTime=time;
		nextWp+=Util.moveI(getMoveSpeed()*deltaT,position,waypoints.subList(nextWp, waypoints.size()));
		System.out.println(position);
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
	public void addBuff(String id,Buff b){
		buffs.put(id,b);
	}
	public boolean hasBuff(String id){
		return buffs.containsKey(id);
	}
}
