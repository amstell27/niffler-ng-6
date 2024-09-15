package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {

    private final SelenideElement avatarInput = $("[for='image__input']");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement categoryInput = $("#category");
    private final SelenideElement saveChangesButton = $("[type='submit']");

    private final SelenideElement showArchivedCheckBox = $("[type='checkbox']");
    private final SelenideElement editCategoryButton = $("[aria-label='Edit category']");
    private final SelenideElement archiveCategoryButton = $("[aria-label='Archive category']");
    private final SelenideElement unarchiveCategoryButton = $("[aria-label='Unarchive category']");
    private final ElementsCollection categoryRows = $$(".css-3w20vr");
    private final SelenideElement archiveSubmitButton = $(byText("Archive"));
    private final SelenideElement unArchiveSubmitButton = $(byText("Unarchive"));

    private final SelenideElement successfulSaveMessage = $(byText("Profile successfully updated"));
    private final SelenideElement successfulAddCategory = $(byText("You've added new category:"));

    public ProfilePage setName(String name){
        nameInput.setValue(name);
        saveChangesButton.click();
        successfulSaveMessage.isDisplayed();
        return this;
    }

    public ProfilePage setCategory(String nameCategory){
        categoryInput.setValue(nameCategory).pressEnter();
        successfulAddCategory.isDisplayed();
        return this;
    }

    public ProfilePage setAvatar(String classPath){
        avatarInput.uploadFromClasspath(classPath);
        successfulSaveMessage.isDisplayed();
        return this;
    }

    public ProfilePage checkThatCategoryContainsInList(String categoryName){
        categoryRows.find(text(categoryName)).shouldBe(visible);
        return this;
    }
    public ProfilePage checkThatCategoryNotContainsInList(String categoryName){
        categoryRows.find(text(categoryName)).shouldNot(visible);
        return this;
    }

    public ProfilePage archiveCategory(String categoryName){
        categoryRows.find(text(categoryName)).$("[aria-label='Archive category']").click();
        archiveSubmitButton.click();
        return this;
    }

    public ProfilePage unarchiveCategory(String categoryName) {
        categoryRows.find(text(categoryName)).$("button[aria-label='Unarchive category']").click();
        unArchiveSubmitButton.click();
        return this;
    }

    public ProfilePage showArchivedCheckBox() {
        showArchivedCheckBox.click();
        return this;
    }


}
