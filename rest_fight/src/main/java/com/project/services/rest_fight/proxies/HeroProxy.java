package com.project.services.rest_fight.proxies;

import com.project.services.rest_fight.client.Hero;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "hero-proxy", url = "${hero_proxy_random.url}", fallback = HeroProxy.HeroProxyFallback.class)
public interface HeroProxy extends Proxyable<Hero> {

    @GetMapping("${hero_proxy_random.path}")
    Hero getRandom();

    @Component
    class HeroProxyFallback implements HeroProxy  {

        @Override
        public Hero getRandom() {
            Hero hero = new Hero();
            hero.setName("Fallback hero");
            hero.setPicture("https://dummyimage.com/280x380/1e8fff/ffffff&text=Fallback+Hero");
            hero.setPowers("Fallback hero powers");
            hero.setLevel(1);
            return hero;
        }
    }
}

