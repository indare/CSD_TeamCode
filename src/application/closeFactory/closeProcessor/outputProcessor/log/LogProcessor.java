package application.closeFactory.closeProcessor.outputProcessor.log;

import application.Auction;
import application.closeFactory.closeProcessor.CloseProcessor;
import services.AuctionLogger;

import static application.parameters.Parameters.LOG_FILE;


public class LogProcessor extends CloseProcessor {
    public LogProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {

        AuctionLogger auctionLogger = AuctionLogger.getInstance();

        String message = "onclose " +
                "category:" + auction.getGoodsCategory() +
                "createUser:" + auction.getCreateUser() +
                "bidder:" + auction.getBidderUser() +
                "finalPrice:" + auction.getNowPrice();

        auctionLogger.log(LOG_FILE, message);
    }


}
