package inf_pp.td.view;

import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PlayArea extends JPanel {
	
	/**
	 * all 'cells' in the playing-grid
	 */
	private JPanel[][] fields;
	
	/**
	 * the dimensions of the playing grid, how many rows and columns
	 */
	private int width,height;

	private static final long serialVersionUID = -6607180777510972878L;

	PlayArea(PlayAreaWayHolder pa) {
		//inf_pp.td.model.PlayArea pa=new inf_pp.td.model.PlayArea();
		width=pa.getWidth();
		height=pa.getHeight();
		
		fields=new JPanel[height][width];
		
		this.setLayout(new GridLayout(height,width));
		for(int y=0;y<height;++y){
			for(int x=0;x<width;++x){
				fields[y][x] = new JPanel();
				this.add(fields[y][x]);
			}
		}
		paintWay(pa.getWaypoints());
	}
	
	private void paintWay(ArrayList<Point> waypoints) {
		for (Point wp : waypoints)
			fields[wp.y][wp.x].setBackground(new Color(0xFFFFFF));
	}
	
	/**
	 * refresh the view. has to be called if anything in the model changes
	 * @param game the Model to get all data from
	 */
	void updateState(Game game){
		//fields[(int)(Math.random()*height)][(int)(Math.random()*width)].setBackground(new Color((int)(Math.random()*255)));
		for(int y=0;y<height;++y) {
			for(int x=0;x<width;++x) {
				fields[y][x].setBackground(new Color(0));
			}
		}
		paintWay(game.getPlayArea().getWaypoints());
		for(BaseTower t:game.getTowers()){
			Point p=t.getPosition();
			fields[p.y][p.x].setBackground(new Color(0xFF0000));;
		}
	}
}
