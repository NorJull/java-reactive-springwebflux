package com.javareactivespring;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> fluxString = Flux.just("Naren", "David", "De Avila", "Julio");

        fluxString.subscribe(System.out::println);
    }

    @Test
    public void onErrorFluxTest() {
        Flux<String> fluxString = Flux.just("Naren", "David", "De Avila", "Julio")
          .concatWith(Flux.error(new NullPointerException("Error!")));

        fluxString.subscribe(System.out::println, throwable -> {
            System.out.println("Todo este bloque corresponde al manejo del error: ");
            System.out.println(throwable.getMessage());
        });
    }

    @Test
    public void logFluxTest() {
        Flux<String> fluxString = Flux.just("Naren", "David", "De Avila", "Julio").map(String::toUpperCase)
          .log();

        fluxString.subscribe(System.out::println, throwable -> {
            System.out.println("Todo este bloque corresponde al manejo del error: ");
            System.out.println(throwable.getMessage());
        });
    }


    @Test
    public void eventAfterErrorTest() {
        Flux<String> fluxString = Flux.just("Universidad", "De", "Cartagena")
          .concatWith(Flux.error(new Exception("Something went wrong!")))
          .concatWith(Flux.just("After Error!"))
          .log();

        fluxString.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void onCompleteFluxTest() {
        Flux<String> fluxString = Flux.just("Universidad", "De", "Cartagena");

        fluxString.subscribe(System.out::println, System.out::println, () -> System.out.println("To do this after the onComplete event"));

    }

    @Test
    public void usingStepVerifierTest(){
        Flux<String> fluxString = Flux.just("Universidad", "De", "Cartagena").log();

        StepVerifier.create(fluxString)
          .expectNext("Universidad")
          .expectNext("De")
          .expectNext("Cartagena")
          .verifyComplete();
    }

    @Test
    public void usingStepVerifierWithErrorTest(){
        Flux<String> fluxString = Flux.just("Universidad", "De", "Cartagena")
          .concatWith(Flux.error(new NullPointerException("Error!")))
          .log();

        StepVerifier.create(fluxString)
          .expectNext("Universidad")
          .expectNext("De")
          .expectNext("Cartagena")
          .expectError(NullPointerException.class)
          .verify();
    }
    @Test
    public void usingStepVerifierWithCountTest(){
        Flux<String> fluxString = Flux.just("Universidad", "De", "Cartagena")
          .concatWith(Flux.error(new NullPointerException("Error!")))
          .log();

        StepVerifier.create(fluxString)
          .expectNextCount(3)
          .expectError(NullPointerException.class)
          .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> monoString = Mono.just("TheBig").log();
        StepVerifier.create(monoString)
          .expectNext("TheBig")
          .verifyComplete();
    }


}
