package application.closeFactory.closeProcessor;

import application.Auction;
import application.closeFactory.notice.AuctionNoticeFactory;
import application.GoodsCategory;
import application.closeFactory.notice.Notice;
import services.AuctionLogger;

import java.util.List;

import static application.parameters.Parameters.HIGH_PRICE;
import static application.parameters.Parameters.LOG_FILE;

public class SendMessageProcessor extends CloseProcessor {
    public SendMessageProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {
        AuctionNoticeFactory factory = new AuctionNoticeFactory(auction);
        List<Notice> noticeList = factory.getNotice();
        AuctionLogger auctionLogger = AuctionLogger.getInstance();

        noticeList.forEach( notice -> {
            notice.send();
            if (needLogging()) {
                auctionLogger.log(LOG_FILE, notice.getMessage());
            }
        });
    }

    private Boolean needLogging() {
        return auction.getHours().isOffHours() || auction.getGoodsCategory().equals(GoodsCategory.CAR) || auction.getNowPrice() >= HIGH_PRICE;
    }

}
