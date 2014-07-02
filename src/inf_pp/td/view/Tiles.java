package inf_pp.td.view;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public final class Tiles {
	private Tiles(){}
	
	static enum TileId{
		TOWER_DD,TOWER_AE,TOWER_SL,TOWER_P,
		CREEP_SMILEY,
		WORLD, PROJECTILE
	}
	
	private static final Map<TileId, Image> tiles;
	
	static {
		tiles=new HashMap<TileId,Image>();
		HashMap<TileId,String> paths=new HashMap<>();
		paths.put(TileId.TOWER_DD,"assets/graphics/tower/dd.png");
		paths.put(TileId.TOWER_AE,"assets/graphics/tower/ae.png");
		paths.put(TileId.TOWER_SL,"assets/graphics/tower/sl.png");
		paths.put(TileId.TOWER_P,"assets/graphics/tower/p.png");
		paths.put(TileId.CREEP_SMILEY,"assets/graphics/creeps/smiley1.png");
		paths.put(TileId.PROJECTILE,"assets/graphics/misc/projectile.png");
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
	
	static void setWorldTile(String filename) {
		try {
			tiles.put(TileId.WORLD,ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/graphics/"+filename)));
		} catch (IOException e) {
			// TODO Print Error To User
			//e.printStackTrace();
		}
	}
	
	static Image get(TileId id){
		return tiles.get(id);
	}
}
