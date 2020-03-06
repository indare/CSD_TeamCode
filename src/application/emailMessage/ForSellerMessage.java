package application.emailMessage;

import application.Auction;

public class ForSellerMessage extends MessageBuilder {
    public ForSellerMessage(Auction auction) {
        super(auction);
    }

    @Override
    public String build() {
        return super.getAuction().getItemName() + "のオークションに" +
                super.getAuction().getBidderUser().getUserEmail() + "が" +
                super.getAuction().getNowPrice() + "で販売されました。";
    }
}
