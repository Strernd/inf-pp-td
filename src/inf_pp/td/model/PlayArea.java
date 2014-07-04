package inf_pp.td.model;

import inf_pp.td.intercom.PlayAreaWayHolder;

import java.awt.Point;
import java.util.ArrayList;

public class PlayArea implements PlayAreaWayHolder, java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 385283719813143223L;

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
		waypoints.add(new Point(0,1));
		waypoints.add(new Point(1,1));
		waypoints.add(new Point(2,1));
		waypoints.add(new Point(3,1));
		waypoints.add(new Point(4,1));
		waypoints.add(new Point(5,1));
		waypoints.add(new Point(6,1));
		waypoints.add(new Point(7,1));
		waypoints.add(new Point(8,1));
		waypoints.add(new Point(8,2));
		waypoints.add(new Point(8,3));
		waypoints.add(new Point(7,3));
		waypoints.add(new Point(6,3));
		waypoints.add(new Point(5,3));
		waypoints.add(new Point(4,3));
		waypoints.add(new Point(3,3));
		waypoints.add(new Point(2,3));
		waypoints.add(new Point(1,3));
		waypoints.add(new Point(1,4));
		waypoints.add(new Point(1,5));
		waypoints.add(new Point(2,5));
		waypoints.add(new Point(3,5));
		waypoints.add(new Point(4,5));
		waypoints.add(new Point(5,5));
		waypoints.add(new Point(6,5));
		waypoints.add(new Point(7,5));
		waypoints.add(new Point(8,5));
		waypoints.add(new Point(8,6));
		waypoints.add(new Point(8,7));
		waypoints.add(new Point(7,7));
		waypoints.add(new Point(6,7));
		waypoints.add(new Point(5,7));
		waypoints.add(new Point(4,7));
		waypoints.add(new Point(3,7));
		waypoints.add(new Point(2,7));
		waypoints.add(new Point(1,7));
		waypoints.add(new Point(1,8));
		waypoints.add(new Point(1,9));
		
/*		waypoints.add(new Point(2,0));
		waypoints.add(new Point(2,1));
		waypoints.add(new Point(2,2));
		waypoints.add(new Point(3,2));
		waypoints.add(new Point(4,2));
		waypoints.add(new Point(5,2));
		waypoints.add(new Point(6,2));
		waypoints.add(new Point(7,2));
		waypoints.add(new Point(7,3));
		waypoints.add(new Point(7,4));
		waypoints.add(new Point(7,5));
		waypoints.add(new Point(7,6));
		waypoints.add(new Point(7,7));
		waypoints.add(new Point(7,8));
		waypoints.add(new Point(7,9));*/
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
