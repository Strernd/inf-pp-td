package inf_pp.td.intercom;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Interface to hold the dimensions and waypoints of a PlayArea
 */
public interface PlayAreaWayHolder {
	/**
	 * @return a list of all waypoints in this PlayArea
	 */
	public ArrayList<Point> getWaypoints();

	/**
	 * @return this PlayArea's width
	 */
	public int getWidth();

	/**
	 * @return this PlayArea's height
	 */
	public int getHeight();
}
