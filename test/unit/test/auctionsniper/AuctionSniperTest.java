package test.auctionsniper;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.Auction;
import auctionsniper.AuctionEventListener.PriceSource;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;
import auctionsniper.SniperState;

@RunWith(JMock.class)
public class AuctionSniperTest {
	protected static final String ITEM_ID = "item-54321";
	private final Mockery context = new Mockery();
	private final SniperListener sniperListener = context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
	private final States sniperState = context.states("sniper");
	
	
	@Test
	public void reportLostWhenAuctionCloses() {
		context.checking(new Expectations(){{
			one(sniperListener).sniperLost();
		}});
		sniper.auctionClosed();
	}
	@Test public void bidsHigherAndReportsBiddingWhenNewPriceArrives(){
		final int price = 1001;
		final int increment = 25;
		final int bid = price + increment;
		context.checking(new Expectations(){{
			one(auction).bid(price+increment);
			atLeast(1).of(sniperListener).sniperBidding(new SniperState(ITEM_ID, price, bid));
		}});
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	
	@Test public void reportsIsWinningWhenCurrentPriceComesFromSniper(){
		context.checking(new Expectations(){{
			atLeast(1).of(sniperListener).sniperWinning();
		}});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
	}
	
	@Test public void reportLostIfAuctionClosesImmediately(){
		context.checking(new Expectations(){{
			atLeast(1).of(sniperListener).sniperLost();
		}});
		sniper.auctionClosed();
	}
	@Test public void reportLostIfAuctionCloseWhenBidding(){
		context.checking(new Expectations(){{
			ignoring(auction);
			allowing(sniperListener).sniperBidding(with(any(SniperState.class)));
				then(sniperState.is("Bidding"));
			atLeast(1).of(sniperListener).sniperLost();
				when(sniperState.is("Bidding"));
		}});
		sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
	}
	
	@Test public void reportsWonIfAuctionClosesWhenWinning(){
		context.checking(new Expectations(){{
			ignoring(auction);
			allowing(sniperListener).sniperWinning();
				then(sniperState.is("Widding"));
			atLeast(1).of(sniperListener).sniperWon();
				when(sniperState.is("Widding"));
		}});
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
	}
}
