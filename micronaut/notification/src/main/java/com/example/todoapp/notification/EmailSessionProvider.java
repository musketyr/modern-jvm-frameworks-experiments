package com.example.todoapp.notification;

import io.micronaut.email.javamail.sender.SessionProvider;
import jakarta.inject.Singleton;
import jakarta.mail.Session;

import java.util.Properties;

@Singleton
public class EmailSessionProvider implements SessionProvider {

    private final EmailServerConfiguration config;

    public EmailSessionProvider(EmailServerConfiguration config) {
        this.config = config;
    }

    @Override
    public Session session() {
        var properties = new Properties();
        properties.put("mail.smtp.host", config.getHost());
        properties.put("mail.smtp.port", config.getPort());
        return Session.getDefaultInstance(properties);
    }

}