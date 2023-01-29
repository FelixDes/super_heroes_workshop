package com.example.rest_villains.health;

import com.example.rest_villains.controllers.VillainController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class PingVillainHealthIndicator implements HealthIndicator {

    private final VillainController villainController;

    @Override
    public Health health() {
        String response = villainController.ping().getBody();
        Health.Builder status = Health.up();

        HashMap<String, String> details = new HashMap<>();
        details.put("name", "Ping Villain REST Endpoint");
        details.put("response", response);

        return status.withDetails(details).build();
    }
}
