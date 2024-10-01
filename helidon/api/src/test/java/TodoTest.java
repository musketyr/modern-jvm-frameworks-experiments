import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.helidon.config.mp.MpConfigSources;
import io.helidon.microprofile.testing.junit5.Configuration;
import io.helidon.microprofile.testing.junit5.HelidonTest;

import com.example.todoapp.model.Todo;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
@Configuration(useExisting = true)
@HelidonTest
public class TodoTest {

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql").withTag("5.7.34"))
            .withExposedPorts(3306)
            .withDatabaseName("todo-db")
            .withUsername("user")
            .withPassword("pass");

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
                        "javax.sql.DataSource.todo-db.dataSource.portNumber", mysql.getMappedPort(3306).toString()
                )))
                .build();
        cr.registerConfig(c, Thread.currentThread().getContextClassLoader());
    }

    @Test
    void name(WebTarget target) {
        LocalDate referenceDate = LocalDate.now();

        List<Todo> todos =
                List.of(Todo.of("Buy milk", referenceDate.minusDays(1).atTime(12, 0, 0)),
                        Todo.of("Buy bread", referenceDate.plusDays(1).atTime(12, 0, 0)),
                        Todo.of("Relax", referenceDate.atTime(12, 0, 0)));

        for (Todo todo : todos) {
            try (Response response = target
                    .path("/todos")
                    .request(APPLICATION_JSON_TYPE)
                    .post(Entity.entity(todo, APPLICATION_JSON_TYPE))) {
                assertThat(response.getStatus(), Matchers.is(200));
            }
        }

        try (Response response = target
                .path("/todos")
                .request(APPLICATION_JSON_TYPE)
                .get()) {

            Map<String, Todo> result = response.readEntity(new GenericType<List<Todo>>() { }).stream()
                    .collect(Collectors.toMap(Todo::getTask, Function.identity()));

            assertThat(result.keySet(), Matchers.containsInAnyOrder("Buy milk", "Buy bread", "Relax"));
        }

    }
}
