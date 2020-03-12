package application.closeFactory.closeProcessor.commissionProcessor;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;

import static application.parameters.Parameters.CAR_DELIVERY_COST;

public class CarCommissionProcessor extends CloseProcessor {
    public CarCommissionProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        auction.setBuyerPrice(auction.getNowPrice() + CAR_DELIVERY_COST);
    }
}
