package controller;

import controller.loginmenu.LoginMenuController;
import controller.loginmenu.LoginMenuMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

class LoginMenuControllerTest extends MenuTest {
    @Test
    void findCommandEnterAMenuMethod() {
        LoginMenuMessages result = LoginMenuController.findCommand("menu  enter LogIn menU");
        Assertions.assertEquals(LoginMenuMessages.INVALID_COMMAND, result);

        result = LoginMenuController.findCommand("menu enter LogIn menU");
        Assertions.assertEquals(LoginMenuMessages.INVALID_NAVIGATION, result);

        result = LoginMenuController.findCommand("menu enter MaIn MenU");
        Assertions.assertEquals(LoginMenuMessages.FIRST_LOGIN, result);

        result = LoginMenuController.findCommand("menu enter DUEL MENU");
        Assertions.assertEquals(LoginMenuMessages.FIRST_LOGIN, result);
    }

    @Test
    void findCommandCheckCreateUserMethod() {
        LoginMenuMessages result = LoginMenuController.findCommand("user create --username John 1 --nickname Johny --password 12345");
        Assertions.assertEquals(LoginMenuMessages.INVALID_COMMAND, result);

        result = LoginMenuController.findCommand("user create --U John1 --nickname Johny1 --password 12345");
        Assertions.assertEquals(LoginMenuMessages.USER_CREATED, result);

        result = LoginMenuController.findCommand("user create --username John2 --P 12345 --nickname Johny2");
        Assertions.assertEquals(LoginMenuMessages.USER_CREATED, result);

        result = LoginMenuController.findCommand("user create --N Johny3 --username John3 --password 12345");
        Assertions.assertEquals(LoginMenuMessages.USER_CREATED, result);

        result = LoginMenuController.findCommand("user create --N Johny4 --password 12345 --U John4");
        Assertions.assertEquals(LoginMenuMessages.USER_CREATED, result);

        result = LoginMenuController.findCommand("user create --password 12345 --username John5 --nickname Johny5");
        Assertions.assertEquals(LoginMenuMessages.USER_CREATED, result);

        result = LoginMenuController.findCommand("user create --password 12345 --nickname Johny6 --username John6");
        Assertions.assertEquals(LoginMenuMessages.USER_CREATED, result);

        result = LoginMenuController.findCommand("user create --P 12345 --U John6 --N Johny6");
        Assertions.assertEquals(LoginMenuMessages.USERNAME_EXISTS, result);

        result = LoginMenuController.findCommand("user create --P 12345 --U John7 --nickname Johny6");
        Assertions.assertEquals(LoginMenuMessages.NICKNAME_EXISTS, result);
    }

    @Test
    void findCommandCheckLoginUserMethod() {
        LoginMenuMessages result = LoginMenuController.findCommand("user login --username John 12 --password 123456");
        Assertions.assertEquals(LoginMenuMessages.INVALID_COMMAND, result);

        result = LoginMenuController.findCommand("user login --username John12 --password 12345");
        Assertions.assertEquals(LoginMenuMessages.UNMATCHED_USERNAME_AND_PASSWORD, result);


        result = LoginMenuController.findCommand("user login --username John2 --password 123456");
        Assertions.assertEquals(LoginMenuMessages.UNMATCHED_USERNAME_AND_PASSWORD, result);

        result = LoginMenuController.findCommand("user login --username John1 --password 12345");
        Assertions.assertEquals(LoginMenuMessages.USER_LOGGED_IN, result);

        result = LoginMenuController.findCommand("user login --password 12345 --username John1");
        Assertions.assertEquals(LoginMenuMessages.USER_LOGGED_IN, result);
    }

    @Test
    void loginUser() {
        Utils.resetScanner("user logout\n");
        ByteArrayOutputStream outContent = Utils.setByteArrayOutputStream();
        LoginMenuController.loginUser("user login --password 12345 --username John1");
        Assertions.assertEquals("user logged out successfully!\n", outContent.toString());

        Utils.resetScanner("user logout\n");
        outContent = Utils.setByteArrayOutputStream();
        LoginMenuController.loginUser("user login --username John1 --password 12345");
        Assertions.assertEquals("user logged out successfully!\n", outContent.toString());
    }
}