package application.closeFactory.closeProcessor.outputProcessor.email;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;
import application.closeFactory.closeProcessor.outputProcessor.noticeBuilder.NoBidderMessage;

public class NoBidNoticeProcessor extends CloseProcessor
{
    public NoBidNoticeProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        new NoBidderMessage(this.auction).build().run();
    }
}
