package application.emailMessage;

import application.Auction;

public abstract class MessageBuilder {
    private Auction auction;
    public MessageBuilder(Auction auction) {
        this.auction = auction;
    }

    public Auction getAuction(){
        return this.auction;
    }
    public abstract String build();
}
