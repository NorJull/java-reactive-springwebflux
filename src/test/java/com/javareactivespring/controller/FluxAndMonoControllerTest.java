package com.javareactivespring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxTest01() {
        Flux<Integer> fluxInteger = webTestClient.get().uri("/flux")
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .returnResult(Integer.class)
          .getResponseBody();

        StepVerifier.create(fluxInteger)
          .expectSubscription()
          .expectNext(1)
          .expectNext(2)
          .expectNext(3)
          .expectNext(4)
          .verifyComplete();
    }

    @Test
    public void fluxTest02() {
        webTestClient.get().uri("/flux")
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
          .expectBodyList(Integer.class)
          .hasSize(4);
    }

    @Test
    public void fluxTest03() {

        List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4);

        EntityExchangeResult<List<Integer>> result = webTestClient.get().uri("/flux")
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
          .expectBodyList(Integer.class)
          .returnResult();

        assertEquals(expectedResult, result.getResponseBody());
    }

    @Test
    public void fluxTest04() {

        List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4);

        webTestClient.get().uri("/flux")
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
          .expectBodyList(Integer.class)
          .consumeWith(listEntityExchangeResult -> assertEquals(expectedResult, listEntityExchangeResult.getResponseBody()));
    }

}
