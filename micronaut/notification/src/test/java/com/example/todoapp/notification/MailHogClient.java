package com.example.todoapp.notification;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.Map;

@Client("http://${mailhog.host}:${mailhog.port}") // MailHog API URL
public interface MailHogClient {

    @Get("/api/v2/messages")
    MailHogResponse getMessages();

}