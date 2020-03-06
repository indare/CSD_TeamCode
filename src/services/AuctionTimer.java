package services;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionTimer {
	private Timer timer;
	private Auctionable auctions;
	
	public AuctionTimer() {
	}
	
	public void checkAuction(Auctionable auctions) {
		this.auctions = auctions;
	}
	
	public void start() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() { public void run() {timerTick(); } }, 100, 100);
	}
	
	public void stop() {
		timer.cancel();
	}
	
	private void timerTick() {
		long now = (new Date()).getTime();
		auctions.handleAuctionEvents(now);
	}
}
