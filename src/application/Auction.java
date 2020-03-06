
package application;


import java.time.LocalDateTime;
import java.util.List;

public class Auction {
    private String itemName;
    private int startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private AuctionStatus state;
    private Integer nowPrice;
    private User createUser;
    private User bidderUser;
    private GoodsCategory goodsCategory;
    private Integer sellerPrice;
    private Integer buyerPrice;

    public Auction(User createUser, GoodsCategory goodsCategory,String itemName, int startPrice, LocalDateTime startDate, LocalDateTime endDate)
            throws NotSellerCreateAuctionException, NoneLoggedInUserCreateAuctionException, StartTimeIsGreaterEndTimeException, StartTimeIsPassedDateException {

        if (!createUser.getSellerFlag()) {
            throw new NotSellerCreateAuctionException();
        }

        if (!createUser.getLoginFlag()) {
            throw new NoneLoggedInUserCreateAuctionException();
        }

        if (startDate.isAfter(endDate)) {
            throw new StartTimeIsGreaterEndTimeException();
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new StartTimeIsPassedDateException();
        }

        this.itemName = itemName;
        this.startPrice = startPrice;
        this.nowPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = AuctionStatus.BEFORE_START;
        this.createUser = createUser;
        this.goodsCategory = goodsCategory;
    }

    public AuctionStatus getState() {
        return this.state;
    }

    public void onStart() {
        this.state = AuctionStatus.STARTED;
    }

    public Integer getNowPrice() {
        return this.nowPrice;
    }

    public void setNowPrice(int bidPrice) {
        this.nowPrice = bidPrice;
    }

    public void onClose() {
        this.state = AuctionStatus.ENDED;

        this.sellerPrice = (int) (0.98 * this.nowPrice);

        AuctionCommissionRuleFactory auctionCommissionRuleFactory = new AuctionCommissionRuleFactory(this);
        this.buyerPrice = auctionCommissionRuleFactory.calcCommission().getCommission();

        AuctionNoticeFactory factory = new AuctionNoticeFactory(this);
        List<Notice> noticeList = factory.getNotice();

        noticeList.forEach(
                (Notice it) -> {
                    it.send();
                }
        );
    }

    public String getItemName() {
        return this.itemName;
    }

    public User getCreateUser() {
        return this.createUser;
    }

    public void setBidderUser(User user) {
        this.bidderUser = user;
    }

    public User getBidderUser() {
        return this.bidderUser;
    }


    public Integer getSellerPrice() {
        return this.sellerPrice;
    }

    public Integer getBuyerPrice() {
        return this.buyerPrice;
    }

    public GoodsCategory getGoodsCategory() {
        return this.goodsCategory;
    }
}
