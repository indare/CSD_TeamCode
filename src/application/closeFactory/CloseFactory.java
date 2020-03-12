package application.closeFactory;

import application.Auction;
import application.GoodsCategory;
import application.closeFactory.closeProcessor.commissionProcessor.CarCommissionProcessor;
import application.closeFactory.closeProcessor.commissionProcessor.DownloadSoftwareCommissionProcessor;
import application.closeFactory.closeProcessor.commissionProcessor.ETCCommissionProcessor;
import application.closeFactory.closeProcessor.commissionProcessor.LuxuryTaxCommissionProcessor;
import application.closeFactory.closeProcessor.CloseProcessor;
import application.closeFactory.closeProcessor.commissionProcessor.TransactionFeeProcessor;
import application.closeFactory.closeProcessor.outputProcessor.email.NoBidNoticeProcessor;
import application.closeFactory.closeProcessor.outputProcessor.email.SoldNoticeProcessor;
import application.closeFactory.closeProcessor.outputProcessor.email.WinningNoticeProcessor;
import application.closeFactory.closeProcessor.outputProcessor.log.LogProcessor;


import java.util.ArrayList;
import java.util.List;

import static application.parameters.Parameters.HIGH_PRICE;
import static application.parameters.Parameters.LUXURY_TAX_BORDER;
import static java.util.Objects.isNull;

public class CloseFactory {
    public List<CloseProcessor> getProcessor(Auction auction){
        List<CloseProcessor> retList = new ArrayList<>();

        retList.add(new TransactionFeeProcessor(auction));

        if (auction.getGoodsCategory().equals(GoodsCategory.ETC)) {
            retList.add(new ETCCommissionProcessor(auction));
        }

        if (auction.getNowPrice() >= LUXURY_TAX_BORDER) {
            retList.add(new LuxuryTaxCommissionProcessor(auction));
        }

        if (auction.getGoodsCategory().equals(GoodsCategory.CAR)) {
            retList.add(new CarCommissionProcessor(auction));
        }

        if (auction.getGoodsCategory().equals(GoodsCategory.DOWNLOAD_SOFTWARE)){
            retList.add(new DownloadSoftwareCommissionProcessor(auction));
        }

        if (isNull(auction.getBidderUser())) {
            retList.add(new NoBidNoticeProcessor(auction));
        } else {
            retList.add(new SoldNoticeProcessor(auction));
            retList.add(new WinningNoticeProcessor(auction));
        }

        if(needLog(auction)){
            retList.add(new LogProcessor(auction));
        }

        return retList;
    }

    private Boolean needLog(Auction auction){
        return auction.getHours().isOffHours() || auction.getGoodsCategory().equals(GoodsCategory.CAR) || auction.getNowPrice() >= HIGH_PRICE;
    }


}
