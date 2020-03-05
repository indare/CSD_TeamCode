package application;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersTest {

  private User generateTanakaSanData(){
    String firstName = "田中";
    String lastName = "太郎";
    String userEmail = "tanaka@tanaka.com";
    String userName = "tarou";
    String password = "password";

    return new User(firstName, lastName, userEmail,userName, password);
  }

  private User generateSuzukiSanData(){
    String firstName = "鈴木";
    String lastName = "一郎";
    String userEmail = "suzuki@suzuki.com";
    String userName = "suzuki";
    String password = "pass";

    return new User(firstName, lastName, userEmail,userName, password);
  }

  @Test
  public void test_register_user () throws Exception {

    User user = generateTanakaSanData();

    Users users = new Users();

    users.register(user);

    User actual = users.findByUserName(user.getUserName());

    assertEquals(user.getFirstName(), actual.getFirstName());
    assertEquals(user.getLastName(), actual.getLastName());
    assertEquals(user.getUserEmail(), actual.getUserEmail());
    assertEquals(user.getUserName(), actual.getUserName());
    assertEquals(user.getPassword(), actual.getPassword());
    assertFalse(actual.getLoginFlag());
  }

  @Test(expected=DuplicatedUserNameException.class)
  public void test_reject_duplicate_user_name () throws Exception {

    User user = generateTanakaSanData();

    Users users = new Users();

    users.register(user);
    users.register(user);
  }

  @Test
  public void test_do_login() throws Exception {

    User user = generateTanakaSanData();

    Users users = new Users();
    users.register(user);

    users.login(user.getUserName(), user.getPassword());
    User loginUser = users.findByUserName(user.getUserName());

    assertTrue(loginUser.getLoginFlag());
  }

  @Test(expected=WrongUsernameException.class)
  public void test_user_login_scenario() throws Exception {
    User tanaka = generateTanakaSanData();
    User suzuki = generateSuzukiSanData();

    Users users = new Users();
    users.register(tanaka);
    users.register(suzuki);

    users.login(tanaka.getUserName(), tanaka.getPassword());

    User loginUser = users.findByUserName(tanaka.getUserName());
    assertTrue(loginUser.getLoginFlag());

    users.login(suzuki.getUserName() + "wrong", suzuki.getPassword());
  }

  @Test(expected=WrongPasswordException.class)
  public void test_wrong_password_input_and_login_scenario() throws Exception {
    User suzuki = generateSuzukiSanData();

    Users users = new Users();
    users.register(suzuki);

    users.login(suzuki.getUserName(), suzuki.getPassword() + "wrong");
  }

  @Test
  public void test_logout() throws Exception {
    User tanaka = generateTanakaSanData();

    Users users = new Users();
    users.register(tanaka);

    users.login(tanaka.getUserName(), tanaka.getPassword());
    users.logout(tanaka.getUserName());

    User loginUser = users.findByUserName(tanaka.getUserName());
    assertFalse(loginUser.getLoginFlag());

  }

  @Test
  public void test_set_seller_flag() throws Exception {
    User tanaka = generateTanakaSanData();

    Users users = new Users();
    users.register(tanaka);

    users.setSeller(tanaka.getUserName());

    assertTrue(users.isSeller(tanaka.getUserName()));

  }


}
