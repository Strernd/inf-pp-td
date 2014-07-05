package inf_pp.td.intercom;

import inf_pp.td.TimeSource;

import java.awt.Point;
import java.util.HashSet;

/**
 * The interface a model has to implement, the view and controller can use these methods
 */
public interface GameInterface {

	/**
	 * the game's "tick"-method, call once each tick to
	 * let the model do all its stuff
	 * @param time a TimeSource-Object to get the current time from
	 */
	public abstract void tick(TimeSource time);

	/**
	 * @return the play area used in this game
	 */
	public abstract PlayAreaWayHolder getPlayArea();

	/**
	 * Get a set of all towers in this game
	 * @return a {@link HashSet} of all towers
	 */
	public abstract HashSet<? extends TowerInterface> getTowers();

	/**
	 * Get a set of all towers in this game
	 * @return a {@link HashSet} of all creeps
	 */
	public abstract HashSet<? extends CreepInterface> getCreeps();

	/**
	 * Builds a tower
	 * @param type which tower to build, single target, aoe...
	 * @param position where to build the tower
	 */
	public abstract void buildTower(TowerType type, Point position);

	/**
	 * Get a set of all projectiles in the game
	 * @return a {@link HashSet} of all projectiles
	 */
	public abstract HashSet<? extends ProjectileInterface> getProjectiles();

	/**
	 * @return the number of lives the player has left
	 */
	public abstract int getLives();

	/**
	 * Upgrades a tower
	 * @param type the Upgrade Type (damage,range,firerate,...)
	 * @param position the position of the tower to be upgraded
	 */
	public abstract void upgradeTower(UpgradeType type, Point position);

	/**
	 * Sells a tower, adds gold to the players funds.
	 * @param position the position of the tower to be sold
	 */
	public abstract void sellTower(Point position);

	/**
	 * @return the players amount of gold
	 */
	public abstract int getGold();

	/**
	 * @return true if the game has been won (ie. no more creeps), false otherwise
	 */
	public abstract boolean hasWon();

	/**
	 * @return true if the game has been lost (ie. no more lives), false otherwise
	 */
	public abstract boolean hasLost();

	/**
	 * @return the current wave's index
	 */
	public abstract int getCurrentWaveIndex();

	/**
	 * @return the current wave's name
	 */
	public abstract String getCurrentWaveName();

	/**
	 * @return the number of waves
	 */
	public abstract int getWaveCount();

	/**
	 * Get a tower's price
	 * @param type the type of the tower to get the price of
	 * @return the price of the tower
	 */
	public abstract int getPrice(TowerType type);

	/**
	 * Get the price to upgrade a tower's attribute
	 * @param position The position of the selected tower
	 * @param type The attribute to upgrade
	 * @return the price of the upgrade
	 */
	public abstract int getTowerUpgradePrice(Point position, UpgradeType type);

	/**
	 * Get the type of a tower
	 * @param position The position of the tower to get the type of
	 * @return the type of the tower
	 */
	public abstract TowerType getTowerType(Point position);
	
	/**
	 * Check if a field can be build on
	 * @param position The position to check
	 * @return true if the position is buildable, false otherwise
	 */
	public abstract boolean canBuildHere(Point position);

}