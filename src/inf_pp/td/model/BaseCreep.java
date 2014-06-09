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
	
	BaseCreep(ArrayList<Point> waypoints){
		if(waypoints==null)
			throw new NullPointerException();
		else if(waypoints.size()<2)
			throw new ArrayIndexOutOfBoundsException();
		moveSpeed=1.0f;
		this.waypoints=waypoints;
		this.nextWp=1;
		Point tP=waypoints.get(0);
		this.position=new Point2D.Float(tP.x,tP.y);
	}
	
	/**
	 * moves the creep by its speed
	 */
	public void move(){
	}
}
