package com.tcs.vetclinic;
import com.tcs.vetclinic.domain.person.Person;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetPersonTest {

    RestTemplate restTemplate = new RestTemplate();

    @Test
    @AllureId("1")
    @DisplayName("проверка работы параметров page/size/sort")
    public void NewTestCase1() {
        step("создать 20+ пользователей с разными именами", () -> {});
        step("отправляем гет запрос с параметрами page = 2, size = 10, sort = DESC", () -> {
            String Url = "http://localhost:8080/api/person?page=2&size=10&sort=DESC";
            String getResponseEntity = restTemplate.getForObject(Url, String.class);
            System.out.println(getResponseEntity);
            step("проверяем полученный ответ: код 200, длинна 10", () -> {
                //не знаю как получить код ответа(
            });
        });
    }
}
