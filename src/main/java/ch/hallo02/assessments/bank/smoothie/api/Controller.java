package ch.hallo02.assessments.bank.smoothie.api;

import ch.hallo02.assessments.bank.smoothie.domain.SmoothieService;
import ch.hallo02.assessments.bank.smoothie.model.Smoothie;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class Controller {

    private final SmoothieService smoothieService;

    public Controller(
            SmoothieService smoothieService
    ) {
        this.smoothieService = smoothieService;
    }

    @GetMapping
    public Flux<Smoothie> list() {
        return Flux.fromIterable(smoothieService.getAll());
    }

    @GetMapping("/{id}")
    public Mono<Smoothie> get(
            @PathVariable String id
    ) {
        return smoothieService.getById(id)
                .map(Mono::just)
                .orElseGet(
                        () -> Mono.error(new RuntimeException("Smoothie not found"))
                );
    }

    @PutMapping
    public void update(
            @RequestBody Smoothie smoothie
    ) {
        System.out.println(smoothie);
        this.smoothieService.update(smoothie);
    }

    @DeleteMapping
    public void delete(
            @RequestBody Smoothie smoothie
    ){
        this.smoothieService.delete(smoothie);
    }


}
