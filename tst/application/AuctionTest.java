package application;

import application.exception.*;
import org.junit.*;
import services.AlwaysFalseOffHours;
import services.AlwaysTrueOffHours;
import services.AuctionLogger;
import services.PostOffice;

import java.io.File;
import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AuctionTest {

  private Users users;
  private AlwaysTrueOffHours alwaysTrueOffHours;
  private static String fileName =  "log/log.txt";

  @Before
  public void setup() {
    this.users = new Users();
    this.alwaysTrueOffHours = new AlwaysTrueOffHours();
  }

  @AfterClass
  public static void tearDown() {
    File newdir = new File(fileName);
    newdir.delete();
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


  @Test
  public void test_NotSeller_can_not_create_auction() throws EBabyException {
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);

    try {
      new Auction(tanaka, GoodsCategory.ETC, "リーダブルコード", 1, LocalDateTime.of(2021, 3, 10, 12, 0, 0), LocalDateTime.of(2021, 3, 11, 12, 0, 0));
    }catch (NotSellerCreateAuctionException expect){}

  }

  @Test
  public void test_create_auction_need_login() throws EBabyException {
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);
    users.setSeller(tanaka.getUserName());

    try {
      new Auction(tanaka, GoodsCategory.ETC, "リーダブルコード", 1, LocalDateTime.of(2021, 3, 10, 12, 0, 0), LocalDateTime.of(2021, 3, 11, 12, 0, 0));
    }catch (NoneLoggedInUserCreateAuctionException expect) {}

  }

  @Test
  public void test_auction_startTime_should_be_smaller_than_endTime() throws EBabyException {
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);
    users.setSeller(tanaka.getUserName());
    users.login(tanaka.getUserName(), tanaka.getPassword());

    try {
      new Auction(tanaka, GoodsCategory.ETC, "リーダブルコード", 1,
              LocalDateTime.of(2021, 3, 11, 12, 0, 0),
              LocalDateTime.of(2021, 3, 10, 12, 0, 0));
    }catch(StartTimeIsGreaterEndTimeException expect){}
  }

  @Test
  public void test_auction_startTime_should_be_more_than_now() throws EBabyException {
    User tanaka = generateSellerData();

    Users users = new Users();
    users.register(tanaka);
    users.setSeller(tanaka.getUserName());
    users.login(tanaka.getUserName(), tanaka.getPassword());

    try {
      new Auction(tanaka, GoodsCategory.ETC, "リーダブルコード", 1,
              LocalDateTime.now().minusSeconds(1),
              LocalDateTime.now());
    }catch (StartTimeIsPassedDateException expect){}
  }

  @Test
  public void test_can_change_auction_start() throws EBabyException {
    Auction auction = startAuction();
    auction.onStart();
    assertEquals(auction.getState(), AuctionStatus.STARTED);
  }

  @Test
  public void test_can_change_auction_close() throws EBabyException {
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
    assertThat(auction.getBidderUser().getUserName(), is(suzuki.getUserName()));

  }

  @Test
  public void test_need_login_bid_auction() throws Exception{
    Auction auction = startAuction();
    auction.onStart();

    User suzuki = generateSuzukiData();
    users.register(suzuki);

    try {
      suzuki.bid(auction, 1);
    }catch (NoneLoggedInBidAuctionException expect){}

  }

  @Test
  public void test_same_price_can_not_bid_twice() throws Exception {
    Auction auction = startAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());

    suzuki.bid(auction, 1);

    User david = generateDavidData();
    users.register(david);
    users.login(david.getUserName(), david.getPassword());

    try {
      david.bid(auction, 1);
    }catch (SamePriceException expect){}

  }

  @Test
  public void test_Auction_creator_can_not_bid_own_auction() throws Exception{
    Auction auction = startAuction();
    User user = generateSellerData();
    User seller = this.users.findByUserName(user.getUserName());

    try {
      seller.bid(auction, 1);
    }catch (AuctionCreatorBidException expect){}

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


  @Test
  public void test_no_body_bid_auction_send_email() throws Exception {
    Auction auction = startAuction();

    auction.onClose();

    String soldMessageInclude = "入札者はいませんでした。";

    PostOffice postOffice = PostOffice.getInstance();
    assertTrue(postOffice.doesLogContain(auction.getCreateUser().getUserEmail(), soldMessageInclude));

  }
  @Test
  public void test_adjust_transaction_prices_for_ETC() throws Exception{
    Auction auction = startAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 100);

    auction.onClose();

    assertThat(auction.getSellerPrice(), is(98));
    assertThat(auction.getBuyerPrice(), is(110));

  }

  @Test
  public void test_adjust_transaction_prices_for_download_soft() throws Exception {
    Auction auction = startDownloadSoftAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 100);

    auction.onClose();

    assertThat(auction.getSellerPrice(), is(98));
    assertThat(auction.getBuyerPrice(), is(100));
  }

  @Test
  public void test_adjust_transaction_prices_for_cheap_car() throws Exception {
    Auction auction = startCarAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 100);

    auction.onClose();

    assertThat(auction.getSellerPrice(), is(98));
    assertThat(auction.getBuyerPrice(), is(1100));
  }

  @Test
  public void test_adjust_transaction_prices_for_expensive_car() throws Exception {

    Integer Price = 50000;
    Integer SellerPrice = (int) (0.98 * Price);
    Integer BuyerPrice = (int) (Price * 1.04 + 1000);

    Auction auction = startCarAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, Price);

    auction.onClose();

    assertThat(auction.getSellerPrice(), is(SellerPrice));
    assertThat(auction.getBuyerPrice(), is(BuyerPrice));
  }


  @Test
  public void test_logged_none_sell_auction() throws Exception {
    Auction auction = startAuction();

    auction.onClose();

    AuctionLogger auctionLogger = AuctionLogger.getInstance();
    assertTrue(auctionLogger.findMessage(fileName, logMessage(auction)));

  }

  @Test
  public void test_logged_sell_car() throws Exception {

    Auction auction = startCarAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 100);

    auction.onClose();

    String sellerMessage = auction.getItemName() + "のオークションに" +
            auction.getBidderUser().getUserEmail() + "が" +
            auction.getNowPrice() + "で販売されました。";

    String bidderMessage = "おめでとうございます。" +
            auction.getBidderUser().getUserEmail() + "からの" +
            auction.getItemName() + "のオークションを" +
            auction.getNowPrice() + "で落札しました。";

    AuctionLogger auctionLogger = AuctionLogger.getInstance();
    assertTrue(auctionLogger.findMessage(fileName, logMessage(auction)));

  }

  @Test
  public void test_none_logged_sale_at_cheep_etc_item() throws Exception {

    Auction auction = startAuctionForOnTime();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 100);

    auction.onClose();

    String sellerMessage = auction.getItemName() + "のオークションに" +
            auction.getBidderUser().getUserEmail() + "が" +
            auction.getNowPrice() + "で販売されました。";

    String bidderMessage = "おめでとうございます。" +
            auction.getBidderUser().getUserEmail() + "からの" +
            auction.getItemName() + "のオークションを" +
            auction.getNowPrice() + "で落札しました。";

    AuctionLogger auctionLogger = AuctionLogger.getInstance();
    assertFalse(auctionLogger.findMessage(fileName, sellerMessage));
    assertFalse(auctionLogger.findMessage(fileName, bidderMessage));

  }

  @Test
  public void test_logged_sell_10000_Etc() throws Exception {

    Auction auction = startAuction();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 10000);

    auction.onClose();

    AuctionLogger auctionLogger = AuctionLogger.getInstance();
    assertTrue(auctionLogger.findMessage(fileName, logMessage(auction)));

  }

  @Test
  public void test_logged_ended_all_auctions_when_closed() throws Exception {
    Auction auction = startAuctionForOffHour();

    User suzuki = generateSuzukiData();
    users.register(suzuki);
    users.login(suzuki.getUserName(), suzuki.getPassword());
    suzuki.bid(auction, 1);

    auction.onClose();

    AuctionLogger auctionLogger = AuctionLogger.getInstance();
    assertTrue(auctionLogger.findMessage(fileName, logMessage(auction)));

  }

  private Auction startAuction() throws EBabyException {
    User user = generateSellerData();
    user.setSellerFlag(true);

    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    return new Auction(user, GoodsCategory.ETC,  "リーダブルコード", 1, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), alwaysTrueOffHours);
  }

  private Auction startAuctionForOffHour() throws EBabyException {
    User user = generateSellerData();
    user.setSellerFlag(true);

    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    return new Auction(user,
            GoodsCategory.ETC,
            "リーダブルコード",
            1,
            LocalDateTime.now().plusHours(1),
            LocalDateTime.now().plusHours(2),
            alwaysTrueOffHours);
  }

  private Auction startAuctionForOnTime() throws EBabyException {
    User user = generateSellerData();
    user.setSellerFlag(true);

    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    AlwaysFalseOffHours alwaysFalseOffHours = new AlwaysFalseOffHours();
    return new Auction(user,
            GoodsCategory.ETC,
            "aaaaaa",
            1,
            LocalDateTime.now().plusHours(1),
            LocalDateTime.now().plusHours(2),
            alwaysFalseOffHours);
  }

  private Auction startDownloadSoftAuction() throws EBabyException {
    User user = generateSellerData();
    user.setSellerFlag(true);

    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    return new Auction(user, GoodsCategory.DOWNLOAD_SOFTWARE,  "リーダブルコード", 1, LocalDateTime.now().plusHours(1),  LocalDateTime.now().plusHours(2), alwaysTrueOffHours);
  }

  private Auction startCarAuction() throws EBabyException {
    User user = generateSellerData();
    user.setSellerFlag(true);

    users.register(user);
    users.login(user.getUserName(), user.getPassword());
    return new Auction(user, GoodsCategory.CAR,  "car", 1, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), alwaysTrueOffHours);
  }

  private String logMessage(Auction auction) {
    return "onclose " +
            "category:" + auction.getGoodsCategory() +
            "createUser:" + auction.getCreateUser() +
            "bidder:" + auction.getBidderUser() +
            "finalPrice:" + auction.getNowPrice();

  }

}
