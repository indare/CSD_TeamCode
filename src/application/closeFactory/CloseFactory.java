package application.closeFactory;

import application.Auction;
import application.closeFactory.closeProcessor.AuctionCommissionRuleProcessor;
import application.closeFactory.closeProcessor.CloseProcessor;
import application.closeFactory.closeProcessor.SellerPriceAdjustProcessor;
import application.closeFactory.closeProcessor.SendMessageProcessor;

import java.util.ArrayList;
import java.util.List;

public class CloseFactory {
    public List<CloseProcessor> getProcessor(Auction auction){
        List<CloseProcessor> retList = new ArrayList<>();

        retList.add(new SellerPriceAdjustProcessor(auction));
        retList.add(new AuctionCommissionRuleProcessor(auction));
        retList.add(new SendMessageProcessor(auction));
        return retList;
    }
}
