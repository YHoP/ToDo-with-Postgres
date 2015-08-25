import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  // @ClassRule
  // public static ServerRule server = new ServerRule();
  //
  // @Rule
  // public ClearRule clearRule = new ClearRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Todo list!");
  }

  @Test
  public void categoryIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a new category"));
    fill("#name").with("Household chores");
    submit(".btn");
    assertThat(pageSource()).contains("Your category has been saved.");
  }

  // @Test
  // public void categoryIsDisplayedTest() {
  //   goTo("http://localhost:4567/categories/new");
  //   fill("#name").with("Household chores");
  //   submit(".btn");
  //   click("a", withText("View categories"));
  //   assertThat(pageSource()).contains("Household chores");
  // }

  @Test
  public void categoryIsDisplayedTest() {
    Category myCategory = new Category("Household chores");
    String categoryPath = String.format("http://localhost:4567/%d", myCategory.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Household chores");
  }

  @Test
  public void allTasksDisplayDescriptionOnCategoryPage() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task firstTask = new Task("Mow the lawn", myCategory.getId());
    firstTask.save();
    Task secondTask = new Task("Do the dishes", myCategory.getId());
    secondTask.save();
    String categoryPath = String.format("http://localhost:4567/%d", myCategory.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Mow the lawn");
    assertThat(pageSource()).contains("Do the dishes");
  }

  // @Test
  // public void allTasksDisplayDescriptionOnCategoryPage() {
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Add a new category"));
  //   fill("#name").with("Household chores");
  //   submit(".btn");
  //   click("a", withText("Household chores");
  //   fill("#description").with("Mow the lawn");
  //   submit(".btn");
  //   click("a", withText("Household chores");
  //   fill("#description").with("Do the dishes");
  //   submit(".btn");
  //   goTo("http://localhost:4567/");
  //   assertThat(pageSource()).contains("Mow the lawn");
  //   assertThat(pageSource()).contains("Do the dishes");
  // }

}
