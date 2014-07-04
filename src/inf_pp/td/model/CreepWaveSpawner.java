package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class CreepWaveSpawner implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4498283748257706525L;
	private static class CreepWave implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9042045034217633411L;
		final int health;
		final float moveSpeed;
		final int gold;
		final Class<? extends BaseCreep> creepClass;
		final String type;
		final int count;
		final int distanceMs;
		final String waveName;
		final int waitBefore;
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
	
	private ArrayList<Point> waypoints;
	private ArrayList<CreepWave> waves=new ArrayList<CreepWave>();
	private int waveIndex=0;
	private int cNum=0;
	private long lastCreep;
	
	public CreepWaveSpawner() {
		waves.add(new CreepWave(15,0.001f,10,BaseCreep.class,"smiley",10,2000,"Smileys (I)",1000)); 
		waves.add(new CreepWave(20,0.001f,10,BaseCreep.class,"smiley",15,1500,"Smileys (II)",5000)); 
		waves.add(new CreepWave(15,0.0013f,7,BaseCreep.class,"poring",30,750,"Porings (I)",5000));
		waves.add(new CreepWave(15,0.0013f,8,BaseCreep.class,"poring",10,150,"Poring Crowds (I)",3000));
		waves.add(new CreepWave(15,0.0013f,8,BaseCreep.class,"poring",10,150,"Poring Crowds (I)",3000));
		waves.add(new CreepWave(15,0.0013f,8,BaseCreep.class,"poring",10,150,"Poring Crowds (I)",5000));
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
	
	public void setWaypoints(ArrayList<Point> waypoints) {
		this.waypoints=waypoints;
	}
	

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
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(c);
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
	
	public int getCurrentWaveIndex() {
		return waveIndex;
	}
	public String getCurrentWaveName() {
		if(waveIndex<waves.size())
			return waves.get(waveIndex).waveName;
		else
			return "";
	}
	public int getWaveCount() {
		return waves.size();
	}
	public boolean hasMoreCreeps() {
		return waveIndex<waves.size();
	}
}
