package sample.route;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import sample.Main;
import sample.config.AppConfiguration;

import javax.ws.rs.core.Response;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.junit.Assert.assertEquals;

public class IndexRouteTest {

    private JerseyClient client;

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE = new DropwizardAppRule<>(Main.class, resourceFilePath("app-config-for-test.yml"));

    @Before
    public void setUp() {
        client = new JerseyClientBuilder().build();
    }

    @Test
    public void shouldPingTheApp() {
        String uri = String.format("http://localhost:%d/ping", RULE.getLocalPort());
        Response response = client.target(uri).request().get();
        assertEquals(200, response.getStatus());
        assertEquals("pong", response.readEntity(String.class));
    }
}