package com.project.services.rest_hero.controllers;

import com.project.services.rest_hero.data.Hero;
import com.project.services.rest_hero.services.HeroService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Slf4j
@Tag(name = "Heroes")
@RestController
@RequestMapping("api/heroes")
@OpenAPIDefinition(info = @Info(title = "Hero API",
        description = "This API allows CRUD operations on a hero"))
@CrossOrigin
public class HeroController {
    private final HeroService service;

    public HeroController(HeroService service) {
        this.service = service;
    }

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @GetMapping("/ping")
    @Operation(summary = "Returns \"PING OK\" message")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("PING OK");
    }

    @GetMapping
    @Operation(summary = "Returns all the heroes from the database")
    @ApiResponse(responseCode = "200", content = @Content(
            mediaType = JSON,
            array = @ArraySchema(schema = @Schema(implementation = Hero.class))))
    @ApiResponse(responseCode = "204", description = "No heroes")
    public Mono<List<Hero>> getAllHeroes() {
        return service.findAllHeroes().collectList();
    }

    @GetMapping("/random")
    @Operation(summary = "Returns a random hero")
    @ApiResponse(responseCode = "200", content = @Content(
            mediaType = JSON,
            schema = @Schema(implementation = Hero.class)))
    public Mono<Hero> getRandomHero() {
        return service.findRandomHero().doOnNext(hero -> log.debug("Found random hero " + hero));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Returns a hero for a given identifier")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = JSON, schema = @Schema(implementation = Hero.class)))
    @ApiResponse(responseCode = "204", description = "The Hero is not found for a given identifier")
    public Mono<ResponseEntity<Hero>> getHero(@PathVariable("id") Long id) {
        return service.findHeroById(id).map(hero -> {
            log.debug("Found Hero " + hero);
            return ResponseEntity.ok(hero);
        }).switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping
    @Operation(summary = "Creates a valid hero")
    @ApiResponse(responseCode = "201", description = "The URI of the created Hero", content = @Content(mediaType = JSON, schema = @Schema(implementation = URI.class)))
    public Mono<ResponseEntity<URI>> createHero(@RequestBody @Valid Hero hero, HttpServletRequest request) {
        return service.saveHero(hero).map(h -> {
            var baseUrl = ServletUriComponentsBuilder.fromRequestUri(request);
            UriBuilder builder = baseUrl.pathSegment(Long.toString(h.getId()));
            return ResponseEntity.created(builder.build()).build();
        });
    }

    @PutMapping
    @Operation(summary = "Updates an exiting hero")
    @ApiResponse(responseCode = "200", description = "The updated hero", content = @Content(mediaType = JSON, schema = @Schema(implementation = Hero.class)))
    public Mono<ResponseEntity<Hero>> updateHero(@RequestBody @Valid Hero hero) {
        return service.updateHero(hero).map(h -> {
            log.debug("Hero updated with new valued " + h);
            return ResponseEntity.ok(h);
        });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an exiting hero")
    @ApiResponse(responseCode = "204")
    public Mono<ResponseEntity<Hero>> deleteHero(@PathVariable("id") Long id) {
        return service.deleteHeroById(id).thenReturn(ResponseEntity.noContent().build());
//                .doOnNext(x -> log.info("Hero deleted with " + id))

    }
}
