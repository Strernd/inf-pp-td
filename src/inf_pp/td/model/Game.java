package inf_pp.td.model;

import inf_pp.td.intercom.PlayAreaWayHolder;

import java.awt.Point;
import java.util.LinkedList;

public class Game extends java.util.Observable{
	
	/**
	 * the playing area with dimensions and all teh waypoints 
	 */
	private PlayArea field;
	
	//TODO: put in PlayArea?
	//TODO: change to map<>?
	/**
	 * a list of all towers in the game
	 */
	private LinkedList<BaseTower> towers;
	
	/**
	 * a list of all projectiles in the game
	 */
	private LinkedList<BaseProjectile> projectiles;
	
	/**
	 * a list of all creeps in the game
	 */
	private LinkedList<BaseCreep> creeps;
	
	/**
	 * if the game is running
	 */
	private boolean running;
	
	//TODO: add parameter?
	private CreepWaveSpawner spawner = new CreepWaveSpawner();
	
	/**
	 * the amount of gold that the player currently has
	 */
	private int gold;
	/**
	 * the remaining lives the player has
	 */
	private int lives;
	
	public Game() {
		
		field=new PlayArea(10,10);
		towers=new LinkedList<BaseTower>();
		spawner.setWaypoints(field.getWaypoints());
	}
	
	
	/**
	 * the game's "tick"-method, call once each tick to
	 * let the model do all its stuff and notify the view about changes
	 * @param time the time in ms that have passed since the beginning of the game
	 */
	public void tick(long time){
		//TODO: implement this shit
		//spawner.spawn
		//creeps.addAll(spawner.spawnCreeps(time));
		this.setChanged();
		this.notifyObservers();
	}


	/**
	 * @return the play area used in this game
	 */
	public PlayAreaWayHolder getPlayArea() {
		// TODO Auto-generated method stub
		return field;
	}

	/**
	 * @return get a list of all towers in this game
	 */
	public LinkedList<BaseTower> getTowers() {
		return towers;
	}
	
	/**
	 * @param type which tower to build, single target, aoe...
	 * @param position where to build the tower
	 * @throws ArrayIndexOutOfBoundsException if the tower is to be placed outside the playing field
	 */
	public void buildTower(TowerFactory.TowerType type, Point position) throws ArrayIndexOutOfBoundsException{
		if(position.x<0||position.y<0||position.x>=field.getWidth()||position.y>=field.getHeight())
			throw new ArrayIndexOutOfBoundsException();
		//TODO: prevent from building multiple towers on same position
		towers.add(TowerFactory.buildTower(type, new Point(position)));
	}
	
}
