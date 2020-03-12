package application.closeFactory.closeProcessor.outputProcessor.noticeBuilder;

import application.Auction;
import application.closeFactory.closeProcessor.outputProcessor.output.Notice;

public class ForBidderMessage extends MessageBuilder{
    public ForBidderMessage(Auction auction) {
        super(auction);
    }

    @Override
    public Notice build() {
        return new Notice(auction.getBidderUser().getUserEmail(), buildMessage());
    }

    private String buildMessage(){
        return "おめでとうございます。" +
                auction.getCreateUser().getUserEmail() + "からの" +
                auction.getItemName() + "のオークションを" +
                auction.getNowPrice() + "で落札しました。";

    }
}
