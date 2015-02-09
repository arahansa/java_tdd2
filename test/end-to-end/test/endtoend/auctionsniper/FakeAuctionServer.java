package test.endtoend.auctionsniper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

public class FakeAuctionServer {
	public static final String ITEM_ID_AS_LOGIN = "auction-%s"; 
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	private static final String AUCTION_PASSWORD = "auction";
	private final String itemId; 
	private final XMPPConnection connection;
	private Chat currentChat;
	private final SingleMessageListener messageListener = new SingleMessageListener();
	
	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		this.connection = new XMPPConnection(XMPP_HOSTNAME);
	}

	public void startSellingItem() throws XMPPException{
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}
	public void reportPrice(int price, int increment, String bidder) throws XMPPException{
		currentChat.sendMessage(String.format("SQLVersion: 1.1; Event: PRICE; "
				+"CurrentPrice: %d; Increment: %d;Bidder: %s", price, increment, bidder));
		
	}
	public void hasReceivedJoinRequestFromSniper() throws InterruptedException{
		messageListener.receivesAmessage(is(anything()));
	}
	
	
	public void annouceClosed() throws XMPPException{
		currentChat.sendMessage("SQLVersion: 1.1; Event: CLOSE;");
	}
	public void stop(){
		connection.disconnect();
	}
	
	public String getItemId() {
		return itemId;
	}
	
	public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException{
		receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
	}
	public void hasReceivedBid(int bid, String sniperId)throws InterruptedException{
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
		receivesAMessageMatching(sniperId, equalTo(String.format(Main.BID_COMMAND_FORMAT, bid)));
	}
	
	private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) throws InterruptedException{
		messageListener.receivesAmessage(messageMatcher);
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
	}
	
	public class SingleMessageListener implements MessageListener{
		private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);
		public void processMessage(Chat chat, Message message){
			messages.add(message);
		}
		
		public void receivesAmessage(Matcher<? super String> messageMatcher) throws InterruptedException{
						final Message message = messages.poll(5, TimeUnit.SECONDS);
						assertThat("Message", message, is(notNullValue()));
						assertThat(message.getBody(), messageMatcher);
		}
		/*public void receivesAMessage() throws InterruptedException{
			assertThat("Message", messages.poll(5, TimeUnit.SECONDS) , is(notNullValue()));
		}*/
	}

	
	
	
	
}
