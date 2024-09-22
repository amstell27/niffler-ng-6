package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

@WebTest
public class LoginWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin(){
        String username = "amstell";
        String password = "12345";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(username, password)
                .checkIsLoaded();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials(){
        String username = "badCred";
        String password = "12345";
        String errorMessage = "Неверные учетные данные пользователя";

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .loginWithBadCredentials(username, password)
                .checkErrorMessage(errorMessage);
    }

}
