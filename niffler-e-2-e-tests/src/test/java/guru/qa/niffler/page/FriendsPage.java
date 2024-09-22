package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {

    private final SelenideElement tabAllPeople = $("[href='/people/all']");
    private final SelenideElement emptyFriendList = $(byText("There are no users yet"));
    private final ElementsCollection friendList = $$("#friends tr");
    private final ElementsCollection incomeRequests = $$("#requests tr");
    private final ElementsCollection outcomeRequests = $$("#all tr");

    public FriendsPage checkListContainsNameFriend(String friendName){
        friendList.find(text(friendName)).shouldBe(visible);
        return this;
    }

    public FriendsPage checkTableFriendIsEmpty(){
        emptyFriendList.shouldBe(visible);
        return this;
    }

    public FriendsPage checkListContainsIncomeRequests(String name){
        incomeRequests.find(text(name)).shouldBe(visible);
        return this;
    }

    public FriendsPage transitionToTabAllPeople(){
        tabAllPeople.click();
        return this;
    }

    public FriendsPage checkListContainsOutcomeRequests(String name){
        outcomeRequests.filterBy(text(name)).shouldBe(texts("Waiting..."));
        return this;
    }
}
