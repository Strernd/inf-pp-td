package inf_pp.td.control;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.NoGoldException;
import inf_pp.td.TimeSource;
import inf_pp.td.intercom.GameInterface;
import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.intercom.TdState;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;
import inf_pp.td.intercom.ViewInterface;
import inf_pp.td.model.Game;
import inf_pp.td.view.Frame;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller implements ListenerContainer {
	
	/**
	 * This listens for clicks in the PlayArea to select a field to use
	 */
	private FieldSelectListener fieldListener;
	
	/**
	 * This listens to Button clicks in the sidebar and Menu selections
	 */
	private SidebarListener sbListener;
	
	/**
	 * This listens if the window-close-icon is clicked and asks if the program should terminate
	 */
	private WindowListener wListener=new TDWindowListener();
	
	/**
	 * The currently selected position
	 * Only to be modified under lock.
	 */
	private Point selectedField=new Point(-1,-1);
	
	/**
	 * The View
	 */
	private ViewInterface view;
	/**
	 * A JFileChooser that is used whenever the user wants to save or load the game
	 */
	private JFileChooser fc;
	
	/**
	 * The model object
	 * Only to be modified under lock
	 */
	GameInterface game;
	
	/**
	 * The Time Source that the controller uses to pass the time to the model
	 * Only to be modified under lock (use synchronized(game){...})
	 */
	TimeSource time=new TimeSource();
	
	/**
	 * If the game is paused or not
	 * Only to be modified under lock (use synchronized(pausedLock){...}) if accessed non-atomar
	 */
	private boolean paused=true;
	/**
	 * The lock object for {@link #paused}
	 */
	private Object pausedLock=new Object();

	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.ListenerContainer#getFieldSelectListener()
	 */
	@Override
	public MouseInputListener getFieldSelectListener() {
		return fieldListener;
	}

	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.ListenerContainer#getActionListener()
	 */
	@Override
	public ActionListener getActionListener() {
		return sbListener;
	}
	
	/* (non-Javadoc)
	 * @see inf_pp.td.intercom.ListenerContainer#getWindowListener()
	 */
	@Override
	public WindowListener getWindowListener() {
		return wListener;
	}

	/**
	 * Constructs a controller
	 */
	public Controller() {
		fieldListener = new FieldSelectListener();
		sbListener = new SidebarListener();
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				@Override
				public void run() {
					fc=new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("Tower-Defense Speicherstände", "tdsv"));
					fc.setAcceptAllFileFilterUsed(false);
					//TODO: set directory?
				}
			});
		} catch (InvocationTargetException|InterruptedException e) {
			//Swing seems to have problems, we can't properly display the error
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Set the model to use
	 * @param game an instance of a {@link GameInterface} to use
	 */
	public void setModel(GameInterface game) {
		this.game=game;
	}
	
	/**
	 * Set the model to use 
	 * @param frame an instance of the model
	 */
	public void setView(Frame frame) {
		this.view=frame;
		frame.addListener(this);
		frame.setDefaultCloseOperation(Frame.DO_NOTHING_ON_CLOSE);
	}
	
	
	/**
	 * This method is called each tick (only to be called from 'ticking' Game-Thread)
	 */
	public void tick(){
		boolean lost=false,won=false;
		if(!isPaused()){
			synchronized(game) {
				time.tick();
				try{
					game.tick(time);
					lost=game.hasLost();
					won=game.hasWon();
					if(lost || won){
						pause(true);
					}
				} catch(RuntimeException e){
					view.putWarning("Interner Fehler: "+e.getCause().getClass().getName()+" - "+e.getCause().getLocalizedMessage());
				}
			}
		}
		if(lost){
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					JOptionPane.showMessageDialog(view.getComponent(), "Leider verloren!", "Game over!",JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		else if(won)
		{
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					JOptionPane.showMessageDialog(view.getComponent(), "Gewonnen!", "Game over!",JOptionPane.INFORMATION_MESSAGE);
				}
			});	
		}
	}
	
	/**
	 * Ask the user to start a new game (only to be called from EDT)
	 */
	public void newGame(){
		synchronized(pausedLock){
			boolean p=isPaused();
			pause(true);
			if(JOptionPane.showConfirmDialog(null,"Wollen Sie das Spiel neu starten?","Neustart?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)!=JOptionPane.YES_OPTION)
			{
				pause(p);
				return;
			}
			synchronized(game) {
				GameInterface tempG=new Game(20);
				synchronized(tempG) {
					game=tempG;
					time=new TimeSource();
					if(view!=null) {
						view.newGame(game);
					}
				}
			}
		}
	}
	
	/**
	 * Save the game to a file by serializing the whole model
	 * @param path The file path to save to
	 */
	private void saveGame(String path) {
		try {
			FileOutputStream file=new FileOutputStream(path);
			ObjectOutputStream oout=new ObjectOutputStream(file);
			synchronized(game) {
				oout.writeObject(game);
				oout.writeObject(time);
			}
			oout.close();
		}
		catch(IOException e) {
			view.putWarning("Konnte den Spielstand nicht speichern.");
		}
	}
	
	/**
	 * Load the game from a file by deserializing the whole model
	 * @param path the file path to load from
	 */
	private void loadGame(String path) {
		FileInputStream file;
		try {
			file = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(file);
			GameInterface tempG=(GameInterface) oin.readObject();
			TimeSource tempTS=(TimeSource) oin.readObject();
			synchronized(game){
				synchronized(tempG) {
					game=tempG;
					time=tempTS;
				}
			}
			oin.close();
			file.close();
			time.skipTick();
		} catch (IOException | ClassNotFoundException e) {
			view.putWarning("Konnte den Spielstand nicht laden.");
		}
	}
	
	/**
	 * Displays a FileChooser dialog and saves the game (Only to be called from EDT)
	 */
	private void saveWithDialog() {
		synchronized(pausedLock) {
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
	}
	
	/**
	 * Displays a FileChooser dialog and loads the game (Only to be called from EDT)
	 */
	private void loadWithDialog() {
		synchronized(pausedLock) {
			boolean p=isPaused();
			pause(true);
			if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
				loadGame(fc.getSelectedFile().getAbsolutePath());
			else
				pause(p);
		}
	}
	
	/**
	 * Pauses/resumes the game
	 * @param p true: pause the game; false: resume the game
	 */
	public void pause(boolean p) {
		synchronized(pausedLock){
			if(p==paused)
				return;
			synchronized(game) {
				if(!p&&(game.hasWon()||game.hasLost()))
					return;
			}
			paused=p;
			if(!paused) {
				time.skipTick();
			}
		}
	}
	
	/**
	 * Toggles Paused/Resumed
	 */
	public void togglePause() {
		synchronized(pausedLock){
			pause(!isPaused());
		}
	}
	
	/**
	 * @return true if game paused, false if game resumed
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Displays a ConfirmationDialog and terminates the program if the user confirms (Only to be called from EDT)
	 */
	void askExit() {
		synchronized(pausedLock) {
			boolean p=isPaused();
			pause(true);
			if(JOptionPane.showConfirmDialog(null,"Wollen Sie wirklich Beenden?","Beenden?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
				System.exit(0);
			else
				pause(p);
		}
	}
	
	/**
	 * Starts the 'ticking' game thread
	 */
	public void startGameThread() {
		Thread t=new Thread(){
			long last=System.currentTimeMillis();
			public void run(){
				while(true){
					try {
						Thread.sleep(last-System.currentTimeMillis()+25);
					} catch (InterruptedException e) {
					}
					tick();
					last=System.currentTimeMillis();
				}
			}
		};
		t.start();
	}
	
	/**
	 * Starts the rendering thread that calls the views {@link Frame#updateState} method
	 */
	public void startRenderingThread() {
		Thread t=new Thread(){
			public void run(){
				long last=System.currentTimeMillis();
				while(true){
					try {
						Thread.sleep(last-System.currentTimeMillis()+25);
					} catch (InterruptedException e) {
					}
					synchronized(game) {
						synchronized(selectedField) {
							view.updateState(new TdState(game,selectedField));
						}
					}
					last=System.currentTimeMillis();
				}
			}
		};
		t.start();
	}

	
	/**
	 * Listens to button presses and menu selections
	 */
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
						synchronized(pausedLock) {
							if(!isPaused()) {
								synchronized(game) {
									synchronized(selectedField) {
										game.buildTower(type, selectedField);
									}
								}
							}
						}
					} catch(InvalidFieldException e){
						view.putWarning("Bitte ein freies Feld auswählen.");
					} catch(NoGoldException e) {
						view.putWarning("Nicht genug Gold.");
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
						synchronized(pausedLock) {
							if(!isPaused()) {
								synchronized(game) {
									synchronized(selectedField){
										game.upgradeTower(type,selectedField);
									}
								}
							}
						}
					} catch (InvalidFieldException e) {
						view.putWarning("Bitte einen Turm auswählen.");
					} catch (NoGoldException e) {
						view.putWarning("Nicht genug Gold.");
					}
				}
			}
			else if(ac.equals("sell_tower")) {
				try {
					synchronized(pausedLock) {
						if(!isPaused()) {
							synchronized(game) {
								synchronized(selectedField) {
									game.sellTower(selectedField);
								}
							}
						}
					}
				} catch(InvalidFieldException e) {
					view.putWarning("Bitte einen Turm auswählen.");
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
			else if(ac.equals("help")) {
				synchronized(pausedLock){
					boolean p=isPaused();
					pause(true);
					JOptionPane.showMessageDialog(null, "<html><div style=\"width: 480px\">"
							+ "<h1 style=\"text-align:center\">Tower-Defense</h1>"
							+ "<h2 style=\"text-align:center\">Hilfe</h2>"
							+ "<p>Das Ziel des Spieles ist es, möglichst wenige Creeps durch den Parkour zu lassen.<br />"
							+ "Sie können dafür verschiedene Türme auf dem Spielfeld platzieren, indem Sie ein freies Feld anklicken und dann den entsprechenden Knopf "
							+ "drücken. Diese Türme haben verschiedene Fähigkeiten:"
							+ "<ul>"
							+ "<li>Gelb: Schießt auf Creeps und verursacht Schaden</li>"
							+ "<li>Rot: Verursacht gleichzeitig Schaden an allen Creeps in Reichweite</li>"
							+ "<li>Blau: Verlangsamt Creeps</li>"
							+ "<li>Grün: Vergiftet Creeps, sodass sie kontinuierlich Schaden nehmen</li>"
							+ "</ul>"
							+ "Auch kosten die Türme unterschiedlich viel.<br /><br />"
							+ "Sie können den Verursachten Schaden (bzw. Verlangsamung des blauen Turms), die Reichweite und die Feuerrate ihrer Türme aufwerten.<br /><br />"
							+ "Haben Sie einen Turm an die falsche Stelle gesetzt, können Sie ihn verkaufen. Ihnen wird ein Teil des Kaufpreises erstattet.<br /><br />"
							+ "Viel Spaß!</p>"
							+ "</div></html>", "Hilfe", JOptionPane.PLAIN_MESSAGE);
					pause(p);
				}
			}
			else if(ac.equals("about")) {
				synchronized(pausedLock){
					boolean p=isPaused();
					pause(true);
					JOptionPane.showMessageDialog(null, "<html>"
							+ "<h1 style=\"text-align:center\">Tower-Defense</h1>"
							+ "<p style=\"text-align:center\">Version: 0.9 Beta</p>"
							+ "<h2 style=\"text-align:center\">Ein Projekt für das Modul 'Programmierpraktikum'</h2>"
							+ "<p>Dozent: Prof. Dr. Thomas Slawig</p>"
							+ "<p>Übungsleiter: Corvin Hagemeister</p>"
							+ "<table><tr><td>Projektverantwortliche (alphabetisch):</td><td>Bernd Strehl</td></tr><tr><td></td><td>Marc André Wittorf</td></tr></table>"
							+ "<br /><p>Prüfung: 8. Juli 2014</p>"
							+ "</html>", "Über", JOptionPane.PLAIN_MESSAGE);
					pause(p);
				}
			}
		}

	}
	
	
	
	/**
	 * Listens to clicks in the {@link PlayArea} to select a field
	 *
	 */
	private class FieldSelectListener extends MouseInputAdapter {		
		@Override
		public void mousePressed(MouseEvent ev) {
			int x=ev.getX()*game.getPlayArea().getWidth()/((JPanel)ev.getSource()).getWidth();
			int y=ev.getY()*game.getPlayArea().getHeight()/((JPanel)ev.getSource()).getHeight();
			synchronized(selectedField){
				selectedField.x=x;
				selectedField.y=y;
			}
			super.mousePressed(ev);
		}		
	}

	/**
	 * Listens to the window-close event and asks the user if he/she wants to exit 
	 *
	 */
	public class TDWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			askExit();
		}
		
	}
}
