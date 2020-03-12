package application.closeFactory.closeProcessor;

import application.Auction;

public abstract class CloseProcessor {
    protected Auction auction;

    public CloseProcessor(Auction auction) {
        this.auction = auction;
    }

    public abstract void run();
}
