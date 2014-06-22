package inf_pp.td.control;

import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;
import inf_pp.td.model.TowerFactory;
import inf_pp.td.model.TowerFactory.TowerType;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class Controller implements ListenerContainer {
	
	private FieldSelectListener fieldListener;
	private SidebarListener sbListener;
	private Point selectedField=new Point(-1,-1);
	
	int tickrate;
	
	//TODO: interface?
	Game game;
	

	@Override
	public MouseInputListener getFieldSelectListener() {
		return fieldListener;
	}

	@Override
	public ActionListener getButtonListener() {
		return sbListener;
	}

	public Controller() {
		fieldListener = new FieldSelectListener();
		sbListener = new SidebarListener();
	}
	
	/**
	 * set the model to use
	 * @param game an instance of a game to use
	 */
	public void setModel(Game game) {
		this.game=game;
	}
	
	
	private long time=System.currentTimeMillis();
	public void setStartingTime() {
		this.time=System.currentTimeMillis();
	}
	/**
	 * call this once each tick
	 */
	public void tick(){
		game.tick(System.currentTimeMillis()-this.time);
	}
	

	
	public class SidebarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			//TODO: add upgrade...
			String ac=ev.getActionCommand().toLowerCase();
			if(ac.startsWith("build_")) {
				TowerType type=null;
				switch(ac.substring("build_".length())){
				case "dd":
					type=TowerType.DIRECT_DMG;
					break;
				case "ae":
					type=TowerType.AREA_OF_EFFECT;
					break;
				case "sl":
					type=TowerType.SLOW;
					break;
				case "p":
					type=TowerType.P;
					break;
				}
				if(type!=null){
					try{
						game.buildTower(type, selectedField);
					}catch(ArrayIndexOutOfBoundsException e){
					}
				}
			}
		}

	}
	
	
	
	public class FieldSelectListener extends MouseInputAdapter {		
		@Override
		public void mousePressed(MouseEvent ev) {
			int x=ev.getX()*game.getPlayArea().getWidth()/((JPanel)ev.getSource()).getWidth();
			int y=ev.getY()*game.getPlayArea().getHeight()/((JPanel)ev.getSource()).getHeight();
			selectedField.x=x;
			selectedField.y=y;
			//System.out.println(x+",  "+y);
			//System.out.println(((JPanel)ev.getSource()).getWidth());
			super.mouseClicked(ev);
		}		
	}
}
