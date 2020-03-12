package application.closeFactory.closeProcessor.outputProcessor.email;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;
import application.closeFactory.closeProcessor.outputProcessor.noticeBuilder.ForSellerMessage;

public class SoldNoticeProcessor extends CloseProcessor
{
    public SoldNoticeProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        new ForSellerMessage(this.auction).buildEmail().run();
    }
}
