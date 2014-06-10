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
	public Collection<BaseCreep> spawnCreeps(long time){
		LinkedList<BaseCreep> result=new LinkedList<BaseCreep>();
		//TODO: differentiate...
		if(cNum==0){
			BaseCreep c=new BaseCreep(waypoints);
			result.add(c);
			cNum++;
		}
		return result;
	}
}
