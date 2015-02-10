package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	private static final String ITEM_ID = "item-54321";


	private boolean isWinning = false;
	
	
	private final SniperListener sniperListener;
	private final Auction auction;

	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.sniperListener = sniperListener;
	}

	@Override
	public void auctionClosed() {
		if(isWinning){
			sniperListener.sniperWon();
		}else{			
			sniperListener.sniperLost();
		}
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = priceSource == PriceSource.FromSniper;
		if(isWinning){
			sniperListener.sniperWinning();
		}else{
			int bid = price + increment;
			auction.bid(bid);
			sniperListener.sniperBidding(new SniperState(ITEM_ID, price, bid));
		}
	}

}
