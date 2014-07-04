package inf_pp.td;

import inf_pp.td.model.BaseCreep;
import inf_pp.td.model.Buff;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Util {
	private static class PointListWrapper extends AbstractList<Point2D.Float> {
		private List<Point> sourceList;
		
		public PointListWrapper(List<Point> sourceList) {
			this.sourceList=sourceList;
		}

		@Override
		public Point2D.Float get(int index) {
			Point p=sourceList.get(index);
			return new Point2D.Float(p.x,p.y);
		}

		@Override
		public int size() {
			return sourceList.size();
		}

	}

	private Util(){}
	
	static public int move(float distance, Point2D.Float position, List<Point2D.Float> points) {
		int nextWp=0;
		//Point2D.Float next=new Point2D.Float(points.get(nextWp).x,points.get(nextWp).y);
		Point2D.Float next=points.get(nextWp);
		next=new Point2D.Float(next.x,next.y);
		double dist=next.distance(position);
		
		if(nextWp>=points.size())
			return nextWp;
		
		while(dist<=distance){
			position.x=next.x;
			position.y=next.y;
			++nextWp;
			distance-=dist;
			if(nextWp>=points.size())
				return nextWp;
			next.x=points.get(nextWp).x;
			next.y=points.get(nextWp).y;
			next=new Point2D.Float(next.x,next.y);
			dist=next.distance(position);
		}
		
		next.x-=position.x;
		next.y-=position.y;
		next.x/=dist;
		next.y/=dist;
		next.x*=distance;
		next.y*=distance;
		position.x+=next.x;
		position.y+=next.y;
		return nextWp;
	}

	public static int moveI(float distance, Point2D.Float position, List<Point> points) {
		List<Point2D.Float> list=new PointListWrapper(points);
		return move(distance,position,list);
	}
	
	public static BaseCreep nearestCreep(Point2D.Float position, Collection<BaseCreep> creeps){
		return nearestCreep(position,creeps,Float.POSITIVE_INFINITY);
	}
	public static BaseCreep nearestCreep(Point2D.Float position, Collection<BaseCreep> creeps,float maxDistance){
		if(creeps.size()==0)
			return null;
		double minDist=Double.POSITIVE_INFINITY;
		BaseCreep minCreep=null;
		for(BaseCreep c : creeps){
			double dist=c.getPosition().distance(position);
			if(dist<minDist){
				minDist=dist;
				minCreep=c;
			}
		}
		if(minDist>maxDistance)
			return null;
		else
			return minCreep;
	}
	
	public static Point2D.Float pointToFloat(Point p){
		return new Point2D.Float(p.x,p.y);
	}
	
	public static Object getBuffedValue(Object val, Buff.Type type, Map<String,Buff> buffs, TimeSource time) {
		for(Iterator<Entry<String, Buff> > it= buffs.entrySet().iterator();it.hasNext();) {
			Entry<String,Buff> e=it.next();
			if(e.getValue().canRemove()) {
				it.remove();
			}
			else {
				val= e.getValue().apply(val, type, time);
			}
			
		}
		/*for (Buff b : buffs){
			val=b.apply(val,type);
		}*/
		return val;
	}
}
