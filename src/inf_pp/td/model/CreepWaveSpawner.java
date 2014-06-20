package inf_pp.td.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class CreepWaveSpawner {
	private ArrayList<Point> waypoints;
	
	public void setWaypoints(ArrayList<Point> waypoints) {
		this.waypoints=waypoints;
	}
	
	private int cNum=0;
	private long lastCreep=-1;
	public Collection<BaseCreep> spawnCreeps(long time){
		LinkedList<BaseCreep> result=new LinkedList<BaseCreep>();
		//TODO: differentiate...
		if(cNum<=50 && time>lastCreep+1000){
			BaseCreep c=new BaseCreep(waypoints,100,0.001f);
			result.add(c);
			cNum++;
			lastCreep=time;
		}
		return result;
	}
}
