package test.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionMessageTranslator;

public class AuctionMessageTranslatorTest {
	public static final Chat UNUSED_CHAT = null;
	
	private final Mockery context = new Mockery();
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);
	
	
	
	@Test public void notifiesAuctionCloseWHenCloseMessageReceived(){
		context.checking(new Expectations(){{
			oneOf(listener).auctionClosed();
		}});
		
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: CLOSE;");
		translator.processMessage(UNUSED_CHAT, message);
	}
}
