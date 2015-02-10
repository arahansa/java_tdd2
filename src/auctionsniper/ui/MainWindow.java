package auctionsniper.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame{
	private static final String MAIN_WINDOW_NAME = "Auction Sniper";
	private static final String SNIPER_STATUS_NAME = "Joining";
	private static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding";
	public static final String STATUS_WINNING = "Winning";
	public static final String STATUS_WON = "Won";
	private final JLabel sniperstatus =  createLabel(STATUS_JOINING);
	
	public MainWindow() {
		super("Auction Sniper");
		setName(MAIN_WINDOW_NAME);
		add(sniperstatus);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private static JLabel createLabel(String initalText){
		JLabel result = new JLabel(initalText);
		result.setName(SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.black));
		return result;
	}
	public void showStatus(String status) {
		sniperstatus.setText(status);
	}
}
