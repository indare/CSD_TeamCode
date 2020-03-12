package application.closeFactory.closeProcessor.outputProcessor.noticeBuilder;

import application.Auction;
import application.closeFactory.closeProcessor.output.Notice;

public class ForSellerMessage extends MessageBuilder {
    public ForSellerMessage(Auction auction) {
        super(auction);
    }

    @Override
    public Notice build() {
        return new Notice(auction.getCreateUser().getUserEmail(), buildMessage());
    }

    private String buildMessage(){
        return auction.getItemName() + "のオークションに" +
                auction.getBidderUser().getUserEmail() + "が" +
                auction.getNowPrice() + "で販売されました。";
    }
}
