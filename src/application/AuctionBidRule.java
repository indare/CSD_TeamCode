package application;

import application.exception.AuctionCreatorBidException;
import application.exception.EBabyException;
import application.exception.NoneLoggedInBidAuctionException;
import application.exception.SamePriceException;

import static java.util.Objects.isNull;

public class AuctionBidRule {

    public static void check(Auction auction, User user, Integer bidPrice) throws EBabyException {
        if (!user.getLoginFlag()) {
            throw new NoneLoggedInBidAuctionException();
        }

        if (auction.getCreateUser().getUserName().equals(user.getUserName())) {
            throw new AuctionCreatorBidException();
        }

        if (!isNull(auction.getBidderUser()) && auction.getNowPrice().equals(bidPrice)) {
            throw new SamePriceException();
        }
    }
}
