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
    }

    ;

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
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.text(date));
    }

    @Test
    public void shouldSendFormForNextDay() {
        String date = dateCreate(4);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.text(date));
    }

    @Test
    public void shouldNotSendFormWithEmptyCity() {
        String date = dateCreate(3);
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotSendFormWithNonExistingCity() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("sdf");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldNotSendFormWithNotRussianCity() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("London");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldNotSendFormWithToEarlyDate() {
        String date = dateCreate(2);
        $("[data-test-id='city'] .input__control").setValue("Нижний Новгород");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldSendFormWithOneYearDate() {
        String date = dateCreate(365);
        $("[data-test-id='city'] .input__control").setValue("Нижний Новгород");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.text(date));
    }

    @Test
    public void shouldNotSendFormWithNoNameAndSurname() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Иваново");
        $("[data-test-id='date']").click();
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));;
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendFormWithDoubleName() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Алекс-Тест");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.exactText("Успешно!"), Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована на")).shouldBe(Condition.text(date));
    }

    @Test
    public void shouldNotSendFormWithSymbolsInName() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Анна-Мария Тест@");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotSendFormWithNameInEnglish() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Ann test");
        $("[data-test-id='phone'] .input__control").setValue("+79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotSendFormWithoutPlusInPhone() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Иван тест");
        $("[data-test-id='phone'] .input__control").setValue("79099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotSendFormWith10NumbersInPhone() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Иван тест");
        $("[data-test-id='phone'] .input__control").setValue("+9099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotSendFormWith12NumbersInPhone() {
        String date = dateCreate(3);
        $("[data-test-id='city'] .input__control").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(BACK_SPACE, date));
        $("[data-test-id='name'] .input__control").setValue("Иван тест");
        $("[data-test-id='phone'] .input__control").setValue("+789099991122");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }




}
