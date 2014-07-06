package inf_pp.td.model;

import inf_pp.td.TimeSource;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class for the TD Game model
 * Functions that are used all over the code are placed here
 */
public final class Util {
	/**
	 * We have lists of Points with integer coordinates and lists of Points with float coordinates
	 * This class takes an integer-point-list and converts it to a float-point-list without allocating lots of new objects at once
	 */
	private static class PointListWrapper extends AbstractList<Point2D.Float> {
		/**
		 * The list with integer-points
		 */
		private List<Point> sourceList;
		
		/**
		 * Construct a Int/Float Pointlist Wrapper
		 * @param sourceList the list with integer-points
		 */
		public PointListWrapper(List<Point> sourceList) {
			this.sourceList=sourceList;
		}

		/* (non-Javadoc)
		 * @see java.util.AbstractList#get(int)
		 */
		@Override
		public Point2D.Float get(int index) {
			Point p=sourceList.get(index);
			return new Point2D.Float(p.x,p.y);
		}

		/* (non-Javadoc)
		 * @see java.util.AbstractCollection#size()
		 */
		@Override
		public int size() {
			return sourceList.size();
		}

	}

	/**
	 * Do not instantiate Util
	 */
	private Util(){}
	
	/**
	 * Move a thing along a list of Float-waypoints
	 * @param distance the distance to travel
	 * @param position the thing's current position. Will be modified to the new position.
	 * @param points the list to move along
	 * @return the number of waypoints that the thing has passed
	 */
	static public int move(float distance, Point2D.Float position, List<Point2D.Float> points) {
		int nextWp=0;
		Point2D.Float next=points.get(nextWp);
		//create a new instance to not mess with any waypoints coordinates
		next=new Point2D.Float(next.x,next.y);
		double dist=next.distance(position);
		
		if(nextWp>=points.size())
			return nextWp;
		
		while(dist<=distance){
			//as long as we can travel far enough to reach the next waypoint, set the position to the next waypoint's
			position.x=next.x;
			position.y=next.y;
			//advance the waypoint
			++nextWp;
			//subtract the distance so we can go on with the rest distance
			distance-=dist;
			//we're done if there are no more waypoints
			if(nextWp>=points.size())
				return nextWp;
			//set the new waypoint coordinates
			next.x=points.get(nextWp).x;
			next.y=points.get(nextWp).y;
			//and get the new distance to the new next waypoint
			dist=next.distance(position);
		}
		
		//we can't travel to the next waypoint, we'll need some vector arithmetics
		//we get the vector current_position->next_position
		next.x-=position.x;
		next.y-=position.y;
		//make it length 1
		next.x/=dist;
		next.y/=dist;
		//make it as long as we can travel
		next.x*=distance;
		next.y*=distance;
		//and add the current_position again
		position.x+=next.x;
		position.y+=next.y;
		return nextWp;
	}
	
	/**
	 * Move a thing along a list of Integer-waypoints
	 * @param distance the distance to travel
	 * @param position the thing's current position. Will be modified to the new position.
	 * @param points the list to move along
	 * @return the number of waypoints that the thing has passed
	 */
	public static int moveI(float distance, Point2D.Float position, List<Point> points) {
		//we use our PointListWrapper instead of creating a whole bunch of new objects
		List<Point2D.Float> list=new PointListWrapper(points);
		return move(distance,position,list);
	}
	
	/**
	 * Get the nearest Creep to a position
	 * @param position the reference position
	 * @param creeps a collection of creeps
	 * @return the creep that is nearest to position
	 */
	public static BaseCreep nearestCreep(Point2D.Float position, Collection<BaseCreep> creeps){
		return nearestCreep(position,creeps,Float.POSITIVE_INFINITY);
	}
	/**
	 * Get the nearest Creep to a position
	 * @param position the reference position
	 * @param creeps a collection of creeps
	 * @param maxDistance the maximum distance to return a creep
	 * @return the creep that is nearest to position if the distance is at most maxDistance, null otherwise
	 */
	public static BaseCreep nearestCreep(Point2D.Float position, Collection<BaseCreep> creeps, float maxDistance){
		if(creeps.size()==0)
			return null;
		double minDist=Double.POSITIVE_INFINITY;
		BaseCreep minCreep=null;
		//loop through all creeps
		for(BaseCreep c : creeps){
			double dist=c.getPosition().distance(position);
			//if this one is nearer to our desired position, use this one
			if(dist<minDist){
				minDist=dist;
				minCreep=c;
			}
		}
		//return null if still too far away, quite convenient
		if(minDist>maxDistance)
			return null;
		else
			return minCreep;
	}
	
	
	/**
	 * Convenience function to convert a integer-type-point to a float-type-point
	 * @param p the point (as Point)
	 * @return the point (as Point2D.Float)
	 */
	public static Point2D.Float pointToFloat(Point p){
		return new Point2D.Float(p.x,p.y);
	}
	
	/**
	 * Apply all buffs in a map to a value by daisy-chaining them
	 * @param val the initial value
	 * @param type the buff type, will be passed to each buff
	 * @param buffs a map of buffs to apply
	 * @param time the current time
	 * @return the buffed value
	 */
	public static Object getBuffedValue(Object val, Buff.Type type, Map<String,Buff> buffs, TimeSource time) {
		//loop through all buffs
		for(Iterator<Entry<String, Buff> > it= buffs.entrySet().iterator();it.hasNext();) {
			Entry<String,Buff> e=it.next();
			//check if buff still applicable, remove if not
			if(e.getValue().canRemove(time)) {
				it.remove();
			}
			else {
				//get the buffed value
				val= e.getValue().apply(val, type, time);
			}
			
		}
		return val;
	}
}
