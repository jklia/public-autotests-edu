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

public class PutPersonIDTest {

    @Test
    @AllureId("1")
    @DisplayName("Изменение пользователя по id")
    public void NewTestCase1() {
        step("создаём пользователя и получаем его ID", () -> {

        });
        step("Делаем запрос Put /person/{id}, изменяя имя пользователя", () -> {
            step("Делаем запрос GET /person/{id}, проверяем, что получили обновлённого пользователя ", () -> {

            });
        });

    }

    @Test
    @AllureId("2")
    @DisplayName("Пытаемся изменить пользователя по несуществующему id")
    public void NewTestCase2() {
        step("Делаем запрос Put /person/{id}, указывая несуществующий id", () -> {
            step("Проверяем, что в ответе от Put /person/{id} получена ошибка: 404 Такого клиента нет в базе", () -> {

            });
        });

    }
}
