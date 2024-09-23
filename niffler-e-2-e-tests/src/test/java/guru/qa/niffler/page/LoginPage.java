package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement createAccountButton = $(".form__register");
  private final SelenideElement errorMessage = $(".form__error");

  public MainPage login(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return new MainPage();
  }

  public LoginPage loginWithBadCredentials(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return this;
  }

  public RegisterPage signUpAccount(){
    createAccountButton.click();
    return new RegisterPage();
  }

  public void checkErrorMessage(String errorText){
    errorMessage.shouldBe(visible);
    errorMessage.shouldHave(text(errorText));
  }

}
