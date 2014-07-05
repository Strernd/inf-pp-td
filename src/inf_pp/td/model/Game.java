package inf_pp.td.model;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.NoGoldException;
import inf_pp.td.TimeSource;
import inf_pp.td.intercom.GameInterface;
import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;


public class Game implements java.io.Serializable, GameInterface{
	private static final long serialVersionUID = 4531113116980902426L;

	/**
	 * the playing area with dimensions and all the waypoints 
	 */
	private PlayArea field;
	
	/**
	 * a set of all towers in the game
	 */
	private HashSet<BaseTower> towers=new HashSet<BaseTower>();
	
	/**
	 * a set of all projectiles in the game
	 */
	private HashSet<BaseProjectile> projectiles=new HashSet<BaseProjectile>();
	
	/**
	 * a set of all creeps in the game
	 */
	private HashSet<BaseCreep> creeps=new HashSet<BaseCreep>();

	/**
	 * A CreepWaveSpawner to spawn our creeps
	 */
	private CreepWaveSpawner spawner = new CreepWaveSpawner();
	
	/**
	 * the amount of gold that the player currently has
	 */
	private int gold=300;
	
	/**
	 * the remaining lives the player has
	 */
	private int lives;
	
	/**
	 * Construct a Game
	 * @param lives the amount of lives the player starts with
	 */
	public Game(int lives) {
		this.lives=lives;
		field=new PlayArea();
		spawner.setWaypoints(field.getWaypoints());
	}
	
	
	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.GameInterface#tick(inf_pp.td.TimeSource)
	 */
	@Override
	public void tick(TimeSource time){
		addBasicIncome(time);
		
		for(Iterator<BaseCreep> it=creeps.iterator();it.hasNext();){
			BaseCreep c=it.next();
			c.move(time,this);
			if(c.isDead()){
				it.remove();
			}
		}
		for(BaseTower t: towers){
			t.fire(this,time);
		}
		for(Iterator<BaseProjectile> it=projectiles.iterator();it.hasNext();){
			if(it.next().move(time, this)){
				it.remove();
			}
		}
		creeps.addAll(spawner.spawnCreeps(time));
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.GameInterface#getPlayArea()
	 */
	@Override
	public PlayAreaWayHolder getPlayArea() {
		return field;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getTowers()
	 */
	@Override
	public HashSet<BaseTower> getTowers() {
		return towers;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getCreeps()
	 */
	@Override
	public HashSet<BaseCreep> getCreeps() {
		return creeps;
	}
	
	/**
	 * Gets a Tower at a specific position
	 * @param pos the position to get the tower at
	 * @return the tower at the position
	 */
	private BaseTower getTowerAtPosition(Point pos) {
		for (BaseTower t: towers){
			if(t.getPosition().equals(pos))
				return t;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#buildTower(inf_pp.td.intercom.TowerType, java.awt.Point)
	 */
	@Override
	public void buildTower(TowerType type, Point position) {
		int price=PriceProvider.getTowerPrice(type);
		if(price>gold) {
			throw new NoGoldException();
		}
		if(!canBuildHere(position))
			throw new InvalidFieldException();
		gold-=price;
		towers.add(TowerFactory.buildTower(type, new Point(position)));
	}
	
	/**
	 * Adds a projectile to the game
	 * @param p the projectile to add
	 */
	void addProjectile(BaseProjectile p){
		projectiles.add(p);
	}


	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getProjectiles()
	 */
	@Override
	public HashSet<BaseProjectile> getProjectiles() {
		return projectiles;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getLives()
	 */
	@Override
	public int getLives(){
		return lives;
	}
	
	/**
	 * Take any number of lives from the player
	 * @param num the number of lives to take
	 */
	void takeLife(int num){
		lives-=num;
	}
	
	/**
	 * Take one life from the player
	 */
	void takeLife(){
		takeLife(1);
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#upgradeTower(inf_pp.td.intercom.UpgradeType, java.awt.Point)
	 */
	@Override
	public void upgradeTower(UpgradeType type, Point position){
		BaseTower t=getTowerAtPosition(position);
		if(t==null)
			throw new InvalidFieldException();
		int price=PriceProvider.getUpgradePrice(t,type);
		if(price>gold)
			throw new NoGoldException();
		gold-=price;
		t.upgrade(type);
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#sellTower(java.awt.Point)
	 */
	@Override
	public void sellTower(Point position){
		BaseTower t=getTowerAtPosition(position);
		if(t==null)
			throw new InvalidFieldException();
		TowerType type = t.getType();
		//TODO: Better Sell price
		int price=PriceProvider.getTowerPrice(type);
		gold += price/2;
		t.remove(this);
	}

	
	/**
	 * The last time we added basic income
	 */
	private long lastIncome=0;
	/**
	 * Add some basic income to the players funds
	 * Call once each tick
	 * @param time the current time
	 */
	private void addBasicIncome(TimeSource time) {
		if(lastIncome+5000<time.getMillisSinceStart()){
			lastIncome=time.getMillisSinceStart();
			float now=lastIncome/30000; //Half-Minutes since Start
			gold+=(int)(Math.pow(1.5f,now));
			//TODO: tweak this formula
		}
	}
	
	/**
	 * Add gold to the players funds
	 * @param amount the amount to add
	 */
	void addGold(int amount) {
		gold+=amount;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getGold()
	 */
	@Override
	public int getGold() {
		return gold;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#hasWon()
	 */
	@Override
	public boolean hasWon() {
		return !spawner.hasMoreCreeps() && creeps.size()==0 && !hasLost();
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#hasLost()
	 */
	@Override
	public boolean hasLost() {
		return lives<=0;
	}
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getCurrentWaveIndex()
	 */
	@Override
	public int getCurrentWaveIndex() {
		return spawner.getCurrentWaveIndex();
	}
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getCurrentWaveName()
	 */
	@Override
	public String getCurrentWaveName() {
		return spawner.getCurrentWaveName();
	}
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getWaveCount()
	 */
	@Override
	public int getWaveCount() {
		return spawner.getWaveCount();
	}


	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getPrice(inf_pp.td.intercom.TowerType)
	 */
	@Override
	public int getPrice(TowerType type) {
		return PriceProvider.getTowerPrice(type);
	}


	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getTowerUpgradePrice(java.awt.Point, inf_pp.td.intercom.UpgradeType)
	 */
	@Override
	public int getTowerUpgradePrice(Point position, UpgradeType type) {
		BaseTower t=getTowerAtPosition(position);
		if(t==null)
			throw new InvalidFieldException();
		return PriceProvider.getUpgradePrice(t, type);
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.GameInterface#getTowerType(java.awt.Point)
	 */
	@Override
	public TowerType getTowerType(Point position) {
		BaseTower t=getTowerAtPosition(position);
		if(t==null)
			throw new InvalidFieldException();
		return t.getType();
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.GameInterface#canBuildHere(java.awt.Point)
	 */
	@Override
	public boolean canBuildHere(Point position) {
		if(position.x<0||position.y<0||position.x>=field.getWidth()||position.y>=field.getHeight())
			return false;
		if(getTowerAtPosition(position)!=null)
			return false;
		for (Point p : field.getWaypoints()){
			if(p.equals(position))
				return false;
		}
		return true;
	}
}
