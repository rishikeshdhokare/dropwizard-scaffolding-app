package sample.route;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.*;

public class IndexRouteTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new IndexRoute())
            .build();

    @Test
    public void shouldPingTheApp() {
        // when
        Response response = getTarget("ping").request().get(Response.class);

        // then
        assertEquals(200, response.getStatus());
        Map<String, String> responseBody = response.readEntity(Map.class);
        assertEquals("Inventory integration between LiveDooh and AIDA", responseBody.get("description"));
    }

    private WebTarget getTarget(String path) {
        String uri = String.format("http://localhost:%d/%s", 7013, path);
        return resources.client().target(uri);
    }
}