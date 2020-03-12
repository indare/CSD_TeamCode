package application.closeFactory.closeProcessor.outputProcessor.noticeBuilder;

import application.Auction;
import application.closeFactory.closeProcessor.output.Notice;

public abstract class MessageBuilder {
    protected Auction auction;
    public MessageBuilder(Auction auction) {
        this.auction = auction;
    }
    public abstract Notice build();
}
