package application;

import static java.util.Objects.isNull;

public class AuctionBidRule {

    public static void check(Auction auction, User user, int bidPrice) throws NoneLoggedInBidAuction, AuctionCreaterBidException, SamePriceException {
        if(!user.getLoginFlag()){
            throw new NoneLoggedInBidAuction();
        }

        if (auction.getCreateUser().getUserName().equals(user.getUserName())){
            throw new AuctionCreaterBidException();
        }

        if (!isNull(auction.getBidderUser()) && auction.getNowPrice().equals(bidPrice)){
            throw new SamePriceException();
        }
    }
}
