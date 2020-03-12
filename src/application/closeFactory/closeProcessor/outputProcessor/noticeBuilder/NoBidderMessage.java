package application.closeFactory.closeProcessor.outputProcessor.noticeBuilder;

import application.Auction;
import application.closeFactory.closeProcessor.output.Notice;

public class NoBidderMessage extends MessageBuilder {
    public NoBidderMessage(Auction auction) {
        super(auction);
    }

    @Override
    public Notice build() {
        return new Notice(auction.getCreateUser().getUserEmail(), buildMessage());
    }

    private String buildMessage(){
        return auction.getItemName() + "のオークションに入札者はいませんでした。";
    }
}