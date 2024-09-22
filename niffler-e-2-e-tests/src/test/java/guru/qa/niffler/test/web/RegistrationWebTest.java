package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

@WebTest
public class RegistrationWebTest {

    private static final Config CFG = Config.getInstance();

    String username = "example";
    String password = "12345";
    String passwordSubmit = "123456";

    @Test
    void shouldRegisterNewUser(){
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .signUpAccount()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .registerSuccess("Congratulations! You've registered!");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual(){
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .signUpAccount()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(passwordSubmit)
                .submitRegistration()
                .registerError("Passwords should be equal");
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername(){
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .signUpAccount()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .registerError("Username `" + username +"` already exists");
    }
}
