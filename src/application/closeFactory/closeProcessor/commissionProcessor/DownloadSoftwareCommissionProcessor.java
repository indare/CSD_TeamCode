package application.closeFactory.closeProcessor.commissionProcessor;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;

public class DownloadSoftwareCommissionProcessor extends CloseProcessor {
    public DownloadSoftwareCommissionProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        auction.setBuyerPrice(auction.getNowPrice());
    }
}
