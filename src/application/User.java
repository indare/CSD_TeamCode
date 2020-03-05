package application;

public class User {

  private String firstName;
  private String lastName;
  private String userEmail;
  private String userName;
  private String password;
  private Boolean loginFlag;
  private boolean sellerFlag;

  public User(String firstName, String lastName, String userEmail, String userName, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userEmail = userEmail;
    this.userName = userName;
    this.password = password;
    this.loginFlag = false;
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

  public boolean getLoginFlag() {
    return loginFlag;
  }

  public void setLoginFlag(boolean loginFlag) {
    this.loginFlag = loginFlag;
  }

  public void setSellerFlag(boolean sellerFlag) {
    this.sellerFlag = sellerFlag;
  }

  public boolean getSellerFlag() {
    return this.sellerFlag;
  }
}
