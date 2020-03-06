package application;

import application.exception.EBabyException;

public class User {

    private String firstName;
    private String lastName;
    private String userEmail;
    private String userName;
    private String password;
    private Boolean loginFlag;
    private Boolean sellerFlag;

    public User(String firstName, String lastName, String userEmail, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userName = userName;
        this.password = password;
        this.loginFlag = false;
        this.sellerFlag = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(Boolean loginFlag) {
        this.loginFlag = loginFlag;
    }

    public void setSellerFlag(Boolean sellerFlag) {
        this.sellerFlag = sellerFlag;
    }

    public Boolean getSellerFlag() {
        return this.sellerFlag;
    }

    public void bid(Auction auction, Integer bidPrice) throws EBabyException {

        AuctionBidRule.check(auction, this, bidPrice);
        auction.setBidderUser(this);
        auction.setNowPrice(bidPrice);
    }
}
