package application.emailMessage;

import application.Auction;

public class ForBidderMessage extends MessageBuilder{
    public ForBidderMessage(Auction auction) {
        super(auction);
    }

    @Override
    public String build() {
        return "おめでとうございます。" +
                super.getAuction().getBidderUser().getUserEmail() + "からの" +
                super.getAuction().getItemName() + "のオークションを" +
                super.getAuction().getNowPrice() + "で落札しました。";
    }
}
