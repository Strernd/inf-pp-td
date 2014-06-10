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
		moveSpeed=0.001f;
		this.waypoints=waypoints;
		this.nextWp=1;
		Point tP=waypoints.get(0);
		this.position=new Point2D.Float(tP.x,tP.y);
		System.out.println(position);
	}
	
	/**
	 * moves the creep by its speed
	 */
	public boolean move(long deltaT) {
		Point2D.Float next=new Point2D.Float(waypoints.get(nextWp).x,waypoints.get(nextWp).y);
		//double dist=next.distance(position);
		double dist=Math.sqrt((next.x-position.x)*(next.x-position.x)+(next.y-position.y)*(next.y-position.y));
		System.out.println(next);
		System.out.println(position);
		System.out.println(dist);
		//return;
		if(dist<=moveSpeed*deltaT){
			position.x=waypoints.get(nextWp).x;
			position.y=waypoints.get(nextWp).y;
			++nextWp;
			if(nextWp>=waypoints.size())
				return true;
				//System.out.println("Angekommen!!");
		}
		else{
			//TODO: move to util class
			next.x-=position.x;
			next.y-=position.y;
			next.x/=dist;
			next.y/=dist;
			next.x*=moveSpeed*deltaT;
			next.y*=moveSpeed*deltaT;
			position.x+=next.x;
			position.y+=next.y;
		}
		return false;
	}
}
