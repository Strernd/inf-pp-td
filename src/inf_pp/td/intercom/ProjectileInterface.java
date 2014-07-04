package inf_pp.td.intercom;

import java.awt.geom.Point2D;

public interface ProjectileInterface {

	public abstract Point2D.Float getPosition();

	//TODO: this is hacky...
	public abstract Point2D.Float getMoveVector();

}