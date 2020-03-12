package application.closeFactory.closeProcessor.commissionProcessor;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;

import static application.parameters.Parameters.DELIVERY_COST;

public class ETCCommissionProcessor extends CloseProcessor {
    public ETCCommissionProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        auction.setBuyerPrice(auction.getNowPrice() + DELIVERY_COST);
    }
}
