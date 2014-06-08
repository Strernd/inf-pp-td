package inf_pp.td.model;

import inf_pp.td.intercom.PlayAreaWayHolder;

import java.awt.Point;
import java.util.ArrayList;

public class PlayArea implements PlayAreaWayHolder{
	
	/**
	 * the number of columns this playing field has
	 */
	private int width;
	
	/**
	 * the number of rows this playing field has
	 */
	private int height;
	
	/**
	 * a list of all waypoints that creeps will walk
	 */
	private ArrayList<Point> waypoints;
	
	public PlayArea(int width, int height) {
		this.width=width;
		this.height=height;
		
		//TODO: remove tehse, load from somewhere (c)
		waypoints=new ArrayList<Point>();
		waypoints.add(new Point(2,0));
		waypoints.add(new Point(2,1));
		waypoints.add(new Point(2,2));
		waypoints.add(new Point(3,2));
		waypoints.add(new Point(4,2));
		waypoints.add(new Point(5,2));
		waypoints.add(new Point(6,2));
		waypoints.add(new Point(7,2));
	}

	public ArrayList<Point> getWaypoints() {
		return waypoints;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
