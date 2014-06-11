package inf_pp.td;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.AbstractList;
import java.util.List;

public final class Util {
	private static class PointListWrapper extends AbstractList<Point2D.Float> {
		private List<Point> sourceList;
		
		public PointListWrapper(List<Point> sourceList) {
			this.sourceList=sourceList;
		}

		@Override
		public Float get(int index) {
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
}
