package inf_pp.td.view;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.Tiles;
import inf_pp.td.intercom.GameInterface;
import inf_pp.td.intercom.TdState;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SideBar extends JPanel {
	private static final long serialVersionUID = -2976601330037398908L;
	
	/**
	 * labels to display the player's lives, gold and the current wave
	 */
	private JLabel livesLabel, goldLabel, waveLabel;
	
	/**
	 * buttons to build different towers
	 */
	private JButton buildDD, buildAE, buildSL, buildP;
	/**
	 * buttons to upgrade towers
	 */
	private JButton upgradeDmg, upgradeRange, upgradeFirerate;
	/**
	 * button to sell a tower
	 */
	private JButton sellTower;

	/**
	 * Construct a SideBar
	 */
	public SideBar() {
		this.setBackground(new Color(0xCCCCCC));
		this.setPreferredSize(new Dimension(200,0));
		this.setMinimumSize(new Dimension(200,0));
		BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(layout);
		JPanel twrPane=new JPanel();
		twrPane.setLayout(new GridLayout(2,2,4,4));
		
		Action acceleratingAction=new AbstractAction(){
			private static final long serialVersionUID = -3185980796146069261L;

			@Override
			public void actionPerformed(ActionEvent e) {
				((JButton)e.getSource()).doClick();
			}
		};
		
		buildDD=new JButton();
		buildDD.setActionCommand("build_dd");
		buildDD.setToolTipText("Feuert auf einen Creep");
		buildDD.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildDD.setHorizontalTextPosition(SwingConstants.CENTER);
		buildDD.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_DD)));
		buildDD.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "build_accel");
		buildDD.getActionMap().put("build_accel", acceleratingAction);
		twrPane.add(buildDD);
		buildAE=new JButton();
		buildAE.setActionCommand("build_ae");
		buildAE.setToolTipText("Fügt allen Creeps in Reichweite Schaden zu");
		buildAE.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_AE)));
		buildAE.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildAE.setHorizontalTextPosition(SwingConstants.CENTER);
		buildAE.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "build_accel");
		buildAE.getActionMap().put("build_accel", acceleratingAction);
		twrPane.add(buildAE);
		buildSL=new JButton();
		buildSL.setActionCommand("build_sl");
		buildSL.setToolTipText("Verlangsamt Creeps");
		buildSL.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_SL)));
		buildSL.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildSL.setHorizontalTextPosition(SwingConstants.CENTER);
		buildSL.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "build_accel");
		buildSL.getActionMap().put("build_accel", acceleratingAction);
		twrPane.add(buildSL);
		buildP=new JButton();
		buildP.setActionCommand("build_p");
		buildP.setToolTipText("Vergiftet Creeps");
		buildP.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_P)));
		buildP.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildP.setHorizontalTextPosition(SwingConstants.CENTER);
		buildP.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "build_accel");
		buildP.getActionMap().put("build_accel", acceleratingAction);
		twrPane.add(buildP);
		
		JPanel twrWrapper=new JPanel();
		twrWrapper.setPreferredSize(new Dimension(200,160));
		twrWrapper.setMaximumSize(new Dimension(200,160));
		twrWrapper.setLayout(new BorderLayout());
		twrWrapper.add(twrPane,BorderLayout.CENTER);
		twrWrapper.add(new JLabel("Bauen"),BorderLayout.NORTH);
		this.add(twrWrapper);
		twrPane.setOpaque(false);
		twrWrapper.setOpaque(false);
		
		this.add(Box.createVerticalGlue());
		
		JPanel uPane=new JPanel();
		uPane.setLayout(new GridLayout(2,2,4,4));
		//JButton b;
		upgradeDmg=new JButton();
		upgradeDmg.setActionCommand("upgrade_damage");
		upgradeDmg.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "build_accel");
		upgradeDmg.getActionMap().put("build_accel", acceleratingAction);
		uPane.add(upgradeDmg);
		upgradeRange=new JButton();
		upgradeRange.setActionCommand("upgrade_range");
		upgradeRange.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "build_accel");
		upgradeRange.getActionMap().put("build_accel", acceleratingAction);
		uPane.add(upgradeRange);
		upgradeFirerate=new JButton();
		upgradeFirerate.setActionCommand("upgrade_firerate");
		upgradeFirerate.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "build_accel");
		upgradeFirerate.getActionMap().put("build_accel", acceleratingAction);
		uPane.add(upgradeFirerate);
		sellTower=new JButton("Verkaufen");
		sellTower.setActionCommand("sell_tower");
		uPane.add(sellTower);
		
		JPanel uWrapper=new JPanel();
		uWrapper.setPreferredSize(new Dimension(200,160));
		uWrapper.setMaximumSize(new Dimension(200,160));
		uWrapper.setLayout(new BorderLayout());
		uWrapper.add(uPane,BorderLayout.CENTER);
		uWrapper.add(new JLabel("Upgraden"),BorderLayout.NORTH);
		this.add(uWrapper);
		uPane.setOpaque(false);
		uWrapper.setOpaque(false);
		
		
		this.add(Box.createVerticalGlue());
		
		JPanel iPane=new JPanel();
		//iPane.setBackground(new Color(0x0000FF));
		iPane.setOpaque(false);
		iPane.setLayout(new GridLayout(0,1));
		iPane.setPreferredSize(new Dimension(200,80));
		iPane.setMinimumSize(new Dimension(200,80));
		iPane.setMaximumSize(new Dimension(200,80));
		goldLabel=new JLabel();
		iPane.add(goldLabel);
		livesLabel=new JLabel();
		iPane.add(livesLabel);
		waveLabel=new JLabel();
		iPane.add(waveLabel);
		this.add(iPane);
	}
	
	/**
	 * hacky method to bind a listener to all buttons in the sidebar
	 * @param l the listener to use
	 */
	void addActionListener(ActionListener l) {
		buildDD.addActionListener(l);
		buildAE.addActionListener(l);
		buildSL.addActionListener(l);
		buildP.addActionListener(l);
		
		upgradeDmg.addActionListener(l);
		upgradeRange.addActionListener(l);
		upgradeFirerate.addActionListener(l);
		sellTower.addActionListener(l);
	}
	
	/**
	 * Update the view's state
	 * @param state a TdState to get the model's state from
	 */
	void updateState(final TdState state){
		GameInterface game=state.getGame();
		//We need to save these variables as we update the UI from the UI thread whereas updateState is called by our view-thread
		final int lives=game.getLives();
		final int gold=game.getGold();
		final int waveIndex=game.getCurrentWaveIndex()+1;
		final int waveCount=game.getWaveCount();
		final String waveName=game.getCurrentWaveName();
		
		final String ddText="Preis: "+game.getPrice(TowerType.DIRECT_DMG);
		final String aeText="Preis: "+game.getPrice(TowerType.AREA_OF_EFFECT);
		final String slText="Preis: "+game.getPrice(TowerType.SLOW);
		final String pText ="Preis: "+game.getPrice(TowerType.POISON);
		
		final String dmgLabel,rngLabel,frLabel;
		String tdmgLabel,trngLabel,tfrLabel;
		
		boolean tenableUpgrade;
		final boolean enableBuild,enableUpgrade;
		
		try{
			int dmgUPrice=game.getTowerUpgradePrice(state.getSelectedField(),UpgradeType.DAMAGE);
			int rangeUPrice=game.getTowerUpgradePrice(state.getSelectedField(),UpgradeType.RANGE);
			int rateUPrice=game.getTowerUpgradePrice(state.getSelectedField(),UpgradeType.FIRERATE);
			if(game.getTowerType(state.getSelectedField())==TowerType.SLOW){
				tdmgLabel="<html><p style=\"text-align:center\">Verlangsamung<br /><br />Preis: "+dmgUPrice+"</p></html>";
			}
			else {
				tdmgLabel="<html><p style=\"text-align:center\">Schaden<br /><br />Preis: "+dmgUPrice+"</p></html>";
			}
			trngLabel="<html><p style=\"\">Reichweite<br /><br />Preis: "+rangeUPrice+"</p></html>";
			tfrLabel="<html><p style=\"text-align:center\">Feuerrate<br /><br />Preis: "+rateUPrice+"</p></html>";
			tenableUpgrade=true;
		} catch (InvalidFieldException e) {
			tdmgLabel="Schaden";
			trngLabel="Reichweite";
			tfrLabel="Feuerrate";
			tenableUpgrade=false;
		}
		dmgLabel=tdmgLabel;
		rngLabel=trngLabel;
		frLabel=tfrLabel;
		enableUpgrade=tenableUpgrade;
		
		enableBuild=game.canBuildHere(state.getSelectedField());
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				livesLabel.setText("Leben: "+lives);
				goldLabel.setText("Gold: "+gold);
				waveLabel.setText("Welle: "+waveIndex+"/"+waveCount+" "+waveName);
				
				buildDD.setText(ddText);
				buildDD.setEnabled(enableBuild);
				buildAE.setText(aeText);
				buildAE.setEnabled(enableBuild);
				buildSL.setText(slText);
				buildSL.setEnabled(enableBuild);
				buildP.setText(pText);
				buildP.setEnabled(enableBuild);
				
				upgradeDmg.setText(dmgLabel);
				upgradeDmg.setEnabled(enableUpgrade);
				upgradeRange.setText(rngLabel);
				upgradeRange.setEnabled(enableUpgrade);
				upgradeFirerate.setText(frLabel);
				upgradeFirerate.setEnabled(enableUpgrade);
				sellTower.setEnabled(enableUpgrade);
			}
		});
	}
}