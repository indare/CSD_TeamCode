package application.closeFactory.closeProcessor.outputProcessor.email;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;
import application.closeFactory.closeProcessor.outputProcessor.noticeBuilder.ForBidderMessage;

public class WinningNoticeProcessor extends CloseProcessor {
    public WinningNoticeProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        new ForBidderMessage(this.auction).buildEmail().run();
    }
}
