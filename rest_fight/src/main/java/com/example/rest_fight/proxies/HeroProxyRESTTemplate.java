package com.example.rest_fight.proxies;

import com.example.rest_fight.client.Hero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class HeroProxyRESTTemplate implements Proxyable<Hero> {
    @Value("http://"+"${hero_proxy_random.url}" + "${hero_proxy_random.path}")
    private String url;

    @Override
    public Hero getRandom() {
        ResponseEntity<Hero> responseEntity = new RestTemplate().getForEntity(url, Hero.class);
        return responseEntity.getBody();
    }

    public Hero fallbackRandomHero() {
        Hero hero = new Hero();
        hero.setName("Fallback hero");
        hero.setPicture("https://dummyimage.com/280x380/1e8fff/ffffff&text=Fallback+Hero");
        hero.setPowers("Fallback hero powers");
        hero.setLevel(1);
        return hero;
    }
}
