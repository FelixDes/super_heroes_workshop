package com.project.services.rest_fight.proxies;

import com.project.services.rest_fight.client.Villain;
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
public class VillainProxyRESTTemplate implements Proxyable<Villain> {
    @Value("http://"+"${villain_proxy_random.url}" + "${villain_proxy_random.path}")
    private String url;

    @Override
    public Villain getRandom() {
        ResponseEntity<Villain> responseEntity = new RestTemplate().getForEntity(url, Villain.class);
        return responseEntity.getBody();
    }
}
