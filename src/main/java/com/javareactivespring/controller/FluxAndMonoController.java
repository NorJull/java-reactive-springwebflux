package com.javareactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> returnAFlux(){
        return Flux.just(1, 2, 3, 4).log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnAFluxStream(){
        return Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(1)).log();
    }
}
