package com.example.rest_fight.proxies;

import com.example.rest_fight.client.Hero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="${hero_proxy_random.name}", url="${hero_proxy_random.url}")
public interface HeroProxy extends Proxyable<Hero> {

    @GetMapping("${hero_proxy_random.path}")
    Hero getRandom();
}

