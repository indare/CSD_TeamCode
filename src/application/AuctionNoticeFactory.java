package application;

import services.PostOffice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class AuctionNoticeFactory {

    private Auction auction;

    public AuctionNoticeFactory(Auction auction) {
        this.auction = auction;
    }

    public List<Notice> getNotice() {

        List<Notice> ret = new ArrayList<>();

        if (isNull(this.auction.getBidderUser())) {
            String message = auction.getItemName() + "のオークションに入札者はいませんでした。";

            Notice notice = new Notice(this.auction.getCreateUser().getUserEmail(),message);
            ret.add(notice);
        } else {

            String sellerMessage = this.auction.getItemName() + "のオークションに" +
                    this.auction.getBidderUser().getUserEmail() + "が" +
                    this.auction.getNowPrice() + "で販売されました。";

            Notice noticeForSeller = new Notice(this.auction.getCreateUser().getUserEmail(),sellerMessage);
            ret.add(noticeForSeller);

            String bidderMessage = "おめでとうございます。" +
                    this.auction.getBidderUser().getUserEmail() + "からの" +
                    this.auction.getItemName() + "のオークションを" +
                    this.auction.getNowPrice() + "で落札しました。";

            Notice noticeForBidder = new Notice(this.auction.getBidderUser().getUserEmail(),bidderMessage);
            ret.add(noticeForBidder);

        }

        return ret;
    }
}
