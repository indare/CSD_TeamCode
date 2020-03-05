/*
 * (c)Copyright Since 2020, SOFTBANK Corp. All rights reserved.
 *
 */

package application;


import java.time.LocalDateTime;

public class Auction {
  private String userName;
  private String itemDescription;
  private int startPrice;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private AuctionStatus state;

  public Auction(User user, String userName, String itemDescription, int startPrice, LocalDateTime startDate, LocalDateTime endDate)
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
    this.itemDescription = itemDescription;
    this.startPrice = startPrice;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = AuctionStatus.BEFORE_START;

  }

  public AuctionStatus getState() {
    return this.state;
  }

  public void start() {
    this.state = AuctionStatus.STARTED;
  }
}
