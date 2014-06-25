package inf_pp.td.control;

import inf_pp.td.TimeSource;
import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;
import inf_pp.td.model.TowerFactory;
import inf_pp.td.model.TowerType;
import inf_pp.td.model.UpgradeType;
import inf_pp.td.view.Frame;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class Controller implements ListenerContainer {
	
	private FieldSelectListener fieldListener;
	private SidebarListener sbListener;
	private Point selectedField=new Point(-1,-1);
	private Frame frame;
	
	int tickrate;
	
	//TODO: interface?
	Game game;
	
	TimeSource time=new TimeSource();
	

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
		if(frame!=null)
			game.addObserver(frame);
	}
	
	public void setView(Frame frame) {
		this.frame=frame;
		frame.addListener(this);
		if(game!=null)
			game.addObserver(frame);
	}
	
	
	private boolean paused=false;
	/**
	 * call this once each tick
	 */
	public void tick(){
		if(paused)
			return;
		time.tick();
		game.tick(time);
	}
	
	
	private void SaveGame(String path) {
		try {
			FileOutputStream file=new FileOutputStream(path);
			ObjectOutputStream oout=new ObjectOutputStream(file);
			oout.writeObject(game);
			oout.writeObject(time);
			oout.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void LoadGame(String path) {
		FileInputStream file;
		try {
			file = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(file);
			game=(Game) oin.readObject();
			time=(TimeSource) oin.readObject();
			oin.close();
			file.close();
			game.addObserver(frame);
			time.skipTick();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			else if(ac.startsWith("upgrade_")){
				UpgradeType type=null;
				switch(ac.substring("upgrade_".length())){
				case "damage":
					type=UpgradeType.UPGRADE_DAMAGE;
					break;
				case "range":
					type=UpgradeType.UPGRADE_RANGE;
					break;
				case "firerate":
					type=UpgradeType.UPGRADE_FIRERATE;
					break;
				}
				if(type!=null)
					game.upgradeTower(type,selectedField);
			}
			else if(ac.equals("?")){
				SaveGame("save1.tdsv");
			}
			else if(ac.equals("firerate")){
				LoadGame("save1.tdsv");
			}
			else{
				//System.out.println(ac);
				paused=!paused;
				if(!paused)
					time.skipTick();
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
			super.mouseClicked(ev);
		}		
	}

}
