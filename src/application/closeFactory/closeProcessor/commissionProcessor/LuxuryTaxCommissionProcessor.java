package application.closeFactory.closeProcessor.commissionProcessor;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;

import static application.parameters.Parameters.LUXURY_TAX_RATE;

public class LuxuryTaxCommissionProcessor extends CloseProcessor {
    public LuxuryTaxCommissionProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        auction.setNowPrice((int) (auction.getNowPrice() * LUXURY_TAX_RATE));
    }
}
