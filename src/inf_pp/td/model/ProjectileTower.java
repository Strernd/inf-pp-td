package inf_pp.td.model;

import java.awt.geom.Point2D;

public class ProjectileTower extends BaseTower {
	
	public ProjectileTower(){
		range=2.5f;
	}

	@Override
	public int doFire(Game game){
		if(game.getCreeps().size()==0)
			return 0;
		double minDist=Double.POSITIVE_INFINITY;
		BaseCreep minCreep=null;
		for(BaseCreep c : game.getCreeps()){
			double dist=c.getPosition().distance(this.position);
			if(dist<minDist){
				minDist=dist;
				minCreep=c;
			}
		}
		if(minDist>this.range)
			return 0;
		GuidedProjectile p=new GuidedProjectile(new Point2D.Float(position.x,position.y),minCreep);
		game.addProjectile(p);
		return 500;
	}
}
