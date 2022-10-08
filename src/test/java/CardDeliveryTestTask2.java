import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.temporal.TemporalAdjusters.next;


public class CardDeliveryTestTask2 {
    public String dateCreate(int shift) {
        String date;
        LocalDate localDate = LocalDate.now().plusDays(shift);
        date = DateTimeFormatter.ofPattern("dd").format(localDate);
        return date;
    }

    public String currentDateCreate() {
        String currentDate;
        LocalDate localDate = LocalDate.now();
        currentDate = DateTimeFormatter.ofPattern("dd").format(localDate);
        return currentDate;
    }

    @BeforeMethod
    public void openUrl() {
        open("http://localhost:9999/");
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    @Test
    public void shouldSendFormWithCorrectData() {
        String date = dateCreate(7);
        String currentDate = currentDateCreate();
        int today = Integer.parseInt(currentDate);
        int daysInCurrentMonth = LocalDate.now().lengthOfMonth();
        int difference = daysInCurrentMonth - today;
        int nextWeekDay = 7 - difference;
        String nextWeekDayIndex = null;

        $("[data-test-id='city'] .input__control").click();
        $("[data-test-id='city'] .input__control").setValue("ка");
        $(".input__popup").shouldBe(Condition.visible);
        $x("//*[contains(text(), 'Владикавказ')]").click();
        $("[data-test-id='date']").click();
        $(".calendar-input__calendar-wrapper").shouldBe(Condition.visible);
        if (difference < 7) {
            nextWeekDayIndex = String.valueOf(nextWeekDay);
            $("[data-step='1']").click();
            $$(".calendar__day").findBy(Condition.text(nextWeekDayIndex)).click();
            $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
            $("[data-test-id='phone'] .input__control").setValue("+79099991122");
            $("[data-test-id='agreement'] .checkbox__box").click();
            $$("button").find(Condition.exactText("Забронировать")).click();
            $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
            $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
            $(withText("Встреча успешно забронирована на"))
                    .shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldBe(Condition.text(nextWeekDayIndex));
        } else {
            $$(".calendar__day").findBy(Condition.text(date)).click();
            $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
            $("[data-test-id='phone'] .input__control").setValue("+79099991122");
            $("[data-test-id='agreement'] .checkbox__box").click();
            $$("button").find(Condition.exactText("Забронировать")).click();
            $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
            $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
            $(withText("Встреча успешно забронирована на"))
                    .shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldBe(Condition.text(date));
        }
    }
}

