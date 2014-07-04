package inf_pp.td.model;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.NoGoldException;
import inf_pp.td.TimeSource;
import inf_pp.td.intercom.CreepInterface;
import inf_pp.td.intercom.GameInterface;
import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;


public class Game implements java.io.Serializable, GameInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4531113116980902426L;

	/**
	 * the playing area with dimensions and all teh waypoints 
	 */
	private PlayArea field;
	
	//TODO: put in PlayArea?
	//TODO: change to map<>?
	/**
	 * a list of all towers in the game
	 */
	private HashSet<BaseTower> towers=new HashSet<BaseTower>();
	
	/**
	 * a list of all projectiles in the game
	 */
	private HashSet<BaseProjectile> projectiles=new HashSet<BaseProjectile>();
	
	/**
	 * a list of all creeps in the game
	 */
	private HashSet<BaseCreep> creeps=new HashSet<BaseCreep>();
	
	/**
	 * if the game is running
	 */
	
	//TODO: add parameter?
	private CreepWaveSpawner spawner = new CreepWaveSpawner();
	
	/**
	 * the amount of gold that the player currently has
	 */
	private int gold=300; //TODO: Just for debug
	/**
	 * the remaining lives the player has
	 */
	private int lives;
	
	public Game(int lives) {
		this.lives=lives;
		field=new PlayArea(10,10);
		spawner.setWaypoints(field.getWaypoints());
	}
	
	
	//private long lastGold=System.currentTimeMillis();
	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#tick(inf_pp.td.TimeSource)
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
		//this.setChanged();
		//this.notifyObservers(this);
	}


	/* (non-Javadoc)
	 * @see inf_pp.td.model.GameInterface#getPlayArea()
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
	public HashSet<? extends CreepInterface> getCreeps() {
		return creeps;
	}
	
	HashSet<BaseCreep> getBaseCreeps() {
		return creeps;
	}
	
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
		if(position.x<0||position.y<0||position.x>=field.getWidth()||position.y>=field.getHeight())
			throw new InvalidFieldException();
		if(getTowerAtPosition(position)!=null)
			throw new InvalidFieldException();
		for (Point p : field.getWaypoints()){
			if(p.equals(position))
				throw new InvalidFieldException();
		}
		gold-=price;
		towers.add(TowerFactory.buildTower(type, new Point(position)));
	}
	
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
	
	void takeLife(int num){
		lives-=num;
	}
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

	
	private long lastIncome=0;
	private void addBasicIncome(TimeSource time) {
		if(lastIncome+5000<time.getMillisSinceStart()){
			lastIncome=time.getMillisSinceStart();
			float now=lastIncome/30000; //Half-Minutes since Start
			gold+=(int)(Math.pow(1.5f,now));
			//TODO: tweak this formula
		}
	}
	
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
}
