package inf_pp.td.model;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.NoGoldException;
import inf_pp.td.TimeSource;
import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.intercom.TowerType;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;


public class Game implements java.io.Serializable{
	
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
	/**
	 * the game's "tick"-method, call once each tick to
	 * let the model do all its stuff and notify the view about changes
	 * @param time a TimeSource-Object to get the current time from
	 */
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


	/**
	 * @return the play area used in this game
	 */
	public PlayAreaWayHolder getPlayArea() {
		return field;
	}

	/**
	 * @return get a list of all towers in this game
	 */
	public HashSet<BaseTower> getTowers() {
		return towers;
	}
	/**
	 * @return get a list of all towers in this game
	 */
	public HashSet<BaseCreep> getCreeps() {
		return creeps;
	}
	
	/**
	 * @param type which tower to build, single target, aoe...
	 * @param position where to build the tower
	 * @throws ArrayIndexOutOfBoundsException if the tower is to be placed outside the playing field
	 */
	public void buildTower(TowerType type, Point position) {
		int price=PriceProvider.getTowerPrice(type);
		if(price>gold) {
			throw new NoGoldException();
		}
		if(position.x<0||position.y<0||position.x>=field.getWidth()||position.y>=field.getHeight())
			throw new InvalidFieldException();
		for (BaseTower t: towers){
			if(t.getPosition().equals(position))
				throw new InvalidFieldException();
		}
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


	public HashSet<BaseProjectile> getProjectiles() {
		return projectiles;
	}
	
	public int getLives(){
		return lives;
	}
	
	void takeLife(int num){
		lives-=num;
	}
	void takeLife(){
		takeLife(1);
	}
	
	public void upgradeTower(UpgradeType type, Point position){
		BaseTower t=null;
		for (BaseTower ti: towers){
			if(ti.getPosition().equals(position)){
				t=ti;
				break;
			}
		}
		if(t==null)
			throw new InvalidFieldException();
		int price=PriceProvider.getUpgradePrice(t,type);
		if(price>gold)
			throw new NoGoldException();
		gold-=price;
		t.upgrade(type);
	}
	
	public void sellTower(Point position){
		BaseTower t=null;
		for (BaseTower ti: towers){
			if(ti.getPosition().equals(position)){
				t=ti;
				break;
			}
		}
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

	public int getGold() {
		return gold;
	}
	
	public boolean hasWon() {
		return !spawner.hasMoreCreeps() && creeps.size()==0 && !hasLost();
	}
	
	public boolean hasLost() {
		return lives<=0;
	}
	public int getCurrentWaveIndex() {
		return spawner.getCurrentWaveIndex();
	}
	public String getCurrentWaveName() {
		return spawner.getCurrentWaveName();
	}
	public int getWaveCount() {
		return spawner.getWaveCount();
	}
}
