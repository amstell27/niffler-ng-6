package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

@WebTest
public class ProfileWebTest {
    private static final Config CFG = Config.getInstance();

    @User(username = "duck",
            categories = @Category(archived = false)
    )

    @Test
    void archiveCategoryShouldPresentInCategoriesList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345")
                .openProfilePage()
                .archiveCategory(category.name())
                .checkThatCategoryNotContainsInList(category.name())
                .showArchivedCheckBox()
                .checkThatCategoryContainsInList(category.name());
    }

    @User(username = "duck",
            categories = @Category(archived = true)
    )
    @Test
    void activeCategoryShouldPresentInCategoriesList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345")
                .openProfilePage()
                .showArchivedCheckBox()
                .unarchiveCategory(category.name())
                .checkThatCategoryContainsInList(category.name());
    }
}
