package com.project.services.rest_fight.controllers;

import com.project.services.rest_fight.data.Fight;
import com.project.services.rest_fight.data.Fighters;
import com.project.services.rest_fight.services.FightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/fights")
@CrossOrigin
public class FightController {
    private final FightService service;

    public FightController(FightService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    @Operation(summary = "Returns \"PING OK\" message")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("PING OK");
    }

    @GetMapping("/randomfighters")
    public ResponseEntity<Fighters> getRandomFighters(@AuthenticationPrincipal Jwt principal) {
        Fighters fighters = service.findRandomFighters();
        log.debug("Get random fighters " + fighters);
        return ResponseEntity.ok(fighters);
    }

    @GetMapping
    public ResponseEntity<List<Fight>> getAllFights() {
        List<Fight> fights = service.findAllFights();
        log.debug("Total number of fights " + fights);
        return ResponseEntity.ok(fights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fight> getFight(@PathVariable Long id) {
        Fight fight = service.findFightById(id);
        if (fight != null) {
            log.debug("Found fight " + fight);
            return ResponseEntity.ok(fight);
        } else {
            log.debug("No fight found with id " + id);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public Fight fight(@Valid @RequestBody Fighters fighters) {
//    public Fight fight(@Valid Fighters fighters, BindingResult bindingResult) {
        return service.persistFight(fighters);
    }
}
