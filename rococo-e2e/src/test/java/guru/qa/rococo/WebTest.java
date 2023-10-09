package guru.qa.rococo;

import guru.qa.rococo.jupiter.ApiLogin;
import guru.qa.rococo.jupiter.ApiLoginExtension;
import guru.qa.rococo.jupiter.ContextHolderExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@ExtendWith({ContextHolderExtension.class, ApiLoginExtension.class})
public class WebTest {

  @ApiLogin(username = "dima", password = "12345")
  @Test
  void usernameShouldBeVisivleInProfile() {
    $("[data-testid='avatar']").click();
    $("h4.text-center").should(text("dima"));
  }

  @ApiLogin(username = "ivan", password = "12345")
  @Test
  void paintingNameShouldEditedByAuthorizedUser() {
    $$("nav.list-nav a").find(text("Картины")).click();
    $$("div.text-center").find(text("Утро в сосновом лесу")).parent().click();
    $("[data-testid='edit-painting']").click();
    $("input[name='title']").setValue("Edited");
    $("button[type='submit']").click();

    $("header.card-header").should(text("Edited"));
  }

  @ApiLogin(username = "petr", password = "12345")
  @Test
  void museumNameShouldEditedByAuthorizedUser() {
    $$("nav.list-nav a").find(text("Музеи")).click();
    $$("div.text-center").find(text("Эрмитаж")).parent().click();
    $("[data-testid='edit-museum']").click();
    $("input[name='title']").setValue("Edited");
    $("button[type='submit']").click();

    $("header.card-header").should(text("Edited"));
  }

  @ApiLogin(username = "valentin", password = "12345")
  @Test
  void artistNameShouldEditedByAuthorizedUser() {
    $$("nav.list-nav a").find(text("Художники")).click();
    $$("span.flex-auto").find(text("Шишкин")).parent().click();
    $("[data-testid='edit-artist']").click();
    $("input[name='name']").setValue("Edited");
    $("button[type='submit']").click();

    $("header.card-header").should(text("Edited"));
  }
}
