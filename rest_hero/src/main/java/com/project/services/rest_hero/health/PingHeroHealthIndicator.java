package com.project.services.rest_hero.health;

import com.project.services.rest_hero.controllers.HeroController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class PingHeroHealthIndicator implements HealthIndicator {

    private final HeroController heroController;

    @Override
    public Health health() {
        String response = heroController.ping().getBody();
        Health.Builder status = Health.up();

        HashMap<String, String> details = new HashMap<>();
        details.put("name", "Ping Hero REST Endpoint");
        details.put("response", response);

        return status.withDetails(details).build();
    }
}
