package inf_pp.td.view;

import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.model.BaseCreep;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JPanel;

public class PlayArea extends JPanel {
	
	/**
	 * all 'cells' in the playing-grid
	 */
	//private JPanel[][] fields;
	
	//TODO: put into interfaces?
	private ArrayList<Point> waypoints;
	private HashSet<BaseTower> towers;
	private HashSet<BaseCreep> creeps;
	
	/**
	 * the dimensions of the playing grid, how many rows and columns
	 */
	private int width,height;

	private static final long serialVersionUID = -6607180777510972878L;

	PlayArea(PlayAreaWayHolder pa) {
		//inf_pp.td.model.PlayArea pa=new inf_pp.td.model.PlayArea();
		width=pa.getWidth();
		height=pa.getHeight();
		
		/*fields=new JPanel[height][width];
		
		this.setLayout(new GridLayout(height,width));
		for(int y=0;y<height;++y){
			for(int x=0;x<width;++x){
				fields[y][x] = new JPanel();
				this.add(fields[y][x]);
			}
		}
		paintWay(pa.getWaypoints());*/
	}
	
	private void paintWay(ArrayList<Point> waypoints) {
		/*for (Point wp : waypoints)
			fields[wp.y][wp.x].setBackground(new Color(0xFFFFFF));*/
	}
	
	/**
	 * refresh the view. has to be called if anything in the model changes
	 * @param game the Model to get all data from
	 */
	void updateState(Game game){
		//TODO: improve painting
		//fields[(int)(Math.random()*height)][(int)(Math.random()*width)].setBackground(new Color((int)(Math.random()*255)));
		/*for(int y=0;y<height;++y) {
			for(int x=0;x<width;++x) {
				fields[y][x].setBackground(new Color(0));
			}
		}
		paintWay(game.getPlayArea().getWaypoints());
		for(BaseTower t:game.getTowers()){
			Point p=t.getPosition();
			fields[p.y][p.x].setBackground(new Color(0xFF0000));
		}*/
		width=game.getPlayArea().getWidth();
		height=game.getPlayArea().getHeight();
		waypoints=game.getPlayArea().getWaypoints();
		towers=game.getTowers();
		creeps=game.getCreeps();
		//this.invalidate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics gs) {
		Graphics2D g=(Graphics2D)gs;
		Rectangle bounds=g.getDeviceConfiguration().getBounds();
		super.paintComponent(gs);
		//g.setColor(new Color(0xFFFF00));
		//g.fillRect(20, 30, 40, 50);
		int tw=(this.getWidth()/width);
		int th=(this.getHeight()/height);
		//System.out.println(tw+ " , "+th);
		//g.scale(tw,th);
		for(Point wp : waypoints){
			g.fillRect(wp.x*tw, wp.y*th, 1*tw, 1*th);
		}
		g.setColor(new Color(0xFF0000));
		for(BaseTower t: towers){
			g.fillRect(t.getPosition().x*tw, t.getPosition().y*th, tw, th);
		}
		for(BaseCreep c : creeps){
			g.fillRect((int)((c.getPosition().x+.25)*tw), (int)((c.getPosition().y+.25)*th), (int)(.5*tw), (int)(.5*th));
		}
	}
}
