package test.endtoend.auctionsniper;

import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static org.hamcrest.Matchers.equalTo;
import auctionsniper.Main;
import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;


public class AuctionSniperDriver extends JFrameDriver{

	public AuctionSniperDriver(int timeoutMillis) {
		super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME),showingOnScreen()),
		new AWTEventQueueProber(timeoutMillis, 100));
	}
	public void showsSniperStatus(String statusText){
		new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
		//new JLabelDriver(this, named(Main.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
	}
	public void showsSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
		JTableDriver table = new JTableDriver(this);
		table.hasRow(matching(withLabelText(itemId), withLabelText(String.valueOf(lastPrice)), 
				withLabelText(String.valueOf(lastBid)) , withLabelText(statusText)));
		
	}
	
	

}
