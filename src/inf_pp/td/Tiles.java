package inf_pp.td;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * This class holds all tiles to be used by the GUI.
 * It features an enum to identify a specific tile
 * and a method to get the tile as an image
 *
 */
/**
 * @author Marc
 *
 */
/**
 * @author Marc
 *
 */
/**
 * @author Marc
 *
 */
/**
 * @author Marc
 *
 */
/**
 * @author Marc
 *
 */
public final class Tiles {
	/**
	 * This class is not to be instantiated
	 */
	private Tiles(){}
	
	/**
	 * This enum holds the identifiers of all available tiles
	 *
	 */
	public static enum TileId{
		/**
		 * A tile for the DirectDamage Tower
		 */
		TOWER_DD,
		/**
		 * A tile for the AreaOfEffect Tower 
		 */
		TOWER_AE,
		/**
		 * A tile for the Slowing Tower
		 */
		TOWER_SL,
		/**
		 * A tile for the Poison Tower
		 */
		TOWER_P,
		
		/**
		 * Tiles for all creeps, names are self-explainatory
		 */
		CREEP_SMILEY, CREEP_ZOMBIE, CREEP_PORING, CREEP_REAPER, CREEP_STONEGIANT, CREEP_SKELETON,
		
		/**
		 * A tile for the world, ie. the background of the PlayArea
		 */
		WORLD,
		/**
		 * A tile for a projectile
		 */
		PROJECTILE,
		
		/**
		 * The Window's icon
		 */
		WINDOW_ICON
	}
	
	/**
	 * This maps all of the TileId values to actual images
	 * @see TileId
	 */
	private static final Map<TileId, Image> tiles;
	
	static {
		tiles=new HashMap<TileId,Image>();
		HashMap<TileId,String> paths=new HashMap<>();
		paths.put(TileId.TOWER_DD,"assets/graphics/tower/dd.png");
		paths.put(TileId.TOWER_AE,"assets/graphics/tower/ae.png");
		paths.put(TileId.TOWER_SL,"assets/graphics/tower/sl.png");
		paths.put(TileId.TOWER_P,"assets/graphics/tower/p.png");
		paths.put(TileId.CREEP_SMILEY,"assets/graphics/creeps/smiley1.png");
		paths.put(TileId.CREEP_ZOMBIE,"assets/graphics/creeps/zombie.png");
		paths.put(TileId.CREEP_PORING,"assets/graphics/creeps/poring.png");
		paths.put(TileId.CREEP_REAPER,"assets/graphics/creeps/reaper.png");
		paths.put(TileId.CREEP_STONEGIANT,"assets/graphics/creeps/stonegiant.png");
		paths.put(TileId.CREEP_SKELETON,"assets/graphics/creeps/skeleton.png");
		paths.put(TileId.PROJECTILE,"assets/graphics/misc/projectile.png");
		paths.put(TileId.WINDOW_ICON,"assets/graphics/misc/tdicon.png");
		for(Map.Entry<TileId,String> e: paths.entrySet()){
			try {
				tiles.put(e.getKey(),ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(e.getValue())));
			} catch (IOException e1) {
				// TODO Print error to user
				//e1.printStackTrace();
			}
		}
		setWorldTile("world/background.jpg");
	}
	
	/**
	 * Select a world tile
	 * @param filename the tile's filename. Must be in assets/graphics/ directory
	 */
	static void setWorldTile(String filename) {
		try {
			tiles.put(TileId.WORLD,ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/graphics/"+filename)));
		} catch (IOException e) {
		}
	}
	
	/**
	 * Get the tile from its TileId
	 * @param id which tile to get
	 * @return the tile as Image
	 */
	public static Image get(TileId id){
		return tiles.get(id);
	}
}
