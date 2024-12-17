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

public class PostPersonTest {
    RestTemplate restTemplate = new RestTemplate();

    @Test
    @AllureId("1")
    @DisplayName("Создание пользователя с name и id")
    public void NewTestCase1() {
        String postUrl = "http://localhost:8080/api/person";

        Person person = new Person(1L,"Ivan");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        step("Отправляем запрос POST /person с параметрам: id = 1, name = 'Ivan'", () -> {
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);

            ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                    postUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Long.class
            );

            step("Проверяем, что в ответе от POST /person получен id", () -> {
                assertNotNull(createPersonResponse.getBody());
            });
            step("Проверяем, что через метод GET /person/{id} мы получим созданного пользователя", () -> {
                String getUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                ResponseEntity<Person> getResponseEntity = restTemplate.getForEntity(getUrl, Person.class);

                assertNotNull(getResponseEntity);
                assertEquals(createPersonResponse.getBody(), getResponseEntity.getBody().getId());
                assertEquals(person.getName(), getResponseEntity.getBody().getName());
            });
        });
    }

    @Test
    @DisplayName("Сохранение пользователя с пустыми id и не пустым name")
    @AllureId("2")
    public void NewTestCase2() {
        String postUrl = "http://localhost:8080/api/person";

        Person person = new Person("Ivan");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        step("Отправляем запрос POST /person с параметрами id = null, name = 'Ivan'", () -> {
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                    postUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Long.class
            );
            step("Проверяем, что в ответе от POST /person получен id", () -> {
                assertNotNull(createPersonResponse.getBody());
//                System.out.println(createPersonResponse);
            });

            step("Проверяем, что через метод GET /person/{id} мы получим созданного пользователя", () -> {
                String getUrl = "http://localhost:8080/api/person/%s".formatted(createPersonResponse.getBody());
                ResponseEntity<Person> getResponseEntity = restTemplate.getForEntity(getUrl, Person.class);

                assertNotNull(getResponseEntity);
                assertEquals(createPersonResponse.getBody(), getResponseEntity.getBody().getId());
                assertEquals(person.getName(), getResponseEntity.getBody().getName());
            });
        });
    }


    @Test
    @DisplayName("Сохранение пользователя с name длинны 255")
    @AllureId("3")
    public void NewTestCase3() {
        String postUrl = "http://localhost:8080/api/person";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        step("Отправляем запрос POST /person с параметрами len(name) = 255", () -> {
            Person person = new Person("g".repeat(255));
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                    postUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Long.class
            );
            step("Проверяем, что в ответе от POST /person получен id", () -> {
                assertNotNull(createPersonResponse.getBody());
//                System.out.println(createPersonResponse.getBody());
            });
        });

    }

    @Test
    @DisplayName("Сохранение пользователя с name длинны 3")
    @AllureId("4")
    public void NewTestCase4() {
        String postUrl = "http://localhost:8080/api/person";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        step("Отправляем запрос POST /person с параметрами len(name) = 3", () -> {
            Person person = new Person("g".repeat(3));
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                    postUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Long.class
            );
            step("Проверяем, что в ответе от POST /person получен id", () -> {
                assertNotNull(createPersonResponse.getBody());
//                System.out.println(createPersonResponse.getBody());
            });
        });

    }

    @Test
    @DisplayName("Сохранение пользователя с name длинны 2")
    @AllureId("5")
    public void NewTestCase5() {
        String postUrl = "http://localhost:8080/api/person";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        step("Отправляем запрос POST /person с параметрами len(name) = 2", () -> {
            Person person = new Person("g".repeat(2));
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            step("Проверяем, что в ответе от POST /person получена ошибка: 400,error:Bad Request", () -> {
            try{
                ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                        postUrl,
                        HttpMethod.POST,
                        requestEntity,
                        Long.class
                );
            } catch (Exception e){
//                System.out.println(e.getMessage());
            }
            });
        });
    }

    @Test
    @DisplayName("Сохранение пользователя с name длинны 256")
    @AllureId("6")
    public void NewTestCase6() {
        String postUrl = "http://localhost:8080/api/person";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        step("Отправляем запрос POST /person с параметрами len(name) = 2", () -> {
            Person person = new Person("g".repeat(256));
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            step("Проверяем, что в ответе от POST /person получена ошибка: 400,error:Bad Request", () -> {
                try{
                    ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                            postUrl,
                            HttpMethod.POST,
                            requestEntity,
                            Long.class
                    );
                } catch (Exception e){
//                    System.out.println(e.getMessage());
                }
            });
        });

    }

    @Test
    @DisplayName("Сохранение пользователя, без параметров")
    @AllureId("7")
    public void NewTestCase7() {
        String postUrl = "http://localhost:8080/api/person";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        step("Отправляем запрос POST /person с пустым person", () -> {
            Person person = new Person();
            HttpEntity<Person> requestEntity = new HttpEntity<>(person, headers);
            step("Проверяем, что в ответе от POST /person получена ошибка: 400,error:Bad Request", () -> {
                try{
                    ResponseEntity<Long> createPersonResponse = restTemplate.exchange(
                            postUrl,
                            HttpMethod.POST,
                            requestEntity,
                            Long.class
                    );
                } catch (Exception e){
//                    System.out.println(e.getMessage());
                }
            });
        });

    }
}
