/*
 * (c)Copyright Since 2020, SOFTBANK Corp. All rights reserved.
 *
 */

package application;

import org.junit.Before;
import org.junit.Test;
import services.PostOffice;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AuctionTest {

  private Users users;

  @Before
  public void setup() {
    this.users = new Users();
  }

  private User generateSellerData(){
    String firstName = "田中";
    String lastName = "太郎";
    String userEmail = "tanaka@tanaka.com";
    String userName = "tarou";
    String password = "password";

    return new User(firstName, lastName, userEmail,userName, password);
  }


  private User generateSuzukiData(){
    String firstName = "鈴木";
    String lastName = "一郎";
    String userEmail = "suzuki@suzuki.com";
    String userName = "suzuki";
    String password = "pass";

    return new User(firstName, lastName, userEmail,userName, password);
  }

  private User generateDavidData(){
    String firstName = "David";
    String lastName = "Bernstein";
    String userEmail = "bernstein@david.com";
    String userName = "david";
    String password = "csd";

    return new User(firstName, lastName, userEmail,userName, password);
  }

  @Test
  public void test_create_auction_by_seller() throws Exception {
    Auction auction = startAuction();
    assertEquals(auction.getState(), AuctionStatus.BEFORE_START);

  }


  @Test (expected=NotSellerCreateAuctionException.class)
  public void test_NotSeller_can_not_create_auction() throws Exception {
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);

    new Auction(tanaka,"tarou", "リーダブルコード", 1, LocalDateTime.of(2020, 3, 10, 12,0,0), LocalDateTime.of(2020, 3, 11, 12,0,0));

  }

  @Test (expected= NoneLoggedInUserCreateAuctionException.class)
  public void test_create_auction_need_login() throws Exception {
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);
    users.setSeller(tanaka.getUserName());

    new Auction(tanaka,"tarou", "リーダブルコード", 1, LocalDateTime.of(2020, 3, 10, 12,0,0), LocalDateTime.of(2020, 3, 11, 12,0,0));

  }

  @Test (expected = StartTimeIsGreaterEndTimeException.class)
  public void test_auction_startTime_should_be_smaller_than_endTime() throws Exception{
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);
    users.setSeller(tanaka.getUserName());
    users.login(tanaka.getUserName(), tanaka.getPassword());

    new Auction(tanaka,"tarou", "リーダブルコード", 1,
        LocalDateTime.of(2020, 3, 11, 12,0,0),
        LocalDateTime.of(2020, 3, 10, 12,0,0));
  }

  @Test (expected = StartTimeIsPassedDateException.class)
  public void test_auction_startTime_should_be_more_than_now() throws Exception{
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);
    users.setSeller(tanaka.getUserName());
    users.login(tanaka.getUserName(), tanaka.getPassword());

    new Auction(tanaka,"tarou", "リーダブルコード", 1,
        LocalDateTime.now().minusSeconds(1),
        LocalDateTime.now());
  }

  @Test
  public void test_can_change_auction_start() throws Exception{
    Auction auction = startAuction();
    auction.onStart();
    assertEquals(auction.getState(), AuctionStatus.STARTED);
  }

  @Test
  public void test_can_change_auction_close() throws Exception{
    Auction auction = startAuction();
    auction.onStart();
    auction.onClose();
    assertEquals(auction.getState(), AuctionStatus.ENDED);
  }

  @Test
  public void test_can_bid_auction() throws Exception{
    Auction auction = startAuction();
    auction.onStart();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());

    suzuki.bid(auction, 1);

    assertThat(auction.getNowPrice(), is(1));
    assertThat(auction.getBidder(), is(suzuki.getUserName()));

  }


  @Test(expected = NoneLoggedInBidAuction.class )
  public void test_need_login_bid_auction() throws Exception{
    Auction auction = startAuction();
    auction.onStart();

    User suzuki = generateSuzukiData();
    users.register(suzuki);

    suzuki.bid(auction, 1);

  }

  @Test(expected = SamePriceException.class)
  public void test_same_price_can_not_bid_twice() throws Exception {
    Auction auction = startAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());

    suzuki.bid(auction, 1);

    User david = generateDavidData();
    users.register(david);
    users.login(david.getUserName(), david.getPassword());

    david.bid(auction, 1);

  }

  @Test(expected = AuctionCreaterBidException.class)
  public void test_Auction_creator_can_not_bid_own_auction() throws Exception{
    Auction auction = startAuction();
    User user = generateSellerData();
    User seller = this.users.findByUserName(user.getUserName());

    seller.bid(auction, 1);

  }

  @Test
  public void test_no_bid_auction_closed() throws Exception {
    Auction auction = startAuction();
    auction.onClose();

    PostOffice postOffice = PostOffice.getInstance();
    assertTrue(postOffice.doesLogContain(generateSellerData().getUserEmail(), auction.getItemName()));

  }

  @Test
  public void test_bid_auction_closed() throws Exception {
    Auction auction = startAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 1);

    auction.onClose();

    String soldMessageInclude = "販売されました";
    String winnerMessageInclude = "落札しました";

    PostOffice postOffice = PostOffice.getInstance();
    assertTrue(postOffice.doesLogContain(generateSellerData().getUserEmail(), soldMessageInclude));
    assertTrue(postOffice.doesLogContain(suzuki.getUserEmail(), winnerMessageInclude) );

  }

  private Auction startAuction() throws Exception {
    User user = generateSellerData();
    user.setSellerFlag(true);

    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    return new Auction(user, "tarou", "リーダブルコード", 1, LocalDateTime.of(2020, 3, 10, 12,0,0), LocalDateTime.of(2020, 3, 11, 12,0,0));
  }
}
