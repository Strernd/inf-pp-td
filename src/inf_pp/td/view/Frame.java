package inf_pp.td.view;

import inf_pp.td.intercom.GameInterface;
import inf_pp.td.intercom.ListenerContainer;
import inf_pp.td.intercom.TdState;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;


/**
 * @author marc
 *
 */
public class Frame extends JFrame {
	
	JPanel mainPanel;

	/**
	 * a panel to display the playing area, draws all towers, waypoints, creeps, projectiles...
	 */
	private PlayAreaView playArea;
	
	/**
	 * a panel to hold all buttons and labels in the sidebar
	 */
	private SideBar sidebar;
	
	private JLabel statusLabel;
	
	private JMenuBar menuBar;
	
	private static final long serialVersionUID = 8781344923489487475L;
	
	//private Game game;
	
	public Frame(GameInterface game){
		//this.game=game;
		//Game pa=game;

		mainPanel= new JPanel();
		this.add(mainPanel);
		BorderLayout layout=new BorderLayout();
		mainPanel.setLayout(layout);
		
		playArea=new PlayAreaView(game.getPlayArea());
		mainPanel.add(playArea,BorderLayout.CENTER);
		
		sidebar=new SideBar();
		mainPanel.add(sidebar,BorderLayout.EAST);
		
		statusLabel=new JLabel();
		statusLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusLabel.setPreferredSize(new Dimension(0,20));
		statusLabel.setMinimumSize(new Dimension(0,20));
		mainPanel.add(statusLabel,BorderLayout.SOUTH);
		
		menuBar=new JMenuBar();
		JMenu gameMenu=new JMenu("Spiel");
		JMenuItem item=new JMenuItem("Neues Spiel");
		item.setActionCommand("newgame");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		gameMenu.add(item);
		item=new JMenuItem("Pause/Weiter");
		item.setActionCommand("pause");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE,0));
		final JMenuItem pauseItem=item;
		//We want a second accelerator, so we need to use Actions
		gameMenu.getInputMap(JMenu.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P,0),"pause");
		gameMenu.getActionMap().put("pause", new AbstractAction(){
			private static final long serialVersionUID = 6027103533005126611L;
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseItem.doClick();
			}
		});
		gameMenu.add(item);
		item=new JMenuItem("Speichern");
		item.setActionCommand("save");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		gameMenu.add(item);
		item=new JMenuItem("Laden");
		item.setActionCommand("load");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		gameMenu.add(item);
		item=new JMenuItem("Beenden");
		item.setActionCommand("exit");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
		gameMenu.add(item);
		menuBar.add(gameMenu);
		
		
		JMenu helpMenu=new JMenu("Hilfe");
		item=new JMenuItem("Anleitung");
		item.setActionCommand("help");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		helpMenu.add(item);
		item=new JMenuItem("Über");
		item.setActionCommand("about");
		helpMenu.add(item);
		
		menuBar.add(helpMenu);
		
		this.setJMenuBar(menuBar);
		
		this.pack();
	}

	public void update(TdState state) {
		//Game game=state.getGame();
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
	public void addListener(final ListenerContainer l) {
		final Frame savedThis=this;
		//TODO: make this better? this is very ugly
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				playArea.addMouseListener(l.getFieldSelectListener());
				sidebar.addActionListener(l.getActionListener());
				savedThis.addWindowListener(l.getWindowListener());
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
		});
	}
	
	public void newGame(final GameInterface game) {
		//TODO: adjust parameters instead of new object? rebinding the handler is very ugly
		SwingUtilities.invokeLater(new Runnable(){

		@Override
		public void run() {
			mainPanel.remove(playArea);
			MouseListener listeners[]=playArea.getListeners(MouseListener.class);
			playArea=new PlayAreaView(game.getPlayArea());
			for(MouseListener l: listeners) {
				playArea.addMouseListener(l);
			}
			mainPanel.add(playArea,BorderLayout.CENTER);
			mainPanel.revalidate();
			mainPanel.repaint();
		}
		
		});
	}
	
	Timer warningTimer=new Timer(0,null);
	public void putWarning(final String warning) {
		if(warningTimer!=null)
			warningTimer.stop();
		warningTimer=new Timer(33,null);
		warningTimer.addActionListener(new ActionListener(){
			private int aniNum=-1;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(++aniNum<31){
					statusLabel.setText(warning);
					statusLabel.setForeground(new Color(1.0f*(1-(float)aniNum/30),0,0));
				}
				else if(aniNum>60) {
					warningTimer.stop();
					statusLabel.setText("");
				}
			}
			
		});
		warningTimer.setDelay(33);
		warningTimer.setRepeats(true);
		warningTimer.start();
	}

}
