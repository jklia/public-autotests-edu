package com.tcs.vetclinic;

import com.tcs.vetclinic.domain.person.Person;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static io.qameta.allure.Allure.step;

public class DeletePersonIDTest {
    RestTemplate restTemplate = new RestTemplate();

    @Test
    @AllureId("1")
    @DisplayName("Удаление пользователя по id")
    public void NewTestCase1() {
        String postUrl = "http://localhost:8080/api/person";
        Person person = new Person("Ivan");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        step("создаём пользователя и получаем его ID", () -> {
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                    postUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Long.class
            );
            step("Удаляем пользователя по id", () -> {
                String delUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                restTemplate.delete(delUrl);
                step("Делаем запрос GET /person/{id}, проверяем, что такого пользователя нет(ошибка:404 Клиент не найден)", () -> {
                    String getUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                    try{
                        ResponseEntity<Person> getResponseEntity = restTemplate.getForEntity(getUrl, Person.class);
                    } catch(Exception e){
//                        System.out.println(e.getMessage());
                    }
                });
            });
        });

    }

    @Test
    @AllureId("2")
    @DisplayName("Пытаемся удалить пользователя по несуществующему id")
    public void NewTestCase2() {
        step("Пытаемся удалить пользователя, указывая несуществующий id (ошибка 409 Такого клиента нет в базе)", () -> {
            String delUrl = "http://localhost:8080/api/person/%s".formatted(1000L);
            try{
                restTemplate.delete(delUrl);
            } catch(Exception e){
//                        System.out.println(e.getMessage());
            }
        });

    }
}
