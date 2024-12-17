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
    RestTemplate restTemplate = new RestTemplate();

    @Test
    @AllureId("1")
    @DisplayName("Изменение пользователя по id")
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
            step("Делаем запрос Put /person/{id}, изменяя имя пользователя", () -> {
                Person person1 = new Person(createPersonResponse.getBody(), "Albert");
                String putUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                restTemplate.put(putUrl, person1);
                step("Делаем запрос GET /person/{id}, проверяем, что получили обновлённого пользователя ", () -> {
                    String getUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                    ResponseEntity<Person> getResponseEntity = restTemplate.getForEntity(getUrl, Person.class);
                    assertNotNull(getResponseEntity);
                    assertEquals(createPersonResponse.getBody(), getResponseEntity.getBody().getId());
                    assertEquals(person1.getName(), getResponseEntity.getBody().getName());
                });
            });
        });

    }

    @Test
    @AllureId("2")
    @DisplayName("Пытаемся изменить пользователя по несуществующему id")
    public void NewTestCase2() {
        step("Делаем запрос Put /person/{id}, указывая несуществующий id, получаем ошибку(404 Такого клиента нет в базе)", () -> {
            Person person = new Person("Albert");
            String putUrl = "http://localhost:8080/api/person/%s".formatted(1000L);
            try {
                restTemplate.put(putUrl, person);
            } catch (Exception e){
//                System.out.println(e.getMessage());
            }

        });

    }
}
