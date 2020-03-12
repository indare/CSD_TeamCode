
package application;


import application.closeFactory.CloseFactory;
import application.closeFactory.closeProcessor.CloseProcessor;
import application.exception.*;
import services.AuctionLogger;
import services.Hours;
import services.OffHours;

import java.time.LocalDateTime;
import java.util.List;

import static application.parameters.Parameters.*;

public class Auction {

    private String itemName;
    private Integer startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private AuctionStatus state;
    private Integer nowPrice;
    private User createUser;
    private User bidderUser;
    private GoodsCategory goodsCategory;
    private Integer sellerPrice;
    private Integer buyerPrice;
    private Hours hours;

    public Auction(User createUser, GoodsCategory goodsCategory, String itemName, Integer startPrice, LocalDateTime startDate, LocalDateTime endDate) throws EBabyException {

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
        this.hours = OffHours.getInstance();
    }

    public Auction(User createUser, GoodsCategory goodsCategory, String itemName, Integer startPrice, LocalDateTime startDate, LocalDateTime endDate, Hours hours) throws EBabyException {
        this(createUser, goodsCategory, itemName, startPrice, startDate, endDate);
        this.hours = hours;
    }


    public AuctionStatus getState() {
        return this.state;
    }

    public Integer getNowPrice() {
        return this.nowPrice;
    }

    public void setNowPrice(Integer bidPrice) {
        this.nowPrice = bidPrice;
    }

    public void setSellerPrice(Integer sellerPrice){
        this.sellerPrice = sellerPrice;
    }

    public void setBuyerPrice(Integer buyerPrice){
        this.buyerPrice = buyerPrice;
    }

    public Hours getHours(){
        return this.hours;
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

    public void onStart() {
        this.state = AuctionStatus.STARTED;
    }

    public void onClose() {
        this.state = AuctionStatus.ENDED;
        new CloseFactory().getProcessor(this).forEach(CloseProcessor::run);
    }
}
