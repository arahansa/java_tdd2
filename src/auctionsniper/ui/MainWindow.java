package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperState;

public class MainWindow extends JFrame{
	private static final String MAIN_WINDOW_NAME = "Auction Sniper";
	private static final String SNIPER_STATUS_NAME = "Joining";
	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding";
	public static final String STATUS_WINNING = "Winning";
	public static final String STATUS_WON = "Won";
	private static final String SNIPERS_TABLE_NAME = "Snipers Table";
	private final JLabel sniperstatus =  createLabel(STATUS_JOINING);
	
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	public MainWindow() {
		super("Auction Sniper");
		setName(MAIN_WINDOW_NAME);
		fillContentPane(makeSnipersTable());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	private void fillContentPane(JTable snipersTable) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
	}
	private JTable makeSnipersTable() {
		final JTable snipersTable = new JTable(snipers);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}
	
	public void showStatusText(String statusText){
		snipers.setStatusText(statusText);
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
	public void sniperStatusChanged(SniperState state, String statusText) {
		snipers.sniperStatusChanged(state, statusText);
		
	}
	
	
}
