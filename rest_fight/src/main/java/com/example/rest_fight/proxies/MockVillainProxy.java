package com.example.rest_fight.proxies;

import com.example.rest_fight.client.Villain;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MockVillainProxy implements Proxyable<Villain> {
    public static final String DEFAULT_VILLAIN_NAME = "DEFAULT_VILLAIN_NAME";
    public static final String DEFAULT_VILLAIN_PICTURE = "DEFAULT_VILLAIN_PICTURE";
    public static final String DEFAULT_VILLAIN_POWERS = "DEFAULT_VILLAIN_POWERS";
    public static final int DEFAULT_VILLAIN_LEVEL = 42;

    @Override
    public Villain getRandom() {
        Villain v = new Villain();
        v.setName(DEFAULT_VILLAIN_NAME);
        v.setPowers(DEFAULT_VILLAIN_POWERS);
        v.setLevel(DEFAULT_VILLAIN_LEVEL);
        v.setPicture(DEFAULT_VILLAIN_PICTURE);
        return v;
    }
}
