package application.closeFactory.notice;

import application.Auction;
import application.emailMessage.ForBidderMessage;
import application.emailMessage.ForSellerMessage;
import application.emailMessage.MessageBuilder;
import application.emailMessage.NoBidderMessage;

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

            MessageBuilder messageBuilder = new NoBidderMessage(this.auction);

            Notice notice = new Notice(this.auction.getCreateUser().getUserEmail(),messageBuilder.build());
            ret.add(notice);

        } else {

            MessageBuilder forSellerMessage = new ForSellerMessage(this.auction);

            Notice noticeForSeller = new Notice(this.auction.getCreateUser().getUserEmail(),forSellerMessage.build());
            ret.add(noticeForSeller);

            MessageBuilder forBidderMessage = new ForBidderMessage(this.auction);

            Notice noticeForBidder = new Notice(this.auction.getBidderUser().getUserEmail(),forBidderMessage.build());
            ret.add(noticeForBidder);

        }

        return ret;
    }
}
