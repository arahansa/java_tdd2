package test.endtoend.auctionsniper;

import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndtoEndTest {
	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private final ApplicationRunner application = new ApplicationRunner();
	
	@Test public void sniperJoinAuctionUntilAuctionCloses() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper();
		auction.annouceClosed();
		application.showsSniperHasLostAuction();
	}
	
	/*@Test public void sniperMakesAHigerBidBUtLoses() throws Exception{
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper();
		
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding();
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		auction.annouceClosed();
		application.showsSniperHasLostAuction();
	}*/
	
	
	@After public void stopAuction(){
		auction.stop();
	}
	@After public void stopApplication(){
		application.stop();
	}
}
