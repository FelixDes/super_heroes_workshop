package com.example.rest_fight.health;

import com.example.rest_fight.controllers.FightController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class PingFightHealthIndicator implements HealthIndicator {

    private final FightController fightController;

    @Override
    public Health health() {
        String response = fightController.ping().getBody();
        Health.Builder status = Health.up();

        HashMap<String, String> details = new HashMap<>();
        details.put("name", "Ping Fight REST Endpoint");
        details.put("response", response);

        return status.withDetails(details).build();
    }
}
