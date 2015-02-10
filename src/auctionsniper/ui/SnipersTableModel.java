package auctionsniper.ui;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperState;
import static test.endtoend.auctionsniper.ApplicationRunner.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel{
		private final static SniperState STARTING_UP  = new SniperState("", 0, 0);
		private String statuxText = MainWindow.STATUS_JOINING;
		private SniperState sniperState = STARTING_UP;
	
		private String statusText = STATUS_JOINING;
		@Override
		public int getColumnCount() {
			return Column.values().length;
		}

		@Override
		public int getRowCount() {
			return 1;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (Column.at(columnIndex)) {
			case ITEM_IDENTIFIER:return sniperState.itemId;		
			case LAST_PRICE : return sniperState.lastPrice;
			case LAST_BID : return sniperState.lastBid;
			case SNIPER_STATUS : return statusText;
			default : throw new IllegalArgumentException("No Column at "+columnIndex);
			}
		}

		public void setStatusText(String newStatusText) {
			statusText = newStatusText;
			fireTableRowsUpdated(0, 0);
		}

		public void sniperStatusChanged(SniperState newSniperSate, String newStatusText) {
			sniperState = newSniperSate;
			statusText = newStatusText;
			fireTableRowsUpdated(0, 0);
		}
		
	}