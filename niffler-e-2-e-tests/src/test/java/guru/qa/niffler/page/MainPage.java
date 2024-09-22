package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class MainPage {
  private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
  private final SelenideElement openPopupMenu = $("[aria-label='Menu']");
  private final SelenideElement profile = $("[href='/profile']");
  private final SelenideElement headerStat = $("#stat");
  private final SelenideElement historyOfSpendings = $("#spendings");
  private final SelenideElement friends = $("[href='/people/friends']");


  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).$$("td").get(5).click();
    return new EditSpendingPage();
  }

  public void checkThatTableContainsSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).should(visible);
  }

  public MainPage checkThatStatisticsIsDisplayed(){
    headerStat.shouldBe(visible);
    return this;
  }
  public MainPage checkThatHistoryOfSpendingsIsDisplayed(){
    historyOfSpendings.shouldBe(visible);
    return this;
  }

  public ProfilePage openProfilePage(){
    openPopupMenu.click();
    profile.click();
    return new ProfilePage();
  }

  public FriendsPage openFriendsPage(){
    openPopupMenu.click();
    friends.click();
    return new FriendsPage();
  }
}
