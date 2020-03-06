/*
 * (c)Copyright Since 2020, SOFTBANK Corp. All rights reserved.
 *
 */

package application;


import services.PostOffice;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class Auction {
  private String userName;
  private String itemName;
  private int startPrice;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private AuctionStatus state;
  private Integer nowPrice;
  private String bidder;
  private User user;
  private User bidderUser;

  public Auction(User user, String userName, String itemName, int startPrice, LocalDateTime startDate, LocalDateTime endDate)
      throws NotSellerCreateAuctionException, NoneLoggedInUserCreateAuctionException, StartTimeIsGreaterEndTimeException, StartTimeIsPassedDateException {

    if (!user.getSellerFlag()) {
      throw new NotSellerCreateAuctionException();
    }

    if (!user.getLoginFlag()) {
      throw new NoneLoggedInUserCreateAuctionException();
    }

    if (startDate.isAfter(endDate)) {
      throw new StartTimeIsGreaterEndTimeException();
    }

    if (startDate.isBefore(LocalDateTime.now())) {
      throw new StartTimeIsPassedDateException();
    }

    this.userName = userName;
    this.itemName = itemName;
    this.startPrice = startPrice;
    this.nowPrice = startPrice;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = AuctionStatus.BEFORE_START;
    this.user = user;
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

  public String getBidder() {
    return this.bidder;
  }

  public void setNowPrice(int bidPrice) {
    this.nowPrice = bidPrice;
  }

  public void setBidder(String userName) {
    this.bidder = userName;
  }

  public String getUserName() {
    return this.userName;
  }

  public void onClose() {
    this.state = AuctionStatus.ENDED;

    if (isNull(this.bidder)){
      String message = this.getItemName() + "のオークションに入札者はいませんでした。";

      PostOffice postOffice = PostOffice.getInstance();
      postOffice.sendEMail(user.getUserEmail(), message);
    } else {

      String sellerMessage = this.getItemName() + "のオークションに" + this.bidderUser + "が" + this.nowPrice + "で販売されました。";

      PostOffice postOffice = PostOffice.getInstance();
      postOffice.sendEMail(user.getUserEmail(), sellerMessage);

      String bidderMessage = "おめでとうございます。" + user.getUserEmail() + "からの" + this.itemName + "のオークションを" + this.nowPrice + "で落札しました。";

      postOffice.sendEMail(bidderUser.getUserEmail(), bidderMessage);
    }

  }

  public String getItemName() {
    return this.itemName;
  }

  public void setBidderUser(User user) {
    this.bidderUser = user;
  }
}
