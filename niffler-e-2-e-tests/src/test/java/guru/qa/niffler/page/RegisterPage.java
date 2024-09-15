package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitPasswordInput = $("input[name='passwordSubmit']");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement errorMessage = $(".form__error");
    private final SelenideElement successMessage = $("[class='form__paragraph form__paragraph_success']");
    private final SelenideElement signIn = $(".form_sign-in");


    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        submitPasswordInput.setValue(password);
        return this;
    }

    public RegisterPage submitRegistration() {
        submitButton.click();
        return this;
    }

    public RegisterPage registerError(String value) {
        errorMessage.shouldBe(text(value));
        return this;
    }

    public RegisterPage registerSuccess(String value) {
        successMessage.shouldBe(text(value));
        return this;
    }

    public LoginPage signIn() {
        signIn.click();
        return new LoginPage();
    }

}
