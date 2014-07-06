package inf_pp.td.view;

import inf_pp.td.Tiles;
import inf_pp.td.Tiles.TileId;
import inf_pp.td.intercom.CreepInterface;
import inf_pp.td.intercom.GameInterface;
import inf_pp.td.intercom.ProjectileInterface;
import inf_pp.td.intercom.TdState;
import inf_pp.td.intercom.TowerInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

/**
 * The view class to display the PlayArea with the way, towers, creeps, projectiles...
 */
public class PlayAreaView extends JPanel {
	private static final long serialVersionUID = -6607180777510972878L;
	
	/**
	 * Locking object to concurrently access {@link #currentFrame}
	 */
	private final Object frameLock=new Object();
	/**
	 * The frame to draw
	 * Only to be modified/read under lock of {@link #frameLock}.
	 * We update the reference with a new object instead of reusing the old one as the dimensions could have changed
	 */
	private BufferedImage currentFrame;

	/**
	 * Construct a PlayAreaView
	 */
	PlayAreaView() {
	}
	
	/**
	 * re-renders the current frame. has to be called if anything in the model changes
	 * The new frame is scheduled for repaint.
	 * @param state A TdState object to get the model's current state from
	 */
	void updateState(TdState state){
		GameInterface game=state.getGame();
		int width,height;
		ArrayList<Point> waypoints;
		HashSet<? extends TowerInterface> towers;
		HashSet<? extends CreepInterface> creeps;
		HashSet<? extends ProjectileInterface> projectiles;
		Point selectedField;
		
		BufferedImage nextFrame;
		
		//get all data from the model
		width=game.getPlayArea().getWidth();
		height=game.getPlayArea().getHeight();
		waypoints=game.getPlayArea().getWaypoints();
		towers=game.getTowers();
		creeps=game.getCreeps();
		projectiles=game.getProjectiles();
		selectedField=state.getSelectedField();
	
	
		//Paint to a local Image, to avoid holding the Frame Lock for several microseconds
		nextFrame=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g=nextFrame.createGraphics();
		//check if we have valid data
		if(waypoints==null)
			return;

		//the dimensions of a tile
		int tw=(this.getWidth()/width);
		int th=(this.getHeight()/height);

		//draw the background, if available
		Image bg=Tiles.get(TileId.WORLD);
		if(bg!=null) {
			g.drawImage(bg,0,0,tw*width,th*height,null);
		}
		else {
			for(Point wp : waypoints){
				g.fillRect(wp.x*tw, wp.y*th, 1*tw, 1*th);
			}
		}
		//draw the towers
		for(TowerInterface t: towers){
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
			if(gt==null)
				continue;
			g.drawImage(Tiles.get(gt), t.getPosition().x*tw, t.getPosition().y*th, tw, th, null);
		}
		//draw the creeps
		for(CreepInterface c : creeps){
			Tiles.TileId t=null;
			switch(c.getType()) {
			case "smiley":
				t=Tiles.TileId.CREEP_SMILEY;
				break;
			case "poring":
				t=Tiles.TileId.CREEP_PORING;
				break;
			case "zombie":
				t=Tiles.TileId.CREEP_ZOMBIE;
				break;
			case "skeleton":
				t=Tiles.TileId.CREEP_SKELETON;
				break;
			case "stonegiant":
				t=Tiles.TileId.CREEP_STONEGIANT;
				break;
			case "reaper":
				t=Tiles.TileId.CREEP_REAPER;

			}
			if(t==null)
				continue;
			//the creep itself
			g.drawImage(Tiles.get(t), (int)((c.getPosition().x+.25)*tw), (int)((c.getPosition().y+.25)*th), (int)(.5*tw), (int)(.5*th), null);
			//and a health bar
			g.setColor(Color.getHSBColor(c.getHealthPercentage()/3,1,1));
			int hbHeight=Math.max((int)(.075*th),1);
			g.fillRect((int)((c.getPosition().x+.25)*tw), (int)((c.getPosition().y+.25)*th)-hbHeight, (int)(.5*c.getHealthPercentage()*tw), hbHeight);
		}
		
		for(ProjectileInterface p: projectiles){
			//Damn transformations...
			AffineTransform at=new AffineTransform();
			Image tile=Tiles.get(Tiles.TileId.PROJECTILE);
			Point2D.Float direction=p.getMoveVector();
			//move the center of the coordinate system to the correct position
			at.translate((p.getPosition().x+.5)*tw,(p.getPosition().y+.5)*th);
			//scale the tile to the correct size
			at.scale(tw/(float)2/tile.getWidth(null),th/(float)2/tile.getHeight(null));
			//rotate it to the correct angle
			at.rotate(Math.atan2(direction.x,-direction.y));
			//and finally move the tile's center to the previous coordinate space's center
			at.translate(-tw/2,-th/2);
			g.drawImage(tile, at, null);
		}
		g.setColor(new Color(0x00FF00));
		if(selectedField.x>=0&&selectedField.y>=0&&selectedField.x<width&&selectedField.y<height){
			g.drawRect(selectedField.x*tw, selectedField.y*th, tw-1, th-1);
		}
		synchronized(frameLock) {
			currentFrame=nextFrame;
		}
		this.repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics gs) {
		super.paintComponent(gs);
		synchronized(frameLock) {
			gs.drawImage(currentFrame, 0, 0, null);
		}
	}
}
