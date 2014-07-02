package inf_pp.td.view;

import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.intercom.TdState;
import inf_pp.td.model.Game;

import java.awt.BorderLayout;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


/**
 * @author marc
 *
 */
public class Frame extends JFrame {
	
	JPanel mainPanel;

	/**
	 * a panel to display the playing area, draws all towers, waypoints, creeps, projectiles...
	 */
	private PlayArea playArea;
	
	/**
	 * a panel to hold all buttons and labels in the sidebar
	 */
	private SideBar sidebar;
	
	private JMenuBar menuBar;
	
	private static final long serialVersionUID = 8781344923489487475L;
	
	//private Game game;
	
	public Frame(Game game){
		//this.game=game;
		//Game pa=game;

		mainPanel= new JPanel();
		this.add(mainPanel);
		BorderLayout layout=new BorderLayout();
		mainPanel.setLayout(layout);
		
		playArea=new PlayArea(game.getPlayArea());
		mainPanel.add(playArea,BorderLayout.CENTER);
		
		sidebar=new SideBar();
		mainPanel.add(sidebar,BorderLayout.EAST);
		
		menuBar=new JMenuBar();
		JMenu gameMenu=new JMenu("Spiel");
		JMenuItem item=new JMenuItem("Neues Spiel");
		item.setActionCommand("newgame");
		gameMenu.add(item);
		item=new JMenuItem("Pause/Weiter");
		item.setActionCommand("pause");
		gameMenu.add(item);
		item=new JMenuItem("Speichern");
		item.setActionCommand("save");
		gameMenu.add(item);
		item=new JMenuItem("Laden");
		item.setActionCommand("load");
		gameMenu.add(item);
		item=new JMenuItem("Beenden");
		item.setActionCommand("exit");
		gameMenu.add(item);
		menuBar.add(gameMenu);
		//JMenuItem men=new JMenuItem("Pause");
		//men.setActionCommand("pause");
		//menuBar.add(men);
		this.setJMenuBar(menuBar);
	}

	public void update(TdState state) {
		Game game=state.getGame();
		// TODO Auto-generated method stub
		playArea.updateState(state);
		sidebar.updateState(state);
	}
	
	/**
	 * @param set the model to get data from
	 */
	/*public void setModel(Game game) {
		this.game=game;
	}*/

	
	/**
	 * set the listeners for the playing field and the sidebar
	 * @param l a ListenerContainer to get the listers from.
	 */
	public void addListener(ListenerContainer l) {
		playArea.addMouseListener(l.getFieldSelectListener());
		sidebar.addActionListener(l.getActionListener());
		this.addWindowListener(l.getWindowListener());
		for(int i = 0; i<menuBar.getMenuCount();++i) {
			JMenu m=menuBar.getMenu(i);
			if(m==null)
				continue;
			m.addActionListener(l.getActionListener());
			for(int j = 0;j<m.getItemCount();++j) {
				m.getItem(j).addActionListener(l.getActionListener());
			}
		}
		
	}
	
	public void newGame(Game game) {
		//TODO: adjust parameters instead of new-generation?
		mainPanel.remove(playArea);
		playArea=new PlayArea(game.getPlayArea());
		mainPanel.add(playArea,BorderLayout.CENTER);
	}

}
