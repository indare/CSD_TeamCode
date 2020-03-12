package application.closeFactory.closeProcessor;

import application.Auction;

import static application.parameters.Parameters.TRANSACTION_FEE;

public class SellerPriceAdjustProcessor extends CloseProcessor {

    public SellerPriceAdjustProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        auction.setSellerPrice((int) (TRANSACTION_FEE * auction.getNowPrice()));
    }
}
