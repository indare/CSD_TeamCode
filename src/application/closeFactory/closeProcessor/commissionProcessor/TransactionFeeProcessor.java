package application.closeFactory.closeProcessor.commissionProcessor;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;

import static application.parameters.Parameters.TRANSACTION_FEE;

public class TransactionFeeProcessor extends CloseProcessor {

    public TransactionFeeProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        auction.setSellerPrice((int) (TRANSACTION_FEE * auction.getNowPrice()));
    }
}
