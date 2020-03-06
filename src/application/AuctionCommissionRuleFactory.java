package application;

import application.commission.*;

public class AuctionCommissionRuleFactory {

    private Auction auction;

    public AuctionCommissionRuleFactory(Auction auction) {
        this.auction = auction;
    }

    public Commission calcCommission() {
        if (this.auction.getGoodsCategory().equals(GoodsCategory.ETC)) {
            return new EtcCommission(auction.getNowPrice());
        } else if (this.auction.getGoodsCategory().equals(GoodsCategory.CAR)){
            if (this.auction.getNowPrice() >= 50000) {
                LuxuryTaxCommission luxuryTaxCommission =
                        new LuxuryTaxCommission(auction.getNowPrice());
                return new CarShipCommission(
                        luxuryTaxCommission.getCommission()
                );
            }else{
                return new CarShipCommission(auction.getNowPrice());
            }

        } else {
            return new DownloadSoftwareCommission(auction.getNowPrice());
        }
    }
}
