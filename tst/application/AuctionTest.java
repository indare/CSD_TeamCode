/*
 * (c)Copyright Since 2020, SOFTBANK Corp. All rights reserved.
 *
 */

package application;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuctionTest {

  private User generateSellerData(){
    String firstName = "田中";
    String lastName = "太郎";
    String userEmail = "tanaka@tanaka.com";
    String userName = "tarou";
    String password = "password";

    return new User(firstName, lastName, userEmail,userName, password);
  }

  @Test
  public void test_create_auction_by_seller() throws Exception {
      User user = generateSellerData();
      user.setSellerFlag(true);

      Users users = new Users();
      users.register(user);
      users.login(user.getUserName(), user.getPassword());
      Auction auction = new Auction(user, "tarou", "リーダブルコード", 1, LocalDateTime.of(2020, 3, 10, 12,0,0), LocalDateTime.of(2020, 3, 11, 12,0,0));

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
  public void test_can_change_auction_state() throws Exception{
    User user = generateSellerData();
    user.setSellerFlag(true);

    Users users = new Users();
    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    Auction auction = new Auction(user, "tarou", "リーダブルコード", 1, LocalDateTime.of(2020, 3, 10, 12,0,0), LocalDateTime.of(2020, 3, 11, 12,0,0));
    auction.start();
    assertEquals(auction.getState(), AuctionStatus.STARTED);
  }

}
