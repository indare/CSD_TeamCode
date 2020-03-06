package application;

import application.exception.DuplicatedUserNameException;
import application.exception.EBabyException;
import application.exception.WrongPasswordException;
import application.exception.WrongUsernameException;

import java.util.HashMap;
import java.util.Map;

public class Users {

  private Map<String, User> user = new HashMap<>();

  public User findByUserName(String userName) throws WrongUsernameException {
    if (this.user.containsKey(userName)) {
      return this.user.get(userName);
    } else {
      throw new WrongUsernameException();
    }
  }

  public void register(User user) throws DuplicatedUserNameException {
    if (this.user.containsKey(user.getUserName())) {
      throw new DuplicatedUserNameException();
    } else {
      this.user.put(user.getUserName(), user);
    }
  }

  public void login(String userName, String password) throws EBabyException {
    User findUser = findByUserName(userName);

    if (password.equals(findUser.getPassword())) {
      findUser.setLoginFlag(true);
      this.user.put(findUser.getUserName(), findUser);
    } else {
      throw new WrongPasswordException();
    }
  }

  public void logout(String userName) throws EBabyException {
    User findUser = findByUserName(userName);
    findUser.setLoginFlag(false);
    this.user.put(findUser.getUserName(), findUser);
  }

  public void setSeller(String userName) throws EBabyException {
    User findUser = findByUserName(userName);
    findUser.setSellerFlag(true);
    this.user.put(findUser.getUserName(), findUser);
  }

  public Boolean isSeller(String userName) throws EBabyException {
    User findUser = findByUserName(userName);
    return findUser.getSellerFlag();
  }
}
