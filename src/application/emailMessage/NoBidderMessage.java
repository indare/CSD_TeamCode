package application.emailMessage;

import application.Auction;

public class NoBidderMessage extends MessageBuilder {
    public NoBidderMessage(Auction auction) {
        super(auction);
    }

    @Override
    public String build() {
        return super.getAuction().getItemName() + "のオークションに入札者はいませんでした。";
    }
}