//package com.project.services.statistics.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//
//import static org.springframework.messaging.simp.SimpMessageType.*;
//
//@Configuration
//public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//
////    @Bean
////    AuthorizationManager<Message<?>> authorizationManager(Builder messages) {
////        messages.simpDestMatchers("/user/queue/errors").permitAll()
////                .simpDestMatchers("/**").hasRole("ADMIN")
////                .anyMessage().authenticated();
////        return messages.build();
////    }
//
//    @Override
//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//        messages.simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT, HEARTBEAT).permitAll()
//                .simpDestMatchers("/**").hasRole("APP_STATS")
//                .simpSubscribeDestMatchers("/**").hasRole("APP_STATS")
//                .anyMessage().denyAll();
//    }
//
//    @Override
//    protected boolean sameOriginDisabled() {
//        return true;
//    }
//}