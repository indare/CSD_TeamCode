package application.closeFactory.closeProcessor;

import application.Auction;
import application.GoodsCategory;
import application.commission.CarShipCommission;
import application.commission.DownloadSoftwareCommission;
import application.commission.EtcCommission;
import application.commission.LuxuryTaxCommission;

import static application.parameters.Parameters.LUXURY_TAX_BORDER;

public class AuctionCommissionRuleProcessor extends CloseProcessor {
    public AuctionCommissionRuleProcessor(Auction auction) {
        super(auction);
    }

    @Override
    public void run() {

        if (auction.getGoodsCategory().equals(GoodsCategory.ETC)) {
            auction.setBuyerPrice(new EtcCommission(auction.getNowPrice()).getCommission());
        } else if (auction.getGoodsCategory().equals(GoodsCategory.CAR)) {
            if (auction.getNowPrice() >= LUXURY_TAX_BORDER) {
                auction.setBuyerPrice(new CarShipCommission(
                        new LuxuryTaxCommission(auction.getNowPrice()).getCommission()
                ).getCommission());
            } else {
                auction.setBuyerPrice(new CarShipCommission(auction.getNowPrice()).getCommission());
            }
        } else {
            auction.setBuyerPrice(new DownloadSoftwareCommission(auction.getNowPrice()).getCommission());
        }

    }
}
