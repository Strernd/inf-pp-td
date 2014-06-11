package inf_pp.td.model;

import java.awt.geom.Point2D;

public class ProjectileTower extends BaseTower {
	
	public ProjectileTower(){
		range=1.2f;
	}

	@Override
	public void fire(Game game){
		if(game.getCreeps().size()==0)
			return;
		double minDist=Double.POSITIVE_INFINITY;
		BaseCreep minCreep;
		for(BaseCreep c : game.getCreeps()){
			double dist=c.getPosition().distance(this.position);
			if(dist<minDist){
				minDist=dist;
				minCreep=c;
			}
		}
		if(minDist>this.range)
			return;
		BaseProjectile p=new BaseProjectile(new Point2D.Float(position.x,position.y));
		game.addProjectile(p);
	}
}
