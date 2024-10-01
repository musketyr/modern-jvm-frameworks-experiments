package com.example.todoapp.notification;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.helidon.config.mp.MpConfigSources;
import io.helidon.microprofile.testing.junit5.Configuration;
import io.helidon.microprofile.testing.junit5.HelidonTest;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.TodoService;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
@HelidonTest
@Configuration(useExisting = true)
class DailyNotificationJobTest {

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql").withTag("5.7.34"))
            .withDatabaseName("todo-db")
            .withUsername("user")
            .withPassword("pass");

    @Container
    static final GenericContainer<?> mailpit = new GenericContainer<>("axllent/mailpit:v1.15")
            .withExposedPorts(1025, 8025)
            .waitingFor(Wait.forLogMessage(".*accessible via.*", 1));

    private final WebTarget mailpitTarget = ClientBuilder.newClient()
            .target(UriBuilder.newInstance()
                            .scheme("http")
                            .host(mailpit.getHost())
                            .port(mailpit.getMappedPort(8025))
                            .path("/api/v1")
                            .build());
    @Inject
    DailyNotificationJob dailyNotificationJob;

    @Inject
    private TodoService todoService;

    @BeforeAll
    static void init() {
        ConfigProviderResolver cr = ConfigProviderResolver.instance();
        Config c = cr.getBuilder()
                .addDefaultSources()
                .addDiscoveredSources()
                .addDiscoveredConverters()
                .withSources(MpConfigSources.create(Map.of(
                        "config_ordinal", "205",
                        "mp.initializer.allow", "true",
                        "javax.sql.DataSource.todo-db.dataSource.portNumber", mysql.getMappedPort(3306).toString(),
                        "mail.smtp.host", mailpit.getHost(),
                        "mail.kec.host", mailpit.getHost(),
                        "mail.smtp.port", mailpit.getMappedPort(1025).toString()
                )))
                .build();
        cr.registerConfig(c, Thread.currentThread().getContextClassLoader());
    }

    @Test
    @Transactional
    void testCheckTodosAndSendNotifications() throws MessagingException, IOException {
        LocalDateTime expectedDate = LocalDateTime.now();

        todoService.create(Todo.of("Pass test", expectedDate));

        // Call the method to test
        dailyNotificationJob.checkTodosAndSendNotifications();

        Mail mail = firstReceivedEmail("user@example.com");
        assertThat(mail.Text(), containsString("Pass test - *Due Date:* " + expectedDate));
        assertThat(mail.Subject(), is("Reminder: Tasks Due Today"));
    }

    Mail firstReceivedEmail(String from) {
        try (var mailboxResponse = mailpitTarget
                .path("/messages")
                .queryParam("query", "from:" + from)
                .request()
                .get()) {
            MailBox mailBox = mailboxResponse.readEntity(MailBox.class);
            try (var mailResponse = mailpitTarget
                    .path("/message/{id}")
                    .resolveTemplate("id", mailBox.messages().getFirst().ID())
                    .request()
                    .get()) {
                return mailResponse.readEntity(Mail.class);
            }
        }
    }

    public record Mail(String ID, String Subject, String Text) {};
    public record MailBox(List<Mail> messages) {};
}