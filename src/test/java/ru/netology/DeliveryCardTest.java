package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryCardTest {

    public String planDate(int days, String date) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(date));
    }


    @BeforeEach
    public void openBrauser() {
        open("http://localhost:9999/");
    }


    @Test
    public void shouldDeliveryCardOfTheNextDay() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        String date = planDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[name='name']").setValue("Лиукконен Максим");
        $("[name='phone']").setValue("+79819715780");
        $("[class='checkbox__box']").click();
        $("[class='button__content']").click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + date));
    }


    @Test
    public void shouldDeliveryCardOfToday() {
        $("[placeholder='Город']").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        String date = planDate(0, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[name='name']").setValue("Лиукконен Максим");
        $("[name='phone']").setValue("+79819715780");
        $("[class='checkbox__box']").click();
        $("[class='button__content']").click();
        $(byText("Заказ на выбранную дату невозможен")).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldDeliveryCardOfInvalidTown() {
        $("[placeholder='Город']").setValue("Sankt-Peterburg");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        String date = planDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[name='name']").setValue("Лиукконен Максим");
        $("[name='phone']").setValue("+79819715780");
        $("[class='checkbox__box']").click();
        $("[class='button__content']").click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldDeliveryCardOfInvalidName() {
        $("[placeholder='Город']").setValue("Мурманск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        String date = planDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[name='name']").setValue("Liukonen Maksim");
        $("[name='phone']").setValue("+79819715780");
        $("[class='checkbox__box']").click();
        $("[class='button__content']").click();
        $(withText("Имя и Фамилия указаные неверно"))
                .shouldBe(visible).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldDeliveryCardOfInvalidPhone() {
        $("[placeholder='Город']").setValue("Мурманск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        String date = planDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[name='name']").setValue("Лиукконен Максим");
        $("[name='phone']").setValue("+7(981)9715780");
        $("[class='checkbox__box']").click();
        $("[class='button__content']").click();
        $(withText("Телефон указан неверно"))
                .shouldBe(visible).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldDeliveryCardWithEmptyChekBox() {
        $("[placeholder='Город']").setValue("Мурманск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        String date = planDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[name='name']").setValue("Лиукконен Максим");
        $("[name='phone']").setValue("+79819715780");
        $("[class='button__content']").click();
        $(".input_invalid[data-test-id='agreement']").shouldBe(visible);
    }

//    @Test
//    public void shouldDeliveryCardOfSearchTownByTwoLetters() {
//        $("[placeholder='Город']").setValue("Са");
//        $(byText("Санкт-Петербург")).click();
//        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
//        String date = planDate(7, "dd.MM.yyyy");
//        $("[data-test-id=date] input").setValue(date);
//        $("[name='name']").setValue("Лиукконен Максим");
//        $("[name='phone']").setValue("+79819715780");
//        $("[class='checkbox__box']").click();
//        $("[class='button__content']").click();
//        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
//        $("[class='notification__content']").shouldBe(visible).shouldHave(exactText("Встреча успешно забронирована на " + date));
//    }


}