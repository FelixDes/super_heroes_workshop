package com.example.rest_villains.controllers;

import com.example.rest_villains.data.Villain;
import com.example.rest_villains.services.VillainService;
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

import java.net.URI;
import java.util.List;

@Slf4j
@Tag(name = "Villains")
@RestController
@RequestMapping("api/villains")
@OpenAPIDefinition(info = @Info(title = "Villain API",
        description = "This API allows CRUD operations on a villain"))
public class VillainController {
    private final VillainService service;

    public VillainController(VillainService service) {
        this.service = service;
    }

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @GetMapping
    @Operation(summary = "Returns all the villains from the database")
    @ApiResponse(responseCode = "200", content = @Content(
            mediaType = JSON,
            array = @ArraySchema(schema = @Schema(implementation = Villain.class))))
    @ApiResponse(responseCode = "204", description = "No villains")
    public ResponseEntity<List<Villain>> getAllVillains() {
        List<Villain> villains = service.findAllVillains();
        log.debug("Total number of villains " + villains);
        return ResponseEntity.ok(villains);
    }

    @GetMapping("/random")
    @Operation(summary = "Returns a random villain")
    @ApiResponse(responseCode = "200", content = @Content(
            mediaType = JSON,
            schema = @Schema(implementation = Villain.class)))
    public ResponseEntity<Villain> getRandomVillain() {
        Villain villain = service.findRandomVillain();
        log.debug("Found random villain " + villain);
        return ResponseEntity.ok(villain);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a villain for a given identifier")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = JSON, schema = @Schema(implementation = Villain.class)))
    @ApiResponse(responseCode = "204", description = "The villain is not found for a given identifier")
    public ResponseEntity<Villain> getVillain(@PathVariable("id") Long id) {
        Villain villain = service.findVillainById(id);
        if (villain != null) {
            log.debug("Found villain " + villain);
            return ResponseEntity.ok(villain);
        } else {
            log.debug("No villain found with id " + id);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @Operation(summary = "Creates a valid villain")
    @ApiResponse(responseCode = "201", description = "The URI of the created villain", content = @Content(mediaType = JSON, schema = @Schema(implementation = URI.class)))
    public ResponseEntity<URI> createVillain(@RequestBody @Valid Villain villain, HttpServletRequest request) {
        var baseUrl = ServletUriComponentsBuilder.fromRequestUri(request);

        villain = service.saveVillain(villain);
        UriBuilder builder = baseUrl.pathSegment(Long.toString(villain.getId()));
        log.debug("New villain created with URI " + builder.build());
        return ResponseEntity.created(builder.build()).build();
    }

    @PutMapping
    @Operation(summary = "Updates an exiting  villain")
    @ApiResponse(responseCode = "200", description = "The updated villain", content = @Content(mediaType = JSON, schema = @Schema(implementation = Villain.class)))
    public ResponseEntity<Villain> updateVillain(@RequestBody @Valid Villain villain) {
        villain = service.updateVillain(villain);
        log.debug("Villain updated with new valued " + villain);
        return ResponseEntity.ok(villain);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an exiting villain")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Villain> deleteVillain(@PathVariable("id") Long id) {
        service.deleteVillainById(id);
        log.debug("Villain deleted with " + id);
        return ResponseEntity.noContent().build();
    }
}
