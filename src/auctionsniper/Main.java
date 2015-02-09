package auctionsniper;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import auctionsniper.ui.MainWindow;

public class Main {
	public static final String SNIPER_STATUS_NAME = "Joining";
	public static final String MAIN_WINDOW_NAME = "Auction Sniper";
	private MainWindow ui;

	public Main() throws Exception {
		startUserInterface();
	}

	private void startUserInterface() throws Exception{
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow();
			}
		});
		
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();
	}

}
