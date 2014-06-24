package inf_pp.td.view;

import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.model.AreaTower;
import inf_pp.td.model.BaseCreep;
import inf_pp.td.model.BaseProjectile;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;
import inf_pp.td.model.ProjectileTower;
import inf_pp.td.model.SlowTower;
import inf_pp.td.view.Tiles.TileId;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;
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
	private HashSet<BaseProjectile> projectiles;
		
	/**
	 * the dimensions of the playing grid, how many rows and columns
	 */
	private int width,height;

	private static final long serialVersionUID = -6607180777510972878L;

	PlayArea(PlayAreaWayHolder pa) {
		width=pa.getWidth();
		height=pa.getHeight();
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
		projectiles=game.getProjectiles();
		//this.invalidate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics gs) {
		//long t1=System.nanoTime();
		Graphics2D g=(Graphics2D)gs;
		super.paintComponent(gs);
		//check if we have valid data
		if(waypoints==null)
			return;

		//g.setColor(new Color(0xFFFF00));
		//g.fillRect(20, 30, 40, 50);
		int tw=(this.getWidth()/width);
		int th=(this.getHeight()/height);
		//System.out.println(tw+ " , "+th);
		//g.scale(tw,th);
		Image bg=Tiles.get(TileId.WORLD);
		if(bg!=null) {
			g.drawImage(bg,0,0,tw*width,th*height,null);
		}
		else {
			for(Point wp : waypoints){
				g.fillRect(wp.x*tw, wp.y*th, 1*tw, 1*th);
			}
		}
		g.setColor(new Color(0xFF0000));
		for(BaseTower t: towers){
			Tiles.TileId gt=null;
			//TODO: improve this, this is ugly/impossible to separate from model
			if(t instanceof ProjectileTower)
				gt=Tiles.TileId.TOWER_DD;
			else if(t instanceof AreaTower)
				gt=Tiles.TileId.TOWER_AE;
			else if(t instanceof SlowTower)
				gt=Tiles.TileId.TOWER_SL;
			//TODO: do some fallback
			if(gt==null)
				continue;
			g.drawImage(Tiles.get(gt), t.getPosition().x*tw, t.getPosition().y*th, tw, th, null);
		}
		for(BaseCreep c : creeps){
			g.setColor(Color.getHSBColor(c.getHealthPercentage()/3,1,1));
			g.fillRect((int)((c.getPosition().x+.25)*tw), (int)((c.getPosition().y+.25)*th), (int)(.5*tw), (int)(.5*th));
		}
		g.setColor(new Color(0x00FF00));
		for(BaseProjectile p: projectiles){
			g.fillRect((int)((p.getPosition().x+.3333)*tw), (int)((p.getPosition().y+.3333)*th), (int)(.3333*tw), (int)(.3333*th));
		}
		/*float t2=(System.nanoTime()-t1)/(float)100;
		if(t2>1000)
			System.out.println(t2);*/
	}
}
