package inf_pp.td.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BaseCreep {
	private Point2D.Float position;
	private ArrayList<Point> waypoints;
	private int nextWp;
	private int health;
	private float moveSpeed;
	
	/**
	 * moves the creep by its speed
	 */
	public void move(){
	}
}
