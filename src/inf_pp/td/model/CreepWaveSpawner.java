package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class handles spawning of creeps
 */
public class CreepWaveSpawner implements java.io.Serializable {
	private static final long serialVersionUID = -4498283748257706525L;
	
	/**
	 * This class holds all attributes of a creep wave
	 */
	private static class CreepWave implements java.io.Serializable {
		private static final long serialVersionUID = -9042045034217633411L;
		
		/**
		 * The health that each creep in this wave has
		 */
		final int health;
		/**
		 * The speed that each creep in this wave moves at (in fields/ms)
		 */
		final float moveSpeed;
		/**
		 * The amount of gold that the player gets when he/she kills a creep from this wave
		 */
		final int gold;
		/**
		 * The class to instantiate when constructing a creep from this wave
		 * The class needs to provide the a constructor with the same contract as {@link BaseCreep#BaseCreep(ArrayList, int, float, int, String)}
		 */
		final Class<? extends BaseCreep> creepClass;
		/**
		 * The type to assign to each creep in this wave, used to determine the creep's tile
		 */
		final String type;
		/**
		 * How many creeps should be spawned in this wave
		 */
		final int count;
		/**
		 * How much time shall pass between each spawned creep
		 */
		final int distanceMs;
		/**
		 * How this wave is called
		 */
		final String waveName;
		/**
		 * How long to wait before spawning the first creep, can be negative
		 */
		final int waitBefore;
		
		/**
		 * Construct a Creep Wave
		 * @param health each creeps health
		 * @param moveSpeed each creeps move speed
		 * @param gold each creeps gold reward
		 * @param creepClass the class to instantiate when spawning a creep from this wave
		 * @param type the type to assign to each creep
		 * @param count how many creeps are in this wave
		 * @param distanceMs how many time shall pass until spawning the next creep
		 * @param waveName how this wave is called
		 * @param waitBefore how long to wait before spawning the first creep, can be negative
		 */
		public CreepWave(int health, float moveSpeed, int gold, Class<? extends BaseCreep> creepClass,
							String type, int count, int distanceMs, String waveName, int waitBefore) {
			super();
			this.health = health;
			this.moveSpeed = moveSpeed;
			this.gold = gold;
			this.type = type;
			this.count = count;
			this.distanceMs = distanceMs;
			this.creepClass=creepClass;
			this.waveName=waveName;
			this.waitBefore=waitBefore;
		}
	}
	
	/**
	 * The waypoints each creep follows
	 */
	private ArrayList<Point> waypoints;
	/**
	 * A list of all waves
	 */
	private ArrayList<CreepWave> waves=new ArrayList<CreepWave>();
	/**
	 * the current wave
	 */
	private int waveIndex=0;
	/**
	 * How many creeps of this wave have already been spawned
	 */
	private int cNum=0;
	/**
	 * When did we spawn the last creep
	 */
	private long lastCreep=0;
	
	/**
	 * Constructs a CreepWaveSpawner
	 * Initializes the waves
	 */
	public CreepWaveSpawner() {
		waves.add(new CreepWave(15,0.001f,10,BaseCreep.class,"smiley",10,2000,"Smileys (I)",1000)); 
		waves.add(new CreepWave(20,0.001f,10,BaseCreep.class,"smiley",15,1500,"Smileys (II)",5000)); 
		waves.add(new CreepWave(15,0.0013f,7,BaseCreep.class,"poring",30,750,"Porings (I)",5000));
		waves.add(new CreepWave(15,0.0013f,8,BaseCreep.class,"poring",10,150,"Poring Crowds (I)",3000));
		waves.add(new CreepWave(15,0.0013f,8,BaseCreep.class,"poring",10,150,"Poring Crowds (II)",3000));
		waves.add(new CreepWave(15,0.0013f,8,BaseCreep.class,"poring",10,150,"Poring Crowds (III)",5000));
		waves.add(new CreepWave(20,0.0013f,10,BaseCreep.class,"poring",30,750,"Porings (II)",5000));
		waves.add(new CreepWave(80,0.0007f,23,BaseCreep.class,"zombie",15,1500,"Zombies (I)",5000));
		waves.add(new CreepWave(90,0.0007f,27,BaseCreep.class,"zombie",15,1500,"Zombies (II)",5000));
		waves.add(new CreepWave(110,0.001f,30,BaseCreep.class,"skeleton",15,1500,"Skeletons (I)",5000));
		waves.add(new CreepWave(125,0.001f,33,BaseCreep.class,"skeleton",15,1500,"Skeletons (II)",5000));
		waves.add(new CreepWave(450,0.0005f,110,BaseCreep.class,"stonegiant",5,1500,"Stone Giants (I)",25000));
		waves.add(new CreepWave(500,0.0005f,120,BaseCreep.class,"stonegiant",5,1500,"Stone Giants (II)",25000));
		waves.add(new CreepWave(250,0.0008f,65,BaseCreep.class,"reaper",10,1500,"Reapers (I)",5000));
		waves.add(new CreepWave(260,0.0008f,75,BaseCreep.class,"reaper",10,1500,"Reapers (II)",5000));
		waves.add(new CreepWave(2500,0.0008f,1000,BaseCreep.class,"reaper",1,1500,"Reaper (final)",5000));
		lastCreep+=waves.get(waveIndex).waitBefore;
	}
	
	/**
	 * Sets the waypoints the spawned creeps will follow
	 * @param waypoints the waypoints to pass to spawned creeps
	 */
	public void setWaypoints(ArrayList<Point> waypoints) {
		this.waypoints=waypoints;
	}
	

	/**
	 * Spawn creeps
	 * @param time the current time
	 * @return a collection of just spawned creeps
	 */
	public Collection<BaseCreep> spawnCreeps(TimeSource time){
		LinkedList<BaseCreep> result=new LinkedList<BaseCreep>();
		if(waves==null||waveIndex>=waves.size())
			return result;
		CreepWave w=waves.get(waveIndex);
		if(lastCreep+w.distanceMs<time.getMillisSinceStart()){
			++cNum;
			BaseCreep c=null;
			try {
				c=w.creepClass.getDeclaredConstructor(ArrayList.class,int.class,float.class,int.class,String.class).newInstance(waypoints,w.health,w.moveSpeed,w.gold,w.type);
				result.add(c);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
			lastCreep=time.getMillisSinceStart();
		}
		if(cNum>=w.count) {
			++waveIndex;
			cNum=0;
			if(waveIndex<waves.size())
				lastCreep+=waves.get(waveIndex).waitBefore;
		}
		return result;
	}
	
	/**
	 * @return the current wave's index
	 */
	public int getCurrentWaveIndex() {
		return waveIndex;
	}
	/**
	 * @return the current wave's name
	 */
	public String getCurrentWaveName() {
		if(waveIndex<waves.size())
			return waves.get(waveIndex).waveName;
		else
			return "";
	}
	/**
	 * @return the number of waves this CreepWaveSpawner has
	 */
	public int getWaveCount() {
		return waves.size();
	}
	/**
	 * @return true if this spawner has more creeps to spawn, false otherwise
	 */
	public boolean hasMoreCreeps() {
		return waveIndex<waves.size();
	}
}
