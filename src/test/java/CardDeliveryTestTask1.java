import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.DELETE;

public class CardDeliveryTestTask1 {
    public String dateCreate(int shift) {
        String date;
        LocalDate localDate = LocalDate.now().plusDays(shift);
        date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
        return date;
    };

    @BeforeMethod
    public void openUrl() { open("http://localhost:9999/"); }

    @AfterMethod
    public void tearDown() { Selenide.closeWebDriver(); }

    @Test
    public void shouldSendFormWithCorrectData() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date']").doubleClick();
        $("[data-test-id='date']").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.text(date));
    }


}
