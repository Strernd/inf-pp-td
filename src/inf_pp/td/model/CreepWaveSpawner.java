package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CreepWaveSpawner implements java.io.Serializable {
	private static class CreepWave implements java.io.Serializable {
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
		waves.add(new CreepWave(10,0.0008f,100,BaseCreep.class,"smiley",10,2000,"Smileys (I)",1000));
		waves.add(new CreepWave(30,0.001f,100,BaseCreep.class,"smiley",15,1500,"Smileys (II)",5000));
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
			if(waveIndex<waves.size())
				lastCreep+=waves.get(waveIndex).waitBefore;
		}
		return result;
	}
	
	public int getCurrentWaveIndex() {
		return waveIndex;
	}
	public String getCurrentWaveName() {
		return waves.get(waveIndex).waveName;
	}
	public int getWaveCount() {
		return waves.size();
	}
	public boolean hasMoreCreeps() {
		return waveIndex<waves.size();
	}
}
