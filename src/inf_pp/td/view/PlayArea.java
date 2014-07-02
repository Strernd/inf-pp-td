package inf_pp.td.view;

import inf_pp.td.intercom.PlayAreaWayHolder;
import inf_pp.td.intercom.TdState;
import inf_pp.td.model.AreaTower;
import inf_pp.td.model.BaseCreep;
import inf_pp.td.model.BaseProjectile;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;
import inf_pp.td.model.PoisonTower;
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
	private Point selectedField;
		
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
	void updateState(TdState state){
		//System.out.println("updateState "+Thread.currentThread().getName());
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
		Game game=state.getGame();
		width=game.getPlayArea().getWidth();
		height=game.getPlayArea().getHeight();
		waypoints=game.getPlayArea().getWaypoints();
		towers=game.getTowers();
		creeps=game.getCreeps();
		projectiles=game.getProjectiles();
		selectedField=state.getSelectedField();
		//this.invalidate();
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics gs) {
		//System.out.println("paintComponent" + Thread.currentThread().getName());
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
			switch(t.getType()){
			case DIRECT_DMG:
				gt=Tiles.TileId.TOWER_DD;
				break;
			case AREA_OF_EFFECT:
				gt=Tiles.TileId.TOWER_AE;
				break;
			case SLOW:
				gt=Tiles.TileId.TOWER_SL;
				break;
			case POISON:
				gt=Tiles.TileId.TOWER_P;
				break;
			}
			//TODO: do some fallback/throw exception?
			if(gt==null)
				continue;
			g.drawImage(Tiles.get(gt), t.getPosition().x*tw, t.getPosition().y*th, tw, th, null);
		}
		for(BaseCreep c : creeps){
			//TODO: differentiate
			Tiles.TileId t=null;
			switch(c.getType()) {
			case "smiley":
				t=Tiles.TileId.CREEP_SMILEY;
				break;
			}
			//TODO: fallback/exception?
			if(t==null)
				continue;
			g.drawImage(Tiles.get(t), (int)((c.getPosition().x+.25)*tw), (int)((c.getPosition().y+.25)*th), (int)(.5*tw), (int)(.5*th), null);
			g.setColor(Color.getHSBColor(c.getHealthPercentage()/3,1,1));
			int hbHeight=Math.max((int)(.075*th),1);
			g.fillRect((int)((c.getPosition().x+.25)*tw), (int)((c.getPosition().y+.25)*th)-hbHeight, (int)(.5*c.getHealthPercentage()*tw), hbHeight);
		}
		g.setColor(new Color(0x00FF00));
		for(BaseProjectile p: projectiles){
			g.fillRect((int)((p.getPosition().x+.3333)*tw), (int)((p.getPosition().y+.3333)*th), (int)(.3333*tw), (int)(.3333*th));
		}
		if(selectedField.x>=0&&selectedField.y>=0&&selectedField.x<width&&selectedField.y<height){
			g.drawRect(selectedField.x*tw, selectedField.y*th, tw, th);
		}
		/*float t2=(System.nanoTime()-t1)/(float)100;
		if(t2>1000)
			System.out.println(t2);*/
	}
}
