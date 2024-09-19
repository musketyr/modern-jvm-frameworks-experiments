package com.example.todoapp.notification;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("smtp")
public interface EmailServerConfiguration {
    String getHost();
    int getPort();
}