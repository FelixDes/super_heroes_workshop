package com.example.rest_fight.proxies;

import com.example.rest_fight.client.Villain;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${villain_proxy_random.name}", url = "${villain_proxy_random.url}")
public interface VillainProxy extends Proxyable<Villain> {
    @GetMapping("${villain_proxy_random.path}")
    Villain getRandom();
}
