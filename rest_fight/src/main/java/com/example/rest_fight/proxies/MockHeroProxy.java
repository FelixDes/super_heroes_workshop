package com.example.rest_fight.proxies;

import com.example.rest_fight.client.Hero;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MockHeroProxy implements Proxyable<Hero> {
    public static final String DEFAULT_HERO_NAME = "DEFAULT_HERO_NAMEe";
    public static final String DEFAULT_HERO_PICTURE = "DEFAULT_HERO_PICTURE";
    public static final String DEFAULT_HERO_POWERS = "DEFAULT_HERO_POWERS";
    public static final int DEFAULT_HERO_LEVEL = 42;

    @Override
    public Hero getRandom() {
        Hero h = new Hero();
        h.setName(DEFAULT_HERO_NAME);
        h.setPowers(DEFAULT_HERO_POWERS);
        h.setLevel(DEFAULT_HERO_LEVEL);
        h.setPicture(DEFAULT_HERO_PICTURE);
        return h;
    }
}
