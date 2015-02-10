package test.endtoend.auctionsniper;


import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	protected static final String XMPP_HOSTNAME = "localhost";
	public static final String STATUS_JOINING = "Joining";
	private static final String STATUS_LOST = "Lost";
	public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";
	private static final String STATUS_WON = "Won";
	private String itemId;
	
	private AuctionSniperDriver driver; 
	public void startBiddingIn(final FakeAuctionServer auction){
		itemId = auction.getItemId();
		Thread thread = new Thread("Test Application"){
			@Override
			public void run() {
				try{
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
				}catch(Exception e){
					e.printStackTrace();
				}
			};
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.showsSniperStatus(STATUS_JOINING);
	}
	
	public void stop(){
		if(driver != null){
			driver.dispose();
		}
	}
	public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
		driver.showsSniperStatus(itemId, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
	}
	public void hasShownSniperIsWinning(int winningBid) {
		driver.showsSniperStatus(itemId, winningBid, winningBid, MainWindow.STATUS_WINNING);
		
	}
	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(STATUS_LOST);
	}
	public void showsSniperHasWonAuction(int lastPrice) {
		driver.showsSniperStatus(itemId, lastPrice, lastPrice, STATUS_WON);
	}
	
}
