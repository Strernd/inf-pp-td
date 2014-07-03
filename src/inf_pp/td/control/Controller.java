package inf_pp.td.control;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.NoGoldException;
import inf_pp.td.TimeSource;
import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.intercom.TdState;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.model.BaseTower;
import inf_pp.td.model.Game;
import inf_pp.td.model.TowerFactory;
import inf_pp.td.model.UpgradeType;
import inf_pp.td.view.Frame;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller implements ListenerContainer {
	
	private FieldSelectListener fieldListener;
	private SidebarListener sbListener;
	private WindowListener wListener=new TDWindowListener();
	private Point selectedField=new Point(-1,-1);
	private Frame frame;
	private JFileChooser fc;
	
	int tickrate;
	
	//TODO: interface?
	Game game;
	
	TimeSource time=new TimeSource();
	

	@Override
	public MouseInputListener getFieldSelectListener() {
		return fieldListener;
	}

	@Override
	public ActionListener getActionListener() {
		return sbListener;
	}
	
	@Override
	public WindowListener getWindowListener() {
		return wListener;
	}

	public Controller() {
		fieldListener = new FieldSelectListener();
		sbListener = new SidebarListener();
		fc=new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Tower-Defense Speicherstände", "tdsv"));
		fc.setAcceptAllFileFilterUsed(false);
		//TODO: set directory?
	}
	
	/**
	 * set the model to use
	 * @param game an instance of a game to use
	 */
	public void setModel(Game game) {
		this.game=game;
		/*if(frame!=null)
			game.addObserver(frame);*/
	}
	
	public void setView(Frame frame) {
		this.frame=frame;
		frame.addListener(this);
		frame.setDefaultCloseOperation(Frame.DO_NOTHING_ON_CLOSE);
		/*if(game!=null)
			game.addObserver(frame);*/
	}
	
	
	//TODO: prevent pause-abuse
	private boolean paused=true;
	/**
	 * call this once each tick
	 */
	public void tick(){
		if(!paused){
			time.tick();
			game.tick(time);
			//TODO: put this somewhere else (View?)
			if(game.hasLost()){
				JOptionPane.showMessageDialog(frame, "Leider verloren!", "Game over!",JOptionPane.INFORMATION_MESSAGE);
				pause(true);
			}
			else if(game.hasWon()){
				JOptionPane.showMessageDialog(frame, "Gewonnen!", "Game over!",JOptionPane.INFORMATION_MESSAGE);
				pause(true);
			}
		}
		frame.update(new TdState(game,selectedField));
	}
	
	public void newGame(){
		pause(true);
		game=new Game(20);
		time=new TimeSource();
		if(frame!=null) {
			frame.newGame(game);
		}
	}
	
	private void saveGame(String path) {
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
	
	private void loadGame(String path) {
		FileInputStream file;
		try {
			file = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(file);
			game=(Game) oin.readObject();
			time=(TimeSource) oin.readObject();
			oin.close();
			file.close();
			//game.addObserver(frame);
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
	
	private void saveWithDialog() {
		boolean p=isPaused();
		pause(true);
		if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
			String path=fc.getSelectedFile().getAbsolutePath();
			if(!path.endsWith(".tdsv"))
				path=path.concat(".tdsv");
			saveGame(path);
		}
		pause(p);
	}
	
	private void loadWithDialog() {
		boolean p=isPaused();
		pause(true);
		if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
			loadGame(fc.getSelectedFile().getAbsolutePath());
		else
			pause(p);
	}
	
	public void pause(boolean p) {
		if(p==paused)
			return;
		if(!p&&(game.hasWon()||game.hasLost()))
			return;
		paused=p;
		if(!paused)
			time.skipTick();
	}
	
	public void togglePause() {
		pause(!paused);
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void askExit() {
		boolean p=isPaused();
		pause(true);
		if(JOptionPane.showConfirmDialog(null,"Wirklich Beenden?","test",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			System.exit(0);
		else
			pause(p);
	}

	
	private class SidebarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
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
					type=TowerType.POISON;
					break;
				}
				if(type!=null){
					try{
						game.buildTower(type, selectedField);
					} catch(InvalidFieldException e){
						frame.putWarning("Bitte ein freies Feld auswählen.");
					} catch(NoGoldException e) {
						frame.putWarning("Nicht genug Gold.");
					}
				}
			}
			else if(ac.startsWith("upgrade_")){
				UpgradeType type=null;
				switch(ac.substring("upgrade_".length())){
				case "damage":
					type=UpgradeType.DAMAGE;
					break;
				case "range":
					type=UpgradeType.RANGE;
					break;
				case "firerate":
					type=UpgradeType.FIRERATE;
					break;
				}
				if(type!=null) {
					try {
						game.upgradeTower(type,selectedField);
					} catch (InvalidFieldException e) {
						frame.putWarning("Bitte einen Turm auswählen.");
					} catch (NoGoldException e) {
						frame.putWarning("Nicht genug Gold.");
					}
				}
			}
			else if(ac.equals("sell_tower")) {
				try {
					game.sellTower(selectedField);
				} catch(InvalidFieldException e) {
					frame.putWarning("Bitte einen Turm auswählen.");
				}
			}
			else if(ac.equals("pause")) {
				togglePause();
			}
			else if(ac.equals("save")) {
				saveWithDialog();
			}
			else if(ac.equals("load")) {
				loadWithDialog();
			}
			else if(ac.equals("exit")) {
				askExit();
			}
			else if(ac.equals("newgame")) {
				newGame();
			}
		}

	}
	
	
	
	private class FieldSelectListener extends MouseInputAdapter {		
		@Override
		public void mousePressed(MouseEvent ev) {
			int x=ev.getX()*game.getPlayArea().getWidth()/((JPanel)ev.getSource()).getWidth();
			int y=ev.getY()*game.getPlayArea().getHeight()/((JPanel)ev.getSource()).getHeight();
			selectedField.x=x;
			selectedField.y=y;
			super.mousePressed(ev);
		}		
	}

	public class TDWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			askExit();
		}
		
	}
}
