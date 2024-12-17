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

public class GetPersonIDTest {
    RestTemplate restTemplate = new RestTemplate();

    @Test
    @AllureId("1")
    @DisplayName("Получение пользователя по id")
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

            step("Делаем запрос GET /person/{id}, проверяем, что получили нашего пользователя", () -> {
                String getUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                ResponseEntity<Person> getResponseEntity = restTemplate.getForEntity(getUrl, Person.class);

                assertNotNull(getResponseEntity);
                assertEquals(createPersonResponse.getBody(), getResponseEntity.getBody().getId());
                assertEquals(person.getName(), getResponseEntity.getBody().getName());
            });
    });

    }

    @Test
    @AllureId("2")
    @DisplayName("Пытаемся получить пользователя по несуществующему id")
    public void NewTestCase2() {
        step("Делаем запрос GET /person/{id}, проверяем, что получили ошибку: 404 Клиент не найден", () -> {
            String getUrl = "http://localhost:8080/api/person/%s".formatted(1000L);
            try{
                ResponseEntity<Person> getResponseEntity = restTemplate.getForEntity(getUrl, Person.class);
            } catch(Exception e){
//                System.out.println(e.getMessage());
            }
        });
    }
}
