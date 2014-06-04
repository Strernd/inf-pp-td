package inf_pp.td.model;

import java.awt.Point;
import java.util.ArrayList;

public class PlayArea {
	private int width;
	private int height;
	private ArrayList<Point> waypoints;
	
	public PlayArea() {
		width=7;
		height=10;
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
