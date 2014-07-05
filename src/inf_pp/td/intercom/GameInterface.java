package inf_pp.td.intercom;

import inf_pp.td.TimeSource;

import java.awt.Point;
import java.util.HashSet;

public interface GameInterface {

	//private long lastGold=System.currentTimeMillis();
	/**
	 * the game's "tick"-method, call once each tick to
	 * let the model do all its stuff and notify the view about changes
	 * @param time a TimeSource-Object to get the current time from
	 */
	public abstract void tick(TimeSource time);

	/**
	 * @return the play area used in this game
	 */
	public abstract PlayAreaWayHolder getPlayArea();

	/**
	 * @return get a list of all towers in this game
	 */
	public abstract HashSet<? extends TowerInterface> getTowers();

	/**
	 * @return get a list of all towers in this game
	 */
	public abstract HashSet<? extends CreepInterface> getCreeps();

	/**
	 * @param type which tower to build, single target, aoe...
	 * @param position where to build the tower
	 * @throws ArrayIndexOutOfBoundsException if the tower is to be placed outside the playing field
	 */
	public abstract void buildTower(TowerType type, Point position);

	public abstract HashSet<? extends ProjectileInterface> getProjectiles();

	public abstract int getLives();

	public abstract void upgradeTower(UpgradeType type, Point position);

	public abstract void sellTower(Point position);

	public abstract int getGold();

	public abstract boolean hasWon();

	public abstract boolean hasLost();

	public abstract int getCurrentWaveIndex();

	public abstract String getCurrentWaveName();

	public abstract int getWaveCount();

	public abstract int getPrice(TowerType type);

	public abstract int getTowerUpgradePrice(Point position, UpgradeType type);

	public abstract TowerType getTowerType(Point position);
	
	public abstract boolean canBuildHere(Point position);

}