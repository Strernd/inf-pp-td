package inf_pp.td.view;

import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.model.Game;

import java.awt.BorderLayout;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * @author marc
 *
 */
public class Frame extends JFrame implements java.util.Observer {

	/**
	 * a panel to display the playing area, draws all towers, waypoints, creeps, projectiles...
	 */
	private PlayArea playArea;
	
	/**
	 * a panel to hold all buttons and labels in the sidebar
	 */
	private SideBar sidebar;
	private static final long serialVersionUID = 8781344923489487475L;
	
	private Game game;
	
	public Frame(Game game){
		//this.game=game;
		//Game pa=game;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel mainPanel= new JPanel();
		this.add(mainPanel);
		BorderLayout layout=new BorderLayout();
		mainPanel.setLayout(layout);
		
		playArea=new PlayArea(game.getPlayArea());
		mainPanel.add(playArea,BorderLayout.CENTER);
		
		sidebar=new SideBar();
		mainPanel.add(sidebar,BorderLayout.EAST);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		playArea.updateState(game);
		sidebar.updateState(game);
	}
	
	/**
	 * @param set the model to get data from
	 */
	public void setModel(Game game) {
		this.game=game;
	}

	
	/**
	 * set the listeners for the playing field and the sidebar
	 * @param l a ListenerContainer to get the listers from.
	 */
	public void addListener(ListenerContainer l) {
		playArea.addMouseListener(l.getFieldSelectListener());
		sidebar.addActionListener(l.getButtonListener());
		
	}

}
