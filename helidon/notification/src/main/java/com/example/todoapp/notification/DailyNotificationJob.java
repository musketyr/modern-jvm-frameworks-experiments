package com.example.todoapp.notification;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import io.helidon.common.LazyValue;
import io.helidon.config.Config;
import io.helidon.microprofile.scheduling.Scheduled;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.TodoService;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static jakarta.mail.Message.RecipientType.TO;
import static java.lang.System.Logger.Level.INFO;

@ApplicationScoped
public class DailyNotificationJob {

    private static final System.Logger LOGGER = System.getLogger(DailyNotificationJob.class.getName());
    private static final LazyValue<Template> TEMPLATE = LazyValue.create(() -> {
        try {
            return new Handlebars().compile("/templates/todoReminder");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    });

    @Inject
    @ConfigProperty(name = "mail.username")
    Optional<String> username;

    @Inject
    @ConfigProperty(name = "mail.password")
    Optional<String> password;

    @Inject
    private TodoService todoService;

    @Scheduled("0 0 8 * * ?") // Every day at 8 AM
    @Transactional
    void checkTodosAndSendNotifications() throws MessagingException, IOException {
        List<Todo> todos = todoService.findAllToday();
        if (!todos.isEmpty()) {
            Message msg = new MimeMessage(getSession());
            msg.setFrom(new InternetAddress("admin@example.net"));
            msg.setRecipients(TO, InternetAddress.parse("user@example.com"));
            msg.setSubject("Reminder: Tasks Due Today");
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(TEMPLATE.get().apply(Map.of("todos", todos)), "text/html; charset=utf-8");
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            msg.setContent(multipart);
            Transport.send(msg);

            LOGGER.log(INFO, "Daily notification sent for %s todos", todos.size());
        }
    }

    private Session getSession() {
        Properties prop = new Properties();
        prop.putAll(((Config) ConfigProvider.getConfig()).get("mail").asMap().get());
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username.orElse(null), password.orElse(null));
            }
        });
    }
}