package inf_pp.td.view;

import inf_pp.td.InvalidFieldException;
import inf_pp.td.Tiles;
import inf_pp.td.intercom.TdState;
import inf_pp.td.intercom.TowerType;
import inf_pp.td.intercom.UpgradeType;
import inf_pp.td.model.Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SideBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2976601330037398908L;
	
	JLabel livesLabel;
	JLabel goldLabel;
	JLabel waveLabel;
	
	JButton buildDD,buildAE,buildSL,buildP;
	JButton upgradeDmg,upgradeRange,upgradeFirerate;

	public SideBar() {
		this.setBackground(new Color(0xCCCCCC));
		this.setPreferredSize(new Dimension(200,0));
		this.setMinimumSize(new Dimension(200,0));
		BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
		this.setLayout(layout);
		JPanel twrPane=new JPanel();
		twrPane.setLayout(new GridLayout(2,2,4,4));
		//JButton b=new JButton();
		buildDD=new JButton();
		buildDD.setActionCommand("build_dd");
		buildDD.setToolTipText("Feuert auf einen Creep");
		buildDD.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildDD.setHorizontalTextPosition(SwingConstants.CENTER);
		buildDD.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_DD)));
		twrPane.add(buildDD);
		buildAE=new JButton();
		buildAE.setActionCommand("build_ae");
		buildAE.setToolTipText("Fügt allen Creeps in Reichweite Schaden zu");
		buildAE.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_AE)));
		buildAE.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildAE.setHorizontalTextPosition(SwingConstants.CENTER);
		twrPane.add(buildAE);
		buildSL=new JButton();
		buildSL.setActionCommand("build_sl");
		buildSL.setToolTipText("Verlangsamt Creeps");
		buildSL.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_SL)));
		buildSL.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildSL.setHorizontalTextPosition(SwingConstants.CENTER);
		twrPane.add(buildSL);
		buildP=new JButton();
		buildP.setActionCommand("build_p");
		buildP.setToolTipText("Vergiftet Creeps");
		buildP.setIcon(new ImageIcon(Tiles.get(Tiles.TileId.TOWER_P)));
		buildP.setVerticalTextPosition(SwingConstants.BOTTOM);
		buildP.setHorizontalTextPosition(SwingConstants.CENTER);
		twrPane.add(buildP);
		
		twrPane.setPreferredSize(new Dimension(200,160));
		twrPane.setMaximumSize(new Dimension(200,160));
		this.add(twrPane);
		
		this.add(Box.createVerticalGlue());
		
		JPanel uPane=new JPanel();
		uPane.setLayout(new GridLayout(2,2,4,4));
		//JButton b;
		upgradeDmg=new JButton();
		upgradeDmg.setActionCommand("upgrade_damage");
		uPane.add(upgradeDmg);
		upgradeRange=new JButton();
		upgradeRange.setActionCommand("upgrade_range");
		uPane.add(upgradeRange);
		upgradeFirerate=new JButton();
		upgradeFirerate.setActionCommand("upgrade_firerate");
		uPane.add(upgradeFirerate);
		JButton b=new JButton("Verkaufen");
		b.setActionCommand("sell_tower");
		uPane.add(b);
		uPane.setPreferredSize(new Dimension(200,160));
		uPane.setMaximumSize(new Dimension(200,160));
		this.add(uPane);
		
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
		synchronized(this.getTreeLock()) {
			for(Component c: this.getComponents())
			{
				//TODO: make better
				if(c instanceof JButton) {
					((JButton)c).addActionListener(l);
				}
				else if(c instanceof JPanel) {
					synchronized(c.getTreeLock()) {
						for(Component d: ((JPanel)c).getComponents()) {
							if(d instanceof JButton) {
								((JButton)d).addActionListener(l);
							}
						}
					}
				}
			}
		}
	}
	
	void updateState(final TdState state){
		Game game=state.getGame();
		//We need to save these variables as we update the UI from the UI thread whereas updateState is called by our view-thread
		final int lives=game.getLives();
		final int gold=game.getGold();
		final int waveIndex=game.getCurrentWaveIndex()+1;
		final int waveCount=game.getWaveCount();
		final String waveName=game.getCurrentWaveName();
		
		final String ddText="Price: "+game.getPrice(TowerType.DIRECT_DMG);
		final String aeText="Price: "+game.getPrice(TowerType.AREA_OF_EFFECT);
		final String slText="Price: "+game.getPrice(TowerType.SLOW);
		final String pText ="Price: "+game.getPrice(TowerType.POISON);
		
		final String dmgLabel,rngLabel,frLabel;
		String tdmgLabel,trngLabel,tfrLabel;
		
		try{
			int dmgUPrice=game.getTowerUpgradePrice(state.getSelectedField(),UpgradeType.DAMAGE);
			int rangeUPrice=game.getTowerUpgradePrice(state.getSelectedField(),UpgradeType.RANGE);
			int rateUPrice=game.getTowerUpgradePrice(state.getSelectedField(),UpgradeType.FIRERATE);
			tdmgLabel="<html><p style=\"text-align:center\">Schaden<br /><br />Preis: "+dmgUPrice+"</p></html>";
			trngLabel="<html><p style=\"\">Reichweite<br /><br />Preis: "+rangeUPrice+"</p></html>";
			tfrLabel="<html><p style=\"text-align:center\">Feuerrate<br /><br />Preis: "+rateUPrice+"</p></html>";
		} catch (InvalidFieldException e) {
			//dmgUPrice=rangeUPrice=rateUPrice=-1;
			tdmgLabel="Schaden";
			trngLabel="Reichweite";
			tfrLabel="Feuerrate";
		}
		dmgLabel=tdmgLabel;
		rngLabel=trngLabel;
		frLabel=tfrLabel;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				livesLabel.setText("Leben: "+lives);
				goldLabel.setText("Gold: "+gold);
				waveLabel.setText("Welle: "+waveIndex+"/"+waveCount+" "+waveName);
				
				buildDD.setText(ddText);
				buildAE.setText(aeText);
				buildSL.setText(slText);
				buildP.setText(pText);
				
				upgradeDmg.setText(dmgLabel);
				upgradeRange.setText(rngLabel);
				upgradeFirerate.setText(frLabel);
			}
		});
	}
}